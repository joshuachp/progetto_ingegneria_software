package org.example.client.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.example.client.components.CartFactory;
import org.example.client.models.Product;
import org.example.client.utils.Session;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class CheckoutController {
    @FXML
    public DatePicker datePicker;
    @FXML
    public ComboBox hourComboBox;

    private LocalDate selectedDay;
    private static ObservableList<String> hour = FXCollections.observableArrayList
            ("8:00","9:00","10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00");
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
                        (date.getDayOfWeek()).equals(DayOfWeek.SATURDAY ) || date.compareTo(today) < 0);

            }
        });

        hourComboBox.setItems(hour);

    }

    private void setStage(Stage stage) {
        this.stage = stage;
    }

    public void confirmOrder(ActionEvent actionEvent) {
        Session session = Session.getInstance();

    }

    public void cancelConfirmation(ActionEvent actionEvent) {
        stage.close();
    }

}
