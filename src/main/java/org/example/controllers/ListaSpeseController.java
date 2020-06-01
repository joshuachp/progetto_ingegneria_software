package org.example.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class ListaSpeseController {

    private Stage stage;
    public ListView<String> listview;

    private ObservableList<String> list = FXCollections.observableArrayList("test1", "test2");

    public void showView(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(ListaSpeseController.class.getResource("/views/listaSpese.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Lista Spese");
        stage.show();
        ListaSpeseController listaspesecontroller = loader.getController();
        listaspesecontroller.stage = stage;
    }

}
