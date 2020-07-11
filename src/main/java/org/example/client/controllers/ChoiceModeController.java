package org.example.client.controllers;

import javafx.event.ActionEvent;
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
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Modalità");
        stage.show();
        ChoiceModeController controller = loader.getController();
        controller.setStage(stage);
    }

    public void HandleActionVisualizzaSpese(ActionEvent actionEvent) {
        ListaSpeseController listaSpese = new ListaSpeseController();
        try {
            listaSpese.showView(this.stage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void HandleActionManageProduct(ActionEvent actionEvent) {
        ProductListController productlistcontroller = new ProductListController();
        ProductListController.showView(this.stage);
    }

    @FXML
    public void handlerLogutAction(ActionEvent actionEvent) {
        Utils.logOut(this.stage);
    }

    @FXML
    public void handlerAddManagerAction(ActionEvent actionEvent) {
        RegistrationManagerController.showView(this.stage);
    }
}
