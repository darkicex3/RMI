package interfaces;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface HelloInterface extends Remote {
    String getHello(String user) throws RemoteException;

}
