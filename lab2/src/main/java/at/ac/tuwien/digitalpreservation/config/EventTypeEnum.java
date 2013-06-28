package at.ac.tuwien.digitalpreservation.config;

/**
 * This enumeration contains all events that are saved in the
 * xml-configuration-files. Each of this event is recorded or generated during
 * the recording. It specifies the type of the event and therefore the later
 * playback of the configuration.
 * 
 * @author gregor
 * 
 */
public enum EventTypeEnum {

	/** A mouse event, like mouse movement or mouse clicks. */
	MOUSE_EVENT,
	/** A keyboard event, like pressing or releasing buttons. */
	KEYBOARD_EVENT,
	/**
	 * A screenshot event. This is a generated event during playback and tells
	 * the player that it should take a screenshot at the specified time.
	 */
	SCREENSHOT_EVENT;

	/**
	 * Parses the given string to the real EventTypeEnum element.
	 * 
	 * @param eventType
	 *            The enum element as string.
	 * @return The real EventTypeEnum. If the string does not match any element
	 *         of the enumeration, return null.
	 */
	public static EventTypeEnum parseEventType(String eventType) {
		for (EventTypeEnum ete : values()) {
			if (eventType.equals(ete.toString())) {
				return ete;
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
	public static String printEventType(EventTypeEnum eventType) {
		return eventType.toString();
	}

}
