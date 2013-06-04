package at.ac.tuwien.digitalpreservation.application;

public class HtmlGenerator {

	public void doIt() {
		StringBuilder sb = new StringBuilder();
		sb.append("<html>");

		this.addHead(sb);
		this.addBody(sb);

		sb.append("</html>");
	}

	private void addHead(StringBuilder sb) {
		sb.append("<head>");

		sb.append("</head>");
	}

	private void addBody(StringBuilder sb) {
		sb.append("<body>");

		sb.append("</body>");
	}

	private void addScreenshot(StringBuilder sb) {
		sb.append("<img>");

		sb.append("</img>");
	}
}
