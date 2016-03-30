package serveur;

import interfaces.CAdresseInterface;

import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class CAdresseImpl extends UnicastRemoteObject implements CAdresseInterface {

    public CAdresseImpl(int port) throws RemoteException {

        super(port);
    }

    @Override
    public String getClasse(String adresse) throws RemoteException {
        String retour;
        try {
            CAdresseIP cAdresseIP = new CAdresseIP(adresse);
            retour = cAdresseIP.getClasse();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            retour = "Unknown Host";
        }
        return retour;
    }

    @Override
    public String getReseau(String adresse) throws RemoteException {
        String retour;
        try {
            CAdresseIP cAdresseIP = new CAdresseIP(adresse);
            retour = cAdresseIP.getReseau();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            retour = "Unknown Host";
        }
        return retour;
    }

    @Override
    public String getBroadcast(String adresse) throws RemoteException {
        String retour;
        try {
            CAdresseIP cAdresseIP = new CAdresseIP(adresse);
            retour = cAdresseIP.getBroadcast();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            retour = "Unknown Host";
        }
        return retour;
    }
}
