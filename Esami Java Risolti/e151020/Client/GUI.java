package e151020.Client;

import javax.swing.*;
import java.awt.*;

class GUI extends JFrame{
	private JPanel rootPane;
	private JPanel upperPanel;
	private JPanel lowerPanel;
	private JPanel middlePanel;
	private JTextArea display;
	private JTextField ipField;
	private JTextField portField;
	private JButton connectButton;
	private JButton disconnectButton;
	private JComboBox<String> selectionBox;
	private JButton requestButton;
	private JButton stopButton;

	private boolean connected = false;
	private boolean transmitting = false;


	GUI() {
		Act act = new Act(this);
		connectButton.addActionListener(act);
		requestButton.addActionListener(act);
		stopButton.addActionListener(act);
		disconnectButton.addActionListener(act);

		display.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 15));
		updateBtns();

		add(rootPane);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		pack();
		setVisible(true);
	}

	public String getServerAddress() {
		return ipField.getText();
	}

	public int getPort() {
		return Integer.parseInt(portField.getText());
	}

	public String getSelectedImage() {
		return (String) selectionBox.getSelectedItem();
	}

	public void write(String s) {
		display.append(s + "\n");
	}

	public void setStatus(boolean connected, boolean transmitting) {
		this.connected = connected;
		this.transmitting = transmitting;
		updateBtns();
	}

	private void updateBtns() {
		if (!connected) {
			connectButton.setEnabled(true);
			disconnectButton.setEnabled(false);
			requestButton.setEnabled(false);
			stopButton.setEnabled(false);
		} else if (!transmitting) {
			connectButton.setEnabled(false);
			disconnectButton.setEnabled(true);
			requestButton.setEnabled(true);
			stopButton.setEnabled(false);
		} else {
			connectButton.setEnabled(false);
			disconnectButton.setEnabled(false);
			requestButton.setEnabled(false);
			stopButton.setEnabled(true);
		}
	}
}
