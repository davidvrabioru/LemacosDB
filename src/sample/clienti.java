package sample;

import Connection.ConnectionClass;
import Domain.Client;
import Domain.Oferta;
import Domain.SumeAchitate;
import Repository.RepoInMemo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.*;

public class clienti implements Initializable {




    RepoInMemo repo_clienti = new RepoInMemo();
    RepoInMemo repo_oferte = new RepoInMemo();
    ConnectionClass con = null;


    @FXML
    private Button DashButton;

    @FXML
    private Button clientiButton;

    @FXML
    private TableView<Client> TabelClienti;








    @FXML
    private TableColumn<Client, String> id;

    @FXML
    private TableColumn<Client, String> nume;

    @FXML
    private TableColumn<Client, String> adresa;

    @FXML
    private TableColumn<Client, String> contact;





    @FXML
    private TableView<Oferta> TabelOferte;

    @FXML
    private TableColumn<Oferta, Integer> id_oferta;


    @FXML
    private TableColumn<Oferta, Integer> id_client;

    @FXML
    private TableColumn<Oferta, Double> pret;

    @FXML
    private TableColumn<Oferta, Timestamp> data;


    @FXML
    private Button Sterge;
    @FXML
    private Button update_button;


    @FXML
    private Button add_payment;
    @FXML
    private Button delete_payment;





    @Override
    public void initialize(URL location, ResourceBundle resources) {



        add_offer.setDisable(true);
        delete_offer.setDisable(true);
        update_button.setDisable(true);
        Sterge.setDisable(true);
            TabelClienti.setOnMouseClicked(e -> {
                try {
                    total.setVisible(false);
                    achitari.getItems().clear();
                    update_button.setDisable(false);
                    Sterge.setDisable(false);
                    tabelClientiSelect();
                } catch (SQLException | ParseException throwables) {
                    throwables.printStackTrace();
                }
            });

            TabelOferte.setOnMouseClicked(e->{

                try {
                    tabelOferteSelect();
                } catch (SQLException | ParseException throwables) {
                    throwables.printStackTrace();
                }

            });

        achitari.setOnMouseClicked(e->{

            delete_payment.setDisable(false);

        });

    }




    @FXML
    private TableView<SumeAchitate> achitari;

    @FXML
    private TableColumn<SumeAchitate, Date> data_achitare;

    @FXML
    private TableColumn<SumeAchitate, Double> suma_achitare;

    private  void tabelOferteSelect() throws SQLException, ParseException {

        total_payments();
        int id_of = TabelOferte.getSelectionModel().getSelectedItem().getId_oferta();
        delete_offer.setDisable(false);
        add_payment.setDisable(false);
        delete_payment.setDisable(true);
        suma_achitare.setCellValueFactory(new PropertyValueFactory<>("suma"));
        data_achitare.setCellValueFactory(new PropertyValueFactory<>("Data"));


        ArrayList<SumeAchitate> oferte_memo = con.get_SumeAchitate_by_id(id_of).getAll();
        System.out.println(oferte_memo);
        ObservableList<SumeAchitate> observableList_oferte = FXCollections.observableArrayList(oferte_memo);
        achitari.setItems(observableList_oferte);


    }




    private void tabelClientiSelect() throws SQLException, ParseException {
        int id_client_const = TabelClienti.getSelectionModel().getSelectedItem().getId();
       // System.out.println(id_client_const);


       // System.out.println(con.get_offers_by_client_id(id_client_const).getAll());



        add_offer.setDisable(false);
        delete_offer.setDisable(true);
        add_payment.setDisable(true);
        delete_payment.setDisable(true);


        //id_oferta.setCellValueFactory(new PropertyValueFactory<>("id_oferta"));
        id_client.setCellValueFactory(new PropertyValueFactory<>("id_client"));
        pret.setCellValueFactory(new PropertyValueFactory<>("PretFinal"));
        data.setCellValueFactory(new PropertyValueFactory<>("dataLivrare"));

        ArrayList<Oferta> oferte_memo = con.get_offers_by_client_id(id_client_const).getAll();
        ObservableList<Oferta> observableList_oferte = FXCollections.observableArrayList(oferte_memo);
        TabelOferte.setItems(observableList_oferte);


    }



