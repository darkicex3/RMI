package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface CAdresseInterface extends Remote {

    String getClasse(String adresse) throws RemoteException;
    String getReseau(String adresse) throws RemoteException;
    String getBroadcast(String adresse) throws RemoteException;
}