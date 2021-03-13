package Connection;

import java.net.ConnectException;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.Date;

import Domain.*;
import Repository.RepoInMemo;

public class ConnectionClass {


    public static Connection conn = null;
    private static RepoInMemo repo;
    private static RepoInMemo repo_oferte;

    public ConnectionClass(RepoInMemo repo, RepoInMemo repo_oferte) {
        this.repo = repo;
        this.repo_oferte = repo_oferte;
    }


    public static void dbCon() throws SQLException {
        try {
            getConnection();

        } catch (ClassNotFoundException | SQLException | ParseException e) {
            e.printStackTrace();
        }

    }

    public static void getConnection() throws ClassNotFoundException, SQLException, ParseException {


        Class.forName("org.sqlite.JDBC");
        conn = DriverManager.getConnection("jdbc:sqlite:Lemacos.db");
        System.out.println("Succesful connected to DataBase! ");


        initialize();
    }


    private static Date convert_string_to_date(String date_str1) throws ParseException {




        String[] date_str = date_str1.split(" ", 10);

        SimpleDateFormat formatter2=new SimpleDateFormat("yyyy-mm-dd");
        Date date2=formatter2.parse(date_str[0]);



        return date2;
    }



    public static void initialize() throws SQLException, ParseException {
        //get data for clients

        Statement get = conn.createStatement();
        ResultSet result = get.executeQuery("SELECT * FROM clienti");

        while (result.next()) {

            String nume = "";
            String adresa = "";
            String telefon;
            int id;

            id = Integer.parseInt(result.getString("id"));
            nume = result.getString("nume");
            adresa = result.getString("adresa");
            telefon = result.getString("telefon");

            Client c1 = new Client(id, nume, adresa, telefon);
            repo.saveObject(c1);

        }


        Statement get1 = conn.createStatement();
        ResultSet result_oferte = get1.executeQuery("SELECT * FROM Oferte");

        while (result_oferte.next()) {

            String link = "";
            Double total;
            int id_c;
            int id_client;
            Timestamp time;
            String test;

            id_c = Integer.parseInt(result_oferte.getString("id"));
            link = result_oferte.getString("link");
            total = Double.parseDouble(result_oferte.getString("total"));
            id_client = Integer.parseInt(result_oferte.getString("id_client"));
            test = result_oferte.getString("DataComanda");

         //      System.out.println(test);

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                Date parsedDate = dateFormat.parse(test);
                Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
                time = timestamp;
        //        System.out.println(timestamp);














          //  System.out.println(id_c + "<-id     " + link + ' ' + total + ' ' + id_client + ' ' + time.toString());


            Statement get2 = conn.createStatement();
            ResultSet result_achitat = get2.executeQuery("SELECT * FROM Achitat where id_oferta =" + id_c);


            // sume achitate
            Map<Date, Double> SumeAchitate = new HashMap<Date, Double>();
            while (result_achitat.next()) {
                Double val = Double.parseDouble(result_achitat.getString("suma"));
                // System.out.println("Acitat: " + val);
                Timestamp date = result_achitat.getTimestamp("Data");
                test = result_achitat.getString("Data");


                parsedDate = dateFormat.parse(test);
                timestamp = new java.sql.Timestamp(parsedDate.getTime());
                date = timestamp;

                SumeAchitate.put(date, val);
            }


            Statement get3 = conn.createStatement();
            ResultSet result_cost = get2.executeQuery("SELECT * FROM Costuri where id_oferta =" + id_c);


            // costuri suplimentare
            Map<String, Double> Costuri = new HashMap<String, Double>();
            while (result_cost.next()) {
                Double val = Double.parseDouble(result_cost.getString("suma"));
                String marfa = result_cost.getString("marfa");
                Costuri.put(marfa, val);

            }


            Oferta new_Offer = new Oferta(link, total, SumeAchitate, Costuri, time, id_client, id_c);
            repo_oferte.saveObject(new_Offer);


        }


    }


