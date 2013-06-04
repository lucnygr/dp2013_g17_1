package at.ac.tuwien.digitalpreservation;

import java.io.File;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.ac.tuwien.digitalpreservation.config.AbstractEvent;
import at.ac.tuwien.digitalpreservation.config.EventTypeEnum;
import at.ac.tuwien.digitalpreservation.config.GCAP;
import at.ac.tuwien.digitalpreservation.config.KeyboardEvent;
import at.ac.tuwien.digitalpreservation.config.MouseEvent;
import at.ac.tuwien.digitalpreservation.config.Recording;

public class Player {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(Player.class);
	
	String name = "";
	VirtualMachine vm;
	GCAP gcap = null;
	List<Recording> recs = null;
	/**
	 * 
	 * @param gcap
	 * @param recordingName
	 * @param vm
	 */
	Player(File gcap, String recordingName, VirtualMachine vm) {
		this.name = recordingName;
		this.vm = vm;
		
		// load file
		this.gcap = ConfigurationUtils.unmarshal(gcap);
		this.recs = this.gcap.getRecording();
	}
	void play(String name) {
		Recording rec = null;
		for (Recording r : recs) {
			if (r.getDescription().equalsIgnoreCase("name")) {
				rec = r;
			}
		}
		if (rec == null) {
			LOGGER.warn("No such recording \""+name+"\"");
			return;
		}
		
		//TODO
		List<AbstractEvent> list = rec.getKeyboardEventOrMouseEventOrScreenshotEvent();
		long starttime = new Date().getTime();
		for (AbstractEvent ev : list) {
			
			while((new Date().getTime())-starttime < ev.getOffset()) {

			}
			switch(ev.getType()) {
			case KEYBOARD_EVENT: {
				vm.putKeyboardEvent((KeyboardEvent)ev);
			}
			case MOUSE_EVENT: {
				vm.putMouseEvent((MouseEvent)ev);
			}
			case SCREENSHOT_EVENT: {
				vm.takeScreenShot("test.png");
			}
			default:
			}
		}
	}

}
