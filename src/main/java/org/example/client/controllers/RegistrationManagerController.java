package org.example.client.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import org.example.client.components.FactoryUser;
import org.example.client.models.User;
import org.example.client.utils.Session;
import org.example.client.utils.Utils;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.regex.Pattern;

public class RegistrationManagerController {

    @FXML
    public TextField telephone;
    @FXML
    private PasswordField password;
    @FXML
    private PasswordField passwordRepeat;
    @FXML
    private TextField name;
    @FXML
    private TextField surname;
    @FXML
    private TextField address;
    @FXML
    private TextField cap;
    @FXML
    private TextField city;
    @FXML
    private TextField badgeNumber;
    @FXML
    private Label resultLabel;
    @FXML
    private TextField roleField;

    private Stage stage;


    public static void showView(Stage stage) {

        FXMLLoader loader = new FXMLLoader(ChoiceModeController.class.getResource("/views/registration-manager" +
                ".fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert root != null;

        RegistrationManagerController registrationManagerControllerController = loader.getController();
        registrationManagerControllerController.setStage(stage);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Registrazione");
        stage.show();
    }

    public void initialize(){
        // inserimento card number solo se un numero
        this.badgeNumber.setTextFormatter(new TextFormatter<String>(change -> {
            if (!change.isContentChange())
                return change;
            String text = change.getText();
            if (text.isEmpty() || text.matches(Utils.REGEX_BADGE)) {
                return change;
            }
            return null;
        }));
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
    @FXML
    public void handleBackAction() {
       ChoiceModeController.showView(stage);
    }

    public boolean passwordVerify(String password) {
        return Pattern.matches(Utils.REGEX_PASSWORD, password);
    }

    public boolean passwordVerifyEquals(String password, String passwordRepeat) {
        return password.equals(passwordRepeat);
    }

    public boolean capVerify(String cap) {
        return (Pattern.matches(Utils.REGEX_CAP, cap));
    }

    public boolean phoneVerify(String phone) {
        return (Pattern.matches(Utils.REGEX_TELEPHONE, phone));
    }

    public boolean errorMessage(String message, @Nullable TextField field) {
        resultLabel.setText(message);
        resultLabel.setTextFill(Paint.valueOf("red"));
        if (field != null)
            field.setStyle("-fx-border-color: red");
        return true;
    }

    private void resetErrorMessage() {
        name.setStyle(null);
        surname.setStyle(null);
        telephone.setStyle(null);
        password.setStyle(null);
        passwordRepeat.setStyle(null);
        cap.setStyle(null);
        city.setStyle(null);
        address.setStyle(null);
    }

    @FXML
    public void handleConfirmAction() {
        boolean error = false;
        resetErrorMessage();

        if (name.getText().equals(""))
            error = errorMessage("Il campo Nome è vuoto.", name);

        if (surname.getText().equals(""))
            error = errorMessage("Il campo Cognome è vuoto.", surname);

        if (address.getText().equals(""))
            error = errorMessage("Il campo Indirizzo è vuoto.", address);

        // Verifica CAP
        if (!cap.getText().equals("")) {
            if (!capVerify(cap.getText()))
                error = errorMessage("Il CAP deve essere di 5 cifre.", cap);
        } else
            error = errorMessage("Il campo CAP è vuoto.", cap);

        if (city.getText().equals(""))
            error = errorMessage("Il campo Città è vuoto.", city);

        if (!telephone.getText().equals("")) {
            if (!phoneVerify(telephone.getText()))
                error = errorMessage("Il numero di telefono dato non è valido.", telephone);
        } else
            error = errorMessage("Il campo Telefono è vuoto.", telephone);

        // Verifica lunghezza password
        if (!password.getText().equals("")) {
            if (!passwordVerify(password.getText()))
                error = errorMessage("La password deve essere di almeno 8 caratteri e contere almeno una lettera " +
                                "maiuscola.",
                        password);
        } else
            error = errorMessage("Il campo password è vuoto.", password);

        // Verifica uguaglianza password
        if (!passwordRepeat.getText().equals("")) {
            if (!passwordVerifyEquals(password.getText(), passwordRepeat.getText()))
                error = errorMessage("Le password devono coincidere.", passwordRepeat);
        } else
            error = errorMessage("Il campo Ripeti password è vuoto.", passwordRepeat);

        if (roleField.getText().isEmpty())
            error = errorMessage("Il campo Ruolo è vuoto,", roleField);

        if (badgeNumber.getText().isEmpty()){
            error = errorMessage("Il campo Basge è vuoto,", badgeNumber);
        }

        if (!error) {
            int statusCode = Utils.registerManager( badgeNumber.getText(), passwordRepeat.getText(),
                    badgeNumber.getText(), name.getText(),
                    surname.getText(), address.getText(), Integer.valueOf(cap.getText()), city.getText(),
                    telephone.getText(), roleField.getText());
            if (statusCode == 200) {
                errorMessage("Success" + statusCode, null);

                User user = new FactoryUser().getUser(badgeNumber.getText(), passwordRepeat.getText());

                // Set user session
                Session session = Session.getInstance();
                session.setUser(user);

                ChoiceModeController.showView(this.stage);
            } else {
                errorMessage("Error" + statusCode, null);
            }
        }

    }

}
