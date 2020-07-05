package org.example.client.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.client.models.Product;
import org.example.client.utils.TaskLoadImage;
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

    private Stage stage;
    private Product product;

    public static Node createView(Stage stage, Product product) {
        FXMLLoader loader = new FXMLLoader(CartItemController.class.getResource("/views/cart-item.fxml"));
        Node node = null;
        try {
            node = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert node != null;

        CartItemController controller = loader.getController();
        controller.setStage(stage);
        controller.setProduct(product);

        return node;
    }

    protected void setProduct(@NotNull Product product) {
        this.product = product;
        if (product.getImage() != null)
            setImage(product.getImage());
        this.textProduct.setText(product.getName());
        this.textBrand.setText(product.getBrand());
        this.textQuantity.setText(String.valueOf(product.getQuantity()));
        this.textTotal.setText(String.format("€ %.2f", product.getQuantity() * product.getPrice()));
    }

    private void setImage(String image) {
        TaskLoadImage task = new TaskLoadImage(image);
        task.setOnSucceeded((event -> {
            Image img = task.getValue();
            Platform.runLater(() -> this.imageView.setImage(img));
        }));
        new Thread(task).start();
    }

    public void handleOnMouseClick() {
        ProductController.showView(this.stage, this.product);
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}

