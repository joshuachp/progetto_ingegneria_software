package org.example.client.controllers;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.client.components.OrderHistoryFactory;
import org.example.client.models.Order;
import org.example.client.utils.Session;
import org.example.client.utils.Utils;

import java.io.IOException;
import java.util.List;

public class OrderHistoryController {

    @FXML
    public VBox vBoxList;

    private Stage stage;

    public static void showView(Stage stage) {
        FXMLLoader loader = new FXMLLoader(OrderHistoryController.class.getResource("/views/order-history.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert root != null;
        OrderHistoryController controller = loader.getController();
        controller.setStage(stage);

        Scene scene = new Scene(root);
        stage.setScene(scene);
    }

    @FXML
    public void initialize() {
        Task<List<Node>> loadOrders = new Task<>() {
            @Override
            protected List<Node> call() throws Exception {
                Session session = Session.getInstance();
                List<Order> list = Utils.getAllOrders(session.getUser().getSession());
                OrderHistoryFactory factory = new OrderHistoryFactory();
                return factory.getOrderHistoryList(list);
            }
        };
        loadOrders.setOnSucceeded(event -> {
            List<Node> list = loadOrders.getValue();
            this.vBoxList.getChildren().addAll(list);
        });
        new Thread(loadOrders).start();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void handleBackAction() {
        ProfileController.showView(this.stage);
    }
}
