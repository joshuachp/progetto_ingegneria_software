package org.example.client.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import org.example.client.components.CatalogListCell;
import org.example.client.models.Product;
import org.example.client.utils.Session;

import java.io.IOException;

public class CartController {

    @FXML
    public ListView<Product> listView;

    private ObservableList<Product> observableList;
    private Stage stage;

    public static void showView(Stage stage) {
        FXMLLoader loader = new FXMLLoader(AuthController.class.getResource("/views/cart.fxml"));
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
        Task<Void> loadProducts = new Task<Void>() {
            @Override
            protected Void call() {
                // Get all the products
                Session session = Session.getInstance();
                observableList = FXCollections.observableList(session.getProducts());
                // Set the cell factory
                listView.setCellFactory(listView -> new CatalogListCell());
                // Set the list items
                listView.setItems(observableList);
                return null;
            }
        };
        new Thread(loadProducts).start();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
