package at.lucny.tuwien.digitalpreservation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import at.ac.tuwien.digitalpreservation.config.MouseButtonEnum;

@RunWith(JUnit4.class)
public class MouseButtonEnumTest {

	@Test
	public void testGetClickedNone() {
		Set<MouseButtonEnum> result = MouseButtonEnum.getClicked(0);
		assertNotNull(result);
		assertEquals(0, result.size());
	}

	@Test
	public void testGetClickedLeftMouse() {
		Set<MouseButtonEnum> result = MouseButtonEnum
				.getClicked(MouseButtonEnum.LEFT.getBitmap());
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(MouseButtonEnum.LEFT, result.iterator().next());
	}

	@Test
	public void testGetClickedMiddleMouse() {
		Set<MouseButtonEnum> result = MouseButtonEnum
				.getClicked(MouseButtonEnum.MIDDLE.getBitmap());
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(MouseButtonEnum.MIDDLE, result.iterator().next());
	}

	@Test
	public void testGetClickedRightMouse() {
		Set<MouseButtonEnum> result = MouseButtonEnum
				.getClicked(MouseButtonEnum.RIGHT.getBitmap());
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(MouseButtonEnum.RIGHT, result.iterator().next());
	}

	@Test
	public void testGetClickedLeftAndRightMouse() {
		Set<MouseButtonEnum> result = MouseButtonEnum
				.getClicked(MouseButtonEnum.RIGHT.getBitmap()
						| MouseButtonEnum.LEFT.getBitmap());
		assertNotNull(result);
		assertEquals(2, result.size());
		assertTrue(result.contains(MouseButtonEnum.LEFT));
		assertTrue(result.contains(MouseButtonEnum.RIGHT));
	}

	@Test
	public void testGetClickedNoneEnum() {
		int result = MouseButtonEnum
				.getClicked(new ArrayList<MouseButtonEnum>());
		assertEquals(0, result);
	}

	@Test
	public void testGetClickedLeftMouseEnum() {
		int result = MouseButtonEnum.getClicked(Arrays
				.asList(MouseButtonEnum.LEFT));
		assertEquals(MouseButtonEnum.LEFT.getBitmap(), result);
	}

	@Test
	public void testGetClickedRightMouseEnum() {
		int result = MouseButtonEnum.getClicked(Arrays
				.asList(MouseButtonEnum.RIGHT));
		assertEquals(MouseButtonEnum.RIGHT.getBitmap(), result);
	}

	@Test
	public void testGetClickedMiddleMouseEnum() {
		int result = MouseButtonEnum.getClicked(Arrays
				.asList(MouseButtonEnum.MIDDLE));
		assertEquals(MouseButtonEnum.MIDDLE.getBitmap(), result);
	}

	@Test
	public void testGetClickedMiddleAndRightMouseEnum() {
		int result = MouseButtonEnum.getClicked(Arrays.asList(
				MouseButtonEnum.MIDDLE, MouseButtonEnum.RIGHT));
		assertEquals(
				MouseButtonEnum.MIDDLE.getBitmap()
						| MouseButtonEnum.RIGHT.getBitmap(), result);
	}
}
