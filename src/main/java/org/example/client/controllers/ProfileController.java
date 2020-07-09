package org.example.client.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.client.models.Client;
import org.example.client.models.LoyaltyCard;
import org.example.client.models.enums.Payment;
import org.example.client.tasks.TaskCardPoints;
import org.example.client.utils.Session;

import java.io.IOException;

public class ProfileController {

    @FXML
    public Text nameSurname;
    @FXML
    public Text username;
    @FXML
    public Text address;
    @FXML
    public Text telephone;
    @FXML
    public Text cardNumber;
    @FXML
    public Text emissionDate;
    @FXML
    public Text pointsText;
    @FXML
    public Text cardText;
    @FXML
    public Text cardTextPoints;
    @FXML
    public Text paymentText;
    @FXML
    public Text paymentData;

    private Stage stage;

    public static void showView(Stage stage) {
        FXMLLoader loader = new FXMLLoader(SceltaModalitaController.class.getResource("/views/profile.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert root != null;
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Profile");
        stage.show();
        ProfileController controller = loader.getController();
        controller.setStage(stage);
    }

    @FXML
    private void handleButtonEditAction() {
        EditProfileController.showView(this.stage);
    }

    @FXML
    public void initialize() {
        Session session = Session.getInstance();
        Client client = (Client) session.getUser();

        // Set the profile information
        this.nameSurname.setText(String.format("%s %s", client.getName(), client.getSurname()));
        this.username.setText(client.getUsername());
        this.address.setText(String.format("%s, %d, %s", client.getAddress(), client.getCap(), client.getCity()));
        this.telephone.setText(client.getTelephone());
        this.paymentText.setText(client.getPayment().toString());
        // Set payment data if payment is different than cash
        if (client.getPayment() != Payment.CASH) {
            String data = session.getPaymentData();
            if (data.length() > 4)
                data = data.substring(data.length() - 4);
            this.paymentData.setText(String.format("**** **** **** %s", data));
        } else {
            paymentData.setVisible(false);
        }

        // Loyalty card
        if (client.getCardNumber() != null) {
            this.cardNumber.setText(String.format("Card n. %d", client.getCardNumber()));

            // Get card data.
            TaskCardPoints cardTask = new TaskCardPoints(client);
            // Success sets card information
            cardTask.setOnSucceeded(event -> {
                LoyaltyCard card = cardTask.getValue();
                this.pointsText.setText(card.getPoints().toString());
                emissionDate.setText(card.getEmissionDate().toString());
            });
            // Error remove card text and set error
            cardTask.setOnFailed(event -> {
                String error = cardTask.getMessage();

                this.cardText.setText(error);
                this.cardText.setFill(Color.RED);

                this.pointsText.setVisible(false);
                this.emissionDate.setVisible(false);
                this.cardTextPoints.setVisible(false);
            });

            new Thread(cardTask).start();

        } else {
            cardText.setText("No loyalty card registered.");
            pointsText.setVisible(false);
            emissionDate.setVisible(false);
            cardTextPoints.setVisible(false);
        }
    }

    protected void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void handleBackAction() {
        Session session = Session.getInstance();
        Client client = (Client) session.getUser();
        client.redirect(stage);
    }

    @FXML
    public void handleViewShoppingHistoryAction() {
        ShoppingHistoryController.showView(this.stage);
    }
}
