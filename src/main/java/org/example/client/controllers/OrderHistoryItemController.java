package org.example.client.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import org.example.client.models.Order;

import java.io.IOException;

public class OrderHistoryItemController {

    private Order order;

    public static Node createView(Order order) {
        FXMLLoader loader = new FXMLLoader(
                OrderHistoryItemController.class.getResource("/views/order-history-item.fxml"));
        Node node = null;
        try {
            node = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert node != null;
        OrderHistoryItemController controller = loader.getController();
        controller.setOrder(order);
        return node;
    }

    public void setOrder(Order order) {
        this.order = order;
    }


    @FXML
    public void handleOnMouseClick() {
    }
}
