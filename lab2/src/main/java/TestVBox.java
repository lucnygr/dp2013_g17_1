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

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import org.virtualbox_4_2.CPUPropertyType;
import org.virtualbox_4_2.HWVirtExPropertyType;
import org.virtualbox_4_2.Holder;
import org.virtualbox_4_2.IDisplay;
import org.virtualbox_4_2.IEvent;
import org.virtualbox_4_2.IEventListener;
import org.virtualbox_4_2.IEventSource;
import org.virtualbox_4_2.IGuestKeyboardEvent;
import org.virtualbox_4_2.IGuestMouseEvent;
import org.virtualbox_4_2.IGuestPropertyChangedEvent;
import org.virtualbox_4_2.IMachine;
import org.virtualbox_4_2.IMachineStateChangedEvent;
import org.virtualbox_4_2.ISessionStateChangedEvent;
import org.virtualbox_4_2.IVirtualBox;
import org.virtualbox_4_2.MachineState;
import org.virtualbox_4_2.VBoxEventType;
import org.virtualbox_4_2.VBoxException;
import org.virtualbox_4_2.VirtualBoxManager;

public class TestVBox {

	static class EventHandler extends Thread {
		private VirtualBoxManager mgr = null;
		private IEventSource es = null;
		private IEventListener listener = null;
		private boolean running = true;

		EventHandler(VirtualBoxManager mgr) {
			this.mgr = mgr;
			this.es = this.mgr.getVBox().getEventSource();
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
			VBoxEventType type = ev.getType();
			//System.out.println("EventHandler: got event: " + ev.getWrapped() + " type = " + type);
			switch (type) {
			case OnMachineStateChanged: {
				IMachineStateChangedEvent mcse = IMachineStateChangedEvent
						.queryInterface(ev);
				if (mcse == null) {
					//System.out.println("Cannot query an interface");
				}
				else {
					//System.out.println("mid=" + mcse.getMachineId());
					//System.out.println("State=" + mcse.getState().toString());
				}

				break;
			}
			case OnSessionStateChanged: {
				ISessionStateChangedEvent cev = ISessionStateChangedEvent.queryInterface(ev);
				//System.out.println(cev.getState().toString());
			}

			case OnGuestPropertyChanged: {
				IGuestPropertyChangedEvent cev = IGuestPropertyChangedEvent.queryInterface(ev);
				//System.out.println(cev.getName());
			}
			default:
				//System.out.println("EventHandler: type: default");
				break;
			}
		}
	}

	static class KeyBoardEventHandler extends Thread {
		private VirtualBoxManager mgr = null;
		private IEventSource es = null;
		private IEventListener listener = null;
		private boolean running = true;

		KeyBoardEventHandler(VirtualBoxManager mgr) {
			this.mgr = mgr;
			this.es = this.mgr.getSessionObject().getConsole().getKeyboard().getEventSource();
			listener = this.es.createListener();
			es.registerListener(listener, Arrays.asList(VBoxEventType.Any), false);
			System.out.println("KeyBoardEventHandler initialized");
		}

		public void close() {
			this.running = false;
			System.out.println("Closing KeyBoardEventHandler");
		}

		public void run() {
			System.out.println("Starting KeyBoardEventHandler");
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
			VBoxEventType type = ev.getType();
			//System.out.println("KeyBoardEventHandler: got event: " + ev.getWrapped() + " type = " + type);
			switch (type) {
			case OnGuestKeyboard: {
				IGuestKeyboardEvent cev = IGuestKeyboardEvent.queryInterface(ev);
				System.out.println("Scancodes:"+cev.getScancodes());
			}
			default:
				//System.out.println("KeyBoardEventHandler: type: default");
				break;
			}
		}
	}

	static class MouseEventHandler extends Thread {
		private VirtualBoxManager mgr = null;
		private IEventSource es = null;
		private IEventListener listener = null;
		private boolean running = true;

		MouseEventHandler(VirtualBoxManager mgr) {
			this.mgr = mgr;
			this.es = this.mgr.getSessionObject().getConsole().getMouse().getEventSource();
			listener = this.es.createListener();
			es.registerListener(listener, Arrays.asList(VBoxEventType.Any), false);
			System.out.println("MouseEventHandler initialized");
		}

