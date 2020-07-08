package org.example.client.controllers;

import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.example.client.components.CartFactory;
import org.example.client.models.Product;
import org.example.client.utils.Session;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class CheckoutController {

    private Stage stage;

    public static void showView() {
        FXMLLoader loader = new FXMLLoader(CheckoutController.class.getResource("/views/checkout.fxml"));

        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert root != null;
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Checkout");
        CheckoutController checkoutController = loader.getController();
        checkoutController.setStage(stage);
        stage.show();
    }

    private void setStage(Stage stage) {
        this.stage = stage;
    }

    public void confirmOrder(ActionEvent actionEvent) {
        Session session = Session.getInstance();


    }

    public void cancelConfirmation(ActionEvent actionEvent) {
        stage.close();
    }
}
