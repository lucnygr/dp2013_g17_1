package at.ac.tuwien.digitalpreservation.recorder;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.virtualbox_4_2.IGuestKeyboardEvent;
import org.virtualbox_4_2.IGuestMouseEvent;

import at.ac.tuwien.digitalpreservation.ConfigurationUtils;
import at.ac.tuwien.digitalpreservation.VirtualMachine;
import at.ac.tuwien.digitalpreservation.config.GCAP;
import at.ac.tuwien.digitalpreservation.config.KeyboardEvent;
import at.ac.tuwien.digitalpreservation.config.MouseButtonEnum;
import at.ac.tuwien.digitalpreservation.config.MouseEvent;
import at.ac.tuwien.digitalpreservation.config.Recording;
import at.ac.tuwien.digitalpreservation.handler.KeyboardEventHandler;
import at.ac.tuwien.digitalpreservation.handler.MouseEventHandler;

public class Recorder implements KeyboardEventHandler, MouseEventHandler {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(Recorder.class);

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

	public void saveGCAP(File file) {
		if (this.currentRecording != null) {
			throw new IllegalStateException(
					"Stop Recording before saving GCAP file");
		}

		ConfigurationUtils.marshal(this.gcap, file);
	}

	public void startRecording() {
		this.currentRecording = new Recording();
		this.recordingStart = System.currentTimeMillis();
		this.virtualMachine.addKeyboardEventHandler(this);
		this.virtualMachine.addMouseEventHandler(this);
	}

	public void stopRecording(String description) {
		this.virtualMachine.removeKeyboardEventHandler(this);
		this.virtualMachine.removeMouseEventHandler(this);
		this.currentRecording.setDescription(description);
		this.gcap.getRecording().add(this.currentRecording);
		this.recordingStart = 0;
		this.currentRecording = null;
	}

	@Override
	public void handle(IGuestKeyboardEvent event) {
		KeyboardEvent ke = new KeyboardEvent();
		ke.setTimeOffset(System.currentTimeMillis() - this.recordingStart);
		ke.getScancodes().addAll(event.getScancodes());
		this.currentRecording.getKeyboardEventOrMouseEventOrScreenshotEvent()
				.add(ke);
	}

	@Override
	public void handle(IGuestMouseEvent event) {
		MouseEvent me = new MouseEvent();
		me.setTimeOffset(System.currentTimeMillis() - this.recordingStart);
		me.setWDelta(event.getW());
		me.setXPosition(event.getX());
		me.setYPosition(event.getY());
		me.setZDelta(event.getZ());
		me.getMouseButtons().addAll(
				MouseButtonEnum.getClicked(event.getButtons()));
		this.currentRecording.getKeyboardEventOrMouseEventOrScreenshotEvent()
				.add(me);
	}
}
