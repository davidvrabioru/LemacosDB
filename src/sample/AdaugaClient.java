package sample;

import Connection.ConnectionClass;
import Domain.Client;
import Repository.RepoInMemo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.ibex.nestedvm.util.Platform;

import java.net.URL;
import java.util.ResourceBundle;

public class AdaugaClient implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


    ConnectionClass conn = null;
    String mode = null;
    Client client;


    @FXML
    public Button add_button;

    @FXML
    private TextField nume;

    @FXML
    private TextField adresa;

    @FXML
    private TextField telefon;

    @FXML
    public void SendData(ActionEvent event) {


        if(nume.getText().isEmpty() || adresa.getText().isEmpty() || telefon.getText().isEmpty()) {

            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Input not valid");
            errorAlert.setContentText("Va rugam sa completati toate spatiile libere");
            errorAlert.showAndWait();
        }
        else
        {


            String name;
            String adress;
            String Phone;
            try {
                 name = nume.getText();
                 adress = adresa.getText();
                 Phone = telefon.getText();

                 client.setAdresa(adress);
                 client.setNume(name);
                 client.setTelefon(Phone);

                 if(mode == "add") {
                     client.setId(0);
                     conn.Add_Client_to_DB(client);
                 }
                 else
                     conn.Update_Client(client);

                Alert errorAlert = new Alert(Alert.AlertType.INFORMATION);
                errorAlert.setHeaderText("Infromatie: ");
                errorAlert.setContentText("Client adaugat cu Succes in Baza de Date");
                errorAlert.showAndWait();


            }catch (Exception e)
            {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setHeaderText("Input not valid");
                errorAlert.setContentText(e.toString());
                errorAlert.showAndWait();
            }


            Stage stage = (Stage) add_button.getScene().getWindow();

            stage.close();



        }



    }


    @FXML
    private Label tag;

    public void initialize_repo(RepoInMemo repo_clienti, RepoInMemo repo_oferte, ConnectionClass con, String mode, Client client){
        conn = con;
        this.mode = mode;
        this.client = client;
        if(this.mode == "update")
        {
            tag.setText("Modifica Client");
            add_button.setText("Modifica");
            nume.setText(client.getNume());
            adresa.setText(client.getAdresa());
            telefon.setText(client.getTelefon());

        }
    }

}
