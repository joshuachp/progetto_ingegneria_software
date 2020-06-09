package org.example.client.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.example.client.models.Prodotto;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProductListController {
    /*ArrayList<Prodotto> arrayProducts = new ArrayList<Prodotto>(
            new Prodotto());
    // Test products

    // TODO: get products from server
    ObservableList<Prodotto> Products =  FXCollections.observableArrayList(array);*/

    private Stage stage;
    public static void showView(Stage stage) throws IOException{
        FXMLLoader loader = new FXMLLoader(ProductListController.class.getResource("/views/product-list.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert root != null;
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Lista Prodotti");
        stage.show();
        ProductListController controller = loader.getController();
        controller.setStage(stage);
    }

    private void setStage(Stage stage) {
        this.stage = stage;
    }


    public void handlerAddProduct(ActionEvent actionEvent) throws IOException {
        SceltaModalitaController.showView(this.stage);
    }

    public void handleBackAction(ActionEvent actionEvent) throws IOException {
        SceltaModalitaController.showView(this.stage);
    }
    public void handlerLogutAction(ActionEvent actionEvent) {
    }

    public void handlerAddManagerAction(ActionEvent actionEvent) {
    }
}
