package org.example.client.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import org.example.client.models.Product;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class CartItemController {

    @FXML
    public ImageView imageView;
    @FXML
    public Text textProduct;
    @FXML
    public Text textBrand;
    @FXML
    public Text textQuantity;
    @FXML
    public Text textTotal;

    public static Node createView(Product product) {
        FXMLLoader loader = new FXMLLoader(CartItemController.class.getResource("/views/cart-item.fxml"));
        Node node = null;
        try {
            node = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert node != null;

        CartItemController controller = loader.getController();
        controller.setProduct(product);

        return node;
    }

    protected void setProduct(@NotNull Product product) {
        if (product.getImage() != null)
            this.imageView.setImage(new Image(product.getImage()));
        this.textProduct.setText(product.getName());
        this.textBrand.setText(product.getBrand());
        this.textQuantity.setText(String.valueOf(product.getQuantity()));
        this.textTotal.setText(String.format("€ %.2f", product.getQuantity() * product.getPrice()));
    }
}

