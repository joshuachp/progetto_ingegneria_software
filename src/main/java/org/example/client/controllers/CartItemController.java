package org.example.client.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.client.components.QuantitySpinnerFactory;
import org.example.client.models.Product;
import org.example.client.tasks.TaskLoadImage;
import org.example.client.utils.Session;
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
    public Text textTotal;
    @FXML
    public Spinner<Integer> spinnerQuantity;

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
        this.textTotal.setText(String.format("\u20ac %.2f", product.getQuantity() * product.getPrice()));
        // Set spinner
        SpinnerValueFactory<Integer> spinnerValueFactory = new QuantitySpinnerFactory().getCartSpinner(product);
        spinnerValueFactory.valueProperty().addListener((observable, oldValue, newValue) -> {
            Session session = Session.getInstance();
            session.setProductQuantity(this.product.getId(), newValue);
            this.textTotal.setText(String.format("\u20ac %.2f", this.product.getPrice() * this.product.getQuantity()));
        });
        this.spinnerQuantity.setValueFactory(spinnerValueFactory);
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


    @FXML
    public void deleteHandler() {
        Session session = Session.getInstance();
        session.removeProduct(this.product.getId());
    }

}

