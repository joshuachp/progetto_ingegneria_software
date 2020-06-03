package org.example.client;

import javafx.application.Application;
import javafx.stage.Stage;
import org.example.client.controllers.AutenticazioneController;

import java.io.IOException;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        // TODO: Check session
        AutenticazioneController.showView(stage);
    }
}