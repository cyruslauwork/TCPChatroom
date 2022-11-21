import java.io.*;
import java.net.*;
import java.util.*;

/* ​This is a Java program written by LAU Ka Pui */
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 * This thread handles connections for each connected client, 
 * so the server can handle multiple clients at the same time. 
 * So this thread is responsible for reading messages sent by 
 * clients and broadcasting the messages to all other clients.
 *
 * @author LAU Ka Pui
 */
public class ServerUserThread extends Thread {
    private Socket socket;

    private TCPChatroomServer server;

    private PrintWriter writer;
    
    /** 
     * Init
     */
    public ServerUserThread(Socket socket, TCPChatroomServer server) { // Defines socket and server
        this.socket = socket;
        this.server = server;
    }
 
     /** 
     * To run run() from other class(es) by invoking ServerUserThread.start();
     */
    public void run() {
        try {
            // Create input stream attached to socket
            InputStream input = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            // Create output stream attached to socket
            OutputStream output = socket.getOutputStream();
            writer = new PrintWriter(output, true);
 
            printUsers(); // Print out current usernames from TCPChatroomServer().userNames
 
            // Add userName to the TCPChatroomServer().userNames
            String userName = reader.readLine();
            server.addUserName(userName);
            // Broadcast message about new user
            String serverMessage = "New friend connected: " + userName;
            server.broadcast(serverMessage, this);
 
            // Read line from this client 
            String clientMessage;
            do {
                clientMessage = reader.readLine();
                serverMessage = "[" + userName + "]: " + clientMessage;
                // Broadcast client's message to everyone
                server.broadcast(serverMessage, this);
            } while (!clientMessage.equals("quit"));
 
            // If this client has quitted, remove this userName from the TCPChatroomServer()'s userNames and userThreads
            server.removeUser(userName, this);
            socket.close();
            // Broadcast client has quitted
            serverMessage = userName + " has quitted.";
            server.broadcast(serverMessage, this);
 
        } catch (IOException e) {
            System.out.println("Error in UserThread: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /** 
     * Print user list
     */
    void printUsers() { // Sends a list of online users to the newly connected user.
        if (server.hasUsers()) {
            writer.println("Connected friend(s): " + server.getUserNames());
        } else {
            writer.println("No other friend connected");
        }
    }
 
    /** 
     * An user interface with his/her name tag
     */
    void sendMessage(String message) { // Sends a message to the client.
        writer.println(message);
    }
}
