import java.net.*;
import java.io.*;

/* ​This is a Java program written by LAU Ka Pui */
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author LAU Ka Pui
 */

/**
 * 
 * How to Run the TCPChatroomClient:
 * 
 * Connect to localhost:
 * $javac TCPChatroomClient.java
 * $javac ReadThread.java
 * $javac WriteThread.java
 * $java TCPChatroomClient localhost PORT
 * 
 * Connect to remote host:
 * $javac TCPChatroomClient.java
 * $javac ReadThread.java
 * $javac WriteThread.java
 * $java TCPChatroomClient XX.XXX.X.XXX PORT
 */

 /**
  * Type 'quit' to terminate this TCPChatroomClient.
  */

public class TCPChatroomClient {
    private String hostname;
    private int port;

    private String userName;

    /** 
     * Init
     */
    public TCPChatroomClient(String hostname, int port) { // Defines hostname and port
        this.hostname = hostname;
        this.port = port;
    }
    
    public static void main(String[] args) {
        // Make sure the user only invokes two parameters
        if (args.length < 2) return;
 
        // System.in, defines [0]: hostname and [1]: port
        String hostname = args[0];
        int port = Integer.parseInt(args[1]);
 
        // Create this class object to be executed
        TCPChatroomClient client = new TCPChatroomClient(hostname, port); // Defines hostname and port
        client.exe();
    }

    /** 
     * TCP Chatroom Client
     */ 
    public void exe() {
        try {
            // Build socket connection
            Socket socket = new Socket(hostname, port);
            System.out.println("Connected to the chat server");
 
            new ClientReadThread(socket, this).start();
            new ClientWriteThread(socket, this).start();
 
        } catch (UnknownHostException e) {
            System.out.println("Server not found: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("I/O Error: " + e.getMessage());
        }
 
    }
 
    /** 
     * User Management
     */
    void setUserName(String userName) { // Defines username
        this.userName = userName;
    }
    /** 
     * Current User
     */    
    String getUserName() { // Get this username
        return this.userName;
    }
}
