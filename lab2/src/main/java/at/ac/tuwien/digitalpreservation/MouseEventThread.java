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
import org.virtualbox_4_2.VBoxException;

import at.ac.tuwien.digitalpreservation.handler.MouseEventHandler;

public class MouseEventThread extends Thread {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(MouseEventThread.class);

	private IEventSource es = null;
	private IEventListener listener = null;
	private boolean running = true;

	private IConsole console = null;

	private List<MouseEventHandler> mouseEventHandler = new ArrayList<>();

	public MouseEventThread(IConsole console) {
		this.console = console;
		this.init();

		LOGGER.debug("MouseEventThread initialized");
	}

	/**
	 * Call on creation and to reinitialize
	 */
	private synchronized void init() {
		LOGGER.debug("Initializing...");
		this.es = console.getMouse().getEventSource();
		this.listener = this.es.createListener();
		this.es.registerListener(listener,
				Arrays.asList(VBoxEventType.OnGuestMouse), false);
	}

	synchronized public void close() {
		this.running = false;
		LOGGER.debug("Closing MouseEventThread");
		notifyAll();
	}

	public void run() {
		LOGGER.debug("Starting MouseEventThread");
		while (this.running) {
			if (!this.mouseEventHandler.isEmpty()) {
				try {

					IEvent ev = this.es.getEvent(listener, 1);
					if (ev != null) {
						IGuestMouseEvent event = IGuestMouseEvent
								.queryInterface(ev);
						for (MouseEventHandler h : this.mouseEventHandler) {
							h.handle(event);
						}
						this.es.eventProcessed(this.listener, ev);
					}
				} catch (VBoxException e) {
					e.printStackTrace();
					LOGGER.error("Error in MouseEventThread.run: "
							+ e.getMessage());
					try {
						this.es.registerListener(listener,
								Arrays.asList(VBoxEventType.OnGuestMouse),
								false);
					} catch (VBoxException v) {
						init();
					}

				}
			} else {
				this.es.unregisterListener(this.listener);
				LOGGER.debug("waiting...");
				synchronized (this) {
					try {
						wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				LOGGER.debug("continuing");
				try {
					this.es.registerListener(listener,
							Arrays.asList(VBoxEventType.OnGuestMouse), false);
				} catch (VBoxException v) {
					init();
				}
			}

		}
		this.es.unregisterListener(this.listener);
	}

	synchronized public void addMouseEventHandler(MouseEventHandler handler) {
		this.mouseEventHandler.add(handler);
		notifyAll();
	}

	synchronized public void removeMouseEventHandler(MouseEventHandler handler) {
		this.mouseEventHandler.remove(handler);
		notifyAll();
	}
}
