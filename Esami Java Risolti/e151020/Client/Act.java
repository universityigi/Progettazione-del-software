package e151020.Client;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

class Act implements ActionListener {
	private final GUI frame;
	private Socket socket;
	private PrintWriter output;

	Act(GUI frame) {
		this.frame = frame;
	}

	@Override
	public void actionPerformed(ActionEvent actionEvent) {
		if (actionEvent.getSource().getClass().equals(JButton.class)) {
			JButton btn = (JButton) actionEvent.getSource();
			switch (btn.getText()) {
				case "Connect":
					connect();
					break;
				case "Request":
					start();
					break;
				case "Stop":
					stop();
					break;
				case "Disconnect":
					disconnect();
					break;
			}
		}
	}

	void connect() {
		try {
			socket = new Socket(frame.getServerAddress(), frame.getPort());
			output = new PrintWriter(socket.getOutputStream());
			frame.write("~~~~~~~~~~Connessione Avvenuta~~~~~~~~~~~");
			frame.setStatus(true, false);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	void start() {
		output.println("start:" + frame.getSelectedImage());
		output.flush();
		frame.setStatus(true,  true);
		frame.write("========Download Iniziato========");
		try {
			Thread t = new Thread(new Receiver(frame, new Scanner(socket.getInputStream())));
			t.start();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	void stop() {
		output.println("stop");
		output.flush();
		frame.setStatus(true, false);
	}

	void disconnect() {
		output.println("disconnect");
		output.flush();
		try {
			socket.close();
			frame.setStatus(false, false);
			frame.write("~~~~~~~~~~~~Fine Connessione~~~~~~~~~");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
