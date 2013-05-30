package at.ac.tuwien.digitalpreservation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.virtualbox_4_2.IConsole;
import org.virtualbox_4_2.IGuest;
import org.virtualbox_4_2.IGuestSession;
import org.virtualbox_4_2.IMachine;
import org.virtualbox_4_2.IProgress;
import org.virtualbox_4_2.ISession;
import org.virtualbox_4_2.IVirtualBox;
import org.virtualbox_4_2.VBoxException;
import org.virtualbox_4_2.VirtualBoxManager;

public class VirtualMachine {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(VirtualMachine.class);

	public static final String WEBSERVICE_URL = "http://localhost:18083/";

	// ----------------- Parameter section
	private String webserviceUrl;

	private String machineName;

	// ----------------- Variables section
	private VirtualBoxManager manager;

	private IVirtualBox virtualBox;

	private IMachine machine;

	private ISession session;

	private IConsole console;

	private IGuestSession guestSession;

	public VirtualMachine(String machineName) {
		this(WEBSERVICE_URL, machineName);
	}

	public VirtualMachine(String webserviceUrl, String machineName) {
		this.webserviceUrl = webserviceUrl;
		this.machineName = machineName;
	}

	public void init() {
		this.manager = VirtualBoxManager.createInstance(null);
		try {
			this.manager.connect(this.webserviceUrl, null, null);
		} catch (VBoxException e) {
			LOGGER.error(
					"Could not connect to virtual box manager, start webserver fist",
					e);
			return;
		}

		this.virtualBox = this.manager.getVBox();
		for (IMachine machine : this.virtualBox.getMachines()) {
			if (this.machineName.equals(machine.getName())) {
				this.machine = machine;
				break;
			}
		}
		if (this.machine == null) {
			throw new IllegalStateException("Machine " + this.machineName
					+ " not found");
		}

		this.session = this.manager.getSessionObject();
		IProgress progress = this.machine.launchVMProcess(this.session, "gui",
				"");
		progress.waitForCompletion(30000);
		if (progress.getResultCode() != 0) {// check success
			LOGGER.error("could not launch virtual machine");
			return;
		}

		try {
			Thread.sleep(30000);
		} catch (InterruptedException e) {
		}

		this.console = this.session.getConsole();
		IGuest guest = this.console.getGuest();
		this.guestSession = guest.createSession("test", "test", "",
				"testSession");
	}

	public void destroy() {
		if (this.guestSession != null) {
			this.guestSession.close();
		}
		if (this.console != null) {
			IProgress progress = this.console.powerDown();
			progress.waitForCompletion(30000);
			if (progress.getResultCode() != 0) {
				LOGGER.error("problems with shutting down the virtual machine");
			}
		}
		if (this.manager != null) {
//			if (this.session != null) {
//				this.manager.closeMachineSession(this.session);
//			}
			this.manager.disconnect();
			this.manager.cleanup();
		}

		this.guestSession = null;
		this.console = null;
		this.session = null;
		this.machine = null;
		this.virtualBox = null;
		this.manager = null;
	}
}