    // Get all offers
    public  RepoInMemo get_all_offers() throws SQLException, ParseException {


        RepoInMemo repo_oferte_by_date = new RepoInMemo();
        Statement get1 = conn.createStatement();
        ResultSet result_oferte = get1.executeQuery("SELECT * FROM Oferte");


        while (result_oferte.next()) {

            String link = "";
            Double total;
            int id_c;
            int id_client;
            Timestamp time;
            String test;

            id_c = Integer.parseInt(result_oferte.getString("id"));
            link = result_oferte.getString("link");
            total = Double.parseDouble(result_oferte.getString("total"));
            id_client = Integer.parseInt(result_oferte.getString("id_client"));
            test = result_oferte.getString("DataComanda");

            //      System.out.println(test);

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Date parsedDate = dateFormat.parse(test);
            Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
            time = timestamp;
            //        System.out.println(timestamp);


            //  System.out.println(id_c + "<-id     " + link + ' ' + total + ' ' + id_client + ' ' + time.toString());


            Statement get2 = conn.createStatement();
            ResultSet result_achitat = get2.executeQuery("SELECT * FROM Achitat where id_oferta =" + id_c);


            // sume achitate
            Map<Date, Double> SumeAchitate = new HashMap<Date, Double>();
            while (result_achitat.next()) {
                Double val = Double.parseDouble(result_achitat.getString("suma"));
                // System.out.println("Acitat: " + val);
                Timestamp date = result_achitat.getTimestamp("Data");
                test = result_achitat.getString("Data");


                parsedDate = dateFormat.parse(test);
                timestamp = new java.sql.Timestamp(parsedDate.getTime());
                date = timestamp;

                SumeAchitate.put(date, val);
            }


            Statement get3 = conn.createStatement();
            ResultSet result_cost = get2.executeQuery("SELECT * FROM Costuri where id_oferta =" + id_c);


            // costuri suplimentare
            Map<String, Double> Costuri = new HashMap<String, Double>();
            while (result_cost.next()) {
                Double val = Double.parseDouble(result_cost.getString("suma"));
                String marfa = result_cost.getString("marfa");
                Costuri.put(marfa, val);

            }


            Oferta new_Offer = new Oferta(link, total, SumeAchitate, Costuri, time, id_client, id_c);
            repo_oferte_by_date.saveObject(new_Offer);


        }


        return  repo_oferte_by_date;
    }



    // Get all offers By dates

    public  RepoInMemo get_all_offers_by_dates(LocalDate first_Date, LocalDate second_Date) throws SQLException, ParseException {


        Statement get1 = conn.createStatement();
        ResultSet result_oferte = get1.executeQuery("SELECT * FROM Oferte where DataComanda BETWEEN '"+first_Date+"' AND '"+second_Date+"' ORDER BY DataComanda ASC;");

        RepoInMemo repo_oferte_by_date = new RepoInMemo();
        while (result_oferte.next()) {
            String link = "";
            Double total;
            int id_c;
            int id_client;
            Timestamp time;
            String test;

            id_c = Integer.parseInt(result_oferte.getString("id"));
            link = result_oferte.getString("link");
            total = Double.parseDouble(result_oferte.getString("total"));
            id_client = Integer.parseInt(result_oferte.getString("id_client"));
            test = result_oferte.getString("DataComanda");




            //      System.out.println(test);

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Date parsedDate = dateFormat.parse(test);
            Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
            time = timestamp;
            //        System.out.println(timestamp);


            //  System.out.println(id_c + "<-id     " + link + ' ' + total + ' ' + id_client + ' ' + time.toString());


            Statement get2 = conn.createStatement();
            ResultSet result_achitat = get2.executeQuery("SELECT * FROM Achitat where id_oferta =" + id_c);


            // sume achitate
            Map<Date, Double> SumeAchitate = new HashMap<Date, Double>();
            while (result_achitat.next()) {
                Double val = Double.parseDouble(result_achitat.getString("suma"));
                // System.out.println("Acitat: " + val);
                Timestamp date = result_achitat.getTimestamp("Data");
                test = result_achitat.getString("Data");


                parsedDate = dateFormat.parse(test);
                timestamp = new java.sql.Timestamp(parsedDate.getTime());
                date = timestamp;

                SumeAchitate.put(date, val);
            }


            Statement get3 = conn.createStatement();
            ResultSet result_cost = get2.executeQuery("SELECT * FROM Costuri where id_oferta =" + id_c);


            // costuri suplimentare
            Map<String, Double> Costuri = new HashMap<String, Double>();
            while (result_cost.next()) {
                Double val = Double.parseDouble(result_cost.getString("suma"));
                String marfa = result_cost.getString("marfa");
                Costuri.put(marfa, val);

            }


            Oferta new_Offer = new Oferta(link, total, SumeAchitate, Costuri, time, id_client, id_c);
           // System.out.println(new_Offer);

            repo_oferte_by_date.saveObject(new_Offer);


        }

        return  repo_oferte_by_date;
    }







