package at.ac.tuwien.digitalpreservation.application;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.ac.tuwien.digitalpreservation.ConfigurationUtils;
import at.ac.tuwien.digitalpreservation.VirtualMachine;
import at.ac.tuwien.digitalpreservation.config.AbstractEvent;
import at.ac.tuwien.digitalpreservation.config.GCAP;
import at.ac.tuwien.digitalpreservation.config.KeyboardEvent;
import at.ac.tuwien.digitalpreservation.config.MouseEvent;
import at.ac.tuwien.digitalpreservation.config.Recording;

public class Player {
	private static final Logger LOGGER = LoggerFactory.getLogger(Player.class);

	String name = "";
	String gcapname = "";
	VirtualMachine vm;
	GCAP gcap = null;
	List<Recording> recs = null;

	/**
	 * 
	 * @param gcap
	 * @param recordingName
	 * @param vm
	 */
	public Player(File gcap, VirtualMachine vm) {
		this.vm = vm;
		this.gcapname = gcap.getName();
		// load file
		this.gcap = ConfigurationUtils.unmarshal(gcap);
		this.recs = this.gcap.getRecording();
	}

	public List<String> getRecordingTitles() {
		ArrayList<String> out = new ArrayList<String>();
		for (Recording r : recs) {
			out.add(r.getDescription());
		}
		return out;
	}

	public void play(String name) {
		Recording rec = null;
		for (Recording r : recs) {
			if (r.getDescription().equalsIgnoreCase(name)) {
				rec = r;
			}
		}
		if (rec == null) {
			LOGGER.warn("No such recording \"" + name + "\"");
			return;
		}

		try {
			ReportGenerator report = new ReportGenerator("Report_" + name + "_"
					+ System.nanoTime());
			report.init(this.gcapname);
			report.startRecording(rec.getDescription());

			List<AbstractEvent> list = rec
					.getKeyboardEventOrMouseEventOrScreenshotEvent();
			long starttime = System.nanoTime();
			for (AbstractEvent ev : list) {
				//final long TIMEOUT = 1000000000+(System.nanoTime()) - starttime;
				long offset = 0;
				while (offset < ev.getTimeOffset()) {
					offset = (System.nanoTime()) - starttime;
				}
				//System.out.println(offset + " " + ev.getTimeOffset());

				switch (ev.getType()) {
				case KEYBOARD_EVENT: {
					vm.putKeyboardEvent((KeyboardEvent) ev);
					break;
				}
				case MOUSE_EVENT: {
					vm.putMouseEvent((MouseEvent) ev);
					break;
				}
				case SCREENSHOT_EVENT: {
					byte[] image = vm.takeScreenShot();
					report.addScreenshot(ev.getTimeOffset(),
							Calendar.getInstance(), image);
					break;
				}
				default:
					LOGGER.error("Invalid Event");
				}
			}

			report.endRecording();
			report.finish();
			report.write();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
}
