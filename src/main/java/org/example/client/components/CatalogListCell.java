package org.example.client.components;

import javafx.scene.control.ListCell;
import org.example.client.controllers.CartItemController;
import org.example.client.models.Product;

public class CatalogListCell extends ListCell<Product> {

    @Override
    protected void updateItem(Product item, boolean empty) {
        setGraphic(CartItemController.showView(item));
        super.updateItem(item, empty);
    }

}
