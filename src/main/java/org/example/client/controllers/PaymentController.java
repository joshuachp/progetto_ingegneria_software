package org.example.client.controllers;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.example.client.models.enums.Payment;
import org.example.client.utils.Session;

import java.io.IOException;

public class PaymentController {

    @FXML
    public ToggleGroup paymentMethod;
    @FXML
    public TextField paymentDataTextField;

    private Stage stage;
    private Payment payment;

    public static Payment showView(Stage parentStage, Payment payment) {
        FXMLLoader loader = new FXMLLoader(PaymentController.class.getResource("/views/payment.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        PaymentController controller = loader.getController();
        assert root != null;
        Scene scene = new Scene(root);
        Stage stage = new Stage();

        stage.setScene(scene);
        stage.setTitle("Payment method");
        stage.setAlwaysOnTop(true);
        stage.initStyle(StageStyle.UTILITY);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(parentStage);

        controller.setStage(stage);
        controller.setPayment(payment);

        stage.showAndWait();

        return controller.getPayment();
    }

    public Payment getPayment() {
        return this.payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
        Session session = Session.getInstance();
        paymentMethod.selectToggle(paymentMethod.getToggles().get(payment.ordinal()));
        if (payment != Payment.CASH)
            paymentDataTextField.setText(session.getPaymentData());
    }


    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void handleCancelAction() {
        this.stage.close();
    }

    @FXML
    public void handleChangeAction() {
        ObservableList<Toggle> toggles = paymentMethod.getToggles();
        int payment = toggles.indexOf(paymentMethod.getSelectedToggle());
        this.payment = Payment.values()[payment];

        Session session = Session.getInstance();
        session.setPaymentData(this.paymentDataTextField.getText());

        this.stage.close();
    }
}
