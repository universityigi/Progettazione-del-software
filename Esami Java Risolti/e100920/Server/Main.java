package e100920.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static final int PORT = 4400;

    public static void main(String[] args) throws IOException {
        System.out.println("Server opened at " + PORT);
        ServerSocket ss = new ServerSocket(PORT);
        while (true) {
            Socket cs = ss.accept();
            Thread t = new Thread(new ClientHandler(cs));
            t.start();
        }

    }
}
