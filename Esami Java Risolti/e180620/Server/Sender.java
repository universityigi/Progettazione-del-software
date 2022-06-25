package e180620.Server;

import java.io.PrintWriter;
import java.util.Arrays;

class Sender  implements Runnable {
    // si occupa solamente di inviare al client le coppie di numeri
    boolean running = true;
    private static final double MINES_NUMBER = 5;
    private final PrintWriter output;
    boolean interrupted = false;
    private final int[][] grid = new int[10][10];

    public Sender(PrintWriter output, ClientHandler cs) {
        this.output = output;
        populateGrid();
        System.out.println(Arrays.deepToString(grid));
    }

    @Override
    public void run() {
        for (int i=0; i<10 && !interrupted; i++) {
            for (int j=0; j<10 && !interrupted; j++) {
                output.println(i + ":" + j + ":" + grid[i][j]);
                System.out.println("Sent: " + i + ":" + j + ":" + grid[i][j]);
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        if (interrupted) {
            output.println("interrupted");
            output.flush();
            System.out.println("Forced termination sent");
        } else {
            output.println("done");
            output.flush();
            System.out.println("Ending sent");
        }
        running = false;
    }

    private void populateGrid() {
        for (int i=0;i<10; i++) {
            for (int j=0; j<10; j++) {
                grid[i][j] = (Math.random()>(MINES_NUMBER/100.0))?0:-1;
            }
        }

        for (int i=0; i<10; i++) {
            for (int j=0; j<10; j++) {
                if (grid[i][j] != -1) {
                    for (int ii=-1; ii<=1; ii++) {
                        for (int jj=-1; jj<=1; jj++) {
                            if (i+ii<10 && i+ii>=0 && j+jj<10 && j+jj>=0) {
                                grid[i][j] += (grid[i+ii][j+jj]<0)?1:0;
                            }
                        }
                    }
                }
            }
        }
    }
}
