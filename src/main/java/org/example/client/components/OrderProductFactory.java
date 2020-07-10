package org.example.client.components;

import javafx.scene.Node;
import org.example.client.controllers.OrderProductController;
import org.example.client.models.OrderItem;

import java.util.ArrayList;
import java.util.List;

public class OrderProductFactory {

    public List<Node> getOrderProductList(List<OrderItem> list) {
        ArrayList<Node> nodes = new ArrayList<>(list.size());
        list.forEach(item -> nodes.add(OrderProductController.createView(item)));
        return nodes;
    }

}