    // Get all offers of a client based on his id
    public RepoInMemo get_offers_by_client_id(int client_id) throws SQLException, ParseException {
        Statement get1 = conn.createStatement();
        ResultSet result_oferte = get1.executeQuery("SELECT * FROM Oferte where id_client=" + client_id);

        RepoInMemo offers_by_id = new RepoInMemo();
        while (result_oferte.next()) {

            String link = "";
            Double total;
            int id_c;
            int id_client;
            Timestamp time;


            id_c = Integer.parseInt(result_oferte.getString("id"));
            link = result_oferte.getString("link");
            total = Double.parseDouble(result_oferte.getString("total"));
            id_client = Integer.parseInt(result_oferte.getString("id_client"));
            time = result_oferte.getTimestamp("DataComanda");


            String test = result_oferte.getString("DataComanda");

            //      System.out.println(test);

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Date parsedDate = dateFormat.parse(test);
            Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
            time = timestamp;

            // System.out.println(id_c + "<-id     " + link + ' ' + total + ' ' + id_client + ' ' + time.toString());


            Statement get2 = conn.createStatement();
            ResultSet result_achitat = get2.executeQuery("SELECT * FROM Achitat where id_oferta =" + id_c);


            // sume achitate
            Map<Date, Double> SumeAchitate = new HashMap<Date, Double>();
            while (result_achitat.next()) {
                Double val = Double.parseDouble(result_achitat.getString("suma"));
                // System.out.println("Acitat: " + val);
                Timestamp date = result_achitat.getTimestamp("Data");
                test = result_achitat.getString("Data");


                dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                parsedDate = dateFormat.parse(test);
                timestamp = new java.sql.Timestamp(parsedDate.getTime());
                date = timestamp;

                SumeAchitate.put(date, val);
            }


            Statement get3 = conn.createStatement();
            ResultSet result_cost = get2.executeQuery("SELECT * FROM Costuri where id_oferta =" + id_c);


            // costuri suplimentare
            Map<String, Double> Costuri = new HashMap<String, Double>();
            while (result_cost.next()) {
                Double val = Double.parseDouble(result_cost.getString("suma"));
                String marfa = result_cost.getString("marfa");
                Costuri.put(marfa, val);
            }


            Oferta new_Offer = new Oferta(link, total, SumeAchitate, Costuri, time, id_client, id_c);
            offers_by_id.saveObject(new_Offer);


        }

       // System.out.println(offers_by_id.getAll());

        return offers_by_id;
    }



    // returneaza sumele achitate ale unei oferte
    public RepoInMemo get_SumeAchitate_by_id(int id_c) throws SQLException, ParseException {

        RepoInMemo memo = new RepoInMemo();

        Statement get2 = conn.createStatement();
        ResultSet result_achitat = get2.executeQuery("SELECT * FROM Achitat where id_oferta =" + id_c);


        while (result_achitat.next())
        {
            Double val = Double.parseDouble(result_achitat.getString("suma"));
            Timestamp date = result_achitat.getTimestamp("Data");

            String test = result_achitat.getString("Data");

            //      System.out.println(test);

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Date parsedDate = dateFormat.parse(test);
            Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
            date = timestamp;



            int id = Integer.parseInt(result_achitat.getString("id"));

            SumeAchitate sa = new SumeAchitate(id, date, id_c, val);
           // System.out.println(sa);
            memo.saveObject(sa);
        }

        return memo;
    }


