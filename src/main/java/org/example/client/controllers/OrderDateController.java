package org.example.client.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;

import java.io.IOException;

public class OrderDateController {

    public DatePicker datePicker;
    public ComboBox hourComboBox;

    public void showView(Stage stage){
        /*FXMLLoader loader = new FXMLLoader(CartController.class.getResource("/views/cart.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert root != null;
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Shopping Cart");
        stage.show();
        CartController controller = loader.getController();
        controller.setStage(stage);*/

    }


    public void confirmDate(ActionEvent actionEvent) {
    }

    public void cancelOrder(ActionEvent actionEvent) {
    }
}
