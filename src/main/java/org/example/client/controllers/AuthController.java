package org.example.client.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.example.client.models.FactoryUser;
import org.example.client.models.User;
import org.example.client.utils.Session;

import java.io.IOException;


public class AuthController {

    @FXML
    public TextField usernameField;
    @FXML
    public PasswordField passwordField;
    @FXML
    public Label resultLabel;
    @FXML
    public CheckBox rememberCheckBox;

    private Stage stage;

    public static void showView(Stage stage) {
        FXMLLoader loader = new FXMLLoader(AuthController.class.getResource("/views/auth.fxml"));
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
        AuthController authController = loader.getController();
        authController.setStage(stage);
    }

    @FXML
    public void initialize() {
        // Set remember data value
        Session session = Session.getInstance();
        this.rememberCheckBox.setSelected(session.isSaveSession());
    }

    public void handleAuthAction() {
        // Get username and passwords
        String username = usernameField.getText();
        String password = passwordField.getText();
        if (!username.isEmpty() && !password.isEmpty()) {
            User user = new FactoryUser().getUser(username, password);

            // Reset error fields
            usernameField.getStyleClass().add("error");
            passwordField.getStyleClass().add("error");

            if (user != null) {
                resultLabel.setTextFill(Color.BLACK);
                resultLabel.setText("Login effettuato con successo");

                // Set session
                Session session = Session.getInstance();
                session.setUser(user);

                user.redirect(this.stage);
            } else {
                resultLabel.setTextFill(Color.RED);
                resultLabel.setText("Nome utente o password errate");
            }
        } else {
            if (username.isEmpty())
                usernameField.getStyleClass().add("error");
            if (password.isEmpty())
                passwordField.getStyleClass().add("error");
            resultLabel.setTextFill(Color.RED);
            resultLabel.setText("Username o password sono vuoti");
        }
    }


    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void handleActionRegistrati() {
        RegistrationController.showView(this.stage);
    }

    public void handleRememberAction() {
        Session session = Session.getInstance();
        session.setSaveSession(this.rememberCheckBox.isSelected());
    }
}

