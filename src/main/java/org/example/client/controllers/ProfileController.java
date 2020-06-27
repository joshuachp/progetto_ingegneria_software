package org.example.client.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.client.models.Client;
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
        // TODO: Loyalty card
    }

    protected void setStage(Stage stage) {
        this.stage = stage;
    }

}
