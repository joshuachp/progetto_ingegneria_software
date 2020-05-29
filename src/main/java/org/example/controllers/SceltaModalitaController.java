package org.example.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceltaModalitaController {
    public void showView(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/views/sceltaModalita.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Modalita");
        stage.show();
    }
}
