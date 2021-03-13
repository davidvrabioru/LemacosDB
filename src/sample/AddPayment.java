package sample;

import Connection.ConnectionClass;
import Domain.Oferta;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddPayment {


    private int id_oferta;
    private ConnectionClass conn = null;


    @FXML
    private TextField sum;

    @FXML
    public Button push_data;

    @FXML
    private Label tag;

    @FXML
    void add_payment(ActionEvent event) {

        if (sum.getText().isEmpty()) {

            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Input not valid");
            errorAlert.setContentText("Va rugam sa completati toate spatiile libere");
            errorAlert.showAndWait();
        } else {

            try {
                Double suma;




                suma = Double.parseDouble(sum.getText());
                System.out.println("Payment! " + suma);
                conn.Add_Payment_DB(suma, id_oferta);



                Alert errorAlert = new Alert(Alert.AlertType.INFORMATION);
                errorAlert.setHeaderText("Infromatie: ");
                errorAlert.setContentText("Plata adaugat cu Succes in Baza de Date");
                errorAlert.showAndWait();


            } catch (Exception e) {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setHeaderText("Input not valid");
                errorAlert.setContentText(e.toString());
                errorAlert.showAndWait();
            }


            Stage stage = (Stage) push_data.getScene().getWindow();

            stage.close();

        }


    }

    public void conn_init(ConnectionClass conection, int id_oferta) {
        this.id_oferta = id_oferta;
        this.conn = conection;


    }
}