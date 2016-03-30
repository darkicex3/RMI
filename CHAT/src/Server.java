import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Enumeration;

class Server {

	public static String host;
	private static ServerSocket welcomeSocket;
	private static ArrayList<DataOutputStream> outToClients;
	private static ArrayList<Connection> connections;

	public static void main(String argv[]) throws Exception {
		connections = new ArrayList<Connection>();
		Integer count = 0;

		//host = InetAddress.getLocalHost().getHostAddress(); //Serveur en local
		System.out.println(host);
		host = InetAddress.getByName("139.124.187.162").toString();
		System.out.println(host);

		welcomeSocket = new ServerSocket(6789);
		outToClients = new ArrayList<DataOutputStream>();

		while (true) {

			Socket connectionSocket = welcomeSocket.accept();
			DataOutputStream outToClient = new DataOutputStream(
					connectionSocket.getOutputStream());
			outToClients.add(outToClient);
			connections.add(new Connection(connectionSocket, count++,
					outToClients, connections));
			connections.get(connections.size() - 1).start();
		}
	}
}