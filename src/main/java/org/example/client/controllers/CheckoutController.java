package org.example.client.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.client.models.Product;
import org.example.client.utils.Session;
import org.example.client.utils.Utils;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

public class CheckoutController {
    private static ObservableList<String> hour = FXCollections.observableArrayList
            ("8:00 - 12:00", "14:00 - 18:00");
    @FXML
    public DatePicker datePicker;
    @FXML
    public ComboBox hourComboBox;
    public Label label;
    private Date selectedDay;
    private Stage stage;

    public static void showView() {
        FXMLLoader loader = new FXMLLoader(CheckoutController.class.getResource("/views/checkout.fxml"));

        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert root != null;
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Checkout");
        CheckoutController checkoutController = loader.getController();
        checkoutController.setStage(stage);
        stage.show();
    }

    public void initialize() {
        // Crate selectable cell only for day after today and weeksday
        this.datePicker.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();

                setDisable(empty || (date.getDayOfWeek()).equals(DayOfWeek.SUNDAY) ||
                        (date.getDayOfWeek()).equals(DayOfWeek.SATURDAY) || date.compareTo(today) < 0);

            }
        });

        hourComboBox.setItems(hour);

    }

    private void setStage(Stage stage) {
        this.stage = stage;
    }

    public void confirmOrder(ActionEvent actionEvent) {
        LocalDate date = null;
        Date deliveryStart = null;
        Date deliveryEnd = new Date();
        long time = 0;

        if (this.datePicker.getValue() != null) {
            date = this.datePicker.getValue();
            deliveryStart = Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
        } else {
            label.setText("Si prega di selezionare una data per la consegna.");
        }

        if (hourComboBox.getValue() != null) {
            int hourSlot = hour.indexOf(hourComboBox.getValue());
            switch (hourSlot) {
                case 0:
                    assert deliveryStart != null;
                    time = deliveryStart.getTime() + 8 * 3600 * 1000;
                    deliveryStart.setTime(time);
                    time += 4 * 3600 * 1000;
                    deliveryEnd.setTime(time);
                    break;
                default:
                    time = deliveryStart.getTime() + 14 * 3600 * 1000;
                    deliveryStart.setTime(time);
                    time += 4 * 3600 * 1000;
                    deliveryEnd.setTime(time);
                    break;
            }

        } else {
            label.setText("Si prega di selezionare uno slot orario.");
        }

        Session session = Session.getInstance();
        List<Product> products = new ArrayList<>(session.getMapProducts().values());

        // If delivery data has been inserted, confirm order
        if (deliveryStart != null) {
            try {
                Utils.createOrder(session.getUser().getSession(), products, deliveryStart, deliveryEnd);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Order sent");
            alert.setContentText("You order is confirmed. \nWe deliver it on " + date + " between " + deliveryStart.getHours() +
                    ":00" +
                    " and " + deliveryEnd.getHours() + ":00.");
            alert.show();
        }

        this.stage.close();


    }

    public void cancelConfirmation(ActionEvent actionEvent) {
        stage.close();
    }

}
