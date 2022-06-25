package e160720.ClientJacopo;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


class Listener implements ActionListener{

    private Gui mainFrame;

    private Socket socket;
    private String adress;
    private int port;
    
    private JTextField command;
    private JTextArea text;
    private Scanner scan;
    private PrintWriter out;
    
    private Logger logger = Logger.getLogger("Listener");
    
    public Listener(Gui m, String ad, int p, JTextField c, JTextArea t){
        mainFrame = m;
        adress = ad;
        port = p;
        command = c;
        text = t;
    }
   
    public void actionPerformed(ActionEvent ae) {
        if(ae.getActionCommand() == "connect"){
            logger.log(Level.INFO,"connecting");
            try {
                socket = new Socket(adress,port);
            } catch (IOException ex) {
                logger.log(Level.WARNING,"FAILED TO CONNECT");
                ex.printStackTrace();
            }
            logger.log(Level.INFO,"connection established");
            try{
               scan = new Scanner(socket.getInputStream());
               out = new PrintWriter(socket.getOutputStream()); 
            }
            catch(IOException e){
                logger.log(Level.WARNING,"STREAM NOT OPEN");
                e.printStackTrace();
            }  
        }
        else if(ae.getActionCommand() == "disconnect"){
            logger.log(Level.INFO,"disconnecting...");
            scan.close();
            out.close();
            try {
                socket.close();
            } catch (IOException ex) {
                logger.log(Level.WARNING,"ERROR: CONNECTION NOT CLOSE CORRECTLY");
                ex.printStackTrace();
            }
            logger.log(Level.INFO,"stream closed, disconnected");
        }
        else if(ae.getActionCommand() == "execute"){
            logger.log(Level.INFO,"executing...");
            System.out.println("printing: " + command.getText());
            out.println(command.getText());
            out.flush();
            Downloader downloader = new Downloader(scan,text);
            Thread sender= new Thread(downloader);
            sender.start();
            
            logger.log(Level.INFO,"started correctly");
        }
        else if(ae.getActionCommand() == "interrupt"){
            logger.log(Level.INFO,"interrupted");
            out.println("INTERRUPT");
            out.flush();
        }
    }
    
}
