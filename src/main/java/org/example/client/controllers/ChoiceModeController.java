package org.example.client.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.client.utils.Utils;

import java.io.IOException;

public class ChoiceModeController {

    private Stage stage;

    public static void showView(Stage stage) {
        FXMLLoader loader = new FXMLLoader(ChoiceModeController.class.getResource("/views/choice-mode.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert root != null;
        ChoiceModeController controller = loader.getController();
        controller.setStage(stage);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Modalità");
        stage.show();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void handleActionVisualizzaSpese() {
        ManageOrdersController.showView(this.stage);
    }

    @FXML
    public void handleActionManageProduct() {
        ProductListController.showView(this.stage);
    }

    @FXML
    public void handlerLogoutAction() {
        Utils.logOut(this.stage);
    }

    @FXML
    public void handlerAddManagerAction() {
        RegistrationManagerController.showView(this.stage);
    }
}
