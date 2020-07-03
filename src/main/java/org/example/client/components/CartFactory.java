package org.example.client.components;

import javafx.scene.Node;
import org.example.client.controllers.CartItemController;
import org.example.client.models.Product;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CartFactory {

    public List<Node> getCartList(Collection<Product> products) {
        ArrayList<Node> nodes = new ArrayList<>(products.size());
        for (Product product : products) {
            nodes.add(CartItemController.createView(product));
        }
        return nodes;
    }

}
