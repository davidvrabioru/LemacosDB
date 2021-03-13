package Domain;

import java.util.Date;
import java.util.Objects;

public class SumeAchitate {


    private int id;
    private Date Data;
    private int id_oferta;
    private Double suma;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getData() {
        return Data;
    }

    public void setData(Date data) {
        Data = data;
    }

    public int getId_oferta() {
        return id_oferta;
    }

    public void setId_oferta(int id_oferta) {
        this.id_oferta = id_oferta;
    }

    public Double getSuma() {
        return suma;
    }

    public void setSuma(Double suma) {
        this.suma = suma;
    }

    public SumeAchitate(int id, Date data, int id_oferta, Double val)
    {
            this.id = id;
            this.Data = data;
            this.id_oferta = id_oferta;
            this.suma = val;
    }




    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SumeAchitate sumeAchitate = (SumeAchitate) o;
        return id == sumeAchitate.id &&
                id_oferta == sumeAchitate.id_oferta &&
                Objects.equals(suma, sumeAchitate.suma) &&
                Objects.equals(Data, sumeAchitate.Data);
    }

    @Override
    public String toString() {
        return
                "id=" + id +
                        ", id_oferta='" + id_oferta + '\n' +
                        ", suma='" + suma + '\n' +
                        ", data=" + Data;
    }
}

