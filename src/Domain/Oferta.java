package Domain;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

public class Oferta {

    private int id_oferta;
    private int id_client;
    private String link;
    private double PretFinal;
    private Map<Date,Double> SumeAchitate;
    private Map<String,Double> CosturiSuplimentare;
    private Timestamp dataLivrare;


    public Oferta(String link, double PretFinal, Map<Date, Double> sume, Map<String, Double> Costuri, Timestamp livrare, int id_client, int id_oferta)

    {
        this.link = link;
        this.PretFinal = PretFinal;
        this.SumeAchitate = sume;
        this.CosturiSuplimentare = Costuri;
        this.dataLivrare = livrare;
        this.id_client = id_client;
        this.id_oferta = id_oferta;
    }


    public int getId_oferta() {
        return id_oferta;
    }

    public void setId_oferta(int id_oferta) {
        this.id_oferta = id_oferta;
    }

    public int getId_client() {
        return id_client;
    }

    public void setId_client(int id_client) {
        this.id_client = id_client;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public double getPretFinal() {
        return PretFinal;
    }

    public void setPretFinal(double pretFinal) {
        PretFinal = pretFinal;
    }

    public Map<Date, Double> getSumeAchitate() {
        return SumeAchitate;
    }

    public void setSumeAchitate(Map<Date, Double> sumeAchitate) {
        SumeAchitate = sumeAchitate;
    }

    public Map<String, Double> getCosturiSuplimentare() {
        return CosturiSuplimentare;
    }

    public void setCosturiSuplimentare(Map<String, Double> costuriSuplimentare) {
        CosturiSuplimentare = costuriSuplimentare;
    }


    public Timestamp getDataLivrare() {
        return dataLivrare;
    }

    public void setDataLivrare(Timestamp dataLivrare) {
        this.dataLivrare = dataLivrare;
    }

    @Override
    public String toString() {
        String r = "Oferta{\n" +
                "id = " + id_oferta + '\n'+
                "link = " + link + '\n' +
                "PretFinal = " + PretFinal +'\n'+
                "dataLivrare = " + dataLivrare + '\n';


        r+= "Plati Facute: \n";
        for (Map.Entry<Date, Double> entry : this.SumeAchitate.entrySet()) {
            r = r + (entry.getKey() + "    -> " + entry.getValue().toString());
            r = r + "\n";
        }
        r+="\nCosturi Suplimentare: \n";
        for (Map.Entry<String , Double> entry : this.CosturiSuplimentare.entrySet()) {
            r = r + (entry.getKey() + "    -> " + entry.getValue().toString());
            r = r + "\n";
        }




        return r;
    }
}
