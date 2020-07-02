package org.example.client.controllers;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.client.components.CartFactory;
import org.example.client.utils.Session;

import java.io.IOException;
import java.util.List;

public class CartController {

    @FXML
    public VBox vBoxCart;

    private Stage stage;

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
        Task<Void> loadProducts = new Task<>() {
            @Override
            protected Void call() {
                Session session = Session.getInstance();
                List<Node> nodes = new CartFactory().getCartList(session.getProducts());
                Platform.runLater(() -> vBoxCart.getChildren().addAll(nodes));
                return null;
            }
        };
        new Thread(loadProducts).start();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
