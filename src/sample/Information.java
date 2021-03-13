package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

public class Information implements Initializable {


    @FXML
    private WebView web;

    @FXML
    private Button pdf;

    private WebEngine engine;

    @FXML
    void get_pdf(ActionEvent event) {

        if (Desktop.isDesktopSupported()) {
            try {
                URL url = getClass().getResource("prezentare.pdf");
                File myFile = new File(url.toURI());
                Desktop.getDesktop().open(myFile);
            } catch (IOException | URISyntaxException ex) {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setHeaderText("Eroare ");
                errorAlert.setContentText("Fisierul nu a fost gasit! ");
            }}
            else{
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setHeaderText("Eroare ");
                errorAlert.setContentText("Din pacate David a facut sa mearga deschiderea pdf-ului doar pe Windows! ");
            }

        }




    @Override
    public void initialize(URL location, ResourceBundle resources) {

        engine = web.getEngine();
        engine.load("https://github.com/davidvrabioru");
    }
}
