package org.example.client.controllers;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.client.components.TableCellOrderId;
import org.example.client.models.Order;
import org.example.client.models.enums.OrderSate;
import org.example.client.models.enums.Payment;
import org.example.client.utils.Utils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class ManageOrdersController {

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy HH:mm");
    @FXML
    public ChoiceBox<String> choiceBox;
    @FXML
    public TextField searchField;
    @FXML
    public TableView<Order> tableView;
    @FXML
    public TableColumn<Order, Integer> id;
    @FXML
    public TableColumn<Order, String> payment;
    @FXML
    public TableColumn<Order, String> total;
    @FXML
    public TableColumn<Order, String> deliveryStart;
    @FXML
    public TableColumn<Order, String> deliveryEnd;
    private Stage stage;

    public static void showView(Stage stage) {
        FXMLLoader loader = new FXMLLoader(ManageOrdersController.class.getResource("/views/manage-orders.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert root != null;

        ManageOrdersController controller = loader.getController();
        controller.setStage(stage);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Manage Orders");
        stage.show();
    }

    public void initialize() {
        // TODO: Task for load
        List<Order> list =
                Collections.singletonList(new Order(1, 5.30f, Payment.CASH, new Date(0), new Date(1),
                        OrderSate.CONFIRMED));

        // Id cell factory
        id.setCellFactory(cell -> {
            // TODO view list cell.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> this.stage);
            return new TableCellOrderId();
        });
        id.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getId()));

        payment.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getPayment().toString()));
        total.setCellValueFactory(param ->
                new SimpleStringProperty(String.format("%.2f €", param.getValue().getTotal()))
        );
        deliveryStart.setCellValueFactory(param ->
                new SimpleStringProperty(dateFormat.format(param.getValue().getDeliveryStart()))
        );
        deliveryEnd.setCellValueFactory(param ->
                new SimpleStringProperty(dateFormat.format(param.getValue().getDeliveryEnd()))
        );

        tableView.setItems(FXCollections.observableList(list));

    }

    private void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void handleBackAction() {
        SceltaModalitaController.showView(this.stage);
    }

    @FXML
    public void handlerAddManagerAction() {
    }

    @FXML
    public void handlerLogoutAction() {
        Utils.logOut(this.stage);
    }

}
