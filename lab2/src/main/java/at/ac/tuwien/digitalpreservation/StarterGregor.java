package at.ac.tuwien.digitalpreservation;

public class StarterGregor {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		VirtualMachine machine = new VirtualMachine(
				"Linux - Ubuntu 13.04 - 64bit");
		machine.init();
		machine.destroy();
	}

}
