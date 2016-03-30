package Client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Created by w14007405 on 30/03/16.
 */
class ServerListener extends Thread {
    Socket clientSocket;
    BufferedReader inFromServer;
    String mensagem;

    public ServerListener(Socket c) {
        clientSocket = c;
    }

    public void run() {
        try{
            while(true) {
                inFromServer = new BufferedReader(
                        new InputStreamReader(clientSocket.getInputStream()));
                mensagem = inFromServer.readLine();
                System.out.println(mensagem);
                if(mensagem.equals("quit")){
                    break;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}