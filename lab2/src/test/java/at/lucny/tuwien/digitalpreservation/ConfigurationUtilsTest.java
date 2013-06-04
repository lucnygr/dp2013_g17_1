package at.lucny.tuwien.digitalpreservation;

import static org.junit.Assert.assertNotNull;

import java.io.File;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import at.ac.tuwien.digitalpreservation.ConfigurationUtils;
import at.ac.tuwien.digitalpreservation.config.EventTypeEnum;
import at.ac.tuwien.digitalpreservation.config.GCAP;
import at.ac.tuwien.digitalpreservation.config.KeyboardEvent;
import at.ac.tuwien.digitalpreservation.config.MouseEvent;
import at.ac.tuwien.digitalpreservation.config.Recording;
import at.ac.tuwien.digitalpreservation.config.ScreenshotEvent;

@RunWith(JUnit4.class)
public class ConfigurationUtilsTest {

	private GCAP createTestConfiguration() {
		MouseEvent mouseE = new MouseEvent();
		mouseE.setTimeOffset(10);
		mouseE.setWDelta(10);
		mouseE.setXPosition(10);
		mouseE.setYPosition(10);
		mouseE.setZDelta(10);
		mouseE.setType(EventTypeEnum.MOUSE_EVENT);

		KeyboardEvent keyboardE = new KeyboardEvent();
		keyboardE.setTimeOffset(20);
		keyboardE.getScancodes().add(30);
		keyboardE.getScancodes().add(40);
		keyboardE.setType(EventTypeEnum.KEYBOARD_EVENT);

		ScreenshotEvent screenshotE = new ScreenshotEvent();
		screenshotE.setTimeOffset(30);
		screenshotE.setType(EventTypeEnum.SCREENSHOT_EVENT);

		Recording recording = new Recording();
		recording.setDescription("Test Recording");
		recording.setTakeScreenshotAfterEvent(true);
		recording.setTakeScreenshotBeforeEvent(false);
		recording.getKeyboardEventOrMouseEventOrScreenshotEvent().add(mouseE);
		recording.getKeyboardEventOrMouseEventOrScreenshotEvent()
				.add(keyboardE);
		recording.getKeyboardEventOrMouseEventOrScreenshotEvent().add(
				screenshotE);

		GCAP gcap = new GCAP();
		gcap.getRecording().add(recording);
		return gcap;
	}

	@Test
	public void testMarshal() {
		GCAP gcap = this.createTestConfiguration();

		ConfigurationUtils.marshal(gcap, new File(
				"src/test/resources/ConfigurationUtilsTest/testMarshal.xml"));
	}

	@Test
	public void testUnmarshal() {
		GCAP gcap = ConfigurationUtils.unmarshal(new File(
				"src/test/resources/ConfigurationUtilsTest/testMarshal.xml"));
		assertNotNull(gcap);
	}
}
