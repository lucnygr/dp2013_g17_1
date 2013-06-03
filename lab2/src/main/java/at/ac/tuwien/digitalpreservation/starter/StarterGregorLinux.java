package at.ac.tuwien.digitalpreservation.starter;

import at.ac.tuwien.digitalpreservation.VirtualMachine;

public class StarterGregorLinux {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		VirtualMachine machine = new VirtualMachine(
				"Linux - Ubuntu 13.04 - 64bit", "test", "test");
		machine.init();
		machine.destroy();
	}

}