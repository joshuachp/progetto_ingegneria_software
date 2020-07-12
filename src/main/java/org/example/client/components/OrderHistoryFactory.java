package org.example.client.components;

import javafx.scene.Node;
import javafx.stage.Stage;
import org.example.client.controllers.OrderHistoryItemController;
import org.example.client.models.Order;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class OrderHistoryFactory {

    public List<Node> getOrderHistoryList(Stage stage, @NotNull List<Order> orders) {
        ArrayList<Node> nodes = new ArrayList<>(orders.size());
        orders.forEach(order -> nodes.add(OrderHistoryItemController.createView(stage, order)));
        return nodes;
    }
}
