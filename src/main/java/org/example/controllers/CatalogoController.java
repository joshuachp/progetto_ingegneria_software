package org.example.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class CatalogoController {

    private final Stage stage;

    public CatalogoController(Stage stage) {
        this.stage = stage;
    }

    public void showView() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/views/catalogo.fxml"));
        Scene scene = new Scene(root);
        this.stage.setScene(scene);
        this.stage.setTitle("Catalogo");
        this.stage.show();
    }

}
