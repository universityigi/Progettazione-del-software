package e120520.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

class UpperPanel extends JPanel {
    private final JButton startBtn = new JButton("Start");
    private final JTextField ipField = new JTextField();
    private final JTextField portField = new JTextField();
    private final JButton stopBtn = new JButton("Stop");

    public UpperPanel(ActionListener act) {
        setLayout(new FlowLayout());

        ipField.setText("127.0.0.1"); //useful for testing purpose
        portField.setText("4400");

        add(startBtn);
        add(new JLabel("IP Address"));
        add(ipField);
        add(new JLabel("Port"));
        add(portField);
        add(stopBtn);

        startBtn.addActionListener(act);
        stopBtn.addActionListener(act);



    }

    public JButton getStartBtn() {
        return startBtn;
    }

    public JTextField getIpField() {
        return ipField;
    }

    public JTextField getPortField() {
        return portField;
    }

    public JButton getStopBtn() {
        return stopBtn;
    }
}
