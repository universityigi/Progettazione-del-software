package e160720.Client;

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
			JButton src = (JButton) actionEvent.getSource();
			switch (src.getText()) {
				case "Connect":
					connect();
					break;
				case "Disconnect":
					disconnect();
					break;
				case "Execute":
					execute();
					break;
				case "Interrupt":
					interrupt();
					break;
			}
		}
	}

	private void connect() {
		frame.reset();
		try {
			socket = new Socket(frame.getIP(), frame.getPort());

			frame.println("=====Connessione Avvenuta=====");
			frame.setStatus(true, false);
		} catch (IOException e) {
			frame.println("=====Connessione Fallita======");
			frame.println(e.getMessage());
		}

	}

	private void execute() {
		try {
			output = new PrintWriter(socket.getOutputStream());
			output.println(frame.getCMD());
			output.flush();
			frame.setStatus(true,true);

			Thread t = new Thread(new Receiver(new Scanner(socket.getInputStream()), frame));
			t.start();

		} catch (IOException e) {
			e.printStackTrace();
		}


	}

	private void interrupt() {
		output.println("INTERRUPT");
		output.flush();
		frame.setStatus(true, false);
	}

	private void disconnect() {
		output.println("DISCONNECT");
		output.flush();
		try {
			socket.close();
			frame.println("======Connessione Terminata=====");
			frame.setStatus(false, false);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
