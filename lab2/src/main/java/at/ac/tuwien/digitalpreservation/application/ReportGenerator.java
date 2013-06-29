package at.ac.tuwien.digitalpreservation.application;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import at.ac.tuwien.digitalpreservation.util.PathUtils;

public class ReportGenerator {

	private StringBuilder sb = new StringBuilder();

	private Path path;

	public ReportGenerator(String path) throws IOException {
		this.path = PathUtils.createDirectoryRecursive(Paths.get(path));
	}

	private void startTag(String s) {
		this.sb.append("<").append(s).append(">").append("\n");
	}

	private void endTag(String s) {
		this.sb.append("</").append(s).append(">").append("\n");
	}

	private void write(String s) {
		this.sb.append(s).append("\n");
	}

	public void init(String name) {
		this.startTag("html");
		this.printHead(name);
		this.startTag("body");
		this.startTag("h1");
		this.write(name);
		this.endTag("h1");
	}

	public void finish() {
		this.endTag("body");
		this.endTag("html");
	}

	public void write() throws IOException {
		Files.write(this.path.resolve("index.html"), this.sb.toString()
				.getBytes(Charset.forName("UTF-8")),
				StandardOpenOption.CREATE_NEW);
	}

	private void printHead(String name) {
		this.startTag("head");
		this.startTag("title");
		this.write(name);
		this.endTag("title");
		this.endTag("head");
	}

	public void startRecording(String description) {
		this.startTag("h2");
		this.write(description);
		this.endTag("h2");
		this.startTag("p");
	}

	public void endRecording() {
		this.endTag("p");
	}

	public void addScreenshot(long offset, Calendar timestamp, byte[] image)
			throws IOException {
		Path imagePath = this.path.resolve(System.currentTimeMillis() + ".png");
		Files.write(imagePath, image, StandardOpenOption.CREATE_NEW);

		this.startTag("p");
		this.startTag("h3");
		this.write("Timestamp: "
				+ new SimpleDateFormat("dd.MM.yyyy hh:mm:ss.ssss")
						.format(timestamp.getTime()) + ", Offset: " + offset);
		this.endTag("h3");
		this.sb.append("<img src='").append(imagePath.getFileName())
				.append("' />");
		this.endTag("p");
	}
}
