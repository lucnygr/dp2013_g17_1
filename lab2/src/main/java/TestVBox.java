/* $Id: TestVBox.java 74251 2011-09-30 10:12:01Z klaus $ */

/* Small sample/testcase which demonstrates that the same source code can
 * be used to connect to the webservice and (XP)COM APIs. */

/*
 * Copyright (C) 2010-2011 Oracle Corporation
 *
 * This file is part of VirtualBox Open Source Edition (OSE), as
 * available from http://www.virtualbox.org. This file is free software;
 * you can redistribute it and/or modify it under the terms of the GNU
 * General Public License (GPL) as published by the Free Software
 * Foundation, in version 2 as it comes in the "COPYING" file of the
 * VirtualBox OSE distribution. VirtualBox OSE is distributed in the
 * hope that it will be useful, but WITHOUT ANY WARRANTY of any kind.
 */
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.virtualbox_4_2.CPUPropertyType;
import org.virtualbox_4_2.HWVirtExPropertyType;
import org.virtualbox_4_2.IEvent;
import org.virtualbox_4_2.IEventListener;
import org.virtualbox_4_2.IEventSource;
import org.virtualbox_4_2.IGuestPropertyChangedEvent;
import org.virtualbox_4_2.IMachine;
import org.virtualbox_4_2.IMachineStateChangedEvent;
import org.virtualbox_4_2.ISessionStateChangedEvent;
import org.virtualbox_4_2.IVirtualBox;
import org.virtualbox_4_2.LockType;
import org.virtualbox_4_2.MachineState;
import org.virtualbox_4_2.VBoxEventType;
import org.virtualbox_4_2.VBoxException;
import org.virtualbox_4_2.VirtualBoxManager;
import org.virtualbox_4_2.jaxws.SessionState;

public class TestVBox {
	
	static class EventHandler extends Thread {
		private VirtualBoxManager mgr = null;
		private IEventSource es = null;
		private IEventListener listener = null;
		private boolean running = true;
		
		EventHandler(VirtualBoxManager mgr, IEventSource es) {
			this.mgr = mgr;
			this.es = es;
			listener = this.es.createListener();
			es.registerListener(listener, Arrays.asList(VBoxEventType.Any), false);
			System.out.println("EventHandler initialized");
		}
		public void close() {
			this.running = false;
			System.out.println("Closing EventHandler");
		}
		public void run() {
			System.out.println("Starting EventHandler");
			while(running) {
				IEvent ev = es.getEvent(listener, 500);
				if (ev != null) {
					handleEvent(ev);
					es.eventProcessed(listener, ev);
				}
			}
			this.es.unregisterListener(listener);
		}
		private void handleEvent(IEvent ev) {
			System.out.println("got event: " + ev.getWrapped());
			VBoxEventType type = ev.getType();
			System.out.println("type = " + type);
			switch (type) {
			case OnMachineStateChanged: {
				IMachineStateChangedEvent mcse = IMachineStateChangedEvent
						.queryInterface(ev);
				if (mcse == null)
					System.out.println("Cannot query an interface");
				else
					System.out.println("mid=" + mcse.getMachineId());
					System.out.println("State=" + mcse.getState().toString());
				break;
			}
			case OnSessionStateChanged: {
				ISessionStateChangedEvent cev = ISessionStateChangedEvent.queryInterface(ev);
				System.out.println(cev.getState().toString());
			}
			
			case OnGuestPropertyChanged: {
				IGuestPropertyChangedEvent cev = IGuestPropertyChangedEvent.queryInterface(ev);
				System.out.println(cev.getName());
			}
			default:
				System.out.println("type: default");
				break;
			}
		}
	}
	
	public static EventHandler eventHandler = null;


	static void testEnumeration(VirtualBoxManager mgr, IVirtualBox vbox) {
		List<IMachine> machs = vbox.getMachines();
		for (IMachine m : machs) {
			String name;
			Long ram = 0L;
			boolean hwvirtEnabled = false, hwvirtNestedPaging = false;
			boolean paeEnabled = false;
			boolean inaccessible = false;
			try {
				name = m.getName();
				ram = m.getMemorySize();
				hwvirtEnabled = m
						.getHWVirtExProperty(HWVirtExPropertyType.Enabled);
				hwvirtNestedPaging = m
						.getHWVirtExProperty(HWVirtExPropertyType.NestedPaging);
				paeEnabled = m.getCPUProperty(CPUPropertyType.PAE);
			} catch (VBoxException e) {
				name = "<inaccessible>";
				inaccessible = true;
			}
			System.out.println("VM name: " + name);
			if (!inaccessible) {
				System.out.println(" RAM size: " + ram + "MB" + ", HWVirt: "
						+ hwvirtEnabled + ", Nested Paging: "
						+ hwvirtNestedPaging + ", PAE: " + paeEnabled);
			}
		}
	}

