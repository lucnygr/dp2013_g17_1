package at.ac.tuwien.digitalpreservation.handler;

import org.virtualbox_4_2.IGuestKeyboardEvent;

public interface KeyboardEventHandler {

	public void handle(IGuestKeyboardEvent event);

}
