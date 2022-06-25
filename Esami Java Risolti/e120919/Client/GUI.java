package e120919.Client;

import e120919.PedinaButton.PedinaButton;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

class GUI extends JFrame {
	private JPanel rootPane;
	private JButton startButton;
	private JTextField ipField;
	private JTextField portField;
	private JButton stopButton;
	private JPanel centerGrid;
	private ArrayList<PedinaButton> pedinaList;
	private JButton connectButton;
	private JButton disconnectButton;
	private JButton clearButton;

	private boolean connected = false;
	private boolean transmitting = false;

	private void createUIComponents() {
		centerGrid = new JPanel();
		centerGrid.setLayout(new GridLayout(4,4));
		pedinaList = new ArrayList<>();
		for (int i=0; i<16; i++) {
			PedinaButton p = new PedinaButton();
			pedinaList.add(p);
			centerGrid.add(p);
		}
	}

	GUI() {
		super("CLIENT");
		Act act = new Act(this);
		startButton.addActionListener(act);
		stopButton.addActionListener(act);
		connectButton.addActionListener(act);
		disconnectButton.addActionListener(act);
		clearButton.addActionListener(act);

		add(rootPane);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		pack();
		update();
		setVisible(true);
	}

	private void update() {
		if (!connected) {
			startButton.setEnabled(false);
			stopButton.setEnabled(false);
			connectButton.setEnabled(true);
			disconnectButton.setEnabled(false);
			clearButton.setEnabled(true);
		} else if (!transmitting){
			startButton.setEnabled(true);
			stopButton.setEnabled(false);
			connectButton.setEnabled(false);
			disconnectButton.setEnabled(true);
			clearButton.setEnabled(true);
		} else {
			startButton.setEnabled(false);
			stopButton.setEnabled(true);
			connectButton.setEnabled(false);
			disconnectButton.setEnabled(false);
			clearButton.setEnabled(false);
		}
	}

	public void setStatus(boolean connected, boolean transmitting) {
		this.connected = connected;
		this.transmitting = transmitting;
		update();
	}

	public void setColor(int n, Color c) {
		pedinaList.get(n).setBackground(c);
	}

	String getIP() {
		return ipField.getText();
	}

	int getPort() {
		return Integer.parseInt(portField.getText());
	}

	void declareWinner(Color userColor) {
		boolean userWins = checkWinner(userColor), cpuWins = checkWinner((userColor.equals(Color.YELLOW))?Color.CYAN:Color.YELLOW);

		if (userWins && !cpuWins) {
			JOptionPane.showMessageDialog(this, "Hai vinto!");
		} else if (cpuWins && !userWins) {
			JOptionPane.showMessageDialog(this, "Hai perso :c");
		} else {
			JOptionPane.showMessageDialog(this, "Pareggio");
		}

	}


	boolean checkWinner(Color userColor) {
		// Forse la funzione piu' brutta che io abbia mai scritto in vita mia
		boolean d1 = true;
		boolean d2 = true;
		for (int i=0; i<4; i++) {
			if (pedinaList.get(i).checkColor(userColor)) {
				// columns
				boolean b = true;
				for (int ii=1; ii<4 && b; ii++) {
					if (!pedinaList.get(ii*4+i).checkColor(userColor)) {
						b = false;
						//System.out.println(i +";" + ii*4);
					}
				}
				if (b) return true;
			}

			if (pedinaList.get(i*4).checkColor(userColor)) {
				// rows
				boolean b = true;
				for (int ii=1; ii<4 && b; ii++) {
					if (!pedinaList.get(i*4+ii).checkColor(userColor)) {
						b = false;
						//System.out.println(ii +";" + i*4);
					}
				}
				if (b) return true;
			}

			// Diagonals
			if (d1 && !pedinaList.get(5*i).checkColor(userColor)) {
				d1 = false;
			}
			if (d2 && !pedinaList.get(3*i).checkColor(userColor)) {
				d2 = false;
			}

		}
		return d1||d2;


	}
}
