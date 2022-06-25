package e180620.Client;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

class ActionHandler implements ActionListener {
    private static Logger log = Logger.getLogger("Action Handler");
    private final GUI frame;
    private Socket cs;

    ActionHandler(GUI frame) {
        this.frame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource().getClass().equals(JButton.class)) {
            switch ( ( (JButton) actionEvent.getSource()).getText()) {
                case "Connect":
                    connect();
                    break;
                case "Start":
                    start();
                    break;
                case "Stop":
                    stop();
                    break;
                case "Disconnect":
                    disconnect();
                    break;
                case "Rivela":
                    ActionHandler.reveal(frame);
                    break;
            }
        }
    }

    private void connect() {
        try {
            cs = new Socket(frame.getIpField().getText(), Integer.parseInt(frame.getPortField().getText()));
            log.log(Level.INFO, "Connection established");
            frame.setConnected(true);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void start() {
        try {
            PrintWriter bw = new PrintWriter(cs.getOutputStream());
            bw.println("start");
            bw.flush();
            frame.resetBoard();
            frame.setTransmitting(true);
            log.log(Level.INFO, "Sent: start");

            Thread t = new Thread(new Receiver(new Scanner(cs.getInputStream()), frame));
            t.start();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private void stop() {
        try {
            PrintWriter bw = new PrintWriter(cs.getOutputStream());
            bw.println("stop");
            bw.flush();
            frame.setTransmitting(false);
            frame.toggleBoard(false);
            log.log(Level.INFO, "Sent: stop");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void disconnect() {
        try {
            PrintWriter bw = new PrintWriter(cs.getOutputStream());
            bw.println("disconnect");
            bw.flush();
            log.log(Level.INFO, "Sent: disconnect");

            cs.close();

            frame.setConnected(false);
            frame.toggleBoard(false);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void reveal(GUI frame) {
        log.log(Level.INFO, "Revealed");

        for (int i=0;i<10;i++) {
            for (int j=0; j<10; j++) {
                frame.getCell(i,j).setSelected(true);
            }
        }

    }
}
