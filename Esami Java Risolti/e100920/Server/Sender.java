package e100920.Server;

import java.io.PrintWriter;
import java.util.ArrayList;

class Sender implements Runnable {
    // si occupa solamente di inviare al client le coppie di numeri
    private final ArrayList<Integer> numberList = new ArrayList<>();
    private final PrintWriter output;
    public boolean interrupted = false;

    public Sender(PrintWriter output, ClientHandler cs) {
        this.output = output;
        for (int i=1; i<=90; i++) {
            numberList.add(i);
        }
    }

    @Override
    public void run() {
        while (numberList.size()>60 && !interrupted) {
            int randomNumber = (int) (Math.random()*numberList.size());
            output.println(numberList.get(randomNumber));
            output.flush();
            System.out.println("Sent: " + numberList.get(randomNumber));
            numberList.remove(randomNumber);
            // l'array numberList viene consumato ad ogni iterazione
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (interrupted) {
            output.println("+");
            output.flush();
            System.out.println("Forced termination sent");
        } else {
            output.println("+");
            output.flush();
            System.out.println("Ending sent");
        }
    }
}
