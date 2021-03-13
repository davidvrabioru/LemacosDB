package Domain;

import java.util.ArrayList;

public class ClientFull extends Client{

    ArrayList<String> informatii;
    ArrayList<Oferta> oferte;

    public ClientFull(int id, String nume, String adresa, String telefon, ArrayList<String> informatii, ArrayList<Oferta> oferte)
    {
        super(id, nume, adresa, telefon);
        this.informatii = informatii;
        this.oferte = oferte;
    }

    public ArrayList<String> getInformatii() {
        return informatii;
    }

    public void setInformatii(ArrayList<String> informatii) {
        this.informatii = informatii;
    }

    public ArrayList<Oferta> getOferte() {
        return oferte;
    }

    public void setOferte(ArrayList<Oferta> oferte) {
        this.oferte = oferte;
    }

    @Override
    public String toString() {
        return "ClientFull{" +
                "informatii=" + informatii +
                ", oferte=" + oferte +
                ", id=" + id +
                ", nume='" + nume + '\'' +
                ", adresa='" + adresa + '\'' +
                ", telefon=" + telefon +
                '}';
    }
}
