package e160720.Client;

import java.util.Scanner;

class Receiver implements Runnable {
	private final Scanner input;
	private final GUI frame;
	private boolean running = true;

	Receiver(Scanner input, GUI frame) {
		this.input = input;
		this.frame = frame;
	}

	@Override
	public void run() {
		while (running && input.hasNextLine()) {
			String s = input.nextLine();
			System.out.println("Received: " + s);
			switch (s) {
				case "END":
					frame.println("=====Download Completato=====");
					running = false;
					break;
				case "INTERRUPTED":
					frame.println("=====Download Interrotto=====");
					running = false;
					break;
				case "ERROR":
					frame.println("===ERRORE: Disconnetti e riconnetti===");
					break;
				default:
					frame.println(s);


			}
		}
		frame.setStatus(true, false);

	}

}
