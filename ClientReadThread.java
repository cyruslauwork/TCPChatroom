import java.io.*;
import java.net.*;

/* ​This is a Java program written by LAU Ka Pui */
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author LAU Ka Pui
 */
public class ClientReadThread extends Thread {
    private Socket socket;
    private TCPChatroomClient client;

    private BufferedReader reader;
 
    /**
    * Init
    */
    public ClientReadThread(Socket socket, TCPChatroomClient client) { // Create client socket, connect to server
        this.socket = socket;
        this.client = client;
 
        try {
            // Create input stream attached to socket
            InputStream input = socket.getInputStream();
            reader = new BufferedReader(new InputStreamReader(input));
        } catch (IOException e) {
            System.out.println("Error getting input stream: " + e.getMessage());
            e.printStackTrace();
        }
    }
 
    public void run() {
        while (true) {
            try { // Loop while connected
                // Read line from server
                String response = reader.readLine();
                System.out.println("\n" + response);
 
                // prints the username after displaying the server's message
                if (client.getUserName() != null) {
                    System.out.print("[" + client.getUserName() + "]: ");
                }
            } catch (IOException e) {
                System.out.println("Error reading from server: " + e.getMessage());
                e.printStackTrace();
                break; // Break when disconnected
            }
        }
    }
}
