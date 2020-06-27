package org.example.client;

import javafx.application.Application;
import javafx.stage.Stage;
import org.example.client.controllers.EditProfileController;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        // XXX: TEST
        EditProfileController.showView(stage);

        //Session session = Session.getInstance();
        //// Check if user is authenticated
        //if (session.isAuth()) {
        //    // Redirects to the user home
        //    session.getUser().redirect(stage);
        //} else {
        //    AutenticazioneController.showView(stage);
        //}
    }
}