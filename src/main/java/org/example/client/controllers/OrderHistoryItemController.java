package org.example.client.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.text.Text;
import org.example.client.models.Order;

import java.io.IOException;

public class OrderHistoryItemController {

    @FXML
    public Text textOrderId;
    @FXML
    public Text textStatus;
    @FXML
    public Text textPayment;
    @FXML
    public Text textTotal;

    private Order order;

    public static Node createView(Order order) {
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
        controller.setOrder(order);
        return node;
    }

    public void setOrder(Order order) {
        this.order = order;
        this.textOrderId.setText(String.format("#%d", order.getId()));
        this.textStatus.setText(order.getState().toString());
        this.textStatus.setFill(order.getState().getColor());
        this.textPayment.setText(order.getPayment().toString());
        this.textTotal.setText(String.format("€ %.2f", order.getTotal()));
    }


    @FXML
    public void handleOnMouseClick() {
    }
}
