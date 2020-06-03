package org.example.client.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
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
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.converter.DefaultStringConverter;
import org.example.client.models.Pagamento;
import org.example.client.models.Spesa;
import org.example.client.models.StatoSpesa;

import java.io.IOException;

import static java.lang.Thread.sleep;

public class ListaSpeseController {

    public ListView<Spesa> listview;
    public ObservableList<StatoSpesa> stato;
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
    private TableView<Spesa> tableview;
    private Stage stage;

    public void showView(Stage stage) throws IOException {

        FXMLLoader loader = new FXMLLoader(ListaSpeseController.class.getResource("/views/listaSpese.fxml"));
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
        listaspesecontroller.DataCol.setCellValueFactory(cellData -> cellData.getValue().dateProperty());

        // Dovrebbe dare un combobox in tabella...
        stato = FXCollections.observableArrayList();
        stato.add(StatoSpesa.CONFERMATA);
        stato.add(StatoSpesa.INPREPARAZIONE);
        stato.add(StatoSpesa.CONSEGNATA);

        listaspesecontroller.StatoCol.setCellValueFactory(new PropertyValueFactory("StatoSpesa"));
        listaspesecontroller.StatoCol.setCellFactory(ComboBoxTableCell.forTableColumn(stato));
        // TODO: Handler ComboBox
        listaspesecontroller.StatoCol.setOnEditCommit(event -> event.getRowValue().setStatoSpesa(StatoSpesa.valueOf(event.getNewValue().toString())));

        listaspesecontroller.tableview.setItems(spese);

        Task<Void> task = new Task<>(){
            @Override
            protected Void call() throws Exception {
                sleep(10000);
                spese.add(new Spesa(3, "10/09/20", "10:00 - 14:00", "utente", 100, Pagamento.PAYPAL, StatoSpesa.CONFERMATA ));
                return null;
            }
        };

        Thread th = new Thread(task);
        th.start();

    }


    public void handleViewProducts(MouseEvent mouseEvent) {
        FXMLLoader loader = new FXMLLoader(AutenticazioneController.class.getResource("/views/autenticazione.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert root != null;
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Lista prodotti Spesa");
        stage.show();
        AutenticazioneController autenticazioneController = loader.getController();
        autenticazioneController.setStage(stage);

    }
}
