/**
 * Created by maxbook on 30/03/16.
 */
package client;

import interfaces.CAdresseInterface;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class CAdresseClient {
    public static void main(String args[]) {
        try {
            String adrServeur = "139.124.187.167";
            Registry registre = LocateRegistry.getRegistry(adrServeur, 50099);
            CAdresseInterface stub = (CAdresseInterface) registre.lookup("cadresse");
            String msg;

            Scanner entree = new Scanner(System.in);

            System.out.println("Adresse :");
            String adresse = entree.nextLine();
            System.out.println("Action (1/Classe, 2/Reseau, 3/Diffusion) :");
            int action = entree.nextInt();
            entree.close();
            switch (action) {
                case 0:
                    return;
                case 1:
                    System.out.println("Classe de l'adresse :");
                    msg = stub.getClasse(adresse);
                    break;
                case 2:
                    System.out.println("Adresse réseau associé :");
                    msg = stub.getReseau(adresse);
                    break;
                case 3:
                    System.out.println("Adresse de diffusion :");
                    msg = stub.getBroadcast(adresse);
                    break;
                default:
                    return;
            }
            System.out.println(msg);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


