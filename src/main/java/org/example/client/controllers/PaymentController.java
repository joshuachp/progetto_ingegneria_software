package org.example.client.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.example.client.models.Client;
import org.example.client.utils.Session;

import java.io.IOException;

public class PaymentController {

    @FXML
    public ToggleGroup paymentMethod;
    @FXML
    public TextField paymentDataTextField;

    private Stage stage;

    public static void showView(Stage parentStage) {
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

        stage.showAndWait();
    }

    @FXML
    public void initialize() {
        Session session = Session.getInstance();
        Client client = (Client) session.getUser();
        int payment = client.getPayment().ordinal();
        paymentMethod.selectToggle(paymentMethod.getToggles().get(payment));
        paymentDataTextField.setText(session.getPaymentData());
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void handleCancelAction() {
        this.stage.close();
    }
}
