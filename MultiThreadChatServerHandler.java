import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintStream;

/**
 * Created by vishal.p on 02/10/17.
 */
public class MultiThreadChatServerHandler implements ActionListener {
    private MultiThreadChatServer multiThreadChatServer;

    MultiThreadChatServerHandler(MultiThreadChatServer multiThreadChatServer) {
        this.multiThreadChatServer = multiThreadChatServer;
    }

    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==multiThreadChatServer.send)
        {
            try {
                String string =multiThreadChatServer.chatField.getText();
                multiThreadChatServer.chatArea.append("<Server>: "+string+"\n");
                multiThreadChatServer.chatField.setText("");
                for (int i = 0; i < MultiThreadChatServer.maxClientsCount; i++) {
                    if (MultiThreadChatServer.threads[i] != null) {
                        PrintStream os = new PrintStream(MultiThreadChatServer.threads[i].getClientSocket().getOutputStream());
                        os.println("<Server>: "+string+"\n");
                    }
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
}
