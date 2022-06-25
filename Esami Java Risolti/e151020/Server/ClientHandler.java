package e151020.Server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

class ClientHandler implements Runnable {
    // Gestisce un Client, riceve i suoi comandi
    private final Socket client;
    private final Scanner input;
    private final PrintWriter output;
    public boolean running = true;
    private Sender sender;

    public ClientHandler(Socket client) throws IOException {
        this.client = client;
        input = new Scanner(client.getInputStream());
        output = new PrintWriter(client.getOutputStream());

    }

    @Override
    public void run() {
        System.out.println("New Client accepted");
        while (running &&  input.hasNext()) {
            System.out.println("waiting for input...");
            getCmd();
        }
        System.out.println("Client dismissed");

    }

    private void start(String imageX) {
        System.out.println("Starting sending image");
        sender = new Sender(output, imageX);
        Thread t = new Thread(sender);
        t.start();
    }

    private void disconnect() {
        running = false;
        try {
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void interrupt() {
        sender.interrupted = true;
    }

    private void getCmd() {
        if (input.hasNext()) {
            String cmd = input.nextLine();
            if (!running) { return; }
            System.out.println("Received: " + cmd);
            if (cmd.substring(0,cmd.length()-1).equals("start:image")) {
                start(cmd.substring(6));
            } else if (cmd.equals("stop")) {
                interrupt();
            } else if (cmd.equals("disconnect")) {
                disconnect();
            } else {
                output.println("ERROR");
                output.flush();
                try {
                    client.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
