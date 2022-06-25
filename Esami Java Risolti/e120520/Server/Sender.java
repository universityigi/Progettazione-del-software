package e120520.Server;

import java.io.PrintWriter;
import java.util.ArrayList;

class Sender  implements Runnable {
    // si occupa solamente di inviare al client le coppie di numeri
    private final ArrayList<Integer> indexList = new ArrayList<>();
    private final PrintWriter output;
    public boolean interrupted = false;

    public Sender(PrintWriter output, ClientHandler cs) {
        this.output = output;
        for (int i=0;i<5;i++) {
            indexList.add(i);
        }
    }

    @Override
    public void run() {
        while (indexList.size()!=0 && !interrupted) {
            int randomIndex = (int) (Math.random()*indexList.size());
            int randomDigit = (int) (Math.random()*10);
            output.println(indexList.get(randomIndex) + ";" + randomDigit);
            output.flush();
            System.out.println("Sent: " + indexList.get(randomIndex) + ";" + randomDigit);
            indexList.remove(randomIndex);
            // l'array indexList viene consumato ad ogni iterazione
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (interrupted) {
            output.println("-1;-1");
            output.flush();
            System.out.println("Forced termination sent");
        } else {
            output.println("*;*");
            output.flush();
            System.out.println("Ending sent");
        }
    }
}
