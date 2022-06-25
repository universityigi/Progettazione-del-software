package e120520.Client;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

class ActionHandler implements ActionListener {
    private final GUI frame;
    private Socket cs;

    public ActionHandler(GUI frame) {
        this.frame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        Object origin = actionEvent.getSource();
        if (origin.getClass().equals(JButton.class)) {
            switch (((JButton) origin).getText()) {
                case "Start":
                    start();
                    break;
                case "Stop":
                    stop();
                    break;
                case "Connect":
                    connect();
                    break;
                case "Disconnect":
                    disconnect();
                    break;
                case "Clear":
                    clear();
                    break;
                default:
                    System.out.println("This shouldn't happen");
            }
        }
    }

    private void start() {
        if (frame.getMp().isFilled()) {
            frame.getMp().resetColor();
            try {
                PrintStream send = new PrintStream(cs.getOutputStream());
                send.println("start");
                send.flush();
                Thread t = new Thread(new Transmitter(new Scanner(cs.getInputStream()), frame));
                t.start();
                frame.setTransmitting(true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void stop() {
        try {
            PrintStream send = new PrintStream(cs.getOutputStream());
            send.println("interrompi");
            send.flush();
            frame.setTransmitting(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void connect() {
        try {
            cs = new Socket(frame.getUp().getIpField().getText(), Integer.parseInt(frame.getUp().getPortField().getText()));
            frame.setConnected(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void disconnect() {
        try {
            PrintStream send = new PrintStream(cs.getOutputStream());
            send.println("disconnect");
            send.flush();

            cs.close();
            frame.setConnected(false);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void clear() {
        frame.getMp().resetColor();
        frame.getMp().resetNumbers();

    }

}
