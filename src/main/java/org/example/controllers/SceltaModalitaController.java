package org.example.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceltaModalitaController {
    private Stage stage;

    public void showView(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(SceltaModalitaController.class.getResource("/views/sceltaModalita.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Modalita");
        stage.show();
        SceltaModalitaController sceltamodalitacontroller = loader.getController();
        sceltamodalitacontroller.stage = stage;
    }

    public void HandleActionVisualizzaSpese(ActionEvent actionEvent) {
        ListaSpeseController listaSpese = new ListaSpeseController();
        try {
            listaSpese.showView(this.stage);
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
