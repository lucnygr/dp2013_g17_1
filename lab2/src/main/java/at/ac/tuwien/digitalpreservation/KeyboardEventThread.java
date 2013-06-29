package at.ac.tuwien.digitalpreservation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.virtualbox_4_2.IConsole;
import org.virtualbox_4_2.IEvent;
import org.virtualbox_4_2.IEventListener;
import org.virtualbox_4_2.IEventSource;
import org.virtualbox_4_2.IGuestKeyboardEvent;
import org.virtualbox_4_2.VBoxEventType;
import org.virtualbox_4_2.VBoxException;

import at.ac.tuwien.digitalpreservation.handler.KeyboardEventHandler;

public class KeyboardEventThread extends Thread {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(KeyboardEventThread.class);

	private IEventSource es = null;
	private IEventListener listener = null;
	private boolean running = true;

	private IConsole console = null;
	
	private List<KeyboardEventHandler> keyboardEventHandler = new ArrayList<>();

	public KeyboardEventThread(IConsole console) {
		this.console = console;
		this.init();
		LOGGER.debug("KeyboardEventThread initialized");
	}
	
	/**
	 * Call on creation and to reinitialize
	 */
	private synchronized void init() {
		LOGGER.debug("Initializing...");
		this.es = console.getKeyboard().getEventSource();
		this.listener = this.es.createListener();
		this.es.registerListener(listener,
				Arrays.asList(VBoxEventType.OnGuestKeyboard), false);
	}
	
	public synchronized void close() {
		this.running = false;
		LOGGER.debug("Closing KeyboardEventThread");
		notifyAll();
	}

	public void run() {
		LOGGER.debug("Starting KeyboardEventThread");
		while (this.running) {
			if (!this.keyboardEventHandler.isEmpty()) {
				IEvent ev = this.es.getEvent(listener, 1);
				if (ev != null) {
					IGuestKeyboardEvent event = IGuestKeyboardEvent
							.queryInterface(ev);
					for (KeyboardEventHandler h : this.keyboardEventHandler) {
						h.handle(event);
					}
					this.es.eventProcessed(this.listener, ev);
				}
			} else {
				this.es.unregisterListener(this.listener);
				LOGGER.debug("waiting...");
				synchronized(this) {
					try {
						wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				LOGGER.debug("continuing");
				try {
					this.es.registerListener(listener,Arrays.asList(VBoxEventType.OnGuestKeyboard), false);
				} catch (VBoxException v) {
					init();
				}
			}
		}
		this.es.unregisterListener(this.listener);
	}

	public synchronized void addKeyboardEventHandler(KeyboardEventHandler handler) {
		this.keyboardEventHandler.add(handler);
		notifyAll();
	}

	public synchronized void removeKeyboardEventHandler(KeyboardEventHandler handler) {
		this.keyboardEventHandler.remove(handler);
		notifyAll();
	}
}
