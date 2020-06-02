package org.example.client.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class CatalogoController {


    public void showView(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/views/catalogo.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Catalogo");
        stage.show();
    }

}