		public void close() {
			this.running = false;
			System.out.println("Closing MouseEventHandler");
		}

		public void run() {
			System.out.println("Starting MouseEventHandler");
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
			VBoxEventType type = ev.getType();
			//System.out.println("MouseEventHandler: got event: " + ev.getWrapped() + " type = " + type);
			switch (type) {
			case OnGuestMouse: {
				IGuestMouseEvent cev = IGuestMouseEvent.queryInterface(ev);
				//System.out.println("X:"+cev.getX()+" Y:"+cev.getY()+" Z:"+cev.getZ()+" W:"+cev.getW()+" ButtonMask:"+cev.getButtons());
			}
			default:
				//System.out.println("MouseEventHandler: type: default");
				break;
			}
		}
	}

	static class Recorder extends Thread {
		enum LineType {
			Invalid,
			KeyboardEvent,
			MouseEvent,
			ScreenShotEvent
		}
		static class Recording {
			static class REvent {
				public LineType type = LineType.Invalid;
				public Object content = null;
				REvent() {

				}
				REvent(LineType type, Object content) {
					this.type = type;
					this.content = content;
				}
			}
			private String name = null;

			private ArrayList<REvent> list = new ArrayList<REvent>();

			String getName() {
				return name;
			}
			Recording(String name) {
				this.name = name;
			}

			void add(REvent e) {
				this.list.add(e);
			}
			Iterator<REvent> iterator() {
				return this.list.iterator();
			}
		}
		static class Serializer {
			static final String DELIMITER = " ";

			static boolean parseLine(String line, Recording rec) {
				StringTokenizer tok = new StringTokenizer(line, DELIMITER);
				if(!tok.hasMoreTokens()) {
					return false;
				}
				String firstToken = tok.nextToken();
				if (firstToken.equals(LineType.MouseEvent.toString())) {
					return false; //TODO
					//return LineType.MouseEvent;
				} else if (firstToken.equals(LineType.KeyboardEvent.toString())) {
					if (!tok.hasMoreTokens()) {
						return false;
					}
					List<Integer> l = new ArrayList<Integer>();
					while (tok.hasMoreTokens()) {
						String scancode = tok.nextToken();
						try {
							l.add(Integer.parseInt(scancode));
						} catch (NumberFormatException e) {
							return false;
						}
					}
					rec.add(new Recording.REvent(LineType.KeyboardEvent, l));
					return true;
				} else if (firstToken.equals(LineType.ScreenShotEvent.toString())) {
					return false; //TODO
					//return LineType.ScreenShotEvent;
				} else {
					return false; //TODO
					//return LineType.Invalid;
				}

			}

			static List<Integer> StringToGuestKeyboardEvent(String line) {
				return null;
			}
			static String GuestKeyboardEventToString(IGuestKeyboardEvent ev) {
				return null;
			}
		}

		private boolean ready = false;
		private boolean running = true;
		private VirtualBoxManager mgr = null;
		private String filename = null;

		IEventSource mouseEs = null;
		IEventSource keyboardEs = null;
		private IEventListener mouseListener = null;
		private IEventListener keyboardListener = null;

		Recorder(VirtualBoxManager mgr, String filename) {
			this.mgr = mgr;
			this.reset(filename);
		}
		public String getFilename() {
			return this.filename;
		}

		public void reset() {
			this.deset();

			this.mouseEs = this.mgr.getSessionObject().getConsole().getMouse().getEventSource();
			mouseListener = this.mouseEs.createListener();
			mouseEs.registerListener(mouseListener, Arrays.asList(VBoxEventType.Any), false);

			this.keyboardEs = this.mgr.getSessionObject().getConsole().getKeyboard().getEventSource();
			mouseListener = this.keyboardEs.createListener();
			keyboardEs.registerListener(mouseListener, Arrays.asList(VBoxEventType.Any), false);

			this.running = false;
			this.ready = true;
		}

		private void deset() {
			if (mouseEs != null && mouseListener != null) {
				this.mouseEs.unregisterListener(mouseListener);
				this.mouseEs = null;
				this.mouseListener = null;
			}

			if (keyboardEs != null && keyboardListener != null) {
				this.keyboardEs.unregisterListener(keyboardListener);
				this.keyboardEs = null;
				this.keyboardListener = null;
			}

			this.ready = false;
			this.running = false;
		}

