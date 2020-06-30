package org.example.client;

import javafx.application.Application;
import javafx.stage.Stage;
import org.example.client.controllers.AuthController;
import org.example.client.controllers.ProfileController;
import org.example.client.utils.Session;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        Session session = Session.getInstance();
        // Check if user is authenticated
        if (session.isAuth()) {
            // Redirects to the user home
            ProfileController.showView(stage);
            session.getUser().redirect(stage);
        } else {
            AuthController.showView(stage);
        }
    }
}