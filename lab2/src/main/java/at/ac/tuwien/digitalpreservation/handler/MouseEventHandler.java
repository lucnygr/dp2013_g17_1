package at.ac.tuwien.digitalpreservation.handler;

import org.virtualbox_4_2.IGuestMouseEvent;

/**
 * Extend this interface to receive MouseEvents from the VirtualBox.
 * 
 * @author gregor
 * 
 */
public interface MouseEventHandler {

	/**
	 * Handles a IGuestMouseEvent.
	 * 
	 * @param event
	 */
	public void handle(IGuestMouseEvent event);

}
