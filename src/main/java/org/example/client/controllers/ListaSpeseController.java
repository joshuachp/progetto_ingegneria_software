package org.example.client.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
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
import org.example.client.models.Prodotto;
import org.example.client.models.Spesa;
import org.example.client.models.StatoSpesa;

import java.io.IOException;
import java.util.Objects;

import static java.lang.Thread.sleep;

public class ListaSpeseController {

    public ObservableList<String> columnFilterString;
    public ObservableList<String> stato;
    public ObservableList<Spesa> spese;

    @FXML
    private ChoiceBox<String> CbxColumn;
    @FXML
    private TextField Search;
    @FXML
    private TableColumn<Spesa, String> DataCol;
    @FXML
    private TableColumn<Spesa, String> OraCol;
    @FXML
    private TableColumn<Spesa, String> MetodoPagamentoCol;
    @FXML
    private TableColumn<Spesa, Double> TotaleCol;
    @FXML
    private TableColumn<Spesa, String> StatoCol;
    @FXML
    private TableColumn<Spesa, Integer> IDCol;
    @FXML
    private TableView<Spesa> tableView;
    private Stage stage;

    // Search filter enum
    public enum columnFilterEnum{
        ID("Id"), DATE ("Data"), HOUR ("Ora"), PAYMENT ("Tipo pagamento"), STATE ("Stato ordine");

        private final String column;

        columnFilterEnum(final String column) {
            this.column = column;
        }

        public String toString() {
            return column;
        }

        public static columnFilterEnum fromString(String text) {
            for (columnFilterEnum x : columnFilterEnum.values()) {
                if (x.column.equalsIgnoreCase(text)) {
                    return x;
                }
            }
            return null;
        }
    }

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
        spese.add(new Spesa(123, "12/09/20", "10:00 - 14:00", "utente", 100.0, Pagamento.PAYPAL,
                StatoSpesa.CONSEGNATA));
        spese.add(new Spesa(256, "10/09/20", "10:00 - 14:00", "utente", 100.0, Pagamento.PAYPAL,
                StatoSpesa.CONFERMATA));

        // Create setCellFactory for IDCol with link to list of product
        listaspesecontroller.IDCol.setCellFactory(new Callback<TableColumn<Spesa, Integer>, TableCell<Spesa, Integer>>() {
            @Override
            public TableCell<Spesa, Integer> call(TableColumn<Spesa, Integer> col) {
                final TableCell<Spesa, Integer> cell = new TableCell<>() {
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
                            // TODO: view ordered products list
                            System.out.println("double click on " + cell.getItem());
                            try {
                                handleViewIdProductList(cell.getItem());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
                return cell ;
            }
        });
        // Initializing table colums
        listaspesecontroller.IDCol.setCellValueFactory(cellData -> cellData.getValue().propertyID().asObject()); // asObject needed by FX implementation
        listaspesecontroller.DataCol.setCellValueFactory(cellData -> cellData.getValue().propertyDataConsegna());
        //listaspesecontroller.IDCol.setCellValueFactory(new PropertyValueFactory<>("DataConsegna"));
        listaspesecontroller.OraCol.setCellValueFactory(cellData -> cellData.getValue().propertyOraConsegna());
        listaspesecontroller.MetodoPagamentoCol.setCellValueFactory(cellData -> cellData.getValue().propertyPagamento());
        listaspesecontroller.TotaleCol.setCellValueFactory(new PropertyValueFactory<>("CostoTotale"));

        // Search with filter
        columnFilterString = FXCollections.observableArrayList(
                columnFilterEnum.ID.toString(),
                columnFilterEnum.HOUR.toString(),
                columnFilterEnum.DATE.toString(),
                columnFilterEnum.PAYMENT.toString(),
                columnFilterEnum.STATE.toString());
        listaspesecontroller.CbxColumn.setItems(columnFilterString);
        listaspesecontroller.CbxColumn.setValue(columnFilterEnum.ID.toString());

        FilteredList<Spesa> flspese = new FilteredList<>(spese, p -> true);

        listaspesecontroller.Search.setOnKeyReleased(keyEvent ->
        {
            //Switch on choiceBox value
            switch (Objects.requireNonNull(columnFilterEnum.fromString(listaspesecontroller.CbxColumn.getValue())))
            {
                case ID:
                    flspese.setPredicate(p -> p.getID().toString().contains(listaspesecontroller.Search.getText().trim()));
                    break;
                case DATE:
                    flspese.setPredicate(p -> p.getDataConsegna().contains(listaspesecontroller.Search.getText().trim()));
                    break;
                case STATE:
                    flspese.setPredicate(p -> p.getStatoSpesa().toString().contains(listaspesecontroller.Search.getText().trim()));
                    break;
                case HOUR:
                    flspese.setPredicate(p -> p.getOraConsegna().contains(listaspesecontroller.Search.getText().trim()));
                    break;
                case PAYMENT:
                    // TODO: toLowerCase dosn't work
                    flspese.setPredicate(p -> p.getPagamento().toString().toLowerCase().contains(listaspesecontroller.Search.getText().toLowerCase().trim()));
                    break;
            }
        });

        // Setting-up checkBox state
        stato = FXCollections.observableArrayList(StatoSpesa.CONFERMATA.toString(),
                StatoSpesa.INPREPARAZIONE.toString(),StatoSpesa.CONSEGNATA.toString());

        listaspesecontroller.StatoCol.setCellValueFactory(cellData -> cellData.getValue().propertyStatoSpesa());
        listaspesecontroller.StatoCol.setCellFactory(ComboBoxTableCell.forTableColumn(stato));
        listaspesecontroller.StatoCol.setEditable(true);
        listaspesecontroller.StatoCol.setOnEditCommit(event->{
            event.getRowValue().setStatoSpesa(StatoSpesa.fromString(event.getNewValue()));
            // TODO: update server
            System.out.println(event.getRowValue().getStatoSpesa());
        });

        // Wrap the FilteredList in a SortedList.
        SortedList<Spesa> sortedData = new SortedList<>(flspese);
        // Bind the SortedList comparator to the TableView comparator.
        sortedData.comparatorProperty().bind(listaspesecontroller.tableView.comparatorProperty());

        listaspesecontroller.tableView.setItems(sortedData);

        listaspesecontroller.setStage(stage);

        Task<Void> task = new Task<>() {
            @Override
            protected Void call() throws Exception {
                sleep(10000);
                spese.add(new Spesa(3, "10/09/20", "10:00 - 14:00", "utente", 100.72, Pagamento.PAYPAL,
                        StatoSpesa.CONFERMATA));
                return null;
            }
        };

        Thread th = new Thread(task);
        th.start();

    }

    private void handleViewIdProductList(Integer id) throws IOException {
        OrderProductListController orderProductList =  new OrderProductListController();
        orderProductList.showView(id);
    }

    private void setStage(Stage stage) {
        this.stage = stage;
    }

    public void handleBackAction(ActionEvent actionEvent) throws IOException {
        SceltaModalitaController.showView(this.stage);
    }

    public void handlerAddManagerAction(ActionEvent actionEvent) {
    }

    public void handlerLogutAction(ActionEvent actionEvent) {
    }
}
