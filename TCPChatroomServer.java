import java.io.*;
import java.net.*;
import java.util.*;

/* ​This is a Java program written by LAU Ka Pui */
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */

/**
 *
 * @author LAU Ka Pui
 */

/**
 * 
 * How to Run the TCPChatroomServer locally:
 * 1. Create/Edit java file in CLI ("cd" first):
 *      $vi TCPChatroomServer.java
 * 2. Quit from editing:
 *      Press esc
 *      $wq!
 * 3. Compile java file in CLI:
 *      $javac TCPChatroomServer.java
 *      $$javac ServerUserThread.java
 * 4. Run java file in CLI:
 *      $java TCPChatroomServer PORT
 * 
 * How to Run the TCPChatroomServer remotely:
 * 1. Open Putty
 * 2. Enter XX.XXX.X.XXX in the field of IP, and the port should default be 22
 * 3. Enter username: xxxxx
 * 4. Enter password: xxxxx
 * 5. Create/Edit java file in Putty CLI:
 *      $vi TCPChatroomServer.java
 * 6. Quit from editing:
 *      Press esc
 *      $wq!
 * 7. Compile java file in Putty CLI:
 *      $javac TCPChatroomServer.java
 *      $javac ServerUserThread.java
 * 8. Run java file in Putty CLI:
 *      $java TCPChatroomServer PORT
 */

public class TCPChatroomServer {
    private int port;

    private Set<ServerUserThread> userThreads = new HashSet<>();
    private Set<String> userNames = new HashSet<>();
    
    /** 
     * Init
     */
    public TCPChatroomServer(int port) { // Defines port
        this.port = port;
    }

    public static void main(String[] args) { // @param args from the command line arguments
        // Make sure user enter one parameter only
        if (args.length < 1) {
            System.out.println("Syntax: java TCPChatroomServer <port-number>");
            System.exit(0);
        }
 
        int port = Integer.parseInt(args[0]); // System.in, defines port
 
        // Create this class object to be executed
        TCPChatroomServer server = new TCPChatroomServer(port); // Defines port
        server.exe(); // Execute server
    }

    /** 
     * TCP Chatroom Server
     */ 
    public void exe() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("TCP Chatroom Server is listening on port " + port);
 
            while (true) { // While a new user in
                // Build socket connection
                Socket socket = serverSocket.accept();
                System.out.println("New friend connected");
 
                /** 
                 * Invokes ServerUserThread class
                 */
                ServerUserThread newUser = new ServerUserThread(socket, this); // Defines ServerUserThread for new user
                userThreads.add(newUser); // Add the new user into userThreads
                newUser.start(); // Runs this ServerUserThread
            }
 
        } catch (IOException e) {
            System.out.println("Error in the server: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /** 
     * Broadcasting
     */
    void broadcast(String message, ServerUserThread excludeUser) { // Delivers a message from one user to others (broadcasting)
        for (ServerUserThread user : userThreads) { // Loops all users in userThreads
            if (user != excludeUser) { // Avoid to send message to excludeUser(The sender)
                user.sendMessage(message);
            }
        }
    }

    /** 
     * User Management
     */
    void addUserName(String userName) { // Stores username of the newly connected client.
        userNames.add(userName); // Add username to the userNames
    }
    void removeUser(String userName, ServerUserThread user) { // When a client is disconnected, removes the associated username and UserThread
        boolean removed = userNames.remove(userName); // Defines the username to be removed from userNames
        if (removed) {
            userThreads.remove(user); // Remove the username from userThreads
            System.out.println("Your friend " + userName + " quitted");
        }
    }
 
    /** 
     * Current User
     */
    boolean hasUsers() { // Returns true if there are other users connected (not count the currently connected user)
        return !this.userNames.isEmpty();
    }
    Set<String> getUserNames() { // Get all usernames from userNames
        return this.userNames;
    }
}
