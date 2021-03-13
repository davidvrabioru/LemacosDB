package ServiceL;

import Domain.Oferta;
import Repository.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Service {

    RepoInMemo repo;

    public Service(RepoInMemo repo) {
        this.repo = repo;
    }
/*
    public void add_from_file() throws FileNotFoundException, IOException, ParseException // adaugare date din fisier
    {
        File objects = new File("C:\\Users\\DVY\\Desktop\\INFO\\date.in");
        Scanner scanner = new Scanner(objects);
        while (scanner.hasNextLine()) {

            String[] atributes = scanner.nextLine().split(",", -1);
            String adresa = atributes[0];
            double valoare = Double.parseDouble(atributes[1]);
            String[] plati = atributes[2].split(" ");

            Map<Date, Double> SumeAchitate = new HashMap<Date, Double>();

            Map<String, Double> Costuri = new HashMap<String, Double>();


            for (int i = 0; i < plati.length; i++) {
                String[] plata = plati[i].split("/");
                double suma = Double.parseDouble(plata[1]);
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                String dateString = format.format(new Date());
                Date date = format.parse(plata[0]);

                SumeAchitate.put(date, suma);

            }

            String[] costuri = atributes[3].split("-");
            for (int i = 0; i < costuri.length; i++) {

                String[] cost = costuri[i].split("/");
                double val = Double.parseDouble(cost[1]);
                String denumire = cost[0];
                Costuri.put(denumire, val);
            }

            String data_montaj = atributes[4];
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String dateString = format.format(new Date());
            Date datamontaj = format.parse(data_montaj);


            Oferta oferta = new Oferta(adresa, valoare, SumeAchitate, Costuri, datamontaj,0,0);
            System.out.println(oferta);






        }

 */

}