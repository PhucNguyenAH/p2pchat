package Client;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author dang
 */
public class Listener implements Runnable {
    private ClientController controller;
    private ServerSocket listener;
    private int port;

    public Listener(ClientController controller, int port) {
        this.controller = controller;
        this.port = port;
        
        try {
            listener = new ServerSocket(port);
            new Thread(this).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getPort() {
        return port;
    }
    
    @Override
    public void run() {
        System.out.println("Listening to port " + port + "...");
        try {
            Connection connection;
            while(true) {
                connection = new Connection(controller, listener.accept());
                System.out.println("Listener: " + "New connection established");
                controller.addConnectionBuffer(connection);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}