package e120520.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

class GUI extends JFrame {
    private final ActionListener act = new ActionHandler(this);
    private final LowerPanel lp = new LowerPanel(act);
    private final MiddlePanel mp = new MiddlePanel();
    private final UpperPanel up = new UpperPanel(act);

    private boolean connected = false;
    private boolean transmitting = false;


    public GUI(String nome, String cognome, String matricola) {
        setTitle(nome + " " + cognome + " " + matricola);
        setLayout(new BorderLayout());
        add(up, BorderLayout.NORTH);
        add(mp, BorderLayout.CENTER);
        add(lp, BorderLayout.SOUTH);



        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 200);
        update();
        setVisible(true);
    }

    public ActionListener getAct() {
        return act;
    }

    public LowerPanel getLp() {
        return lp;
    }

    public MiddlePanel getMp() {
        return mp;
    }

    public UpperPanel getUp() {
        return up;
    }

    public boolean isConnected() {
        return connected;
    }

    public boolean isTransmitting() {
        return transmitting;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
        update();
    }

    public void setTransmitting(boolean transmitting) {
        this.transmitting = transmitting;
        update();
    }

    private void update() {
        if (!connected) {
            lp.getConnectBtn().setEnabled(true);
            lp.getDisconnectBtn().setEnabled(false);
            lp.getClearBtn().setEnabled(true);
            up.getStartBtn().setEnabled(false);
            up.getStopBtn().setEnabled(false);
            mp.enableChange();
        } else if (connected && !transmitting) {
            lp.getConnectBtn().setEnabled(false);
            lp.getDisconnectBtn().setEnabled(true);
            lp.getClearBtn().setEnabled(true);
            up.getStartBtn().setEnabled(true);
            up.getStopBtn().setEnabled(false);
            mp.enableChange();
        } else if (connected && transmitting) {
            lp.getConnectBtn().setEnabled(false);
            lp.getDisconnectBtn().setEnabled(false);
            lp.getClearBtn().setEnabled(false);
            up.getStartBtn().setEnabled(false);
            up.getStopBtn().setEnabled(true);
            mp.disableChange();
        }

    }

    public void declareWinner() {
        if (mp.countGreenNumbers() > 0) {
            JOptionPane.showMessageDialog(null, "Hai vinto!");
        } else {
            JOptionPane.showMessageDialog(null, "Hai perso :c");
        }
    }

    public void colorField(int i, int value) {
        NumberField field = mp.getFieldList().get(i);
        Color c = (value==Integer.parseInt(field.getText()))?Color.GREEN:Color.RED;
        field.changeColor(c);
    }
}
