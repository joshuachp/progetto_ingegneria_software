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

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;

public class CheckoutController {
    private static ObservableList<String> hour = FXCollections.observableArrayList
            ("8:00 - 12:00", "14:00 - 18:00");
    @FXML
    public DatePicker datePicker;
    @FXML
    public ComboBox hourComboBox;
    public Label label;
    private LocalDate selectedDay;
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
        int startHour = 0;
        int endHour = 0;
        LocalDate date = null;

        if (this.datePicker.getValue() != null) {
            date = this.datePicker.getValue();
        } else {
            label.setText("Si prega di selezionare una data per la consegna.");
        }

        if (hourComboBox.getValue() != null) {
            int hourSlot = hour.indexOf(hourComboBox.getValue());
            switch (hourSlot) {
                case 0:
                    startHour = 8;
                    endHour = 12;
                    break;
                default:
                    startHour = 14;
                    endHour = 18;
                    break;
            }

        } else {
            label.setText("Si prega di selezinare uno slot orario.");
        }

        // If delivery data has been inserted, confirm order
        if (startHour != 0 && date != null && endHour != 0) {

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Order sent");
            alert.setContentText("You order is confirmed. \nWe deliver it on " + date + " between " + startHour +
                    ":00" +
                    " and " + endHour + ":00.");
            alert.show();
        }


    }

    public void cancelConfirmation(ActionEvent actionEvent) {
        stage.close();
    }

}
