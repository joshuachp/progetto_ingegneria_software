package org.example.client.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.util.Callback;
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
    private TableColumn <Spesa, Button> VisualizzaCol;
    @FXML
    private TableColumn<Spesa, String> DataCol;
    @FXML
    private TableColumn<Spesa, String> OraCol;
    @FXML
    private TableColumn<Spesa, Pagamento> MetodoPagamentoCol;
    @FXML
    private TableColumn<Spesa, Float> TotaleCol;
    @FXML
    private TableColumn<Spesa, StatoSpesa> StatoCol;
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

        // List Demo
        spese = FXCollections.observableArrayList();
        spese.add(new Spesa(123, "12/09/20", "10:00 - 14:00", "utente", 100, Pagamento.PAYPAL, StatoSpesa.CONSEGNATA));
        spese.add(new Spesa(256, "10/09/20", "10:00 - 14:00", "utente", 100, Pagamento.PAYPAL, StatoSpesa.CONFERMATA));

        // Create setCellFactory for IDCol with link to list of product
        listaspesecontroller.IDCol.setCellFactory(new Callback<TableColumn<Spesa, Integer>, TableCell<Spesa, Integer>>() {
            @Override
            public TableCell<Spesa, Integer> call(TableColumn<Spesa, Integer> col) {
                final TableCell<Spesa, Integer> cell = new TableCell<Spesa, Integer>() {
                    @Override
                    public void updateItem(Integer ID, boolean empty) {
                        super.updateItem(ID, empty);
                        if (empty) {
                            setText(null);
                        } else {
                            setText(String.valueOf(ID));
                            setTextFill(Paint.valueOf("0066FF"));
                            setCursor(Cursor.HAND);
                            setUnderline(true);
                        }
                    }
                };
                cell.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        if (event.getClickCount() > 0) {
                            System.out.println("double click on "+cell.getItem());
                        }
                    }
                });
                return cell ;
            }
        });
        // Setting-up data colums
        listaspesecontroller.IDCol.setCellValueFactory(new PropertyValueFactory<>("ID"));
        listaspesecontroller.DataCol.setCellValueFactory(cellData -> cellData.getValue().dateProperty());

        // Setting-up checkBox state
        stato = FXCollections.observableArrayList();
        stato.add(StatoSpesa.CONFERMATA);
        stato.add(StatoSpesa.INPREPARAZIONE);
        stato.add(StatoSpesa.CONSEGNATA);

        listaspesecontroller.StatoCol.setCellValueFactory(new PropertyValueFactory("StatoSpesa"));
        listaspesecontroller.StatoCol.setCellFactory(ComboBoxTableCell.forTableColumn(stato));
        // TODO: Handler ComboBox
        listaspesecontroller.StatoCol.setOnEditCommit(event -> event.getRowValue().setStatoSpesa(StatoSpesa.valueOf(event.getNewValue().toString())));

        listaspesecontroller.tableView.setItems(spese);

        listaspesecontroller.setStage(stage);

        Task<Void> task = new Task<>() {
            @Override
            protected Void call() throws Exception {
                sleep(10000);
                spese.add(new Spesa(3, "10/09/20", "10:00 - 14:00", "utente", 100, Pagamento.PAYPAL,
                        StatoSpesa.CONFERMATA));
                return null;
            }
        };

        Thread th = new Thread(task);
        th.start();

    }

    private void addButtonToTable() {

    }

    private void setStage(Stage stage) {
        this.stage = stage;
    }


    public void viewProductOrder () {

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

    public void handleBackAction(ActionEvent actionEvent) throws IOException {
        SceltaModalitaController sceltaModalitaController = new SceltaModalitaController();
        SceltaModalitaController.showView(this.stage);
    }

    public void handlerAddManagerAction(ActionEvent actionEvent) {
    }

    public void handlerLogutAction(ActionEvent actionEvent) {
    }
}
