package Serveur;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by w14007405 on 30/03/16.
 */
class Connection extends Thread {
    Integer id;
    Boolean active = false;
    Socket connectionSocket;
    String nome, host, inMessage, outMessage;
    BufferedReader inFromClient;
    DataOutputStream outToClient;
    ArrayList<DataOutputStream> outToClients;
    ArrayList<Connection> connections;

    public Connection(Socket c, Integer i, ArrayList<DataOutputStream> otc,
                      ArrayList<Connection> coms) {
        connectionSocket = c;
        id = i;
        outToClients = otc;
        connections = coms;
        nome = id.toString();
    }

    public void run() {
        System.out.println("Connected with:" + id);
        active = true;
        try {
            inFromClient = new BufferedReader(new InputStreamReader(
                    connectionSocket.getInputStream()));

            while (true) {
                inMessage = inFromClient.readLine();
                System.out.println("Message recieved: \"" + inMessage + "\"- from:" + id);

                if (inMessage.equals("quit")) {
                    outMessage = "BYE";
                    outToClients.get(id).writeBytes(outMessage);
                    System.out.println("Client.Client disconnected");
                    break;

                } else if (inMessage.startsWith("@")) {
                    System.out.println("send a private message to: " + inMessage.substring(1, 2));
                    Integer destination = Integer.valueOf(inMessage.substring(
                            1, 2));
                    if (connections.size() < destination) {
                        outMessage = "User:" + destination + " not found\n";
                        outToClients.get(id).writeBytes(outMessage);
                    } else if (connections.get(destination).active) {
                        outMessage = "Private from:" + id + ":" + inMessage.substring(2) + "\n";
                        outToClients.get(destination).writeBytes(outMessage);
                    } else {
                        outMessage = "User:" + destination + " not active\n";
                        outToClients.get(id).writeBytes(outMessage);
                    }
                } else if (inMessage.startsWith("name:")) {
                    nome = inMessage.replace("name:", "");

                    System.out.println("user " + id + " changed name to:" + nome);

                    Integer destination = id;
                    outMessage = "Your new username is: " + nome + "\n";
                    outToClients.get(destination).writeBytes(outMessage);

                } else {
                    outMessage = id + ":" + inMessage + '\n';
                    for (int i = 0; i < outToClients.size(); i++) {
                        if (connections.get(i).active)
                            outToClients.get(i).writeBytes(outMessage);
                    }
                }

            }
            active = false;
            connectionSocket.close();
        } catch (IOException e) {
            //e.printStackTrace();
        }
    }
}
