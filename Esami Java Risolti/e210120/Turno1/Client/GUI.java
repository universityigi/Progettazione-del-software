package e210120.Turno1.Client;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

class GUI extends JFrame {
	private final JTextArea logArea = new JTextArea();
	private final ArrayList<JButton> startBtns = new ArrayList<>();
	private final JButton stopButton = new JButton("Interrompi");
	private final JButton connectButton = new JButton("Connect");
	private final JButton disconnectButton = new JButton("Disconnect");
	private final JTextField ipField = new JTextField("80.211.232.219");
	private final JTextField portField = new JTextField("4400");

	private boolean connected = false;
	private boolean transmitting = false;


	GUI(String nome, String cognome, String matricola) {
		super(nome + " " + cognome + " " + matricola);

		// Populate Start Buttons
		for (int i=1; i<5; i++) {
			startBtns.add(new JButton("Stazione " + i));
		}

		setLayout(new BorderLayout());

		//Populate side bar
		JPanel sidebar = new JPanel();
		sidebar.setName("Stazione");
		sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));

		for (JButton btn: startBtns) {
			sidebar.add(btn);
		}
		sidebar.add(stopButton);
		add(sidebar, BorderLayout.WEST);

		//Populate center panel
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new BorderLayout());
		JScrollPane scrollPane = new JScrollPane(logArea);
		logArea.setColumns(35);
		logArea.setRows(15);
		centerPanel.add(scrollPane, BorderLayout.CENTER);
		add(centerPanel, BorderLayout.CENTER);

		// Populate bottom bar
		JPanel bottomBar = new JPanel();
		bottomBar.add(new JLabel("Server Address"));
		bottomBar.add(ipField);
		bottomBar.add(new JLabel("Port"));
		bottomBar.add(portField);
		bottomBar.add(connectButton);
		bottomBar.add(disconnectButton);
		add(bottomBar, BorderLayout.SOUTH);

		// Add action Listener
		Act act = new Act(this);
		for (JButton btn : startBtns) {
			btn.addActionListener(act);
		}
		stopButton.addActionListener(act);
		connectButton.addActionListener(act);
		disconnectButton.addActionListener(act);


		update();
		setSize(800, 400);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}

	public String getIP() {
		return ipField.getText();
	}

	public int getPort() {
		return Integer.parseInt(portField.getText());
	}

	public void println(String s) {
		logArea.append(s + "\n");
	}

	public void resetText() {
		logArea.setText("");
	}

	public void setStatus(boolean connected, boolean transmitting) {
		this.connected = connected;
		this.transmitting = transmitting;
		update();
	}

	private void update() {
		if (!connected) {
			for (JButton btn : startBtns) {
				btn.setEnabled(false);
			}
			stopButton.setEnabled(false);
			connectButton.setEnabled(true);
			disconnectButton.setEnabled(false);
		} else if (!transmitting) {
			for (JButton btn : startBtns) {
				btn.setEnabled(true);
			}
			stopButton.setEnabled(false);
			connectButton.setEnabled(false);
			disconnectButton.setEnabled(true);
		} else {
			for (JButton btn : startBtns) {
				btn.setEnabled(false);
			}
			stopButton.setEnabled(true);
			connectButton.setEnabled(false);
			disconnectButton.setEnabled(false);
		}
	}

	// GETTERS
	public boolean isInStartBtns( JButton btn ) {
		for (JButton sBtn : startBtns) {
			if (sBtn.equals(btn)) return true;
		}
		return false;
	}

	public JButton getStopButton() {
		return stopButton;
	}

	public JButton getConnectButton() {
		return connectButton;
	}

	public JButton getDisconnectButton() {
		return disconnectButton;
	}

}
