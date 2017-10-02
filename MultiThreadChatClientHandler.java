import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

/**
 * Created by vishal.p on 02/10/17.
 */
public class MultiThreadChatClientHandler implements ActionListener {
    private MultiThreadChatClient multiThreadChatClient;

    public MultiThreadChatClientHandler(MultiThreadChatClient multiThreadChatClient) {
        this.multiThreadChatClient = multiThreadChatClient;
    }

    public void actionPerformed(ActionEvent e) {
        DataOutputStream dos;
        if(e.getSource()==multiThreadChatClient.send) {
            try {
                String str;
                str=multiThreadChatClient.textField.getText();
                System.out.println(str);
                multiThreadChatClient.textField.setText("");
                PrintStream os = new PrintStream(MultiThreadChatClient.clientSocket.getOutputStream());
                os.println(str);
            }
            catch (IOException e1) {
                e1.printStackTrace();
            }
            catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }
}
