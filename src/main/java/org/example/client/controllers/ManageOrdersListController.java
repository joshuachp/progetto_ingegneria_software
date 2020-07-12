package org.example.client.controllers;

import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.client.components.OrderProductFactory;
import org.example.client.models.Order;
import org.example.client.models.OrderItem;
import org.example.client.models.enums.OrderSate;
import org.example.client.utils.Session;
import org.example.client.utils.Utils;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

public class ManageOrdersListController {

    private final SimpleDateFormat startFormat = new SimpleDateFormat("dd/MM/yy HH:mm");
    private final SimpleDateFormat endFormat = new SimpleDateFormat("HH:mm");

    @FXML
    public VBox vBoxList;
    @FXML
    public Text addressText;
    @FXML
    public Text deliveryText;
    @FXML
    public Text totalText;
    @FXML
    public ChoiceBox<String> stateChoiceBox;


    private Stage stage;
    private Order order;

    public static void showView(Stage stage, Order order) {
        FXMLLoader loader = new FXMLLoader(ManageOrdersListController.class.getResource("/views/manage-orders-list" +
                ".fxml"));
        Parent parent = null;
        try {
            parent = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert parent != null;
        ManageOrdersListController controller = loader.getController();
        controller.setStage(stage);
        controller.setOrder(order);

        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();
    }

    public void initialize() {
        this.stateChoiceBox.setItems(FXCollections.observableList(OrderSate.getLabels()));
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void handleBackAction() {
        ManageOrdersController.showView(this.stage);
    }

    @FXML
    public void handleSaveAction() {
        int stateIndex = this.stateChoiceBox.getSelectionModel().getSelectedIndex();
        if (stateIndex != this.order.getState().ordinal()) {
            Session session = Session.getInstance();
            try {
                Utils.updateOrderState(session.getUser().getSession(), order.getId(), OrderSate.values()[stateIndex]);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        ManageOrdersController.showView(this.stage);
    }

    public void setOrder(@NotNull Order order) {
        this.order = order;
        this.addressText.setText(order.getAddress());
        this.deliveryText.setText(String.format("%s-%s",
                startFormat.format(order.getDeliveryStart()),
                endFormat.format(order.getDeliveryEnd())
        ));
        this.totalText.setText(String.format("\u20ac %.2f", order.getTotal()));
        this.stateChoiceBox.getSelectionModel().select(order.getState().ordinal());
        Task<List<Node>> task = new Task<>() {
            @Override
            protected List<Node> call() throws Exception {
                Session session = Session.getInstance();
                List<OrderItem> orderItems = Utils.getOrderItems(session.getUser().getSession(), order.getId());
                return new OrderProductFactory().getManageOrdersItemList(stage, orderItems);
            }
        };
        task.setOnSucceeded(event -> this.vBoxList.getChildren().addAll(task.getValue()));
        new Thread(task).start();
    }
}
