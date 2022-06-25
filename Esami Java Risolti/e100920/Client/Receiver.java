package e100920.Client;

import e100920.TicketCell.TicketCell;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Scanner;

class Receiver implements Runnable {
    private final JTextArea log;
    private final Scanner input;
    private final List<TicketCell> cells;
    private final GUI frame;
    private boolean running = true;

    Receiver(JTextArea log, Scanner input, List<TicketCell> cells, GUI frame) {
        this.log = log;
        this.input = input;
        this.cells = cells;
        this.frame = frame;
    }


    @Override
    public void run() {
        log.append("====Inizio Partita====\n");
        while (running && input.hasNextLine()) {
            String s = input.nextLine();
            System.out.println("Received: " + s);

            if (s.equals("+")) {
                System.out.println("Termination string received");
                log.append("====Fine Partita====\n");
                running = false;
            } else {
                log.append("Estratto: " + s + "\n");
                int greenCells = checkCells(Integer.parseInt(s));
            }
        }
        frame.setStatus(true, false);
    }

    private int checkCells(int v) {
        int res = 0;

        for (TicketCell t : cells) {

            if (t.getValue() == v) {
                t.setSelected(true);
            }
            if (t.isSelected()) {
                res += 1;
            }
        }
        return res;
    }
}
