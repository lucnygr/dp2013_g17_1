package at.ac.tuwien.digitalpreservation.starter;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.ac.tuwien.digitalpreservation.VirtualMachine;
import at.ac.tuwien.digitalpreservation.application.Player;
import at.ac.tuwien.digitalpreservation.application.Recorder;
import at.ac.tuwien.digitalpreservation.util.PathUtils;
import ch.qos.logback.classic.Level;

public class Starter {

	public static final String GCAPPATH = "recordings/";

	private static final Logger LOGGER = LoggerFactory.getLogger(Starter.class);

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		String vm = null;
		String url = "127.0.0.1:18083";
		String user = "user";
		String passwd = "";
		boolean debug = false;
		System.out.println("Usage: -vm [VM-name] -url [url:port] -user [user] -passwd [password] -debug");
		System.out.println("Note 1: The VM cannot have spaces in its name.");
		System.out.println("Note 2: If no VM is specified the 1st VM in the list of VirtualBox will be picked.");
		System.out.println("Note 3: Any parameters that aren't specified wll be replaced by default values:");
		System.out.println("-vm "+null+"(1st VM) -url "+url+" -user "+user+" -password (blank) and no debug mode.");
		
		for (int i = 0; i < args.length; i++) {
			if ("-vm".equalsIgnoreCase(args[i])) {
				vm = args[++i];
			} else if ("-url".equalsIgnoreCase(args[i])) {
				url = args[++i];
			} else if ("-user".equalsIgnoreCase(args[i])) {
				user = args[++i];
			} else if ("-passwd".equalsIgnoreCase(args[i])) {
				passwd = args[++i];
			} else if ("-debug".equalsIgnoreCase(args[i])) {
				debug = true;
			}
		}

		if (debug == true) {
			ch.qos.logback.classic.Logger root = (ch.qos.logback.classic.Logger) org.slf4j.LoggerFactory
					.getLogger(ch.qos.logback.classic.Logger.ROOT_LOGGER_NAME);
			root.setLevel(Level.ALL);
		} else {
			ch.qos.logback.classic.Logger root = (ch.qos.logback.classic.Logger) org.slf4j.LoggerFactory
					.getLogger(ch.qos.logback.classic.Logger.ROOT_LOGGER_NAME);
			root.setLevel(Level.INFO);
		}

		LOGGER.debug("-vm: {} -url: {} -user: {} -passwd: {}", vm, url, user,
				passwd);

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
				LOGGER.error("Error reading input", e);
			}

			if ("exit".equalsIgnoreCase(line)) {
				running = false;
				break;
			} else if ("help".equalsIgnoreCase(line)) {
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
					System.out
							.println("Command run needs a parameter <gcapname>");
					continue;
				}

				String name = tok.nextToken();
				File gcap = new File(GCAPPATH + name + ".xml");
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
					String choice = null;
					try {
						choice = in.readLine();
					} catch (IOException e) {
						e.printStackTrace();
						break;
					}
					if ("back".equalsIgnoreCase(choice)) {
						break;
					}
					boolean valid = false;
					for (String s : recs) {
						if (s.equalsIgnoreCase(choice)) {
							player.play(s);
							System.out.println("playing " + name + " - "
									+ choice + " ...");
							while (player.isPlaying()) {
								// System.out.print(".");

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
			} else if ("record".equalsIgnoreCase(command)) {
				if (!tok.hasMoreTokens()) {
					System.out
							.println("Command record needs a parameter <newgcapname>");
					continue;
				}

				String name = tok.nextToken();

				Recorder recorder = new Recorder(machine);

				while (true) {
					Set<String> names = new HashSet<String>();
					boolean takeScreenshotOnMouseclick = false;
					try {
						System.out
								.println("Take screenshot on mouseclick events (Y/N)");
						line = in.readLine();
						if ("y".equalsIgnoreCase(line)) {
							takeScreenshotOnMouseclick = true;
						}

						System.out.println("Press enter to start recording");
						in.readLine();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					recorder.startRecording(takeScreenshotOnMouseclick);
					System.out.println("Press enter to stop recording");
					try {
						in.readLine();
						recorder.stopRecording();
						
						boolean desc_ok = false;
						while(!desc_ok) {
							System.out.println("Enter description of the recording: ");
							line = in.readLine();
							if (names.contains(line) || line == null || line.equals("")) {
								desc_ok = false;
								System.out.println("Invalid Description!");
							} else {
								names.add(line);
								desc_ok = true;
							}
						}
						
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
				Path savePath;
				try {
					savePath = PathUtils.createDirectoryRecursive(Paths
							.get(GCAPPATH));
					recorder.saveGCAP(savePath, name);
				} catch (IOException e) {
					System.err.println("Could not save recording to directory"
							+ GCAPPATH);
					LOGGER.error("Could not save recording to directory"
							+ GCAPPATH, e);
				}

				continue;
			} else {
				System.out.println("Invalid command");
				continue;
			}
		}
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
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
