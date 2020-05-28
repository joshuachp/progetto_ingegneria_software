package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.example.controllers.AutenticazioneController;
import org.example.server.Server;

import java.io.IOException;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/views/main.fxml"));

        Scene scene = new Scene(root, 600, 400);
        Stage splashScreen = new Stage();
        splashScreen.setScene(scene);
        splashScreen.setTitle("Spesa Online");
        splashScreen.initModality(Modality.WINDOW_MODAL);
        splashScreen.setResizable(false);
        splashScreen.initStyle(StageStyle.UNDECORATED);
        splashScreen.alwaysOnTopProperty();
        splashScreen.show();
        // TODO: check session before asking new credential
        Server.getInstance();
        AutenticazioneController autenticazioneController = new AutenticazioneController(stage);
        autenticazioneController.show();
        splashScreen.close();
    }

}