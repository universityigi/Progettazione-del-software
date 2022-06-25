package e210120.Turno1.Client;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Locale;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

class Act implements ActionListener {
	private final GUI frame;
	private static Logger log = Logger.getLogger("Action Listener");
	private Socket socket;
	private PrintWriter output;

	Act(GUI frame) {
		this.frame = frame;
	}

	@Override
	public void actionPerformed(ActionEvent actionEvent) {
		if (actionEvent.getSource().getClass().equals(JButton.class)) {
			JButton src = (JButton) actionEvent.getSource();
			if (src.equals(frame.getConnectButton())) {
				connect();
			} else if (src.equals(frame.getDisconnectButton())) {
				disconnect();
			} else if (src.equals(frame.getStopButton())) {
				stop();
			} else if (frame.isInStartBtns(src)) {
				start(src);
			} else {
				throw new RuntimeException("Couldn't recognize button");
			}
		}

	}

	private void connect() {
		try {
			socket = new Socket(frame.getIP(), frame.getPort());
			output = new PrintWriter(socket.getOutputStream());
			frame.setStatus(true, false);
			frame.resetText();
			log.log(Level.INFO, "Connection established");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void start(JButton src) {
		String s = src.getText().toLowerCase(Locale.ROOT).replace(" ", "");
		log.log(Level.INFO, "Requested: " + s);
		frame.setStatus(true, true);
		frame.println("======Download Iniziato=====");
		output.println("start:" + s);
		output.flush();

		try {
			Scanner sc = new Scanner(socket.getInputStream(), "UTF-8");
			Thread t = new Thread(new Receiver(frame, sc));
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

	private void disconnect() {
		output.println("disconnect");
		output.flush();
		log.log(Level.INFO, "Sent: stop");
		try {
			socket.close();
			log.log(Level.INFO, "Connection Terminated");
			frame.setStatus(false, false);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
