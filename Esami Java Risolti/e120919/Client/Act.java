package e120919.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

class Act implements ActionListener {
	private final GUI frame;
	private Socket socket;
	private static Logger log = Logger.getLogger("Action listener");
	private PrintWriter output;

	public Act(GUI frame) {
		this.frame = frame;
	}


	@Override
	public void actionPerformed(ActionEvent actionEvent) {
		if (actionEvent.getSource().getClass().equals(JButton.class)) {
			JButton src = (JButton) actionEvent.getSource();
			switch (src.getText()) {
				case "Connect":
					connect();
					break;
				case "Start":
					start();
					break;
				case "Stop":
					stop();
					break;
				case "Disconnect":
					disconnect();
					break;
				case "Clear":
					clear();
					break;

			}
		}
	}

	private void connect() {
		try {
			socket = new Socket(frame.getIP(), frame.getPort());
			output = new PrintWriter(socket.getOutputStream());
			log.log(Level.INFO, "Connection established");
			frame.setStatus(true, false);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void disconnect() {
		output.println("disconnect");
		output.flush();
		try {
			socket.close();
			log.log(Level.INFO, "Connnection terminated by the user");
			frame.setStatus(false, false);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void start() {
		clear();
		String[] colors = {"yellow", "cyan"};
		int userChoice = JOptionPane.showOptionDialog(
				frame,
				"Choose your color",
				"Color choice",
				JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE,
				null,
				colors,
				colors[0]);
		String userColor = colors[userChoice];
		output.println("start");
		output.flush();
		try {
			Thread t = new Thread(new Receiver(frame, new Scanner(socket.getInputStream()), userColor));
			t.start();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void stop() {
		output.println("stop");
		output.flush();
		log.log(Level.INFO, "Sent: stop");

	}

	private void clear() {
		for (int i=0; i<16; i++) {
			frame.setColor(i, Color.LIGHT_GRAY);
		}
		log.log(Level.INFO, "Board cleared");
	}
}
