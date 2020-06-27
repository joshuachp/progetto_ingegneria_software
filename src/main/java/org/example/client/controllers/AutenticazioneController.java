package org.example.client.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.example.client.models.FactoryUser;
import org.example.client.models.User;

import java.io.IOException;


public class AutenticazioneController {

    private Stage stage;

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label resultLabel;

    public static void showView(Stage stage) {
        FXMLLoader loader = new FXMLLoader(AutenticazioneController.class.getResource("/views/autenticazione.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert root != null;
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Autenticazione");
        stage.show();
        AutenticazioneController autenticazioneController = loader.getController();
        autenticazioneController.setStage(stage);
    }

    public void handleActionAccedi() {
        // Get username and passwords
        String username = usernameField.getText();
        String password = passwordField.getText();
        if (!username.isEmpty() && !password.isEmpty()) {
            User user = new FactoryUser().getUtente(username, password);
            if (user != null) {
                resultLabel.setTextFill(Color.BLACK);
                resultLabel.setText("Login effettuato con successo");
                // TODO: Get scene
                user.redirect(this.stage);
            } else {
                resultLabel.setTextFill(Color.RED);
                resultLabel.setText("Nome utente o password errate");
            }
        } else {
            resultLabel.setTextFill(Color.RED);
            // TODO: Label is to short and text is cut
            resultLabel.setText("Username o password sono vuoti");
        }
    }


    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void handleActionRegistrati(ActionEvent actionEvent) {
        RegistrazioneController.showView(this.stage);
    }
}

