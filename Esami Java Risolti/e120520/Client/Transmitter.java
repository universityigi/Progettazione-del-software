package e120520.Client;

import javax.swing.*;
import java.util.Scanner;

class Transmitter implements Runnable {
    private final Scanner input;
    public boolean running = true;
    private final GUI frame;

    public Transmitter(Scanner scan, GUI frame) {
        input = scan;
        this.frame = frame;
    }


    @Override
    public void run() {
        System.out.println("Started receiving");

        while (running) {
            if (input.hasNextLine()) {

                String[] s = input.nextLine().split(";");

                System.out.println("Received: " +  s[0] + ";" + s[1]);
                if (s[0].equals("-1")) {
                    running = false;
                    frame.setTransmitting(false);

                    JOptionPane.showMessageDialog(null,"Ti sei ritirato, hai perso");
                } else if (s[0].equals("*")) {
                    running = false;
                    frame.setTransmitting(false);

                    frame.declareWinner();
                } else {
                    frame.colorField(Integer.parseInt(s[0]), Integer.parseInt(s[1]));
                }
            }

        }
        System.out.println("Stopped receiving");

    }
}
