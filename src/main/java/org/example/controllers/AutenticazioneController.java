package org.example.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import org.example.models.FactoryUtente;
import org.example.models.ResponsabileReparto;
import org.example.models.Utente;

import java.io.IOException;


public class AutenticazioneController {

    @FXML
    private TextField usr;
    @FXML
    private Label lbl;
    @FXML
    private PasswordField psw;
    private Stage stage;

    public AutenticazioneController(Stage stage){
        this.stage = stage;
    }

    public void show() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/views/autenticazione.fxml"));
        Scene scene = new Scene(root);
        this.stage.setScene(scene);
        this.stage.setTitle("Autenticazione");
        this.stage.show();
    }

    public void getCredential(ActionEvent event) {
        String username = usr.getText();
        String password = psw.getText();
        Utente utente = new FactoryUtente().getUtente(username, password);
        //TODO check credential
        if (utente != null) {
            lbl.setTextFill(Color.BLACK);
            lbl.setText("Login effettuato con successo");
            utente.redirect(this.stage);
        } else {
            lbl.setTextFill(Color.RED);
            lbl.setText("Nome utente o password errate");
        }
    }
}

