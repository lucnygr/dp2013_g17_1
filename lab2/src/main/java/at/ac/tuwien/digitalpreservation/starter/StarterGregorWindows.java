package at.ac.tuwien.digitalpreservation.starter;

import at.ac.tuwien.digitalpreservation.VirtualMachine;

public class StarterGregorWindows {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		VirtualMachine machine = new VirtualMachine(
				"Windows XP - SP 3 - 32bit", "test-VB", "");
		machine.init();
		machine.destroy();
	}

}
