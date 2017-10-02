import javax.swing.*;
import java.awt.*;
import java.io.DataInputStream;
import java.io.PrintStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class MultiThreadChatClient extends JFrame implements Runnable {

  static Socket clientSocket = null;
  private static PrintStream os = null;
  private static DataInputStream is = null;

  private static BufferedReader inputLine = null;
  private static boolean closed = false;
  JTextField textField;
  JTextArea textArea;
  JButton send;

  private void createClient() {
    textArea=new JTextArea();
    textField=new JTextField(20);
    send=new JButton("Send");

    add(textArea,BorderLayout.CENTER);
    add(textField,BorderLayout.SOUTH);
    add(send,BorderLayout.NORTH);
    setTitle("CHAT SERVICE :)");
    setVisible(true);
    setSize(250, 500);
    setDefaultCloseOperation(EXIT_ON_CLOSE);

    MultiThreadChatClientHandler clientHandler = new MultiThreadChatClientHandler(this);
    send.addActionListener(clientHandler);
  }

  public MultiThreadChatClient() {
    createClient();
  }

  public static void main(String[] args) {

    int portNumber = 2500;
    String host = "localhost";

    if (args.length < 2) {
      System.out
          .println("Usage: java MultiThreadChatClient <host> <portNumber>\n"
              + "Now using host=" + host + ", portNumber=" + portNumber);
    } else {
      host = args[0];
      portNumber = Integer.valueOf(args[1]).intValue();
    }

    try {
      clientSocket = new Socket(host, portNumber);
      inputLine = new BufferedReader(new InputStreamReader(System.in));
      os = new PrintStream(clientSocket.getOutputStream());
      is = new DataInputStream(clientSocket.getInputStream());
    } catch (UnknownHostException e) {
      System.err.println("Don't know about host " + host);
    } catch (IOException e) {
      System.err.println("Couldn't get I/O for the connection to the host "
          + host);
    }

    if (clientSocket != null && os != null && is != null) {
      try {

        /* Create a thread to read from the server. */
        new Thread(new MultiThreadChatClient()).start();
        while (!closed) {
          os.println(inputLine.readLine().trim());
        }
        os.close();
        is.close();
        clientSocket.close();
      } catch (IOException e) {
        System.err.println("IOException:  " + e);
      }
    }
  }

  public void run() {
    String responseLine;
    try {
      while ((responseLine = is.readLine()) != null) {
        System.out.println(responseLine);
        textArea.append(responseLine + "\n" + "");
        if (responseLine.indexOf("*** Bye") != -1)
          break;
      }
      closed = true;
    } catch (IOException e) {
      System.err.println("IOException:  " + e);
    }
  }
}