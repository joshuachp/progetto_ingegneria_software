package org.example.client.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.client.models.Order;
import org.example.client.tasks.TaskOrderHistoryItem;
import org.example.client.utils.Session;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OrderHistoryItemController {

    @FXML
    public Text textOrderId;
    @FXML
    public Text textStatus;
    @FXML
    public Text textPayment;
    @FXML
    public Text textTotal;
    @FXML
    public VBox vBoxListOrderProducts;

    private Stage stage;
    private Order order;
    private List<Node> nodes = new ArrayList<>();
    private boolean showList = false;

    public static Node createView(Stage stage, Order order) {
        FXMLLoader loader = new FXMLLoader(
                OrderHistoryItemController.class.getResource("/views/order-history-item.fxml"));
        Node node = null;
        try {
            node = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert node != null;
        OrderHistoryItemController controller = loader.getController();
        controller.setStage(stage);
        controller.setOrder(order);
        return node;
    }

    public void setOrder(@NotNull Order order) {
        this.order = order;
        this.textOrderId.setText(String.format("#%d", order.getId()));
        this.textStatus.setText(order.getState().toString());
        this.textStatus.setFill(order.getState().getColor());
        this.textPayment.setText(order.getPayment().toString());
        this.textTotal.setText(String.format("€ %.2f", order.getTotal()));

        getOrderItems();
    }

    private void getOrderItems() {
        Session session = Session.getInstance();
        TaskOrderHistoryItem task =
                new TaskOrderHistoryItem(this.stage, session.getUser().getSession(), this.order.getId());
        task.setOnSucceeded(event -> this.nodes = task.getValue());
        new Thread(task).start();
    }


    @FXML
    public void handleOnMouseClick() {
        if (showList) {
            vBoxListOrderProducts.getChildren().removeAll(this.nodes);
            showList = false;
        } else {
            vBoxListOrderProducts.getChildren().addAll(nodes);
            showList = true;
        }
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
