package at.ac.tuwien.digitalpreservation.starter;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

import at.ac.tuwien.digitalpreservation.VirtualMachine;
import at.ac.tuwien.digitalpreservation.recorder.Recorder;

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
			System.out
					.println("Not enough arguments, using default arguments.");
			vm = "Windows XP - SP 3 - 32bit";
			url = "127.0.0.1:18083";
			user = "test-vb";
			passwd = "test";
		}
		System.out.println("-vm:" + vm + " url:" + url + " user:" + user
				+ " passwd:" + passwd);
		VirtualMachine machine = new VirtualMachine(vm, user, passwd);
		System.out.println("Starting Virtual Machine \"" + vm + "\"...");

		machine.init();

		boolean running = true;
		InputStreamReader reader = new InputStreamReader(System.in);
		BufferedReader in = new BufferedReader(reader);

		System.out.println("Ready. Type \"help\" for a list of commands");
		while (running) {
			String line = "";
			try {
				line = in.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (line.equalsIgnoreCase("exit")) {
				running = false;
			} else if (line.equalsIgnoreCase("help")) {
				System.out.println("Valid commands:\n" + "run <recording>\n"
						+ "record <newrecording>\n" + "exit");
				continue;
			}
			StringTokenizer tok = new StringTokenizer(line, " ");
			if (!tok.hasMoreTokens()) {
				System.out.println("Invalid command");
				continue;
			}
			String command = tok.nextToken();
			if (command.equalsIgnoreCase("run")) {
				if (!tok.hasMoreTokens()) {
					System.out.println("Invalid command");
					continue;
				}

				String name = tok.nextToken();

				playRecording(machine, name);
				continue;
			} else if (command.equalsIgnoreCase("record")) {
				if (!tok.hasMoreTokens()) {
					System.out.println("Invalid command");
					continue;
				}

				String name = tok.nextToken();

				Recorder recorder = new Recorder(machine);
				recorder.startRecording();

				System.out.println("Click enter to stop recording");
				try {
					in.readLine();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				recorder.stopRecording(name);
				recorder.saveGCAP(new File("src/test/resources/" + name
						+ ".xml"));

				continue;
			} else {
				System.out.println("Invalid command");
				continue;
			}
		}
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Shutting down VM...");
		machine.destroy();
		System.out.println("done");
	}

	static boolean playRecording(VirtualMachine machine, String name) {
		// TODO: implement
		return false;
	}

}
