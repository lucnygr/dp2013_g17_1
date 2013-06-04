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
import org.virtualbox_4_2.IGuestMouseEvent;
import org.virtualbox_4_2.VBoxEventType;

import at.ac.tuwien.digitalpreservation.handler.MouseEventHandler;

public class MouseEventThread extends Thread {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(MouseEventThread.class);

	private IEventSource es = null;
	private IEventListener listener = null;
	private boolean running = true;

	private List<MouseEventHandler> mouseEventHandler = new ArrayList<>();

	public MouseEventThread(IConsole console) {
		this.es = console.getMouse().getEventSource();
		this.listener = this.es.createListener();
		this.es.registerListener(listener,
				Arrays.asList(VBoxEventType.OnGuestMouse), false);
		LOGGER.debug("MouseEventThread initialized");
	}

	public void close() {
		this.running = false;
		LOGGER.debug("Closing MouseEventThread");
	}

	public void run() {
		LOGGER.debug("Starting MouseEventThread");
		while (this.running) {
			IEvent ev = this.es.getEvent(listener, 100);
			if (ev != null) {
				IGuestMouseEvent event = IGuestMouseEvent.queryInterface(ev);
				for (MouseEventHandler h : this.mouseEventHandler) {
					h.handle(event);
				}
				this.es.eventProcessed(this.listener, ev);
			}
		}
		this.es.unregisterListener(this.listener);
	}

	public void addMouseEventHandler(MouseEventHandler handler) {
		this.mouseEventHandler.add(handler);
	}

	public void removeMouseEventHandler(MouseEventHandler handler) {
		this.mouseEventHandler.remove(handler);
	}
}
