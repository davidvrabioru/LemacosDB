package Domain;
import java.util.ArrayList;
import  java.util.Date;
import java.util.Objects;

public class ClientPotential extends Client{

    private String info;
    private ArrayList<Date> notificari;

public ClientPotential(int id, String nume, String adresa, String telefon,String info, ArrayList<Date> notificari )
{
    super(id, nume,adresa,telefon);
    this.info = info;
    this.notificari = notificari;

}

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public ArrayList<Date> getNotificari() {
        return notificari;
    }

    public void setNotificari(ArrayList<Date> notificari) {
        this.notificari = notificari;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ClientPotential)) return false;
        if (!super.equals(o)) return false;
        ClientPotential that = (ClientPotential) o;
        return Objects.equals(info, that.info) &&
                Objects.equals(notificari, that.notificari);
    }

    @Override
    public String toString() {
        return "ClientPotential{" +
                "info='" + info + '\'' +
                ", notificari=" + notificari +
                ", id=" + id +
                ", nume='" + nume + '\'' +
                ", adresa='" + adresa + '\'' +
                ", telefon=" + telefon +
                '}';
    }
}
