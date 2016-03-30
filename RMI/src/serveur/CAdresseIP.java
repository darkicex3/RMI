package serveur;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.StringTokenizer;

public class CAdresseIP {

    // Tableau des 4 octets de l'adresse IP.
    private int o[];

    public CAdresseIP(String adr) throws UnknownHostException {

        byte buf[]  = InetAddress.getByName(adr).getAddress();
        o = new int [4];
        for (int i=0; i<4; i++) o[i] = buf[i]&255;

    }

    public String getClasse() {

        if (o[0] >= 1 && o[0] <= 126)
            return "A";
        if (o[0] >= 128 && o[0] <= 191)
            return "B";
        if (o[0] >= 192 && o[0] <= 223)
            return "C";
        if (o[0] >= 224 && o[0] <= 239)
            return "D";
        if (o[0] >= 240 && o[0] <= 255)
            return "E";
        return "Erreur";
    }

    public String getBroadcast() {

        if (o[0] >= 1 && o[0] <= 126)
            return o[0] + ".255.255.255";
        if (o[0] >= 128 && o[0] <= 191)
            return o[0] + "." + o[1] + ".255.255";
        if (o[0] >= 192 && o[0] <= 223)
            return o[0] + "." + o[1] + "." + o[2] + ".255";

        return "Broadcast non défini";
    }

    public String getReseau() {

        if (o[0] >= 1 && o[0] <= 126)
            return o[0] + ".0.0.0";
        if (o[0] >= 128 && o[0] <= 191)
            return o[0] + "." + o[1] + ".0.0";
        if (o[0] >= 192 && o[0] <= 223)
            return o[0] + "." + o[1] + "." + o[2] + ".0";

        return "Reseau non défini";
    }



}
