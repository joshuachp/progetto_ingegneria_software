package org.example.client.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class ProductListController {


    private Stage stage;
    public static void showView(Stage stage) throws IOException{
        FXMLLoader loader = new FXMLLoader(ProductListController.class.getResource("/views/product-list.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert root != null;
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Lista Prodotti");
        stage.show();
        ProductListController controller = loader.getController();
        controller.setStage(stage);
    }

    private void setStage(Stage stage) {
        this.stage = stage;
    }


    public void handlerAddProduct(ActionEvent actionEvent) {
        GestioneProdottiController gestioneProdottiController = new GestioneProdottiController();
        try {
            gestioneProdottiController.showView(this.stage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