    public void initialize_repo(RepoInMemo repo_clienti, RepoInMemo repo_oferte, ConnectionClass con)
    {
        this.repo_clienti = repo_clienti;
        this.repo_oferte = repo_oferte;
        this.con = con;

       // System.out.println(this.repo_clienti.getAll().toString());


        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        nume.setCellValueFactory(new PropertyValueFactory<>("nume"));
        adresa.setCellValueFactory(new PropertyValueFactory<>("adresa"));
        contact.setCellValueFactory(new PropertyValueFactory<>("telefon"));


        ArrayList<Client> clients_memo = repo_clienti.getAll();
       // System.out.println(clients_memo);
        ObservableList<Client> observableList = FXCollections.observableArrayList(clients_memo);
        TabelClienti.setItems(observableList);




        // oferte clienti initializare tabel



       // id_oferta.setCellValueFactory(new PropertyValueFactory<>("id_oferta"));
        id_client.setCellValueFactory(new PropertyValueFactory<>("id_client"));
        pret.setCellValueFactory(new PropertyValueFactory<>("PretFinal"));
        data.setCellValueFactory(new PropertyValueFactory<>("dataLivrare"));

        ArrayList<Oferta> oferte_memo = repo_oferte.getAll();
       // System.out.println(oferte_memo.get(0).getId());
        ObservableList<Oferta> observableList_oferte = FXCollections.observableArrayList(oferte_memo);
       TabelOferte.setItems(observableList_oferte);





    }








    public void ButtonAction(ActionEvent event) {

        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("test.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.show();
            Stage primaryStage = (Stage) DashButton.getScene().getWindow();
            primaryStage.close();
            stage.setMaximized(true);
            stage.setResizable(true);



        }catch (Exception e)
        {
            System.out.println(e);
        }
    }



    @FXML
    void StergeClient(ActionEvent event) throws SQLException {


        Client client = TabelClienti.getSelectionModel().getSelectedItem();


        Alert errorAlert = new Alert(Alert.AlertType.CONFIRMATION);
        errorAlert.setHeaderText("Esti pe cale sa stergi un client! ");
        errorAlert.setContentText("Doriti sa stergeti clientul: "+client+ "  ?");


        Optional<ButtonType> result = errorAlert.showAndWait();
        if (result.get() == ButtonType.OK){
            con.Delete_Client_from_DB(client);
            search_button.fire();
        }
        else{

        }






    }



    // search bar

    @FXML
    private TextField search_bar;

    @FXML
    private Button search_button;

    @FXML
    void search_filter(ActionEvent event) throws SQLException {


        update_button.setDisable(true);
        add_offer.setDisable(true);
        delete_offer.setDisable(true);
        Sterge.setDisable(true);
        total.setVisible(false);
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        nume.setCellValueFactory(new PropertyValueFactory<>("nume"));
        adresa.setCellValueFactory(new PropertyValueFactory<>("adresa"));
        contact.setCellValueFactory(new PropertyValueFactory<>("telefon"));


        TabelOferte.getItems().clear();

        ArrayList<Client> clients_memo = con.search_by_characters(search_bar.getText()).getAll();
        //System.out.println(clients_memo);
        ObservableList<Client> observableList = FXCollections.observableArrayList(clients_memo);
        TabelClienti.setItems(observableList);

    }


    // Add client


    // action event



    @FXML
    private Button AddClientButton;
    @FXML
    void Add_client_Button(ActionEvent event) {

        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AdaugaClient.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();

            Client client = new Client(0,"","","");
            AdaugaClient c_controller = fxmlLoader.getController();
            c_controller.initialize_repo(repo_clienti, repo_oferte, con,"add", client);






            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.show();
            Stage primaryStage = (Stage) DashButton.getScene().getWindow();
            stage.setMaximized(false);
            stage.setResizable(false);
            stage.setAlwaysOnTop(false);





            // when button is pressed


            EventHandler<ActionEvent> event1 = new EventHandler<ActionEvent>() {
                public void handle(ActionEvent e)
                {
                    c_controller.SendData(e);
                    search_button.fire();

                }
            };
            c_controller.add_button.setOnAction(event1);


        }catch (Exception e)
        {
            System.out.println(e);
        }

    }


