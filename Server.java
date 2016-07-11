import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class Server extends JFrame{
	
	ServerSocket ss;
	Socket s1;
	JButton send;
	JTextArea chatArea;
	JTextField chatField;
	DataInputStream dis;
	
	DataOutputStream dos;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Server();
	}
	
	public Server(){
		
		send= new JButton("Send");
		chatField = new JTextField(10);
		chatArea= new JTextArea();
		add(chatArea, BorderLayout.CENTER);
		add(chatField,BorderLayout.NORTH);
		add(send, BorderLayout.SOUTH);
		setTitle("Twitch Chat!");
		
		Handler h = new Handler(this);
		send.addActionListener(h);
		
		setVisible(true);
		setSize(250, 500);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		
		
		try {
			ss= new ServerSocket(2500,2,InetAddress.getLocalHost());
			System.out.println("Waiting for client!...\n");
			s1 = ss.accept();
			System.out.println("accepted!");
			
			DataInputStream dis2 = new DataInputStream(s1.getInputStream());
		
			String str;
			while((str=dis2.readUTF())!=null)
			   chatArea.append("Vibhav: "+str+"\n");
		
		
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
