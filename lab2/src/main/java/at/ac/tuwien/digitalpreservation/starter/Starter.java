package at.ac.tuwien.digitalpreservation.starter;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.util.List;
import java.util.StringTokenizer;

import at.ac.tuwien.digitalpreservation.VirtualMachine;
import at.ac.tuwien.digitalpreservation.application.Player;
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

		if (!machine.init()) {
			System.out.println("Exiting...");
			return;
		}

		boolean running = true;
		InputStreamReader reader = new InputStreamReader(System.in);
		BufferedReader in = new BufferedReader(reader);

		System.out.println("Ready. Type \"help\" for a list of commands");
		while (running) {
			System.out.println("Enter command:");
			String line = "";
			try {
				line = in.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (line.equalsIgnoreCase("exit")) {
				running = false;
				break;
			} else if (line.equalsIgnoreCase("help")) {
				System.out.println("Valid commands:\n" + "run <gcapname>\n"
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
				File gcap = new File("src/test/resources/" + name + ".xml");
				if (!gcap.isFile()) {
					System.out.println("There is no GCAP named \"" + name
							+ "\"");
					continue;
				}
				
				while (true) {
					Player player = new Player(gcap, machine);
					List<String> recs = player.getRecordingTitles();
					System.out
							.println("Choose one of the following recordings:");
					for (String s : recs) {
						System.out.println("  " + s);
					}
					System.out
							.println("Enter \"back\" to go back to the main menu.");
					String choice;
					try {
						choice = in.readLine();
					} catch (IOException e) {
						e.printStackTrace();
						break;
					}
					if (choice.equalsIgnoreCase("back")) {
						break;
					}
					boolean valid = false;
					for (String s : recs) {
						if (s.equalsIgnoreCase(choice)) {
							player.play(s);
							System.out.println("playing "+name+" - "+choice+" ...");
							while(player.isPlaying()) {
								System.out.print(".");
							}
							System.out.println("\ndone");
							valid = true;
							break;
						}
					}
					if (!valid) {
						System.out.println("There is no such recording");
					}

				}
				continue;
			} else if (command.equalsIgnoreCase("record")) {
				if (!tok.hasMoreTokens()) {
					System.out.println("Invalid command");
					continue;
				}

				String name = tok.nextToken();

				Recorder recorder = new Recorder(machine);

				while (true) {
					System.out.println("Press enter to start recording");
					try {
						in.readLine();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					recorder.startRecording();
					System.out.println("Press enter to stop recording");
					try {
						in.readLine();
						recorder.stopRecording();

						System.out
								.println("Enter description of the recording: ");
						line = in.readLine();
						recorder.finishRecording(line);
						
						System.out.println("Continue recording (Y/N)");
						line = in.readLine();
						if ("n".equalsIgnoreCase(line)) {
							break;
						} 
						
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				System.out.println("Save recordings as " + name);
				recorder.saveGCAP(Paths.get("src/test/resources"), name);

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

	static boolean playRecording(VirtualMachine machine, File gcap,
			String recDescription) {

		return false;
	}

}