// Update Clients Button

    @FXML
    void Update_button_action(ActionEvent event) throws IOException {


        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AdaugaClient.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Client client = TabelClienti.getSelectionModel().getSelectedItem();

            AdaugaClient c_controller = fxmlLoader.getController();
            c_controller.initialize_repo(repo_clienti, repo_oferte, con, "update",client);






            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.show();
            Stage primaryStage = (Stage) DashButton.getScene().getWindow();
            stage.setMaximized(false);
            stage.setResizable(false);
            stage.setAlwaysOnTop(false);



            EventHandler<ActionEvent> event1 = new EventHandler<ActionEvent>() {
                public void handle(ActionEvent e)
                {
                    c_controller.SendData(e);
                    search_button.fire();


                }
            };
            c_controller.add_button.setOnAction(event1);



        }catch (Exception e)
        {
            System.out.println(e);
        }


    }



    @FXML
    void tabel_clicked(MouseEvent event) {
        System.out.println(TabelClienti.getSelectionModel().getSelectedItem());
        System.out.println("clicked");
    }







    // add offer button
    @FXML
    private Button add_offer;

    @FXML
    void add_offer_action(ActionEvent event) {

        int id_client = TabelClienti.getSelectionModel().getSelectedItem().getId();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Add_Offer.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();


            Oferta oferta = new Oferta("", 0.0, null, null, null, id_client, 0);
            AddOffer c_controller = fxmlLoader.getController();
            c_controller.conn_init(con, "add", oferta);


            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.show();
            Stage primaryStage = (Stage) DashButton.getScene().getWindow();
            stage.setMaximized(false);
            stage.setResizable(false);
            stage.setAlwaysOnTop(false);


            // when button is pressed


            EventHandler<ActionEvent> event1 = new EventHandler<ActionEvent>() {
                public void handle(ActionEvent e)
                {
                    try {
                        c_controller.adauga_oferta(e);
                    } catch (SQLException | ParseException throwables) {
                        throwables.printStackTrace();
                    }
                    try {
                        tabelClientiSelect();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    } catch (ParseException parseException) {
                        parseException.printStackTrace();
                    }

                }
            };
            c_controller.push_data.setOnAction(event1);


        }catch (Exception e)
        {
            System.out.println(e);
        }


        }


        // delete offer

    @FXML
    private Button delete_offer;

    @FXML
    void Delete_offer_action(ActionEvent event) throws SQLException, ParseException {



        Oferta oferta = TabelOferte.getSelectionModel().getSelectedItem();


        Alert errorAlert = new Alert(Alert.AlertType.CONFIRMATION);
        errorAlert.setHeaderText("Esti pe cale sa stergi un client! ");
        errorAlert.setContentText("Doriti sa stergeti Oferta: "+oferta+ "  ?");


        Optional<ButtonType> result = errorAlert.showAndWait();
        if (result.get() == ButtonType.OK){
            con.Delete_Offer_from_DB(oferta);
            tabelClientiSelect();
        }
        else{

        }

    }





// add payment

    @FXML
    void add_payment_action(ActionEvent event) {

        int id_of = TabelOferte.getSelectionModel().getSelectedItem().getId_oferta();
        System.out.println("Here 1");
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Add_Payment.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();



            AddPayment c_controller = fxmlLoader.getController();
            c_controller.conn_init(con, id_of);



            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.show();
            Stage primaryStage = (Stage) DashButton.getScene().getWindow();
            stage.setMaximized(false);
            stage.setResizable(false);
            stage.setAlwaysOnTop(false);


            // when button is pressed


            EventHandler<ActionEvent> event1 = new EventHandler<ActionEvent>() {
                public void handle(ActionEvent e)
                {
                    c_controller.add_payment(e);
                    try {
                        tabelOferteSelect();
                    } catch (SQLException | ParseException throwables) {
                        throwables.printStackTrace();
                    }

                }
            };
            c_controller.push_data.setOnAction(event1);


        }catch (Exception e)
        {
            System.out.println(e);
        }






    }


    // delete payment

    @FXML
    void delete_payment_action(ActionEvent event) throws SQLException, ParseException {


        SumeAchitate achitate = achitari.getSelectionModel().getSelectedItem();


        Alert errorAlert = new Alert(Alert.AlertType.CONFIRMATION);
        errorAlert.setHeaderText("Esti pe cale sa stergi un client! ");
        errorAlert.setContentText("Doriti sa stergeti Plata: " + achitate + "  ?");


        Optional<ButtonType> result = errorAlert.showAndWait();
        if (result.get() == ButtonType.OK) {
            con.Delete_Payment_from_DB(achitate);
            tabelOferteSelect();
        } else {

        }


    }



    // total sume achitate

    @FXML
    private Label total;

    private void total_payments() throws SQLException, ParseException {


        total.setVisible(true);


        int id_of = TabelOferte.getSelectionModel().getSelectedItem().getId_oferta();
        ArrayList<SumeAchitate> oferte_memo = con.get_SumeAchitate_by_id(id_of).getAll();
        Double total_payments = 0.0;
        for (SumeAchitate payment : oferte_memo) {
            total_payments+=payment.getSuma();
        }


        total.setText("Total: "+total_payments+"€");





        }

    //info form
    @FXML
    void info_button_action(ActionEvent event) throws IOException {



        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Info.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();

        Stage stage = new Stage();
        stage.setScene(new Scene(root1));
        stage.show();
        Stage primaryStage = (Stage) DashButton.getScene().getWindow();
        stage.setMaximized(false);
        stage.setResizable(false);
        stage.setAlwaysOnTop(false);

    }




}



