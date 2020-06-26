package org.example.client.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.io.IOException;

public class ProfileController {

    private Stage stage;

    public static void showView(Stage stage) {
        FXMLLoader loader = new FXMLLoader(SceltaModalitaController.class.getResource("/views/profile.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert root != null;
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Profile");
        stage.show();
        ProfileController controller = loader.getController();
        controller.setStage(stage);
    }

    @FXML
    private void handleButtonEditAction(ActionEvent event) {
        EditProfileController.showView(this.stage);
    }

    protected void setStage(Stage stage) {
        this.stage = stage;
    }

}
