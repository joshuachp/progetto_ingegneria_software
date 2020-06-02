package org.example.client.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class GestioneProdottiController {
    public void showView(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/views/gestioneProdotti.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Gestione Prodotti");
        stage.show();
    }
}
