package org.example.client.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import okhttp3.Response;
import org.example.client.models.Client;
import org.example.client.utils.Session;
import org.example.client.utils.Utils;

import java.io.IOException;
import java.util.Objects;

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
    public Text error;

    private Stage stage;
    private Client client;

    /**
     * Shows the edit profile view.
     *
     * @param stage Stage to show the scene in
     */
    public static void showView(Stage stage) {
        FXMLLoader loader = new FXMLLoader(SceltaModalitaController.class.getResource("/views/edit-profile.fxml"));
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
     * Validate cap, set class t error if cap has error.
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

    @FXML
    public void initialize() {
        Session session = Session.getInstance();
        this.client = (Client) session.getUser();

        // Set the client data
        this.name.setText(this.client.getName());
        this.surname.setText(this.client.getSurname());
        this.address.setText(this.client.getAddress());
        this.cap.setText(this.client.getCap().toString());
        this.city.setText(this.client.getCity());
        this.telephone.setText(this.client.getTelephone());
        // TODO: Loyalty card

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
        validateCap();
        this.cap.focusedProperty().addListener(((observable, oldValue, newValue) -> {
            // Focus lost
            if (!newValue) {
                validateCap();
            }
        }));
        validateTelephone();
        this.telephone.focusedProperty().addListener(((observable, oldValue, newValue) -> {
            // Focus lost
            if (!newValue) {
                validateTelephone();
            }
        }));
    }


    @FXML
    public void handleButtonCancelAction() {
        ProfileController.showView(this.stage);
    }

    protected void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void handleButtonSaveAction() throws IOException {
        if (validateCap() && validateTelephone()) {
            // Set client new parameters
            this.client.setName(this.name.getText().trim());
            this.client.setSurname(this.surname.getText().trim());
            this.client.setAddress(this.address.getText().trim());
            this.client.setCap(Integer.valueOf(this.cap.getText().trim()));
            this.client.setCity(this.city.getText().trim());
            this.client.setTelephone(this.telephone.getText());

            // TODO: password
            Response response = Utils.updateUser(this.client, null);
            if (response == null) {
                this.error.setVisible(true);
            } else if (response.code() == 200) {
                // Update session
                Session session = Session.getInstance();
                session.setUser(this.client);

                ProfileController.showView(this.stage);
            } else {
                // Set response error
                if (response.body() != null)
                    error.setText(Objects.requireNonNull(response.body()).string());
                this.error.setVisible(true);
            }
        }
    }
}
