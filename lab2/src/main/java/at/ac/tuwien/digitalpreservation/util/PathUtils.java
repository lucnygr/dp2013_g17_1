package at.ac.tuwien.digitalpreservation.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NotDirectoryException;
import java.nio.file.Path;
import java.nio.file.Paths;

final public class PathUtils {

	private PathUtils() {
	}

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
