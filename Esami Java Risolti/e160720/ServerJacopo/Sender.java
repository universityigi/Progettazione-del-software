package e160720.ServerJacopo;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

class Sender implements Runnable{
    
    private String file;
    private PrintWriter out;
    private Scanner scan;
    private FileReader reader;
    private String string;
    private boolean finish = false;
    
    private Logger logger = Logger.getLogger("Sender");
    
    public Sender(PrintWriter o, String f){
        out = o;
        file = f;
    }
    
    public void run(){

        try {
            reader = new FileReader(file);
        } catch (FileNotFoundException ex) {
            logger.log(Level.WARNING,"ERROR: FILE NOT FOUND");
            finish = true;
            out.println("File Not Found");
            out.flush();
        }
        scan = new Scanner(reader);
        while(scan.hasNextLine() && !finish) {
            string = scan.nextLine();
            out.println(string);
            out.flush();
            if (string.equals("END")) {
                finish = true;
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                logger.log(Level.WARNING, "ERROR: INTERRUPTED SLEEP THREAD");
            }

        }
    }
    
    public void stopThread(){
        finish = true;
    }
}
