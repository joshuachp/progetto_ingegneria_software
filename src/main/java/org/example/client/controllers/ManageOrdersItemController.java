package org.example.client.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.client.models.OrderItem;
import org.example.client.models.Product;
import org.example.client.models.ROProduct;
import org.example.client.tasks.TaskOrderProduct;
import org.example.client.utils.Session;

import java.io.IOException;

public class ManageOrdersItemController {

    @FXML
    public Text textId;
    @FXML
    public Text textName;
    @FXML
    public Text textPrice;
    @FXML
    public Text textQuantity;
    @FXML
    public Text textTotal;

    private Stage stage;
    private ROProduct product;

    public static Node createView(Stage stage, OrderItem orderItem) {
        FXMLLoader loader =
                new FXMLLoader(OrderProductController.class.getResource("/views/manage-orders-item.fxml"));
        Node node = null;
        try {
            node = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert node != null;
        ManageOrdersItemController controller = loader.getController();
        controller.setStage(stage);
        controller.setOrderItem(orderItem);
        return node;
    }

    public void setOrderItem(OrderItem orderItem) {
        this.textId.setText(orderItem.getProductId().toString());
        this.textName.setText(orderItem.getName());
        this.textPrice.setText(String.format("Price \u20ac %.2f", orderItem.getPrice()));
        this.textQuantity.setText(orderItem.getQuantity().toString());
        this.textTotal.setText(String.format("\u20ac %.2f", orderItem.getPrice() * orderItem.getQuantity()));

        Session session = Session.getInstance();
        TaskOrderProduct task = new TaskOrderProduct(session.getUser().getSession(), orderItem.getProductId());
        task.setOnSucceeded(event -> this.product = session.getProductReference(task.getValue()));
        new Thread(task).start();
    }

    @FXML
    public void handleMouseClicked() {
        ProductController.showView(this.stage, this.product);
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
