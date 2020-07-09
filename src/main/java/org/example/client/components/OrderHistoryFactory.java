package org.example.client.components;

import javafx.scene.Node;
import org.example.client.controllers.OrderHistoryItemController;
import org.example.client.models.Order;

import java.util.ArrayList;
import java.util.List;

public class OrderHistoryFactory {

    public List<Node> getOrderHistoryList(List<Order> orders) {
        ArrayList<Node> nodes = new ArrayList<>(orders.size());
        orders.forEach(order -> nodes.add(OrderHistoryItemController.createView(order)));
        return nodes;
    }
}
