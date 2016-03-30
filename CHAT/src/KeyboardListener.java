import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Created by w14007405 on 30/03/16.
 */
class KeyboardListener extends Thread {
    String sentence;
    Socket clientSocket;
    BufferedReader inFromUser;
    DataOutputStream outToServer;

    public KeyboardListener(String s, Socket c) throws Exception {
        sentence = s;
        clientSocket = c;
        inFromUser = new BufferedReader(new InputStreamReader(System.in));
        outToServer = new DataOutputStream(clientSocket.getOutputStream());
    }

    public void run() {
        try {
            while (true) {
                sentence = inFromUser.readLine();
                if(sentence.equals("help")) {
                    System.out.println("Options du serveur:");
                    System.out.println("@user: envoyez message privé");
                    System.out.println("name: - changer de name");
                    System.out.println("quit - quitter le chat");
                }
                else {
                    outToServer.writeBytes(sentence + '\n');
                    if (sentence.endsWith("quit")) {
                        System.out.println("BYE!");
                        break;
                    }
                }
            }
        } catch (Exception e) {
            //e.printStackTrace();
        }
    }
}
