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
public class ClientWriteThread extends Thread {
    private Socket socket;
    private TCPChatroomClient client;

    private PrintWriter writer;
 
    /**
     * Init
     */
    public ClientWriteThread(Socket socket, TCPChatroomClient client) { // Create client socket, connect to server
        this.socket = socket;
        this.client = client;
 
        try {
            // Create output stream attached to socket
            OutputStream output = socket.getOutputStream();
            writer = new PrintWriter(output, true);
        } catch (IOException e) {
            System.out.println("Error getting output stream: " + e.getMessage());
            e.printStackTrace();
        }
    }
 
    public void run() {
        // Read System.in
        Console console = System.console();
        String userName = console.readLine("\nPlease enter your name: ");
        client.setUserName(userName); // Set userName to this TCPClient()
        writer.println(userName);
 
        String text;
        do { // Send line to server
            text = console.readLine("[" + userName + "]: ");
            writer.println(text);
        } while (!text.equals("quit"));
 
        try {
            socket.close();
        } catch (IOException e) {
            System.out.println("Error writing to server: " + e.getMessage());
        }
    }
}
