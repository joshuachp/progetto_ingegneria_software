package org.example.client.controllers;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import okhttp3.Response;
import org.example.client.models.Client;
import org.example.client.models.LoyaltyCard;
import org.example.client.utils.Session;
import org.example.client.utils.Utils;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;

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
    public Text points;
    @FXML
    public Text cardText;
    @FXML
    public Text cardTextPoints;

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
        // TODO: Payment method
        // Loyalty card
        if (client.getCardNumber() != null) {
            this.cardNumber.setText(String.format("Card n. %d", client.getCardNumber()));
            // Get card data.
            Task<Void> getPoints = new Task<>() {
                @Override
                protected Void call() throws Exception {
                    Response response = Utils.getLoyaltyCard(client.getSession(), client.getCardNumber());
                    if (response != null && response.code() == 200 && response.body() != null) {
                        LoyaltyCard loyaltyCard =
                                new LoyaltyCard(new JSONObject(Objects.requireNonNull(response.body()).string()));
                        // Sets the card information
                        points.setText(String.valueOf(loyaltyCard.getPoints()));
                        emissionDate.setText(loyaltyCard.getEmissionDate().toString());
                    }
                    return null;
                }
            };
            new Thread(getPoints).start();
        } else {
            cardText.setText("No loyalty card registered.");
            points.setVisible(false);
            emissionDate.setVisible(false);
            cardTextPoints.setVisible(false);
        }
    }

    protected void setStage(Stage stage) {
        this.stage = stage;
    }

}
