package at.ac.tuwien.digitalpreservation.config;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * This enumeration elements represent the three mouse buttons left, right and
 * middle.
 * 
 * @author gregor
 * 
 */
public enum MouseButtonEnum {

	/** The left mousebutton. Internally represented as 1. */
	LEFT(1),
	/** The right mousebutotn. Internally represented as 2. */
	RIGHT(2),
	/** The right mousebutotn. Internally represented as 4. */
	MIDDLE(4);

	private int bitmap;

	private MouseButtonEnum(int bitmap) {
		this.bitmap = bitmap;
	}

	/**
	 * Returns the bitmap-representation of the clicked mouse button.
	 * 
	 * @return
	 */
	public int getBitmap() {
		return bitmap;
	}

	/**
	 * Looks if the current button is marked as clicked in the given bitmap.
	 * 
	 * @param bitmap
	 *            The bitmask of the clicked buttons.
	 * @return true if the button is clicked, otherwise false
	 */
	public boolean isClicked(int bitmap) {
		int result = this.bitmap & bitmap;
		if (result == 0) {
			return false;
		}
		return true;
	}

	/**
	 * Returns a Set of all mouse buttons marked as clicked in the given bitmap.
	 * 
	 * @param bitmap
	 *            The bitmask of the clicked buttons.
	 * @return A set with all clicked mouse buttons.
	 */
	public static Set<MouseButtonEnum> getClicked(int bitmap) {
		Set<MouseButtonEnum> mbeSet = new HashSet<>();
		for (MouseButtonEnum mbe : values()) {
			if (mbe.isClicked(bitmap)) {
				mbeSet.add(mbe);
			}
		}
		return mbeSet;
	}

	/**
	 * Returns a bitmap of all the clicked mousebuttons given as collection.
	 * 
	 * @param mouseButtons
	 *            A collection of clicked mousebuttons
	 * @return The bitmask representing the clicked buttons.
	 */
	public static int getClicked(Collection<MouseButtonEnum> mouseButtons) {
		int bitmap = 0;
		for (MouseButtonEnum mbe : mouseButtons) {
			bitmap |= mbe.bitmap;
		}
		return bitmap;
	}

	/**
	 * Parses the given string to the real MouseButtonEnum element.
	 * 
	 * @param eventType
	 *            The enum element as string.
	 * @return The real MouseButtonEnum. If the string does not match any
	 *         element of the enumeration, return null.
	 */
	public static MouseButtonEnum parseMouseButton(String mouseButton) {
		for (MouseButtonEnum mbe : values()) {
			if (mouseButton.equals(mbe.toString())) {
				return mbe;
			}
		}
		return null;
	}

	/**
	 * Returns the toString() representation of the Enumeration element.
	 * 
	 * @param eventType
	 *            The enum element.
	 * @return a string
	 */
	public static String printMouseButton(MouseButtonEnum mouseButton) {
		return mouseButton.toString();
	}
}
