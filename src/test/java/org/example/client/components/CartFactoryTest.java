package org.example.client.components;

import javafx.scene.Node;
import org.example.client.models.Product;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;


class CartFactoryTest {

    @Test
    void getCartList() {
        Product product = new Product(1, "Name", "Brand", 1, 1., null, 1, "", "Section");
        ArrayList<Product> list = new ArrayList<>();
        list.add(product);
        AtomicReference<List<Node>> nodes = new AtomicReference<>();
        CartFactory factory = new CartFactory();
        assertDoesNotThrow(() -> nodes.set(factory.getCartList(list)));
        assertFalse(nodes.get().isEmpty());
    }
}