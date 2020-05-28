package org.example.controllers;

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
import org.example.models.FactoryUtente;
import org.example.models.Utente;

import java.io.IOException;


public class AutenticazioneController {

    private Stage stage;

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label resultLabel;

    public void showView(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/views/autenticazione.fxml"));
        Scene scene = new Scene(root);
        this.stage = stage;
        this.stage.setScene(scene);
        this.stage.setTitle("Autenticazione");
    }

    public void handleActionAccedi(ActionEvent actionEvent) {
        // Get username and passwords
        String username = usernameField.getText();
        String password = passwordField.getText();
        if (!username.isEmpty() && !password.isEmpty()) {
            Utente utente = new FactoryUtente().getUtente(username, password);
            if (utente != null) {
                resultLabel.setTextFill(Color.BLACK);
                resultLabel.setText("Login effettuato con successo");
                utente.redirect(this.stage);
            } else {
                resultLabel.setTextFill(Color.RED);
                resultLabel.setText("Nome utente o password errate");
            }
        } else {
            resultLabel.setTextFill(Color.RED);
            resultLabel.setText("Username o password sono vuoti");
        }
    }
}

