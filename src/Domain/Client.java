package Domain;

import java.util.Objects;

public class Client {


   protected int id;
   protected String nume, adresa;
   protected String telefon;



    public Client(int id, String nume, String adresa, String telefon)
    {
        this.id = id;
        this.telefon = telefon;
        this.adresa = adresa;
        this.nume = nume;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return id == client.id &&
                telefon == client.telefon &&
                Objects.equals(nume, client.nume) &&
                Objects.equals(adresa, client.adresa);
    }

    @Override
    public String toString() {
        return
                "id=" + id +
                ", nume='" + nume + '\'' +
                ", adresa='" + adresa + '\'' +
                ", telefon=" + telefon;
    }
}
