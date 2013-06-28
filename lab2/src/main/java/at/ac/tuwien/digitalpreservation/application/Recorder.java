package at.ac.tuwien.digitalpreservation.application;

import java.io.File;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.virtualbox_4_2.IGuestKeyboardEvent;
import org.virtualbox_4_2.IGuestMouseEvent;

import at.ac.tuwien.digitalpreservation.ConfigurationUtils;
import at.ac.tuwien.digitalpreservation.VirtualMachine;
import at.ac.tuwien.digitalpreservation.config.EventTypeEnum;
import at.ac.tuwien.digitalpreservation.config.GCAP;
import at.ac.tuwien.digitalpreservation.config.KeyboardEvent;
import at.ac.tuwien.digitalpreservation.config.MouseEvent;
import at.ac.tuwien.digitalpreservation.config.Recording;
import at.ac.tuwien.digitalpreservation.config.ScreenshotEvent;
import at.ac.tuwien.digitalpreservation.handler.KeyboardEventHandler;
import at.ac.tuwien.digitalpreservation.handler.MouseEventHandler;

public class Recorder implements KeyboardEventHandler, MouseEventHandler {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(Recorder.class);

	public static final List<Integer> SCANCODE_PRINT_SCREEN = Arrays
			.asList(new Integer[] { 224, 42, 224, 55 });
	public static final List<Integer> SCANCODE_PRINT_SCREEN_RELEASE = Arrays
			.asList(new Integer[] { 224, 183, 224, 170 });

	private VirtualMachine virtualMachine;

	private GCAP gcap;

	private Recording currentRecording;

	private long recordingStart;

	public Recorder(VirtualMachine virtualMachine) {
		LOGGER.debug("create new recorder");
		this.virtualMachine = virtualMachine;
		this.gcap = new GCAP();
	}

	public Recorder(VirtualMachine virtualMachine, File file) {
		LOGGER.debug("create recorder with existing configuration " + file);
		this.virtualMachine = virtualMachine;
		this.gcap = ConfigurationUtils.unmarshal(file);
	}

	public void startRecording(boolean takeScreenshotOnMouseclickEvent) {
		this.currentRecording = new Recording();
		this.currentRecording
				.setTakeScreenshotOnMouseclickEvent(takeScreenshotOnMouseclickEvent);
		this.recordingStart = System.nanoTime();
		this.virtualMachine.addKeyboardEventHandler(this);
		this.virtualMachine.addMouseEventHandler(this);
	}

	public void stopRecording() {
		this.virtualMachine.removeKeyboardEventHandler(this);
		this.virtualMachine.removeMouseEventHandler(this);
		this.gcap.getRecording().add(this.currentRecording);
	}

	public void finishRecording(String description) {
		this.currentRecording.setDescription(description);
		this.currentRecording = null;
		this.recordingStart = 0;
	}

	public void saveGCAP(Path directoryPath, String gcapName) {
		if (this.currentRecording != null) {
			throw new IllegalStateException(
					"Finish Recording before saving GCAP file");
		}
		this.gcap.postprocess();
		this.gcap.setName(gcapName);
		ConfigurationUtils.marshal(this.gcap,
				directoryPath.resolve(gcapName + ".xml").toFile());
	}

	@Override
	public void handle(IGuestKeyboardEvent event) {
		if (this.equals(event.getScancodes(), SCANCODE_PRINT_SCREEN)) {
			this.addScreenshotEvent(System.nanoTime() - this.recordingStart);
		} else if (this.equals(event.getScancodes(),
				SCANCODE_PRINT_SCREEN_RELEASE)) {
			return;
		} else {
			KeyboardEvent ke = new KeyboardEvent();
			ke.setTimeOffset(System.nanoTime() - this.recordingStart);
			ke.getScancodes().addAll(event.getScancodes());
			ke.setType(EventTypeEnum.KEYBOARD_EVENT);
			this.currentRecording
					.getKeyboardEventOrMouseEventOrScreenshotEvent().add(ke);
		}
	}

	private boolean equals(List<Integer> list1, List<Integer> list2) {
		if (list1.size() != list2.size()) {
			return false;
		}

		for (int i = 0; i < list1.size(); i++) {
			if (list1.get(i).intValue() != list2.get(i).intValue()) {
				return false;
			}
		}
		return true;
	}

	@Override
	public void handle(IGuestMouseEvent event) {
		MouseEvent me = new MouseEvent();
		me.setTimeOffset(System.nanoTime() - this.recordingStart);
		me.setWDelta(event.getW());
		me.setXPosition(event.getX());
		me.setYPosition(event.getY());
		me.setZDelta(event.getZ());
		me.setMouseButtons(event.getButtons());
		me.setType(EventTypeEnum.MOUSE_EVENT);
		LOGGER.debug("Buttonmask: "+me.getMouseButtons());
		this.currentRecording.getKeyboardEventOrMouseEventOrScreenshotEvent()
				.add(me);

		if (me.getMouseButtons() > 0) {
			if (this.currentRecording.isTakeScreenshotOnMouseclickEvent()) {
				this.addScreenshotEvent(me.getTimeOffset());
			}
		}
		/*me.getMouseButtons().addAll(
				MouseButtonEnum.getClicked(event.getButtons()));
		me.setType(EventTypeEnum.MOUSE_EVENT);
		this.currentRecording.getKeyboardEventOrMouseEventOrScreenshotEvent()
				.add(me);

		if (me.getMouseButtons().size() > 0) {
			if (this.currentRecording.isTakeScreenshotOnMouseclickEvent()) {
				this.addScreenshotEvent(me.getTimeOffset());
			}
		}*/
	}

	private void addScreenshotEvent(long offset) {
		ScreenshotEvent sse = new ScreenshotEvent();
		sse.setTimeOffset(offset);
		sse.setType(EventTypeEnum.SCREENSHOT_EVENT);
		this.currentRecording.getKeyboardEventOrMouseEventOrScreenshotEvent()
				.add(sse);
	}
}
