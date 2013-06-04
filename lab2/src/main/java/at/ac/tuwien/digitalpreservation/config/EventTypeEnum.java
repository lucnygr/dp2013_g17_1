package at.ac.tuwien.digitalpreservation.config;

public enum EventTypeEnum {

	MOUSE_EVENT, KEYBOARD_EVENT, SCREENSHOT_EVENT;

	public static EventTypeEnum parseEventType(String eventType) {
		for (EventTypeEnum ete : values()) {
			if (eventType.equals(ete.toString())) {
				return ete;
			}
		}
		return null;
	}

	public static String printEventType(EventTypeEnum eventType) {
		return eventType.toString();
	}

}
