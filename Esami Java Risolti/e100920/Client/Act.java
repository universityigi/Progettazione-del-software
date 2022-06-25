package e100920.Client;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

class Act implements ActionListener {
    private final GUI frame;
    private final static Logger log = Logger.getLogger("ActionListener");
    private Socket cs;

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
                case "Disconnect":
                    disconnect();
                    break;
                case "Start":
                    start();
                    break;
                case "Stop":
                    stop();
                    break;
            }
        }
    }

    private void connect() {
        try {
            cs = new Socket(frame.getIP(), frame.getPort());
            log.log(Level.INFO, "Connection Established");
            frame.setStatus(true, false);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void start() {
        try {
            PrintWriter pw = new PrintWriter(cs.getOutputStream());
            pw.println("start");
            pw.flush();
            log.log(Level.INFO, "Sent: start");
            frame.setStatus(true, true);
            frame.reset();

            Thread t = new Thread(new Receiver(frame.getLogArea(), new Scanner(cs.getInputStream()), frame.getCells(), frame));
            t.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void stop() {
        try {
            PrintWriter pw = new PrintWriter(cs.getOutputStream());
            pw.println("stop");
            pw.flush();
            log.log(Level.INFO, "Sent: stop");
            frame.setStatus(true, false);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void disconnect() {
        try {
            PrintWriter pw = new PrintWriter(cs.getOutputStream());
            pw.println("disconnect");
            pw.flush();
            log.log(Level.INFO, "Sent: disconnect");
            cs.close();
            frame.setStatus(false, false);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
