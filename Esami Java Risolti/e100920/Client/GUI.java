package e100920.Client;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import e100920.TicketCell.TicketCell;

class GUI extends JFrame {
    private ArrayList<TicketCell> cells;
    private JPanel rootPanel;
    private JPanel upperPart;
    private JTextField ipField;
    private JTextField portField;
    private JButton connectButton;
    private JButton disconnectButton;
    private JPanel sideBar;
    private JTextArea logArea;
    private JPanel bottomPanel;
    private JButton startButton;
    private JButton stopButton;
    private JPanel centerGrid;
    private final Act act = new Act(this);

    private boolean connected = false;
    private boolean transmitting = false;

    public GUI() {
        setContentPane(rootPanel);

        connectButton.addActionListener(act);
        disconnectButton.addActionListener(act);
        stopButton.addActionListener(act);
        startButton.addActionListener(act);
        update();
        pack();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

    }

    private void createUIComponents() {
        // This function runs before the constructor
        List<Integer> randNums = generateRandomArrayOrdered();

        cells = new ArrayList<>();
        centerGrid = new JPanel();
        centerGrid.setLayout(new GridLayout(3, 5));
        for (int i = 0; i < 15; i++) {
            TicketCell t = new TicketCell(randNums.get(i));
            cells.add(t);
            centerGrid.add(t);
        }
    }

    public List<TicketCell> getCells() {
        return cells;
    }

    public JTextArea getLogArea() {
        return logArea;
    }

    public String getIP() {
        return ipField.getText();
    }

    public int getPort() {
        return Integer.parseInt(portField.getText());
    }

    public void setStatus(boolean connected, boolean transmitting) {
        this.transmitting = transmitting;
        this.connected = connected;
        update();
    }

    public void reset() {
        for (TicketCell t : cells) {
            t.setSelected(false);
        }
    }


    private void update() {
        if (!connected) {
            connectButton.setEnabled(true);
            disconnectButton.setEnabled(false);
            startButton.setEnabled(false);
            stopButton.setEnabled(false);
        } else if (!transmitting) {
            connectButton.setEnabled(false);
            disconnectButton.setEnabled(true);
            startButton.setEnabled(true);
            stopButton.setEnabled(false);
        } else {
            connectButton.setEnabled(false);
            disconnectButton.setEnabled(false);
            startButton.setEnabled(false);
            stopButton.setEnabled(true);
        }
    }

    private static List<Integer> generateRandomArrayOrdered() {
        //generate random array with elements from 1 to 90 (included)
        ArrayList<Integer> arr = new ArrayList<>();
        arr.add((int) (Math.random()*90+1));
        for (int i=0; i<15; i++) {
            int rand;
            do {
                rand = (int) (Math.random()*90+1);
            } while (arr.contains(rand));
            arr.add(rand);
        }
        Collections.sort(arr);
        return arr;
    }


}
