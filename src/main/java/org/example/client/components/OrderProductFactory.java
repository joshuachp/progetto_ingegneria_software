package org.example.client.components;

import javafx.scene.Node;
import javafx.stage.Stage;
import org.example.client.controllers.ManageOrdersItemController;
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

    public List<Node> getManageOrdersItemList(Stage stage, List<OrderItem> list) {
        ArrayList<Node> nodes = new ArrayList<>(list.size());
        list.forEach(item -> nodes.add(ManageOrdersItemController.createView(stage, item)));
        return nodes;
    }

}
