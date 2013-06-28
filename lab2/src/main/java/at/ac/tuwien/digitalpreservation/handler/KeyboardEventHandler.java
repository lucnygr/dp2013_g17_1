package at.ac.tuwien.digitalpreservation.handler;

import org.virtualbox_4_2.IGuestKeyboardEvent;

/**
 * Extend this interface to receive KeyboardEvents from the VirtualBox.
 * 
 * @author gregor
 * 
 */
public interface KeyboardEventHandler {

	/**
	 * Handles a IGuestKeyboardEvent.
	 * 
	 * @param event
	 */
	public void handle(IGuestKeyboardEvent event);

}
