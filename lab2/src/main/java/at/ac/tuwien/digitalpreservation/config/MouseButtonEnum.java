package at.ac.tuwien.digitalpreservation.config;

import java.util.HashSet;
import java.util.Set;

public enum MouseButtonEnum {

	LEFT(1), RIGHT(2), MIDDLE(4);

	private int bitmap;

	private MouseButtonEnum(int bitmap) {
		this.bitmap = bitmap;
	}

	public boolean isClicked(int bitmap) {
		int result = this.bitmap & bitmap;
		if (result == 0) {
			return false;
		}
		return true;
	}

	public static Set<MouseButtonEnum> getClicked(int bitmap) {
		Set<MouseButtonEnum> mbeSet = new HashSet<>();
		for (MouseButtonEnum mbe : values()) {
			if (mbe.isClicked(bitmap)) {
				mbeSet.add(mbe);
			}
		}
		return mbeSet;
	}

	public static MouseButtonEnum parseMouseButton(String mouseButton) {
		for (MouseButtonEnum mbe : values()) {
			if (mouseButton.equals(mbe.toString())) {
				return mbe;
			}
		}
		return null;
	}

	public static String printMouseButton(MouseButtonEnum mouseButton) {
		return mouseButton.toString();
	}
}
