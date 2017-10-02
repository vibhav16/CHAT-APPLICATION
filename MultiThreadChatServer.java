import javax.swing.*;
import java.awt.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.PrintStream;
import java.io.IOException;
import java.net.Socket;
import java.net.ServerSocket;

public class MultiThreadChatServer extends JFrame {

    JButton send;
    JTextArea chatArea;
    JTextField chatField;
    DataOutputStream dos;
    static ServerSocket serverSocket = null;
    static Socket clientSocket = null;
    private static MultiThreadChatServer multiThreadChatServer = null;

    static final int maxClientsCount = 10;
    static final ClientThread[] threads = new ClientThread[maxClientsCount];

    static MultiThreadChatServer getInstance() {
        if (multiThreadChatServer == null) {
            multiThreadChatServer = new MultiThreadChatServer();
            multiThreadChatServer.createServer();
        }
        return multiThreadChatServer;
    }

    private void createServer() {
        send = new JButton("Send");
        chatField = new JTextField(10);
        chatArea = new JTextArea();
        add(chatArea, BorderLayout.CENTER);
        add(chatField, BorderLayout.NORTH);
        add(send, BorderLayout.SOUTH);
        setTitle("Twitch Chat!");

        MultiThreadChatServerHandler serverHandler = new MultiThreadChatServerHandler(this);
        send.addActionListener(serverHandler);

        setVisible(true);
        setSize(250, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public static void main(String args[]) {
        MultiThreadChatServer.getInstance();
        int portNumber = 2500;
        if (args.length < 1) {
            System.out
                    .println("Usage: java MultiThreadChatServer <portNumber>\n"
                            + "Now using port number=" + portNumber);
        } else {
            portNumber = Integer.valueOf(args[0]).intValue();
        }

        try {
            serverSocket = new ServerSocket(portNumber);
        } catch (IOException e) {
            System.out.println(e);
        }

        while (true) {
            try {
                clientSocket = serverSocket.accept();
                int i = 0;
                for (i = 0; i < maxClientsCount; i++) {
                    if (threads[i] == null) {
                        (threads[i] = new ClientThread(clientSocket, threads)).start();
                        break;
                    }
                }
                if (i == maxClientsCount) {
                    PrintStream os = new PrintStream(clientSocket.getOutputStream());
                    os.println("Server too busy. Try later.");
                    os.close();
                    clientSocket.close();
                }
            } catch (IOException e) {
                System.out.println(e);
            }
        }
    }
}

class ClientThread extends Thread {

    private DataInputStream is = null;
    private PrintStream os = null;
    private Socket clientSocket = null;
    private final ClientThread[] threads;
    private int maxClientsCount;
    private MultiThreadChatServer multiThreadChatServer;

    public Socket getClientSocket() {
        return clientSocket;
    }

    public ClientThread(Socket clientSocket, ClientThread[] threads) {
        this.clientSocket = clientSocket;
        this.threads = threads;
        maxClientsCount = threads.length;
        multiThreadChatServer = MultiThreadChatServer.getInstance();
    }

    public void run() {
        int maxClientsCount = this.maxClientsCount;
        ClientThread[] threads = this.threads;

        try {
            is = new DataInputStream(clientSocket.getInputStream());
            os = new PrintStream(clientSocket.getOutputStream());
            os.println("Enter your name.");
            String name = is.readLine().trim();
            os.println("Hello " + name
                    + " to our chat room.\nTo leave enter /quit in a new line");
            multiThreadChatServer.chatArea.append("*** A new user " + name
                    + " entered the chat room !!! ***" + "\n");
            for (int i = 0; i < maxClientsCount; i++) {
                if (threads[i] != null && threads[i] != this) {
                    threads[i].os.println("*** A new user " + name
                            + " entered the chat room !!! ***");
                }
            }
            while (true) {
                String line = is.readLine();
                if (line.startsWith("/quit")) {
                    break;
                }
                multiThreadChatServer.chatArea.append("<" + name + ">: " + line + "\n");
                for (int i = 0; i < maxClientsCount; i++) {
                    if (threads[i] != null) {
                        threads[i].os.println("<" + name + ">: " + line);
                    }
                }
            }
            multiThreadChatServer.chatArea.append("*** The user " + name
                    + " is leaving the chat room !!! ***" + "\n");
            for (int i = 0; i < maxClientsCount; i++) {
                if (threads[i] != null && threads[i] != this) {
                    threads[i].os.println("*** The user " + name
                            + " is leaving the chat room !!! ***");
                }
            }
            os.println("*** Bye " + name + " ***");
            for (int i = 0; i < maxClientsCount; i++) {
                if (threads[i] == this) {
                    threads[i] = null;
                }
            }

            is.close();
            os.close();
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}