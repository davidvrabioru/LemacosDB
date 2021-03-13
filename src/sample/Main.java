package sample;

import ServiceL.Service;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import Repository.*;
import Connection.*;
import Domain.*;
import java.sql.Timestamp;
import java.util.Date;

public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception{
        System.out.println("Here");
        Parent root = FXMLLoader.load(getClass().getResource("test.fxml"));
        primaryStage.setTitle("Lemacos Dashbord");
        primaryStage.setScene(new Scene(root, 1100, 500));

         primaryStage.show();
         primaryStage.setMaximized(true);
         primaryStage.setResizable(true);

     //   PDF_Extract pdf = new PDF_Extract("first");


        RepoInMemo repo_clienti = new RepoInMemo();
        RepoInMemo repo_oferte = new RepoInMemo();
        ConnectionClass connect = new ConnectionClass(repo_clienti,repo_oferte);
        ConnectionClass.dbCon();
   //     System.out.println(repo_oferte.getAll());







    }


    public static void main(String[] args) {
        launch(args);
    }
}
