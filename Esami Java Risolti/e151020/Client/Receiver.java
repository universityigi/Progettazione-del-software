package e151020.Client;

import java.util.Scanner;

class Receiver implements Runnable {
	private final GUI frame;
	private final Scanner input;
	private boolean finished = false;

	Receiver(GUI frame, Scanner input) {
		this.frame = frame;
		this.input = input;
	}


	@Override
	public void run() {

		while (!finished && input.hasNextLine()) {
			String s = input.nextLine();
			System.out.println("Received: " + s);
			switch (s) {
				case "END":
					frame.write("=======Download Completato=======");
					finished = true;
					frame.setStatus(true, false);
					break;
				case "INTERRUPTED":
					frame.write("=======Download Interrotto=======");
					finished = true;
					frame.setStatus(true, false);
					break;
				case "ERROR":
					frame.write("=============Errore============");
					finished = true;
					frame.setStatus(false, false);
					break;
				case "File Not Found":
					frame.write("===========File non trovato==========");
					finished = true;
					frame.setStatus(true, false);
					break;
				default:
					frame.write(s);
					break;
			}
		}


	}
}
