package org.example.client.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import okhttp3.Response;
import org.example.client.models.Client;
import org.example.client.models.enums.Payment;
import org.example.client.utils.Session;
import org.example.client.utils.Utils;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

public class EditProfileController {


    @FXML
    public TextField name;
    @FXML
    public TextField surname;
    @FXML
    public TextField address;
    @FXML
    public TextField cap;
    @FXML
    public TextField city;
    @FXML
    public TextField telephone;
    @FXML
    public TextField cardNumber;
    @FXML
    public TextField password;
    @FXML
    public TextField confirmPassword;

    @FXML
    public Text username;
    @FXML
    public Text error;
    @FXML
    public Text paymentText;
    @FXML
    public Text paymentData;

    private Stage stage;
    private Client client;
    private Payment payment;

    /**
     * Shows the edit profile view.
     *
     * @param stage Stage to show the scene in
     */
    public static void showView(Stage stage) {
        FXMLLoader loader = new FXMLLoader(ChoiceModeController.class.getResource("/views/edit-profile.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert root != null;
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Edit Profile");
        stage.show();
        EditProfileController controller = loader.getController();
        controller.setStage(stage);
    }

    /**
     * Validate cap, set class t error if cap has error.
     *
     * @return Valid or not
     */
    private boolean validateTelephone() {
        boolean valid = telephone.getText().matches(Utils.REGEX_TELEPHONE);
        if (!valid) {
            telephone.getStyleClass().add("error");
        } else {
            telephone.getStyleClass().remove("error");
        }
        return valid;
    }

    /**
     * Validate cap, set text field class to error if cap has error.
     *
     * @return Valid or not
     */
    private boolean validateCap() {
        boolean valid = cap.getText().matches(Utils.REGEX_CAP);
        if (!valid) {
            cap.getStyleClass().add("error");
        } else {
            cap.getStyleClass().remove("error");
        }
        return valid;
    }

    /**
     * Validate password, set text field class to error if password has error.
     *
     * @return Valid or not
     */
    private boolean validatePassword() {
        // Validate the password only if it's not empty
        boolean valid = password.getText().isEmpty() || password.getText().matches(Utils.REGEX_PASSWORD);
        if (!valid) {
            password.getStyleClass().add("error");
        } else {
            password.getStyleClass().remove("error");
        }
        return valid;
    }

    /**
     * Validate password, set text field class to error if password has error.
     *
     * @return Valid or not
     */
    private boolean validateConfirmPassword() {
        // Validate the password confirmation only if the password it's not empty
        boolean valid = password.getText().isEmpty() ||
                (confirmPassword.getText().matches(Utils.REGEX_PASSWORD) &&
                        confirmPassword.getText().equals(password.getText()));
        if (!valid) {
            confirmPassword.getStyleClass().add("error");
        } else {
            confirmPassword.getStyleClass().remove("error");
        }
        return valid;
    }


    @FXML
    public void initialize() {
        Session session = Session.getInstance();
        this.client = (Client) session.getUser();

        // Set the client data
        this.name.setText(this.client.getName());
        this.username.setText(this.client.getUsername());
        this.surname.setText(this.client.getSurname());
        this.address.setText(this.client.getAddress());
        this.cap.setText(this.client.getCap().toString());
        this.city.setText(this.client.getCity());
        this.telephone.setText(this.client.getTelephone());

        this.payment = client.getPayment();

        setPayment();

        // Loyalty card
        if (this.client.getCardNumber() != null)
            this.cardNumber.setText(this.client.getCardNumber().toString());

        // Filter for only valid CAP. You can insert more than 5 digits copy and parting, we delegate this case to
        // the validation
        this.cap.setTextFormatter(new TextFormatter<String>(change -> {
            String controlText = change.getControlText();
            String text = change.getText();
            if (!change.isContentChange() || text.isEmpty())
                return change;
            if (controlText.length() < 5) {
                if (text.matches("^\\d*$")) {
                    return change;
                }
            }
            return null;
        }));
        // Filter for only valid telephones characters.
        this.telephone.setTextFormatter(new TextFormatter<String>(change -> {
            if (!change.isContentChange())
                return change;
            String text = change.getText();
            if (text.isEmpty() || text.matches("^[\\d+()]*$")) {
                return change;
            }
            return null;
        }));
        // Only numbers for card
        this.cardNumber.setTextFormatter(new TextFormatter<String>(change -> {
            if (!change.isContentChange())
                return change;
            String text = change.getText();
            if (text.isEmpty() || text.matches("^[\\d]*$")) {
                return change;
            }
            return null;
        }));

        // Validation
        // CAP
        validateCap();
        this.cap.focusedProperty().addListener(((observable, oldValue, newValue) -> {
            // Focus lost
            if (!newValue) {
                validateCap();
            }
        }));
        // Telephone
        validateTelephone();
        this.telephone.focusedProperty().addListener(((observable, oldValue, newValue) -> {
            // Focus lost
            if (!newValue) {
                validateTelephone();
            }
        }));
        // Password
        this.password.focusedProperty().addListener(((observable, oldValue, newValue) -> {
            // Focus lost
            if (!newValue) {
                validatePassword();
            }
        }));
        // Confirm password
        this.confirmPassword.focusedProperty().addListener(((observable, oldValue, newValue) -> {
            // Focus lost
            if (!newValue) {
                validateConfirmPassword();
            }
        }));
    }

    private void setPayment() {
        Session session = Session.getInstance();

        this.paymentText.setText(this.payment.toString());

        // Set payment data if payment is different than cash
        if (this.payment != Payment.CASH) {
            // Reset visibility
            this.paymentData.setVisible(true);

            String data = session.getPaymentData();
            if (data.length() > 4)
                data = data.substring(data.length() - 4);
            this.paymentData.setText(String.format("**** **** **** %s", data));
        } else {
            this.paymentData.setVisible(false);
        }
    }


    @FXML
    public void handleButtonCancelAction() {
        ProfileController.showView(this.stage);
    }

    protected void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void handleButtonSaveAction() {
        boolean check = validateCap() & validateTelephone();
        // Check for new password
        if (!password.getText().isEmpty()) {
            check &= validatePassword() & validateConfirmPassword();
        }
        if (check) {
            showAlert();
        }
    }

    private void updateProfile() {
        // Set client new parameters
        this.client.setName(this.name.getText().trim());
        this.client.setSurname(this.surname.getText().trim());
        this.client.setAddress(this.address.getText().trim());
        this.client.setCap(Integer.valueOf(this.cap.getText().trim()));
        this.client.setCity(this.city.getText().trim());
        this.client.setTelephone(this.telephone.getText());
        this.client.setPayment(this.payment);
        this.client.setCardNumber(Integer.valueOf(this.cardNumber.getText().trim()));

        // New password is set
        String pass = null;
        if (!password.getText().isEmpty())
            pass = password.getText();

        Response response = Utils.updateUser(this.client, pass);
        if (response == null) {
            this.error.setVisible(true);
        } else {
            if (response.code() == 200) {
                // Update session
                Session session = Session.getInstance();
                session.setUser(this.client);

                ProfileController.showView(this.stage);

            } else {
                // Set response error
                if (response.body() != null) {
                    try {
                        error.setText(Objects.requireNonNull(response.body()).string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                this.error.setVisible(true);

            }

            Objects.requireNonNull(response.body()).close();
        }
    }

    private void showAlert() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Save changes");
        alert.setContentText("Are you sure you want to save the changes? This operation can not be undone.");


        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            updateProfile();
        }
    }

    @FXML
    public void handleChangePaymentAction() {
        this.payment = PaymentController.showView(this.stage, this.payment);
        setPayment();
    }

}
