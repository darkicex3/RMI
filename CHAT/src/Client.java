import java.io.*;
import java.net.*;
import java.util.Scanner;

class Client extends Thread {

	public static void main(String argv[]) throws Exception {
		String sentence = null; //phrase écris par l'utilisateur
		KeyboardListener keyboardListener; //lit les entrées au clavier
		ServerListener serverListener; //Ecoute du serveur

		Scanner entrada = new Scanner (System.in);
		
		System.out.println("Options:");
		System.out.println("help: show help");
		System.out.println("@user: send a private message");
		System.out.println("name: - change name");
		System.out.println("quit - quit chat");

		System.out.println("Entrer l'IP du serveur ou tapez local pour utiliser l'adresse local");
		String server = entrada.nextLine();

		if (server.equals("local")) {
			server = "127.0.0.1";
		}

		Socket clientSocket = new Socket(server, 6789);

		keyboardListener = new KeyboardListener(sentence, clientSocket);
		serverListener = new ServerListener(clientSocket);

		keyboardListener.start();
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
					System.out.println("Options:");
					System.out.println("help: show help");
					System.out.println("@user: send a private message");
					System.out.println("name: - change name");
					System.out.println("quit - quit chat");
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