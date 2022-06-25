package e160720.ServerJacopo;

import javax.swing.*;
import java.awt.*;
import java.util.logging.Logger;

class Gui extends JFrame{
    private JFrame mainFrame;

    private JPanel menu, info;
    
    private JButton startServer;
    private JButton closeServer;
    
    private JLabel portL;
    private JTextField port;
    private JTextArea toRead;
    
    private Listener listener;
    private Logger logger = Logger.getLogger("Gui");
    
    public Gui(){
        //inizializzi Frame e metto listener
         createObject();
         
         listener = new Listener(this,port);
         
         menu.add(startServer); 
         menu.add(closeServer); 

         info.add(portL);
         info.add(port);
         
         mainFrame.add(menu,BorderLayout.NORTH);
         mainFrame.add(info,BorderLayout.CENTER);
         mainFrame.add(toRead,BorderLayout.SOUTH);
        
     //faccio listener
        startServer.setActionCommand("START");  startServer.addActionListener(listener);
        closeServer.setActionCommand("CLOSE");  closeServer.addActionListener(listener);

        startServer.setEnabled(true);
        closeServer.setEnabled(false);
        
        toRead.setEditable(false);
        
        functionPack();
        
    }
    
    public void setChangePort(boolean b){
        if(b == true) port.setEditable(true);
        else port.setEditable(false);
    }
    
    public int getPort(){
        return Integer.parseInt(port.getText());
    }
    
    public void buttons(boolean run){
        if(run == true){
            port.setEditable(false);
            startServer.setEnabled(false);
            closeServer.setEnabled(true);
        }
        else if(run == false){
            port.setEditable(true);
            startServer.setEnabled(true);
            closeServer.setEnabled(false);
        }
        
    }
    
    //inizializzo oggetti
    private void createObject(){
        mainFrame = new JFrame("SERVER");
        menu = new JPanel();
        info = new JPanel();
        portL = new JLabel("Port: ");
        port = new JTextField("7542",15);
        toRead = new JTextArea("ipAdress should be 0.0.0.0 on virtual machine, it depend from your computer.\nPlease read file.txt given with the server",2,2);
        closeServer = new JButton("close server");
        startServer = new JButton("start server");
        
        
    }
    
    //funzioni per visualizzazione grafica
    private void functionPack(){
        mainFrame.setTitle("SERVER");
        mainFrame.pack();
        mainFrame.setLocation(1430,550);
        mainFrame.setSize(300,120);
        mainFrame.setVisible(true);
        mainFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    
}
