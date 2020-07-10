package org.example.client.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import org.example.client.models.OrderItem;

import java.io.IOException;

public class OrderProductController {

    private OrderItem orderItem;

    public static Node createView(OrderItem orderItem) {
        FXMLLoader loader = new FXMLLoader(OrderProductController.class.getResource("/views/order-product.fxml"));
        Node node = null;
        try {
            node = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert node != null;
        OrderProductController controller = loader.getController();
        controller.setOrderItem(orderItem);
        return node;
    }

    public void setOrderItem(OrderItem orderItem) {
        this.orderItem = orderItem;
    }

}
