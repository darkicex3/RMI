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