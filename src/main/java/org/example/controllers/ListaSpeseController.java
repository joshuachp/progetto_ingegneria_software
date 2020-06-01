package org.example.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Cell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.example.models.Pagamento;
import org.example.models.Spesa;
import org.example.models.StatoSpesa;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


import java.io.IOException;
import java.util.ArrayList;

public class ListaSpeseController {

    @FXML private TableColumn<Spesa, String> DataCol;
    @FXML private TableColumn<Spesa, String> OraCol;
    @FXML private TableColumn<Spesa, Pagamento> MetodoPagamentoCol;
    @FXML private TableColumn<Spesa, Float> TotaleCol;
    @FXML private TableColumn<Spesa, StatoSpesa> StatoCol;
    @FXML private TableColumn<Spesa, Integer> IDCol;
    @FXML private TableView <Spesa> tableview;

    private Stage stage;
    public ListView<Spesa> listview;

    public void showView(Stage stage) throws IOException {

        FXMLLoader loader = new FXMLLoader(ListaSpeseController.class.getResource("/views/listaSpese.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Lista Spese");
        stage.show();
        ListaSpeseController listaspesecontroller = loader.getController();

        ObservableList<Spesa> list = FXCollections.observableArrayList(
                new Spesa(1, "10/09/20", "10:00 - 14:00", "utente", 100, Pagamento.PAYPAL, StatoSpesa.CONSEGNATA ),
                new Spesa(2, "10/09/20", "10:00 - 14:00", "utente", 100, Pagamento.PAYPAL, StatoSpesa.CONSEGNATA )
        );

        listaspesecontroller.IDCol.setCellValueFactory(new PropertyValueFactory<>("ID"));
        //listaspesecontroller.listview.setItems(list);
        listaspesecontroller.stage = stage;
        listaspesecontroller.DataCol.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        listaspesecontroller.tableview.setItems(list);
    }
}
