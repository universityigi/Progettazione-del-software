package e160720.ClientJacopo;

import javax.swing.*;
import java.awt.*;

import static java.lang.String.valueOf;


class Gui extends JFrame {
    private JFrame mainFrame;
    private JPanel north, south;
    private JTextArea text;
    private JTextField ipAdress, port, command;
    private JLabel ipAdressL,portL, commandL;
    private JButton connect,disconnect, execute, interrupt;
    
    private Listener listener;
    
    public Gui(String ad, int p){
        
        createObjects(ad,p);
        
        north.add(ipAdressL);   north.add(ipAdress);
        north.add(portL);       north.add(port);
        north.add(connect);     north.add(disconnect);
        
        south.add(commandL);    south.add(command);
        south.add(execute);     
        south.add(interrupt);
        
        mainFrame.add(north,BorderLayout.NORTH);
        mainFrame.add(text,BorderLayout.CENTER);
        mainFrame.add(south,BorderLayout.SOUTH);
        
        listener = new Listener(this,ad,p,command,text);
        
        connect.addActionListener(listener);    connect.setActionCommand("connect");
        disconnect.addActionListener(listener);  disconnect.setActionCommand("disconnect");
        execute.addActionListener(listener);    execute.setActionCommand("execute");
        interrupt.addActionListener(listener);  interrupt.setActionCommand("interrupt");
        
        functionPack();
    }
    
    
    private void createObjects(String ad, int p){
        mainFrame = new JFrame();   mainFrame.setLayout(new BorderLayout());
        north = new JPanel();
        south = new JPanel();
        
        text = new JTextArea(20,20);
        ipAdress = new JTextField(ad,30);
        port = new JTextField(valueOf(p),30);
        command = new JTextField("GET:canto1",30);
        
        ipAdressL = new JLabel("Server Adress");
        portL = new JLabel("Port");
        commandL = new JLabel("command");
        
        connect = new JButton("connect");
        disconnect = new JButton("disconnect");
        execute = new JButton("execute");
        interrupt = new JButton("interrupt");
        
    }
    
    private void functionPack(){
        mainFrame.setTitle("CLIENT");
        mainFrame.pack();
        mainFrame.setLocation(200,100);
        mainFrame.setSize(1100,600);
        mainFrame.setVisible(true);
        mainFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}