	/**
	 * Starts the first Machine in VirtualBox
	 * @param mgr
	 * @param vbox
	 */
	static void testStart(VirtualBoxManager mgr, IVirtualBox vbox) {
		String m = vbox.getMachines().get(0).getName();
		System.out.println("\nAttempting to start VM '" + m + "'");
		if (!mgr.getVBox().getMachines().get(0).getState().equals(MachineState.Running)) {
			//mgr.startVm(m, null, 7000);
			mgr.getVBox().getMachines().get(0).lockMachine(mgr.getSessionObject(), LockType.Write);
			System.out.println("Press Enter to start");
			try {
				System.in.read();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			mgr.getSessionObject().getConsole().powerUp();
		}
		
		
		//mgr.getSessionObject().unlockMachine();
	}


	

	public static void main(String[] args) {
		VirtualBoxManager mgr = VirtualBoxManager.createInstance(null);
		
		boolean ws = false;
		String url = null;
		String user = null;
		String passwd = null;

		for (int i = 0; i < args.length; i++) {
			if ("-w".equals(args[i]))
				ws = true;
			else if ("-url".equals(args[i]))
				url = args[++i];
			else if ("-user".equals(args[i]))
				user = args[++i];
			else if ("-passwd".equals(args[i]))
				passwd = args[++i];
		}
		
		System.out.println("-w:"+ws+" url:"+url+" user:"+user+" passwd:"+passwd);
		
		if (ws) {
			try {
				mgr.connect(url, user, passwd);
			} catch (VBoxException e) {
				e.printStackTrace();
				System.out.println("Cannot connect, start webserver first!");
			}
		}
		
		try {
			IVirtualBox vbox = mgr.getVBox();
			if (vbox != null) {
				System.out.println("VirtualBox version: " + vbox.getVersion()
						+ "\n");
				//testEnumeration(mgr, vbox);
				//testReadLog(mgr, vbox);
				testStart(mgr, vbox);
				System.out.println("Box started");
				eventHandler = new EventHandler(mgr, vbox.getEventSource());
				System.out.println("EventHandler created");
				eventHandler.start();
				System.out.println("EventHandler started");
				System.out.println("Press Enter to stop");
				System.in.read();
			
				System.out.println("Stopping EventHandler");
				eventHandler.close();
				System.out.println("done, press Enter...");
				System.in.read();
			}
		} catch (VBoxException e) {
			System.out.println("VBox error: " + e.getMessage() + " original="
					+ e.getWrapped());
			e.printStackTrace();
		} catch (java.io.IOException e) {
			e.printStackTrace();
		}
		
		if (ws) {
			try {
				mgr.disconnect();
			} catch (VBoxException e) {
				e.printStackTrace();
			}
		}

		mgr.cleanup();
		System.out.println("Exit");
	}
	static void testMultiServer() {
		VirtualBoxManager mgr1 = VirtualBoxManager.createInstance(null);
		VirtualBoxManager mgr2 = VirtualBoxManager.createInstance(null);

		try {
			mgr1.connect("http://i7:18083", "", "");
			mgr2.connect("http://main:18083", "", "");

			String mach1 = mgr1.getVBox().getMachines().get(0).getName();
			String mach2 = mgr2.getVBox().getMachines().get(0).getName();

			mgr1.startVm(mach1, null, 7000);
			mgr2.startVm(mach2, null, 7000);
		} finally {
			mgr1.cleanup();
			mgr2.cleanup();
		}
	}

	static void testReadLog(VirtualBoxManager mgr, IVirtualBox vbox) {
		IMachine m = vbox.getMachines().get(0);
		long logNo = 0;
		long off = 0;
		long size = 16 * 1024;
		while (true) {
			byte[] buf = m.readLog(logNo, off, size);
			if (buf.length == 0)
				break;
			System.out.print(new String(buf));
			off += buf.length;
		}
	}
}