    // searches in clients data base by characters (search bar instruction)
    public RepoInMemo search_by_characters(String search_data) throws SQLException {
        RepoInMemo search_repo = new RepoInMemo();


     //   System.out.println(search_data);
        Statement get1 = conn.createStatement();
        ResultSet result_search = get1.executeQuery(" Select * from Clienti WHERE "+
                        " id like '%"+search_data+"%' "+
                        " or nume like '%"+search_data+"%' "+
                        " or adresa like '%"+search_data+"%' ");

        while (result_search.next())
        {
            String nume = "";
            String adresa = "";
            String telefon;
            int id;

            id = Integer.parseInt(result_search.getString("id"));
            nume = result_search.getString("nume");
            adresa = result_search.getString("adresa");
            telefon = result_search.getString("telefon");

            Client c1 = new Client(id, nume, adresa, telefon);
            search_repo.saveObject(c1);
        }

        return  search_repo;
    }



    // ADD Client to db

    public void Add_Client_to_DB(Client client) throws SQLException {
        Statement statement = conn.createStatement();
        statement.executeUpdate("Insert into Clienti (nume, adresa, telefon) Values " +

                " ('"+client.getNume()+"', '"+client.getAdresa()+"','"+client.getTelefon()+"');"


                );

    }

    public  void Delete_Client_from_DB(Client client) throws SQLException {
        Statement statement = conn.createStatement();
        statement.executeUpdate("Delete from clienti where id="+client.getId());


        Statement statement1 = conn.createStatement();
        statement1.executeUpdate("Delete from oferte where id_client="+client.getId());

    }

    public  void Update_Client(Client client) throws SQLException {
       // Statement statement = conn.createStatement();
       // statement.executeUpdate("Update clienti set nume ='"+client.getNume()+"', adresa='"+client.getAdresa()+"', telefon ='"+client.getTelefon()+"' where id="+client.getId());

        String sql = "UPDATE Clienti set nume = '"+client.getNume()+"' "
                + ", adresa = '"+client.getAdresa()+"'"
                + ", telefon = '"+client.getTelefon()+"'"
                + " WHERE id = "+client.getId();


       // System.out.println(sql);
        PreparedStatement pstmt = conn.prepareStatement(sql);


            pstmt.executeUpdate();



    }


    //add Offer
    public void Add_Offer(String link, Double pretFinal, int id_client, LocalDate date) throws SQLException {
        Statement statement = conn.createStatement();
        statement.executeUpdate("Insert into Oferte (id_client, link, total, DataComanda) Values " +

                " ("+id_client+", '"+link+"',"+pretFinal+", '"+date+" 00:00:00');"
        );


    }

    //Update offer
    public  void Update_Offer(Oferta oferta) throws SQLException {

        String sql = "UPDATE Oferte set link = '"+oferta.getLink()+"' "
                + ", total = "+oferta.getPretFinal()
                + " WHERE id = "+oferta.getId_oferta();


       // System.out.println(sql);
        PreparedStatement pstmt = conn.prepareStatement(sql);

        pstmt.executeUpdate();
    }


    // delete offer

    public void Delete_Offer_from_DB(Oferta oferta) throws SQLException {
        Statement statement = conn.createStatement();
        statement.executeUpdate("Delete from Oferte where id="+oferta.getId_oferta());
    }

    // add payment

    public  void Add_Payment_DB(Double sum, int id_offer) throws SQLException {
        Statement statement = conn.createStatement();
        statement.executeUpdate("Insert into Achitat (id_oferta, suma) Values " +

                " ("+id_offer+", "+sum+");"
        );
    }

    // delete payment

    public void Delete_Payment_from_DB(SumeAchitate achitate) throws SQLException {
        Statement statement = conn.createStatement();
        statement.executeUpdate("Delete from Achitat where id="+achitate.getId());
    }









}
