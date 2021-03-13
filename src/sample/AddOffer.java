package sample;

import Domain.Client;
import Domain.ClientFull;
import Domain.ClientPotential;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import Connection.ConnectionClass;
import Domain.Oferta;
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

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

public class
AddOffer{


    private int id_client;
    ConnectionClass conn = null;
    String mode = null;
    Oferta oferta;

    @FXML
    private DatePicker data;

    @FXML
    private TextField link;

    @FXML
    private TextField pret;

    @FXML
    public Button push_data;

    @FXML
    void adauga_oferta(ActionEvent event) throws SQLException, ParseException {


        if(link.getText().isEmpty() || pret.getText().isEmpty()) {

            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Input not valid");
            errorAlert.setContentText("Va rugam sa completati toate spatiile libere");
            errorAlert.showAndWait();
        }


        else {


            String Link = link.getText();
            Double pretFinal = Double.parseDouble(pret.getText());
            LocalDate date = data.getValue();
            if (mode == "add") {
                System.out.println("here");


                conn.Add_Offer(Link, pretFinal, this.oferta.getId_client(),date );
            }

            else{
                //conn.Update_Offer(offer);
            }
        }


        Stage stage = (Stage) push_data.getScene().getWindow();

        stage.close();


    }

    @FXML
    private Label tag;
    public void conn_init(ConnectionClass conection, String Mode, Oferta offer)
    {
        this.conn = conection;
        this.mode = Mode;
        this.oferta = offer;

        data.setValue(LocalDate.now());

        if(Mode == "Update"){

            tag.setText("Modifica Oferta");
            link.setText(oferta.getLink());
            pret.setText(String.valueOf(oferta.getPretFinal()));


        }

    }



}
