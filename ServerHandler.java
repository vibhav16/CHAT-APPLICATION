import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;



public class ServerHandler implements ActionListener {

	Server s;
	
	public ServerHandler(Server s)
	{
		this.s = s;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==s.send)
		{
			try {
				/*s.dis = new DataInputStream(s.s1.getInputStream());*/
				s.dos = new DataOutputStream(s.s1.getOutputStream());
				
				//String str;
				
					/*str=s.dis.readUTF();
					System.out.println(str);
					s.chatArea.append("Vibhav: "+str+"\n");*/
					
					String str1=s.chatField.getText();
					s.dos.writeUTF(str1+"\n");
					s.chatArea.append("Aditya:"+str1+"\n");
					s.chatField.setText("");
				
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			
		}
		
	}

}
