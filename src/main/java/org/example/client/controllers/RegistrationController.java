package org.example.client.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import org.example.client.models.FactoryUser;
import org.example.client.models.User;
import org.example.client.models.enums.Payment;
import org.example.client.utils.Session;
import org.example.client.utils.Utils;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.regex.Pattern;

public class RegistrationController {

    @FXML
    public ComboBox<String> cbxPagamento;
    @FXML
    public TextField telephone;
    @FXML
    private PasswordField password;
    @FXML
    private PasswordField passwordRepeat;
    @FXML
    private TextField email;
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
    private TextField cardNumber;
    @FXML
    private Label resultLabel;

    private Stage stage;
    private Integer paymentMethod = 0;

    public static void showView(Stage stage) {
        FXMLLoader loader = new FXMLLoader(ChoiceModeController.class.getResource("/views/registration.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert root != null;

        RegistrationController registrationController = loader.getController();
        registrationController.setStage(stage);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Registrazione");
        stage.show();
    }

    @FXML
    public void initialize() {
        // Initialize ComboBox with all payments
        ObservableList<String> payments = FXCollections.observableArrayList(Payment.getLabels());
        this.cbxPagamento.setItems(payments);
        this.cbxPagamento.getSelectionModel().selectFirst();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void handlerSetPaymentAction() {
        this.paymentMethod = cbxPagamento.getSelectionModel().getSelectedIndex();
    }

    @FXML
    public void handleBackAction() {
        AuthController.showView(this.stage);
    }

    public boolean passwordVerify(String password) {
        return Pattern.matches(Utils.REGEX_PASSWORD, password);
    }

    public boolean passwordVerifyEquals(String password, String passwordRepeat) {
        return password.equals(passwordRepeat);
    }

    public boolean mailVerify(String email) {
        return (Pattern.matches(Utils.REGEX_MAIL, email));
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
            field.getStyleClass().add("error");
        return true;
    }

    private void resetErrorMessage() {
        name.getStyleClass().remove("error");
        surname.getStyleClass().remove("error");
        telephone.getStyleClass().remove("error");
        password.getStyleClass().remove("error");
        passwordRepeat.getStyleClass().remove("error");
        cap.getStyleClass().remove("error");
        city.getStyleClass().remove("error");
        address.getStyleClass().remove("error");
    }

    @FXML
    public void handleConfirmAction() {
        boolean error = false;
        resetErrorMessage();

        if (name.getText().isEmpty())
            error = errorMessage("Il campo Nome è vuoto.", name);

        if (surname.getText().isEmpty())
            error = errorMessage("Il campo Cognome è vuoto.", surname);

        if (address.getText().isEmpty())
            error = errorMessage("Il campo Indirizzo è vuoto.", address);

        // Verifica CAP
        if (!cap.getText().isEmpty()) {
            if (!capVerify(cap.getText()))
                error = errorMessage("Il CAP deve essere di 5 cifre.", cap);
        } else
            error = errorMessage("Il campo CAP è vuoto.", cap);

        if (city.getText().isEmpty())
            error = errorMessage("Il campo Città è vuoto.", city);

        if (!telephone.getText().isEmpty()) {
            if (!phoneVerify(telephone.getText()))
                error = errorMessage("Il numero di telefono dato non è valido.", telephone);
        } else {
            error = errorMessage("Il campo Telefono è vuoto.", telephone);
        }

        // verifica email
        if (!email.getText().isEmpty()) {
            if (!mailVerify(email.getText()))
                error = errorMessage("La mail non è coretta.", email);
        } else {
            error = errorMessage("Il campo email è vuoto.", email);
        }

        // Verifica lunghezza password
        if (!password.getText().isEmpty()) {
            if (!passwordVerify(password.getText()))
                error = errorMessage("La lunghezza della password deve essere di almeno 8 caratteri, una lettera " +
                        "maiuscola e una minuscola.", password);
        } else
            error = errorMessage("Il campo password è vuoto.", password);

        // Verifica uguaglianza password
        if (!passwordRepeat.getText().isEmpty()) {
            if (!passwordVerifyEquals(password.getText(), passwordRepeat.getText()))
                error = errorMessage("Le password devono coincidere.", passwordRepeat);
        } else
            error = errorMessage("Il campo Ripeti password è vuoto.", passwordRepeat);

        // inserimento card number solo se un numero
        this.cardNumber.setTextFormatter(new TextFormatter<String>(change -> {
            if (!change.isContentChange())
                return change;
            String text = change.getText();
            if (text.isEmpty() || text.matches("^[\\d]*$")) {
                return change;
            }
            return null;
        }));

        if (!error) {
            int statusCode = Utils.registerClient(email.getText(), passwordRepeat.getText(), name.getText(),
                    surname.getText(), address.getText(), Integer.valueOf(cap.getText()), city.getText(),
                    telephone.getText(), this.paymentMethod, Integer.parseInt(this.cardNumber.getText()));
            if (statusCode == 200) {
                errorMessage("Success" + statusCode, null);

                User user = new FactoryUser().getUser(email.getText(), passwordRepeat.getText());

                // Set user session
                Session session = Session.getInstance();
                session.setUser(user);

                CatalogController.showView(this.stage);
            } else {
                errorMessage("Error" + statusCode, null);
            }
        }

    }

}
