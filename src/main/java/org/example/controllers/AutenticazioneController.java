package org.example.controllers;

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

    private final Stage stage;
    @FXML
    private TextField usr;
    @FXML
    private Label lbl;
    @FXML
    private PasswordField psw;

    public AutenticazioneController(Stage stage) {
        this.stage = stage;
    }

    public void show() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/views/autenticazione.fxml"));
        Scene scene = new Scene(root);
        this.stage.setScene(scene);
        this.stage.setTitle("Autenticazione");
        this.stage.show();
    }

    public void getCredential() {
        // Get username and passwords
        String username = usr.getText();
        String password = psw.getText();
        if (!username.isEmpty() && !password.isEmpty()) {
            Utente utente = new FactoryUtente().getUtente(username, password);
            if (utente != null) {
                lbl.setTextFill(Color.BLACK);
                lbl.setText("Login effettuato con successo");
                utente.redirect(this.stage);
            } else {
                lbl.setTextFill(Color.RED);
                lbl.setText("Nome utente o password errate");
            }
        } else {
            lbl.setTextFill(Color.RED);
            lbl.setText("Username o password sono vuoti");
        }
    }
}

