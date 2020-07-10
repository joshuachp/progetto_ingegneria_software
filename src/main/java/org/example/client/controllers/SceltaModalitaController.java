package org.example.client.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceltaModalitaController {

    private Stage stage;

    public static void showView(Stage stage) {

        FXMLLoader loader = new FXMLLoader(SceltaModalitaController.class.getResource("/views/scelta-modalita.fxml"));
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
        SceltaModalitaController controller = loader.getController();
        controller.setStage(stage);
    }

    @FXML
    public void HandleActionVisualizzaSpese() {
        ManageOrdersController.showView(this.stage);
    }


    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void HandleActionManageProduct(ActionEvent actionEvent) {
        ProductListController productlistcontroller = new ProductListController();
        ProductListController.showView(this.stage);
    }

    public void handlerLogutAction(ActionEvent actionEvent) {
    }

    public void handlerAddManagerAction(ActionEvent actionEvent) {
    }
}
