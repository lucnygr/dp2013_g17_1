package at.ac.tuwien.digitalpreservation;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import at.ac.tuwien.digitalpreservation.config.GCAP;
import at.ac.tuwien.digitalpreservation.config.KeyboardEvent;
import at.ac.tuwien.digitalpreservation.config.MouseButtonEnum;
import at.ac.tuwien.digitalpreservation.config.MouseEvent;
import at.ac.tuwien.digitalpreservation.config.Recording;
import at.ac.tuwien.digitalpreservation.config.ScreenshotEvent;

public final class ConfigurationUtils {

	private ConfigurationUtils() {
	}

	private static JAXBContext createContext() {
		try {
			return JAXBContext.newInstance(GCAP.class, Recording.class,
					KeyboardEvent.class, MouseEvent.class,
					ScreenshotEvent.class, MouseButtonEnum.class);
		} catch (JAXBException e) {
			throw new RuntimeException(e);
		}
	}

	public static GCAP unmarshal(File file) {
		try {
			Unmarshaller unmarshaller = createContext().createUnmarshaller();
			Object result = unmarshaller.unmarshal(file);
			if (result instanceof GCAP) {
				return (GCAP) result;
			} else {
				throw new IllegalStateException("File " + file
						+ " does not contain a GCAP-Object");
			}
		} catch (JAXBException e) {
			throw new RuntimeException(e);
		}
	}

	public static void marshal(GCAP gcap, File file) {
		try {
			Marshaller marshaller = createContext().createMarshaller();
			marshaller.marshal(gcap, file);
		} catch (JAXBException e) {
			throw new RuntimeException(e);
		}
	}

}
