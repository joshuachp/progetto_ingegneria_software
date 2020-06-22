package org.example.client.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Box;
import javafx.stage.Stage;
import org.example.client.models.Category;

import java.io.IOException;
import java.util.ArrayList;

public class CatalogoController {

    public ChoiceBox CbxColumn;
    public TextField Search;
    public ListView<String> listCategory;
    public GridPane gridpane;
    private Stage stage;

    public static ObservableList<String> categoryList;

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
        CatalogoController catalogoController = loader.getController();
        categoryList = FXCollections.observableArrayList();
        for(Category x : Category.values()){
            categoryList.add(x.toString());
        }

        ArrayList<VBox> ArrayBox = new ArrayList<>();

        for(int i = 0; i < 70 ; i++){
            int j = i % 3;
            int z = i / 3;
            Label label = new Label("prova");
            label.setPrefSize(50,50);
            VBox box = new VBox(label);
            catalogoController.gridpane.add(box, j, z);
            ArrayBox.add(box);
        }

        catalogoController.gridpane.setGridLinesVisible(true);

        catalogoController.listCategory.setItems(categoryList);
        catalogoController.setStage(stage);
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void handlerLogutAction(ActionEvent actionEvent) {
    }

    public void handleBackAction(ActionEvent actionEvent) {
    }
}
