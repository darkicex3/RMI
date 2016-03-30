import interfaces.HelloInterface;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {
    public static void main(String args[]) {
        try {
            String adrServeur = "139.124.187.172";
            Registry registre = LocateRegistry.getRegistry(adrServeur);
            HelloInterface stub = (HelloInterface) registre.lookup("hello");
            String msg = stub.getHello("test");
            System.out.println(msg);
        } catch (Exception e) {e.printStackTrace(); }
    }
}
