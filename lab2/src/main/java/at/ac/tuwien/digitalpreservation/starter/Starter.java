package at.ac.tuwien.digitalpreservation.starter;

import at.ac.tuwien.digitalpreservation.VirtualMachine;

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
			System.out.println("Not enough parameters, using default parameters.");
			vm = "Windows XP - SP 3 - 32bit";
			url = "127.0.0.1:18083";
			user = "test-vb";
			passwd = "test";
		}
		System.out.println("-vm:"+vm+" url:"+url+" user:"+user+" passwd:"+passwd);
		VirtualMachine machine = new VirtualMachine(
				vm, user, passwd);
		machine.init();
		machine.destroy();
	}

}
