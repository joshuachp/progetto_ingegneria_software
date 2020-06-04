package org.example.client.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.client.models.Cliente;
import org.example.client.utils.Utils;

import java.io.IOException;

public class RegistrazioneController {

    @FXML
    private TextField Name;
    @FXML
    private TextField Surname;
    @FXML
    private TextField Addess;
    @FXML
    private TextField CAP;
    @FXML
    private TextField City;
    @FXML
    private TextField Phone;
    @FXML
    private TextField Fidelty;
    @FXML
    private Label resultLabel;

    private Stage stage;

    public static void showView(Stage stage) {
        FXMLLoader loader = new FXMLLoader(SceltaModalitaController.class.getResource("/views/registrazione.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert root != null;
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Registrazione");
        stage.show();
        RegistrazioneController controller = loader.getController();
        controller.setStage(stage);
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void handleBackAction(ActionEvent actionEvent) {
        AutenticazioneController.showView(this.stage);
    }

    public boolean handlePasswordField(ActionEvent actionEvent) {
        return true;
    }

    public boolean handlePasswordVerifyField(ActionEvent actionEvent) {
        return true;
    }

    public boolean handleCardField(ActionEvent actionEvent) {
        return true;
    }

    public boolean handlePhoneField(ActionEvent actionEvent) {
        return true;
    }

    public boolean handleCapField(ActionEvent actionEvent) {
        return true;
    }

    public void handleConfirmAction(ActionEvent actionEvent) {
        //Utils.RegisterClient();
    }
}