		public void reset(String filename) {
			this.filename = filename;
			this.reset();
		}

		public void run() {
			// actually record till the stop signal is given
			while(running) {
				// poll keyboard and mouse events
				IEvent mev = mouseEs.getEvent(mouseListener, 5);
				if (mev != null) {
					handleMouseEvent(mev);
					mouseEs.eventProcessed(mouseListener, mev);
				}

				IEvent kev = keyboardEs.getEvent(keyboardListener, 5);
				if (kev != null) {
					handleKeyBoardEvent(kev);
					keyboardEs.eventProcessed(keyboardListener, kev);
				}
			}
		}

		private void handleKeyBoardEvent(IEvent ev) {

		}

		private void handleMouseEvent(IEvent ev) {

		}

		public void startRecording() {
			if (!ready) {
				System.out.println("The Recorder is not ready. Reset it");
				return;
			}
			this.start();
		}

		public void stopRecording() {
			this.running = false;
			this.ready = false;
		}

		static void playRecording(VirtualBoxManager mgr, String filename) {
			// play a previously recorded file
		}
	}


	public static EventHandler eventHandler = null;
	public static KeyBoardEventHandler keyboardEventHandler = null;
	public static MouseEventHandler mouseEventHandler = null;


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
	static void testStart(VirtualBoxManager mgr) {
		String m = mgr.getVBox().getMachines().get(0).getName();
		System.out.println("\nAttempting to start VM '" + m + "'");
		System.out.println("Session:"+mgr.getVBox().getMachines().get(0).getSessionState().toString());
		System.out.println("Session PID:"+mgr.getVBox().getMachines().get(0).getSessionPID().toString());

		if (mgr.getVBox().getMachines().get(0).getState().equals(MachineState.Running) || mgr.getVBox().getMachines().get(0).getState().equals(MachineState.Paused)) {
			mgr.getVBox().getMachines().get(0).launchVMProcess(mgr.getSessionObject(),"emergencystop",null);
		}
		
		mgr.getVBox().getMachines().get(0).launchVMProcess(mgr.getSessionObject(),"gui",null);
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	static boolean takeScreenShot(VirtualBoxManager mgr, String filepath) {
		boolean returnvalue = true;
		IDisplay d = mgr.getSessionObject().getConsole().getDisplay();

		Holder<Long> width = new Holder<Long>();
		Holder<Long> height = new Holder<Long>();
		Holder<Long> bitsPerPixel = new Holder<Long>();


		d.getScreenResolution(Long.valueOf(0), width, height, bitsPerPixel);
		byte[] image = d.takeScreenShotPNGToArray(Long.valueOf(0), width.value, height.value);
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
			System.out.println("VirtualBox version: " + mgr.getVBox().getVersion() + "\n");
			//testEnumeration(mgr, vbox);
			//testReadLog(mgr, vbox);
			testStart(mgr);
			System.out.println("Session:"+mgr.getVBox().getMachines().get(0).getSessionState().toString());
			System.out.println("Session PID:"+mgr.getVBox().getMachines().get(0).getSessionPID().toString());

			System.out.println("Box started");
			eventHandler = new EventHandler(mgr);
			System.out.println("EventHandler created");
			eventHandler.start();
			System.out.println("EventHandler started");

			keyboardEventHandler = new KeyBoardEventHandler(mgr);
			System.out.println("KeyBoardEventHandler created");
			keyboardEventHandler.start();
			System.out.println("KeyBoardEventHandler started");

			mouseEventHandler = new MouseEventHandler(mgr);
			System.out.println("MouseEventHandler created");
			mouseEventHandler.start();
			System.out.println("MouseEventHandler started");
			System.out.println("Press Enter to take screenshot");

			System.in.read();
			System.in.read();

			takeScreenShot(mgr, "image.png");
			System.out.println("Screenshot taken");

			System.out.println("Press Enter to stop");

			System.in.read();
			System.in.read();

			System.out.println("Stopping EventHandler");
			eventHandler.close();

			System.out.println("Stopping KeyboardEventHandler");
			keyboardEventHandler.close();

			System.out.println("Stopping MouseEventHandler");
			mouseEventHandler.close();

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			mgr.getSessionObject().getConsole().powerButton();

			System.out.println("done, press Enter...");
			System.in.read();
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
