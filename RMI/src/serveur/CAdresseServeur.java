package serveur;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Enumeration;
import java.util.Properties;

public class CAdresseServeur {


    public static String getAdresseLocale() throws SocketException {
        InetAddress adr = null;
        Enumeration<InetAddress> listAdr =
                NetworkInterface.getByName("eth0").getInetAddresses();
        while (listAdr.hasMoreElements())
            adr = listAdr.nextElement();
        return adr.getHostAddress();
    }

    public static void main(String[] args) throws
            RemoteException,
            InterruptedException,
            NotBoundException,
            UnknownHostException,
            SocketException {

        int port = 50099;

        Properties properties = System.getProperties();
        properties.put("java.rmi.server.hostname", getAdresseLocale());

        CAdresseImpl obj = new CAdresseImpl(port);

        Registry registre = LocateRegistry.createRegistry(port);
        registre.rebind("cadresse", obj);
        System.out.println("Serveur CAdresse lancé ! ");
    }
}
