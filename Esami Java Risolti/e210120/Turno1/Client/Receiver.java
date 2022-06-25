package e210120.Turno1.Client;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

class Receiver implements Runnable {
	private final GUI frame;
	private final Scanner input;
	private boolean running = true;

	Receiver(GUI frame, Scanner input) {
		this.frame = frame;
		this.input = input;
	}


	@Override
	public void run() {
		while (running && input.hasNextLine()) {
			String s = new String(input.nextLine().getBytes(StandardCharsets.UTF_8), Charset.forName("UTF-8"));
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
					frame.println("=====!!!ERRORE!!!=====");
					running = false;
					break;
				default:
					frame.println(s);
			}
		}
		frame.setStatus(true, false);
	}
}
