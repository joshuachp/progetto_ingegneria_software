package org.example.client.components;

import javafx.scene.Node;
import javafx.stage.Stage;
import org.example.client.controllers.CartItemController;
import org.example.client.models.Product;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CartFactory {

    public List<Node> getCartList(Stage stage, Collection<Product> products) {
        ArrayList<Node> nodes = new ArrayList<>(products.size());
        products.forEach(product -> nodes.add(CartItemController.createView(stage, product)));
        return nodes;
    }

}
