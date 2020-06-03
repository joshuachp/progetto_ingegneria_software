package org.example.client.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.converter.DefaultStringConverter;
import org.example.client.models.Pagamento;
import org.example.client.models.Spesa;
import org.example.client.models.StatoSpesa;

import java.io.IOException;

public class ListaSpeseController {

    public ListView<Spesa> listview;
    public ObservableList<String> stato;
    public ObservableList<Spesa> spese;
    @FXML
    private TableColumn<Spesa, String> DataCol;
    @FXML
    private TableColumn<Spesa, String> OraCol;
    @FXML
    private TableColumn<Spesa, Pagamento> MetodoPagamentoCol;
    @FXML
    private TableColumn<Spesa, Float> TotaleCol;
    @FXML
    private TableColumn<Spesa, String> StatoCol;
    @FXML
    private TableColumn<Spesa, Integer> IDCol;
    @FXML
    private TableView<Spesa> tableView;
    private Stage stage;

    // TODO: make static
    public void showView(Stage stage) throws IOException {

        FXMLLoader loader = new FXMLLoader(ListaSpeseController.class.getResource("/views/lista-spese.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Lista Spese");
        stage.show();
        ListaSpeseController listaspesecontroller = loader.getController();

        spese = FXCollections.observableArrayList();
        spese.add(new Spesa(1, "10/09/20", "10:00 - 14:00", "utente", 100, Pagamento.PAYPAL, StatoSpesa.CONSEGNATA));
        spese.add(new Spesa(2, "10/09/20", "10:00 - 14:00", "utente", 100, Pagamento.PAYPAL, StatoSpesa.CONFERMATA));

        listaspesecontroller.IDCol.setCellValueFactory(new PropertyValueFactory<>("ID"));

        listaspesecontroller.stage = stage;
        listaspesecontroller.DataCol.setCellValueFactory(cellData -> cellData.getValue().nameProperty());

        // Dovrebbe dare un combobox in tabella...
        stato = FXCollections.observableArrayList();
        stato.add(StatoSpesa.CONFERMATA.getStatoSpesa());
        stato.add(StatoSpesa.INPREPARAZIONE.getStatoSpesa());
        stato.add(StatoSpesa.CONSEGNATA.getStatoSpesa());

        listaspesecontroller.StatoCol.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        listaspesecontroller.StatoCol.setCellFactory(ComboBoxTableCell.forTableColumn(new DefaultStringConverter(),
                stato));
        listaspesecontroller.StatoCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Spesa, String>>() {

            // TODO: Handler ComboBox
            @Override
            public void handle(TableColumn.CellEditEvent<Spesa, String> event) {
                System.out.println(event.getNewValue());
            }
        });
        listaspesecontroller.tableView.setItems(spese);

    }
}
