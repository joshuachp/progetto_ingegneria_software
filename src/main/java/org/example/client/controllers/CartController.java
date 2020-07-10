package org.example.client.controllers;

import javafx.application.Platform;
import javafx.collections.MapChangeListener;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.client.components.CartFactory;
import org.example.client.models.Product;
import org.example.client.utils.Session;

import java.io.IOException;
import java.util.List;

public class CartController {

    @FXML
    public VBox vBoxCart;

    private Stage stage;
    private List<Node> nodes = null;

    public static void showView(Stage stage) {
        FXMLLoader loader = new FXMLLoader(CartController.class.getResource("/views/cart.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert root != null;
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Shopping Cart");
        stage.show();
        CartController controller = loader.getController();
        controller.setStage(stage);
    }

    @FXML
    public void initialize() {
        LoadTask loadTask = new LoadTask();
        loadTask.setOnSucceeded((event) -> {
            if (nodes != null)
                vBoxCart.getChildren().removeAll(nodes);
            this.nodes = loadTask.getValue();
            vBoxCart.getChildren().addAll(this.nodes);
        });
        new Thread(loadTask).start();
        Session.getInstance().getMapProducts()
                .addListener((MapChangeListener<? super Integer, ? super Product>) change ->
                        Platform.runLater(() -> {
                            LoadTask task = new LoadTask();
                            task.setOnSucceeded((event) -> {
                                vBoxCart.getChildren().removeAll(nodes);
                                this.nodes = task.getValue();
                                vBoxCart.getChildren().addAll(this.nodes);
                            });
                            new Thread(task).start();
                        })
                );
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void handleBackAction() {
        CatalogController.showView(this.stage);
    }

    @FXML
    public void handleConfirmationOrder() {
        Session session = Session.getInstance();
        if (!session.getMapProducts().values().isEmpty())
            CheckoutController.showView(this.stage);
        else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Cart is empty");
            alert.setContentText("We're sorry, but your cart is empty.\nAdd some products to your cart to proceed " +
                    "with " +
                    "checkout." +
                    " ");
            alert.show();
        }
    }

    private class LoadTask extends Task<List<Node>> {
        @Override
        protected List<Node> call() {
            Session session = Session.getInstance();
            return new CartFactory().getCartList(stage, session.getMapProducts().values());
        }

    }
}
