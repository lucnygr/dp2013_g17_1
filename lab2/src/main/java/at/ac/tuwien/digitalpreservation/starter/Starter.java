package at.ac.tuwien.digitalpreservation.starter;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

import at.ac.tuwien.digitalpreservation.Player;
import at.ac.tuwien.digitalpreservation.VirtualMachine;
import at.ac.tuwien.digitalpreservation.application.Recorder;

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
				System.out.println("Valid commands:\n" + "run <gcapname> <recording>\n"
						+ "record <newgcapname>\n" + "exit");
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

				if (!tok.hasMoreTokens()) {
					System.out.println("Invalid command");
					continue;
				}
				String desc = tok.nextToken();
				
				playRecording(machine, new File("src/test/resources/" + name + ".xml"), desc);
				System.out.println("done");
				continue;
			} else if (command.equalsIgnoreCase("record")) {
				if (!tok.hasMoreTokens()) {
					System.out.println("Invalid command");
					continue;
				}

				String name = tok.nextToken();

				Recorder recorder = new Recorder(machine);

				while (true) {
					recorder.startRecording();
					System.out.println("Press enter to stop recording");
					try {
						in.readLine();
						recorder.stopRecording();

						System.out
								.println("Enter description of the recording: ");
						line = in.readLine();
						recorder.finishRecording(line);

						System.out.println("Continue recording (/exit) [] ?");
						line = in.readLine();
						if ("exit".equalsIgnoreCase(line)) {
							break;
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				File saveFile = new File("src/test/resources/" + name + ".xml");
				System.out.println("Save recordings to " + saveFile);
				recorder.saveGCAP(saveFile);

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

	static boolean playRecording(VirtualMachine machine, File gcap, String recDescription) {
		Player player = new Player(gcap, machine);
		player.play(recDescription);
		return false;
	}

}
