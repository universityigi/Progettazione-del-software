package e120919.Client;

import javax.swing.*;
import java.awt.*;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Receiver implements Runnable {
	private final GUI frame;
	private final Scanner input;
	private static Logger log = Logger.getLogger("Receiver");
	private boolean running = true;
	private final Color userColor;

	public Receiver(GUI frame, Scanner input, String userColor) {
		this.frame = frame;
		this.input = input;
		this.userColor = toColor(userColor);
	}


	@Override
	public void run() {
		frame.setStatus(true, true);
		while ( running && input.hasNextLine() ) {
			String s = input.nextLine();
			log.log(Level.INFO, "Received: " + s);
			switch (s) {
				case "-1":
					log.log(Level.INFO, "Interruption Received");
					running = false;
					JOptionPane.showMessageDialog(frame, "Ti sei ritirato, hai perso");
					break;
				case "*":
					log.log(Level.INFO, "Termination Received");
					running = false;
					frame.declareWinner(userColor);
					break;
				default:
					String[] v = s.split(";");
					frame.setColor(Integer.parseInt(v[0]), toColor(v[1]));
			}

		}
		frame.setStatus(true, false);


	}

	private static Color toColor(String s) {
		if (s.equals("cyan")) {
			return Color.CYAN;
		} else if (s.equals("yellow")) {
			return Color.YELLOW;
		}

		log.log(Level.WARNING, "Can't recognize Color");
		return Color.LIGHT_GRAY;
	}
}
