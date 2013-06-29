package at.ac.tuwien.digitalpreservation.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NotDirectoryException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * This utility class contains utility-methods that handle Path-objects.
 * 
 * @author gregor
 * 
 */
public final class PathUtils {

	private PathUtils() {
	}

	/**
	 * Gets or creates the given Path. If parent directories dont exist, this
	 * method also creates them.
	 * 
	 * @param path
	 * @return The Path object to the path it created.
	 * @throws IOException
	 */
	public static Path createDirectoryRecursive(Path path) throws IOException {
		if (path == null) {
			return Paths.get(".");
		}
		if (!Files.exists(path)) {
			createDirectoryRecursive(path.getParent());
			return Files.createDirectories(path);
		} else if (!Files.isDirectory(path)) {
			throw new NotDirectoryException(path.toString());
		}
		return path;
	}

}
