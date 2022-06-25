package e160720.Client;

import javax.swing.*;

class GUI extends JFrame {
	private JPanel rootPane;
	private JTextArea logArea;
	private JTextField ipField;
	private JTextField portField;
	private JButton connectButton;
	private JButton disconnectButton;
	private JTextField cmdField;
	private JButton executeButton;
	private JButton interruptButton;

	private boolean connected = false;
	private boolean transmitting = false;

	GUI() {
		Act act = new Act(this);
		connectButton.addActionListener(act);
		disconnectButton.addActionListener(act);
		executeButton.addActionListener(act);
		interruptButton.addActionListener(act);

		add(rootPane);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		pack();
		update();
		setVisible(true);
	}

	void setStatus(boolean connected, boolean transmitting) {
		this.transmitting = transmitting;
		this.connected = connected;
		update();
	}

	private void update() {
		if (!connected) {
			connectButton.setEnabled(true);
			disconnectButton.setEnabled(false);
			executeButton.setEnabled(false);
			interruptButton.setEnabled(false);
		} else if (!transmitting) {
			connectButton.setEnabled(false);
			disconnectButton.setEnabled(true);
			executeButton.setEnabled(true);
			interruptButton.setEnabled(false);
		} else {
			connectButton.setEnabled(false);
			disconnectButton.setEnabled(false);
			executeButton.setEnabled(false);
			interruptButton.setEnabled(true);
		}
	}

	String getIP() {
		return ipField.getText();
	}

	String getCMD() {
		return cmdField.getText();
	}

	int getPort() {
		return Integer.parseInt(portField.getText());
	}

	void reset() {
		logArea.setText("");
	}

	void println(String s){
		logArea.append(s  + "\n");
	}
}
