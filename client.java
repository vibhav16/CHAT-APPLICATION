import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class client extends JFrame
{
	Socket s;
	JTextField tf;
	JTextArea ta;
	JButton tb;
	
	
	public  client() throws UnknownHostException, IOException
	{
		ta=new JTextArea();
		tf=new JTextField(20);
		tb=new JButton("Send");
		
		add(ta,BorderLayout.CENTER);
		add(tf,BorderLayout.SOUTH);
		add(tb,BorderLayout.NORTH);
		setTitle("CHAT SERVICE!!!!!!!!!!!!!!!!!!!");
	    setVisible(true);
	    setSize(250, 500);	    
	    setDefaultCloseOperation(EXIT_ON_CLOSE);
	    
		s=new Socket("192.168.1.40", 2500);
		System.out.println("requesting...");
		
		Handler h=new Handler(this);
		tb.addActionListener(h);
		
		DataInputStream dis1=new DataInputStream(s.getInputStream());
		String str;
		while((str=dis1.readUTF())!=null)
			ta.append("ADITYA:"+str);
		
		
		
		
		
		
		
		
	}
	

	public static void main(String[] args) throws UnknownHostException, IOException
	{
		new client();

	}

}
