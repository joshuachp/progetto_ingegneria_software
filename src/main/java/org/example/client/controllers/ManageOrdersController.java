package org.example.client.controllers;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import org.example.client.tasks.TaskManageOrders;
import org.example.client.utils.Session;
import org.example.client.utils.Utils;

import java.io.IOException;
import java.text.SimpleDateFormat;

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
    public TableColumn<Order, String> state;
    @FXML
    public TableColumn<Order, String> payment;
    @FXML
    public TableColumn<Order, String> total;
    @FXML
    public TableColumn<Order, String> deliveryStart;
    @FXML
    public TableColumn<Order, String> deliveryEnd;

    private Stage stage;
    private ObservableList<Order> list = FXCollections.observableArrayList();

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
        tableView.setSelectionModel(null);
        // Id cell factory
        id.setCellFactory(cell -> {
            // TODO view list cell.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> this.stage);
            return new TableCellOrderId();
        });
        id.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getId()));
        state.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getState().toString()));
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

        tableView.setItems(this.list);

        Session session = Session.getInstance();
        TaskManageOrders task = new TaskManageOrders(session.getUser().getSession());
        task.setOnSucceeded(event -> this.list.addAll(task.getValue()));
        new Thread(task).start();

        this.searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            this.list.filtered(order -> order)
        });
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
