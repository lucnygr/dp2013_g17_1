package at.ac.tuwien.digitalpreservation.starter;

public class Starter {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		
		
		String vm = null;
		String url = null;
		String user = null;
		String passwd = null;
		
		for (int i = 0; i < args.length; i++) {
			if ("-vm".equals(args[i]))
				vm = args[++i];
			else if ("-url".equals(args[i]))
				url = args[++i];
			else if ("-user".equals(args[i]))
				user = args[++i];
			else if ("-passwd".equals(args[i]))
				passwd = args[++i];
		}
		if (vm == null || url == null || user == null || passwd == null) {
			vm = "";
			url = "127.0.0.1:
		}

	}

}
