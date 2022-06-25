package e160720.ServerJacopo;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

class Server implements Runnable {
    private Gui mainFrame;
    private ServerSocket server;
    private Socket socket;
    private Scanner scan;
    private PrintWriter out;
    
    private boolean finish = false;
    private String command;
    private File folder;
    private File[] listOfFiles;
    private Sender sender;
    private Thread thread;
    
    private Logger logger = Logger.getLogger("ServerDownloader");
    
    public Server(int porta, Gui m) throws IOException{
        server = new ServerSocket(porta);
        mainFrame = m;  
    }

    @Override
    public void run() {
        while(!finish){
            logger.log(Level.INFO,"waiting a client...");
            try {
                
                socket = server.accept();
            } catch (IOException ex) {
                logger.log(Level.INFO,"connection non established");
                ex.printStackTrace();
            }
            
            try {
                scan = new Scanner(socket.getInputStream());
                out = new PrintWriter(socket.getOutputStream());
            } catch (IOException ex) {
                logger.log(Level.WARNING,"ERROR: STREAM NOT OPEN");
                ex.printStackTrace();
            }
           
            
            folder = new File(Paths.get("").toAbsolutePath().toString()+"/e160720/ServerJacopo/");
            listOfFiles = folder.listFiles();
            logger.log(Level.INFO, Arrays.toString(listOfFiles));
            //try{
            while(true){
                try{
                   logger.log(Level.INFO,"waiting a command");
                   command = scan.nextLine(); 
                }
                catch(Exception e){
                    logger.log(Level.INFO,"ERROR: line not found");
                    e.printStackTrace();
                    break;
                }
                logger.log(Level.INFO,"RECEIVED command: " + command);

                if ("LIST".equals(command)) {
                    logger.log(Level.INFO, "sending names of files");
                    for (File listOfFile : listOfFiles) {
                        System.out.println("sending: " + listOfFile.getName());
                        out.println(listOfFile.getName());
                        out.flush();

                    }
                    out.println("END");
                    out.flush();
                    logger.log(Level.INFO, "names of files sent");
                } else if ("GET:".equals(command.substring(0,4))) {
                    String fileName = command.substring(4, command.length());
                    logger.log(Level.INFO, "sending " + fileName);
                    sender = new Sender(out, Paths.get("").toAbsolutePath() + "/e160720/ServerJacopo/" + fileName);
                    thread = new Thread(sender);
                    thread.start();
                } else if ("INTERRUPT".equals(command)) {
                    out.println("INTERRUPTED");
                    out.flush();
                    sender.stopThread();
                } else {
                    System.out.println("command not found");
                    out.println("ERROR");
                    out.flush();
                    out.close();
                }
                
                
            }
//            }
//            catch(){
//                logger.log(Level.WARNING,"ERROR: TIMEDOUT CONNECTION");
//                e.printStackTrace();
//            }
            scan.close();
            out.close();
        }
    }
    
    
    public void close() throws IOException {
        finish = true;
        if(scan != null) scan.close();
        if(out != null) out.close();
        if(socket != null) socket.close();
        if(server != null) server.close();
    }
    
    public PrintWriter getPrintWriter(){
        return out;
    }
}
