package org.example.client.components;

import javafx.scene.Node;
import javafx.stage.Stage;
import org.example.client.controllers.OrderProductController;
import org.example.client.models.OrderItem;

import java.util.ArrayList;
import java.util.List;

public class OrderProductFactory {

    public List<Node> getOrderProductList(Stage stage, List<OrderItem> list) {
        ArrayList<Node> nodes = new ArrayList<>(list.size());
        list.forEach(item -> nodes.add(OrderProductController.createView(stage, item)));
        return nodes;
    }

}
