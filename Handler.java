import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;


public class Handler implements ActionListener
{
	client c;
	
	
	public Handler(client c) 
	{
		super();
		this.c = c;
	}


	@Override
	public void actionPerformed(ActionEvent e) 
	{
		DataInputStream dis,dis1;
	DataOutputStream dos;
		
		if(e.getSource()==c.tb)
		{
			try 
			{
				
				dos=new DataOutputStream(c.s.getOutputStream());
				//dis1=new DataInputStream(c.s.getInputStream());	
				String str;
				
					str=c.tf.getText();
					
					System.out.println(str);
					dos.writeUTF(str);
					c.ta.append("VIBHAV:"+str+"\n");
					//String str1=dis1.readUTF();
					//c.ta.append("aditya:"+str1+"\n");
					c.tf.setText("");
				
				
			}
			catch (IOException e1) 
			{				
				e1.printStackTrace();
			}
			catch (Exception e2) 
			{
				e2.printStackTrace();
			}
			
			
		}
		
		
	}

}
