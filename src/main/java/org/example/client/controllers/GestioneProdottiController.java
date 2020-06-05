package org.example.client.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class GestioneProdottiController {

    private Stage stage;

    public static void showView(Stage stage) throws IOException  {
        FXMLLoader loader = new FXMLLoader(GestioneProdottiController.class.getResource("/views/gestione-prodotti.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //assert root != null;
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Modifica prodotto");
        stage.show();
        GestioneProdottiController controller = loader.getController();
        controller.setStage(stage);
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void handleBackAction(ActionEvent actionEvent) {
    }
}
