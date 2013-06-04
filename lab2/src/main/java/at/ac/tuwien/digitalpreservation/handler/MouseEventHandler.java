package at.ac.tuwien.digitalpreservation.handler;

import org.virtualbox_4_2.IGuestMouseEvent;

public interface MouseEventHandler {

	public void handle(IGuestMouseEvent event);

}
