package e151020.Server;

import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

class Sender  implements Runnable {
    // si occupa solamente di inviare al client le coppie di numeri
    private final ArrayList<Integer> indexList = new ArrayList<>();
    private final PrintWriter output;
    private Scanner input;
    public boolean interrupted = false;

    public Sender(PrintWriter output, String imageX) {
        this.output = output;
        for (int i=0;i<5;i++) {
            indexList.add(i);
        }
        System.out.println(Paths.get("").toAbsolutePath().toString());
        try {
            this.input = new Scanner(this.getClass().getResourceAsStream("/e151020/Server/" + imageX));
            System.out.println("Image Found!");
        } catch (NullPointerException e) {
            System.out.println("File not found");
            output.println("File Not Found");
            output.flush();
        }

    }

    @Override
    public void run() {
        if (input == null) return;
        while (input.hasNextLine() && !interrupted) {
            String s = input.nextLine();
            System.out.println("Sent: " + s);
            output.println(s);
            output.flush();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (interrupted) {
            output.println("INTERRUPTED");
            output.flush();
            System.out.println("Forced termination sent");
        } else {
            output.println("END");
            output.flush();
            System.out.println("Ending sent");
        }
    }
}
