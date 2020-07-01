package org.example.client.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.client.models.Pagamento;

import java.io.IOException;

public class OrderProductListController {
    Stage stage = new Stage();

    public void showView(Integer orderID) throws IOException {
        FXMLLoader loader = new FXMLLoader(SceltaModalitaController.class.getResource("/views/order-product-list" +
                ".fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert root != null;

        stage.show();

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Spesa " + orderID.toString());
    }

}