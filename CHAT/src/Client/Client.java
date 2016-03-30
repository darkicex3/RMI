package Client;

import java.io.*;
import java.net.*;
import java.util.Scanner;

class Client extends Thread {

	public static void main(String argv[]) throws Exception {
		String sentence = null; //phrase écris par l'utilisateur
		KeyboardListener keyboardListener;
		ServerListener serverListener; //Ecoute du serveur

		Scanner entrada = new Scanner (System.in); //lit les entrées au clavier
		
		System.out.println("Server Option:");
		System.out.println("@user: Send a private message !");
		System.out.println("name: - Change your name !");
		System.out.println("quit - Quit");

		System.out.println("Enter IP address or 'local.");
		String server = entrada.nextLine();

		if (server.equals("local")) {
			server = "127.0.0.1";
		}

		Socket clientSocket = new Socket(server, 6789); //Création de la socket client

		keyboardListener = new KeyboardListener(sentence, clientSocket); //Création du keyboardListener
		serverListener = new ServerListener(clientSocket); //Création du ServeurListener

		keyboardListener.start(); //
		serverListener.start();
		keyboardListener.join();
		serverListener.join();
		clientSocket.close();
		System.out.println("Signout");
	}
}

