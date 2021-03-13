package sample;

import Connection.ConnectionClass;
import Repository.RepoInMemo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.*;

import java.io.IOException;
import java.util.Arrays;
import Domain.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Controller implements Initializable {


    @FXML
    private Button clientiButton;


    // Chart

    @FXML
    private LineChart<String, Number> chart;

    @FXML
    private javafx.scene.chart.CategoryAxis CategoryAxis;

    @FXML
    private javafx.scene.chart.NumberAxis NumberAxis;



    RepoInMemo repo_clienti = new RepoInMemo();
    RepoInMemo repo_oferte = new RepoInMemo();
    ConnectionClass con = null;




    @FXML
    private DatePicker first_date;

    @FXML
    private DatePicker second_date;


    XYChart.Series series = new XYChart.Series();
    @Override
    public void initialize(URL location, ResourceBundle resources) {


        con = new ConnectionClass(repo_clienti, repo_oferte);
        try {
            con.dbCon();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        // chart initialize get all offers

        try {





            second_date.setValue(LocalDate.of(LocalDate.now().getYear(),LocalDate.now().getMonth(), LocalDate.now().getDayOfMonth()+1));
            first_date.setValue(LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth().getValue(), 1));

            RepoInMemo oferte = con.get_all_offers_by_dates(first_date.getValue(), second_date.getValue());
            AvgOrder(oferte);
            CountOrders(oferte);
            Sales(oferte);
            cifra_rate();
            initialize_barChart();

            chart.getData().removeAll();
            // initialize chart
            for(int i = 0; i< oferte.getAll().size(); i++)
            {

                Oferta of = (Oferta)oferte.getAll().get(i);


                String x = of.getDataLivrare().toString();
                Double y = of.getPretFinal();
                series.getData().add(new XYChart.Data(x,y));
            }

            chart.getData().addAll(series);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


// updating dashbord data
        second_date.valueProperty().addListener((observable, oldDate, newDate)->{

            if(first_date.getValue().isAfter(second_date.getValue()))
            {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setHeaderText("Eroare!");
                errorAlert.setContentText("Prima data selectata trebuie sa fie mai mica sau egala decat a doua");
                errorAlert.showAndWait();
            }


            else{

                try {
                    initialize_barChart();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                RepoInMemo oferte = null;
                try {
                    oferte = con.get_all_offers_by_dates(first_date.getValue(), second_date.getValue());
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                refresh_chart();
                AvgOrder(oferte);
                CountOrders(oferte);
                try {
                    Sales(oferte);
                } catch (InterruptedException | ParseException e) {
                    e.printStackTrace();
                }
            }
        });


        first_date.valueProperty().addListener((observable, oldDate, newDate)->{

            if(first_date.getValue().isAfter(second_date.getValue()))
            {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setHeaderText("Eroare!");
                errorAlert.setContentText("Prima data selectata trebuie sa fie mai mica sau egala decat a doua");
                errorAlert.showAndWait();
            }


            else{


                RepoInMemo oferte = null;
                try {
                    oferte = con.get_all_offers_by_dates(first_date.getValue(), second_date.getValue());
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                refresh_chart();
                AvgOrder(oferte);
                CountOrders(oferte);
                try {
                    Sales(oferte);
                } catch (InterruptedException | ParseException e) {
                    e.printStackTrace();
                }
            }
        });



    }


    // Refresh Chart when set different date


    void refresh_chart() {


        series.getData().clear();
        try {


            RepoInMemo oferte = con.get_all_offers_by_dates(first_date.getValue(), second_date.getValue());



            for(int i = 0; i< oferte.getAll().size(); i++)
            {

                Oferta of = (Oferta)oferte.getAll().get(i);
                String x = of.getDataLivrare().toString();
                Double y = of.getPretFinal();
                series.getData().add(new XYChart.Data(x,y));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }


    }


    @FXML
    void ButtonAction(ActionEvent event)
    {
        try{


            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("clienti.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();


            clienti c_controller = fxmlLoader.getController();
            c_controller.initialize_repo(repo_clienti, repo_oferte, con);



            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.show();
            Stage primaryStage = (Stage) clientiButton.getScene().getWindow();
            primaryStage.close();
            stage.setMaximized(true);

         //   stage.setScene(new Scene(root1, 1100, 500));



        }catch (Exception e)
        {
            System.out.println(e);
        }
    }




    // Avg Per Order

    @FXML
    private Label AvgPerOrd;

    private void AvgOrder(RepoInMemo oferte)
    {
        Double Val = 0.0;
            for(int i=0; i< oferte.getAll().size(); i++)
            {
                Oferta of = (Oferta)oferte.getAll().get(i);
                Val+=of.getPretFinal();
            }

            Double avg = Val/oferte.getAll().size();

        DecimalFormat numberFormat = new DecimalFormat("#.0");
        AvgPerOrd.setText("Media pe comanda: "+numberFormat.format(avg) +" €");

    }



// orders count

    @FXML
    private Label nr_orders;

    private void CountOrders(RepoInMemo oferte)
    {
        nr_orders.setText("Nr. Comenzi: " + oferte.getAll().size());
    }


    // sales

    @FXML
    private Label sales;

    @FXML
    private ProgressBar bar;
     //progres Bar
     @FXML
     private ProgressIndicator bar2;

    @FXML
    private ProgressIndicator bar21;

    private  void Sales(RepoInMemo oferte) throws InterruptedException, ParseException {

        Double Val = 0.0;
        for(int i=0; i< oferte.getAll().size(); i++)
        {
            Oferta of = (Oferta)oferte.getAll().get(i);
            Val+=of.getPretFinal();
        }
        DecimalFormat numberFormat = new DecimalFormat("#.0");
        sales.setText(numberFormat.format(Val) +" €");



        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String[] date = first_date.getValue().toString().split(" ",2);
        String[] date1 = second_date.getValue().toString().split(" ",2);
        Date firstDate = sdf.parse(date[0]);
        Date secondDate = sdf.parse(date1[0]);


        long diffInMillies = Math.abs(secondDate.getTime() - firstDate.getTime());
        long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);

        int procent;
        Double target = 3000.0 * diff;
        procent = (int) ((Val*100)/target);


        if(procent > 80) {
            bar.setStyle("-fx-accent: #009688;");
            bar2.setStyle("-fx-accent: #009688;");
            bar21.setStyle("-fx-accent: #009688;");
        }

        else{

            bar.setStyle("-fx-accent: #B71815;");
            bar2.setStyle("-fx-accent: #B71815;");
            bar21.setStyle("-fx-accent: #B71815;");
        }

        bar.setProgress(procent / 100.0);
        bar2.setProgress(procent/100.0);
        bar21.setProgress(procent/100.0);


    }


    @FXML
    private Label rate;

    private void cifra_rate() throws SQLException, ParseException {
        RepoInMemo all_offers = con.get_all_offers();
        Double Rate = 0.0;
        for(int i = 0; i<all_offers.getAll().size();i++)
        {

            Double achitat = 0.0;
            Oferta oferta = (Oferta) all_offers.getAll().get(i);
            Map<Date,Double> plati = oferta.getSumeAchitate();


           // System.out.println(oferta);


            for (Map.Entry<Date, Double> entry : plati.entrySet()) {
                achitat = achitat + entry.getValue();
            }

            if(oferta.getPretFinal()-achitat>0.0)
            {
                Rate = Rate+(oferta.getPretFinal()-achitat);
            }

        }


        Double Val = 0.0;
        for(int j=0; j< all_offers.getAll().size(); j++)
        {
            Oferta of = (Oferta)all_offers.getAll().get(j);
            Val+=of.getPretFinal();
        }

        if((Rate*100)/Val > 25) {


            rate.setTextFill(Color.web("#B71815"));

            if((Rate*100)/Val > 80) {

                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setHeaderText("Atentie!");
                errorAlert.setContentText("Firma ruleaza la risc ridicat datorita procentajului mare de rata in valoare de: " + (Rate * 100) / Val + "%");
                errorAlert.showAndWait();
                //  System.out.println("OPA!");

            }
        }

        rate.setText(Rate.toString() + "€");


    }




    // Avg order value per month

    @FXML
    private BarChart<String, Double> AvgOrderOnMonth;

    @FXML
    private CategoryAxis x;

    @FXML
    private NumberAxis y;

    XYChart.Series ser = new XYChart.Series();

    private  void initialize_barChart() throws SQLException, ParseException {

        LocalDate localDate = LocalDate.of(second_date.getValue().getYear(),1,1);
        LocalDate localDate1 = LocalDate.of(second_date.getValue().getYear(),12,30);
        Double[] months = {0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0};
        ArrayList<Oferta> ofere_pe_an = con.get_all_offers_by_dates(localDate, localDate1).getAll();


                for(int i=1;i<=12;i++)
                {

                    double sum = 0.0;
                    int count = 0;
                    for(Oferta oferta : ofere_pe_an){



                        long timestamp = oferta.getDataLivrare().getTime();
                        Calendar cal = Calendar.getInstance();
                        cal.setTimeInMillis(timestamp);
                        int month = cal.get(Calendar.MONTH);
                        month++;
                        if(month == i) {
                           // System.out.println(month);
                            sum += oferta.getPretFinal();
                            count++;
                        }



                    }

                    if(sum != 0.0) {
                        months[i] = sum / count;
                      //  System.out.println(months[i]);
                    }
                }


        ser.getData().add(new XYChart.Data("Ian",months[1]));
        ser.getData().add(new XYChart.Data("Feb",months[2]));
        ser.getData().add(new XYChart.Data("Mar",months[3]));
        ser.getData().add(new XYChart.Data("Apr",months[4]));
        ser.getData().add(new XYChart.Data("Mau",months[5]));
        ser.getData().add(new XYChart.Data("Iun",months[6]));
        ser.getData().add(new XYChart.Data("Iul",months[7]));
        ser.getData().add(new XYChart.Data("Aug",months[8]));
        ser.getData().add(new XYChart.Data("Sep",months[9]));
        ser.getData().add(new XYChart.Data("Oct",months[10]));
        ser.getData().add(new XYChart.Data("Nov",months[11]));
        ser.getData().add(new XYChart.Data("Dec",months[12]));

        AvgOrderOnMonth.getData().addAll(ser);
    }


    //info form
    @FXML
    void info_button_action(ActionEvent event) throws IOException {



        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Info.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();

        Stage stage = new Stage();
        stage.setScene(new Scene(root1));
        stage.show();
        Stage primaryStage = (Stage) clientiButton.getScene().getWindow();

        stage.setMaximized(false);
        stage.setResizable(false);
        stage.setAlwaysOnTop(false);

    }

}
