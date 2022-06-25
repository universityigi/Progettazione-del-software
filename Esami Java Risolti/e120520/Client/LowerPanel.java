package e120520.Client;

import javax.swing.*;
import java.awt.event.ActionListener;

class LowerPanel extends JPanel {
    private final JButton connectBtn = new JButton("Connect");
    private final JButton disconnectBtn = new JButton("Disconnect");
    private final JButton clearBtn = new JButton("Clear");

    public LowerPanel(ActionListener act) {
        add(connectBtn);
        add(disconnectBtn);
        add(clearBtn);

        connectBtn.addActionListener(act);
        disconnectBtn.addActionListener(act);
        clearBtn.addActionListener(act);
    }

    public JButton getConnectBtn() {
        return connectBtn;
    }

    public JButton getDisconnectBtn() {
        return disconnectBtn;
    }

    public JButton getClearBtn() {
        return clearBtn;
    }
}
