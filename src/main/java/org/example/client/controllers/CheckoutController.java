package org.example.client.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.client.models.Client;
import org.example.client.models.Product;
import org.example.client.models.enums.DeliveryHours;
import org.example.client.utils.Session;
import org.example.client.utils.Utils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CheckoutController {
    private static final ObservableList<String> hour =
            FXCollections.observableArrayList(DeliveryHours.getDeliveryHours());
    @FXML
    public DatePicker datePicker;
    @FXML
    public ComboBox<String> hourComboBox;
    @FXML
    public Label label;
    @FXML
    public Text addressText;

    private Stage stage;
    private Stage mainStage;

    public static void showView(Stage mainStage) {
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
        stage.initOwner(mainStage);
        //stage.setAlwaysOnTop(true);
        stage.initModality(Modality.APPLICATION_MODAL);

        CheckoutController checkoutController = loader.getController();
        checkoutController.setStage(stage);
        checkoutController.setMainStage(mainStage);

        stage.showAndWait();
    }

    public void initialize() {
        Session session = Session.getInstance();
        Client client = (Client) session.getUser();
        this.addressText.setText(String.format("%s, %d, %s", client.getAddress(), client.getCap(), client.getCity()));

        // Crate selectable cell only for day after today and working days
        this.datePicker.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();
                setDisable(empty ||
                        date.getDayOfWeek().equals(DayOfWeek.SATURDAY) ||
                        date.getDayOfWeek().equals(DayOfWeek.SUNDAY) ||
                        date.compareTo(today) <= 0);
            }
        });
        hourComboBox.setItems(hour);

    }

    private void setStage(Stage stage) {
        this.stage = stage;
    }

    private void setMainStage(Stage mainStage) {
        this.mainStage = mainStage;
    }

    @FXML
    public void confirmOrder() {
        // Validation
        if (this.datePicker.getValue() == null) {
            label.setVisible(true);
            label.setText("Si prega di selezionare una data per la consegna.");
            return;
        }

        if (hourComboBox.getValue() == null) {
            label.setVisible(true);
            label.setText("Si prega di selezionare uno slot orario.");
            return;
        }
        label.setVisible(false);

        LocalDate date = this.datePicker.getValue();
        DeliveryHours hour = DeliveryHours.values()[this.hourComboBox.getSelectionModel().getSelectedIndex()];

        Calendar calendarStart = Calendar.getInstance();
        Calendar calendarEnd = Calendar.getInstance();

        //noinspection MagicConstant
        calendarStart.set(date.getYear(), date.getMonth().getValue(), date.getDayOfMonth(), hour.getStartHour(), 0);
        calendarEnd.setTime(calendarStart.getTime());
        calendarEnd.set(Calendar.HOUR_OF_DAY, hour.getEndHour());

        Date deliveryStart = calendarStart.getTime();
        Date deliveryEnd = calendarEnd.getTime();

        Session session = Session.getInstance();
        Client client = (Client) session.getUser();
        List<Product> products = new ArrayList<>(session.getMapProducts().values());

        // If delivery data has been inserted, confirm order
        try {
            Utils.createOrder(session.getUser().getSession(), products, deliveryStart, deliveryEnd);

            SimpleDateFormat hourFormat = new SimpleDateFormat("hh:mm");
            //noinspection SpellCheckingInspection
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM");


            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Order sent");
            alert.setContentText(
                    String.format("You order is confirmed.\nWe deliver it on %s between %s and %s at %s, %d, %s.",
                            dateFormat.format(deliveryStart),
                            hourFormat.format(deliveryStart),
                            hourFormat.format(deliveryEnd),
                            client.getAddress(),
                            client.getCap(),
                            client.getCity()
                    )
            );

            alert.showAndWait();

        } catch (Exception e) {
            e.printStackTrace();

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }

        session.removeAllProducts();

        this.stage.close();
        CatalogController.showView(mainStage);
    }

    @FXML
    public void cancelConfirmation() {
        stage.close();
    }

}
