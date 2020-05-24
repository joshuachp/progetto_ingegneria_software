package org.example.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class AutenticazioneController {

    public void show(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/views/autenticazione.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Autenticazione");
        stage.show();
    }

}
