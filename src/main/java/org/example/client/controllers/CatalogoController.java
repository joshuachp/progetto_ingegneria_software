package org.example.client.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

public class CatalogoController {

    public ChoiceBox CbxColumn;
    public TextField Search;
    public ListView listCategory;
    public GridPane gridpane;
    private Stage stage;


    public static void showView(Stage stage) {
        FXMLLoader loader = new FXMLLoader(CatalogoController.class.getResource("/views/catalogo.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert root != null;
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Catalogo");
        stage.show();
        CatalogoController controller = loader.getController();
        controller.setStage(stage);
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void handlerLogutAction(ActionEvent actionEvent) {
    }

    public void handleBackAction(ActionEvent actionEvent) {
    }
}
