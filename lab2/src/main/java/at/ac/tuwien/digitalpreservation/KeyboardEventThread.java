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

import at.ac.tuwien.digitalpreservation.handler.KeyboardEventHandler;

public class KeyboardEventThread extends Thread {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(KeyboardEventThread.class);

	private IEventSource es = null;
	private IEventListener listener = null;
	private boolean running = true;

	private List<KeyboardEventHandler> keyboardEventHandler = new ArrayList<>();

	public KeyboardEventThread(IConsole console) {
		this.es = console.getKeyboard().getEventSource();
		this.listener = this.es.createListener();
		this.es.registerListener(listener,
				Arrays.asList(VBoxEventType.OnGuestKeyboard), false);
		LOGGER.debug("KeyboardEventThread initialized");
	}

	public void close() {
		this.running = false;
		LOGGER.debug("Closing KeyboardEventThread");
	}

	public void run() {
		LOGGER.debug("Starting KeyboardEventThread");
		while (this.running) {
			IEvent ev = this.es.getEvent(listener, 500);
			if (ev != null) {
				IGuestKeyboardEvent event = IGuestKeyboardEvent
						.queryInterface(ev);
				for (KeyboardEventHandler h : this.keyboardEventHandler) {
					h.handle(event);
				}
				this.es.eventProcessed(this.listener, ev);
			}
		}
		this.es.unregisterListener(this.listener);
	}

	public void addKeyboardEventHandler(KeyboardEventHandler handler) {
		this.keyboardEventHandler.add(handler);
	}

	public void removeKeyboardEventHandler(KeyboardEventHandler handler) {
		this.keyboardEventHandler.remove(handler);
	}
}
