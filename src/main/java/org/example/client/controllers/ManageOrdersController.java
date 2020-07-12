package org.example.client.controllers;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
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
    private final ObservableList<Order> list = FXCollections.observableArrayList();

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
    @FXML
    public TableColumn<Order, String> address;

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
        tableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        tableView.getSelectionModel().getSelectedItems().addListener((ListChangeListener<Order>) c -> {
            if (!c.getList().isEmpty()) {
                Order order = c.getList().get(0);
                ManageOrdersListController.showView(this.stage, order);
            }
        });

        // Id cell factory
        id.setCellFactory(cell -> {
            TableCellOrderId tableCellOrderId = new TableCellOrderId();
            tableCellOrderId.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            });
            return tableCellOrderId;
        });

        id.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getId()));
        total.setCellValueFactory(param ->
                new SimpleStringProperty(String.format("%.2f €", param.getValue().getTotal()))
        );
        payment.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getPayment().toString()));
        deliveryStart.setCellValueFactory(param ->
                new SimpleStringProperty(dateFormat.format(param.getValue().getDeliveryStart()))
        );
        deliveryEnd.setCellValueFactory(param ->
                new SimpleStringProperty(dateFormat.format(param.getValue().getDeliveryEnd()))
        );
        state.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getState().toString()));
        address.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getAddress()));

        // Make the table searchable
        FilteredList<Order> filteredList = new FilteredList<>(this.list, p -> true);
        this.searchField.textProperty().addListener((observable, oldValue, newValue) ->
                filteredList.setPredicate(order -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String search = newValue.toLowerCase().trim();
                    return order.getId().toString().contains(search) ||
                            order.getTotal().toString().contains(search) ||
                            order.getState().toString().toLowerCase().contains(search) ||
                            order.getPayment().toString().toLowerCase().contains(search) ||
                            dateFormat.format(order.getDeliveryStart()).toLowerCase().contains(search) ||
                            dateFormat.format(order.getDeliveryEnd()).toLowerCase().contains(search) ||
                            order.getAddress().toLowerCase().contains(search);
                }));
        // Make the table sortable
        SortedList<Order> sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(tableView.comparatorProperty());
        tableView.setItems(sortedList);

        Session session = Session.getInstance();
        TaskManageOrders task = new TaskManageOrders(session.getUser().getSession());
        task.setOnSucceeded(event -> this.list.addAll(task.getValue()));
        new Thread(task).start();

    }

    private void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void handleBackAction() {
        ChoiceModeController.showView(this.stage);
    }

    @FXML
    public void handlerAddManagerAction() {
    }

    @FXML
    public void handlerLogoutAction() {
        Utils.logOut(this.stage);
    }

}
