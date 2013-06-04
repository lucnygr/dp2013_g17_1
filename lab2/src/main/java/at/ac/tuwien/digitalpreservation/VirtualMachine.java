package at.ac.tuwien.digitalpreservation;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.virtualbox_4_2.Holder;
import org.virtualbox_4_2.IConsole;
import org.virtualbox_4_2.IDisplay;
import org.virtualbox_4_2.IMachine;
import org.virtualbox_4_2.IProgress;
import org.virtualbox_4_2.ISession;
import org.virtualbox_4_2.IVirtualBox;
import org.virtualbox_4_2.MachineState;
import org.virtualbox_4_2.VBoxException;
import org.virtualbox_4_2.VirtualBoxManager;

import at.ac.tuwien.digitalpreservation.handler.KeyboardEventHandler;
import at.ac.tuwien.digitalpreservation.handler.MouseEventHandler;

public class VirtualMachine {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(VirtualMachine.class);

	public static final String WEBSERVICE_URL = "http://localhost:18083/";

	// ----------------- Parameter section
	private String webserviceUrl;

	private String machineName;

	private String username;

	private String password;

	// ----------------- Variables section
	private VirtualBoxManager manager;

	private IVirtualBox virtualBox;

	private IMachine machine;

	private ISession session;

	private IConsole console;

	// ----------------- Event Thread section
	private KeyboardEventThread keyboardEventThread;

	private MouseEventThread mouseEventThread;

	public VirtualMachine(String machineName, String username, String password) {
		this(WEBSERVICE_URL, machineName, username, password);
	}

	public VirtualMachine(String webserviceUrl, String machineName,
			String username, String password) {
		this.webserviceUrl = webserviceUrl;
		this.machineName = machineName;
		this.username = username;
		this.password = password;
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
		if (this.machine.getState().equals(MachineState.Running)
				|| this.machine.getState().equals(MachineState.Paused)) {
			this.machine.launchVMProcess(this.manager.getSessionObject(),
					"emergencystop", null);
		}
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

		this.keyboardEventThread = new KeyboardEventThread(this.console);
		this.keyboardEventThread.start();

		this.mouseEventThread = new MouseEventThread(this.console);
		this.mouseEventThread.start();
	}

	public void destroy() {
		if (this.mouseEventThread != null) {
			this.mouseEventThread.close();
		}
		if (this.keyboardEventThread != null) {
			this.keyboardEventThread.close();
		}
		if (this.console != null) {
			IProgress progress = this.console.powerDown();
			progress.waitForCompletion(30000);
			if (progress.getResultCode() != 0) {
				LOGGER.error("problems with shutting down the virtual machine");
			}
		}
		if (this.manager != null) {
			// if (this.session != null) {
			// this.manager.closeMachineSession(this.session);
			// }
			this.manager.disconnect();
			this.manager.cleanup();
		}

		this.console = null;
		this.session = null;
		this.machine = null;
		this.virtualBox = null;
		this.manager = null;
	}

	public boolean takeScreenShot(String filepath) {
		boolean returnvalue = true;
		IDisplay d = this.console.getDisplay();

		Holder<Long> width = new Holder<Long>();
		Holder<Long> height = new Holder<Long>();
		Holder<Long> bitsPerPixel = new Holder<Long>();

		d.getScreenResolution(Long.valueOf(0), width, height, bitsPerPixel);
		byte[] image = d.takeScreenShotPNGToArray(Long.valueOf(0), width.value,
				height.value);
		FileOutputStream fos;
		try {
			fos = new FileOutputStream("image.png");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		}
		try {
			fos.write(image);
		} catch (IOException e) {
			e.printStackTrace();
			returnvalue = false;
		}
		try {
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return returnvalue;
	}

	public void addKeyboardEventHandler(KeyboardEventHandler handler) {
		this.keyboardEventThread.addKeyboardEventHandler(handler);
	}

	public void removeKeyboardEventHandler(KeyboardEventHandler handler) {
		this.keyboardEventThread.removeKeyboardEventHandler(handler);
	}

	public void addMouseEventHandler(MouseEventHandler handler) {
		this.mouseEventThread.addMouseEventHandler(handler);
	}

	public void removeMouseEventHandler(MouseEventHandler handler) {
		this.mouseEventThread.removeMouseEventHandler(handler);
	}
}
