package Domain;

import java.util.ArrayList;
import java.util.Objects;
import java.lang.*;
public class Agent {

    private int id;
    private String nume;
    private ArrayList<ClientFull> clientiFull;
    private ArrayList<ClientPotential> clientiPotentiali;
    private long telefon;

    public Agent(int id, String nume, ArrayList<ClientFull> cf, ArrayList<ClientPotential> cp, long telefon)
    {
        this.id = id;
        this.nume = nume;
        this.clientiFull = cf;
        this.clientiPotentiali = cp;
        this.telefon = telefon;
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

    public ArrayList<ClientFull> getClientiFull() {
        return clientiFull;
    }

    public void setClientiFull(ArrayList<ClientFull> clientiFull) {
        this.clientiFull = clientiFull;
    }

    public ArrayList<ClientPotential> getClientiPotentiali() {
        return clientiPotentiali;
    }

    public void setClientiPotentiali(ArrayList<ClientPotential> clientiPotentiali) {
        this.clientiPotentiali = clientiPotentiali;
    }

    public long getTelefon() {
        return telefon;
    }

    public void setTelefon(long telefon) {
        this.telefon = telefon;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Agent)) return false;
        Agent agent = (Agent) o;
        return id == agent.id &&
                telefon == agent.telefon &&
                Objects.equals(nume, agent.nume) &&
                Objects.equals(clientiFull, agent.clientiFull) &&
                Objects.equals(clientiPotentiali, agent.clientiPotentiali);
    }

    @Override
    public String toString() {
        return "Agent{" +
                "id=" + id +
                ", nume='" + nume + '\'' +
                ", clientiFull=" + clientiFull +
                ", clientiPotentiali=" + clientiPotentiali +
                ", telefon=" + telefon +
                '}';
    }

}
