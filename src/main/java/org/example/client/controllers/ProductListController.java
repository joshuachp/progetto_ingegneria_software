package org.example.client.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.example.client.models.Prodotto;

import java.io.IOException;
import java.util.Objects;

public class ProductListController {

    public ChoiceBox<String> CbxColumn;
    public TextField Search;
    public TableView<Prodotto> tableview;
    public TableColumn<Prodotto, Integer> IDCol;
    public TableColumn<Prodotto, String> NameCol;
    public TableColumn<Prodotto, String> QuantityCol;
    public Button buttonimportproductlist;
    public Button buttonaddproduct;
    public TableColumn<Prodotto, Double> PriceCol;
    public TableColumn<Prodotto, String> BrandCol;
    private Stage stage;

    // View generation
    public static void showView(Stage stage) {
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
        ProductListController productListController = loader.getController();
        // TODO: get products from server
        // Test products
        ObservableList<Prodotto> products = FXCollections.observableArrayList(
                new Prodotto(1, "Pasta n10", "Barilla", 1,
                        1.72, "C:\\Users\\david\\Pictures\\Saved Pictures\\34779.jpg", 1, "Pasta", "Alimneti"),
                new Prodotto(2, "Formaggio grattuggiato", "Parmiggiano Reggiano", 1,
                        3.50, "prova", 1, "Formaggi", "Alimneti"));

        // Clickable link for ID column
        productListController.IDCol.setCellFactory(new Callback<TableColumn<Prodotto, Integer>,
                TableCell<Prodotto, Integer>>() {
            @Override
            public TableCell<Prodotto, Integer> call(TableColumn<Prodotto, Integer> col) {
                final TableCell<Prodotto, Integer> cell = new TableCell<>() {
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
                            // TODO: view product form
                            System.out.println("double click on " + cell.getItem());
                            try {
                                handleModifyProduct(cell.getTableRow().getItem(), true);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
                return cell;
            }
        });

        productListController.tableview.setEditable(true); // To allow modify quantity directly in tableview
        // Columns initialization
        productListController.IDCol.setCellValueFactory(new PropertyValueFactory<>("ID"));
        productListController.NameCol.setCellValueFactory(new PropertyValueFactory<>("Name"));
        productListController.BrandCol.setCellValueFactory(new PropertyValueFactory<>("Brand"));
        productListController.PriceCol.setCellValueFactory(new PropertyValueFactory<>("Price"));
        productListController.QuantityCol.setCellValueFactory(new PropertyValueFactory<>("Availability"));
        productListController.QuantityCol.setCellFactory(TextFieldTableCell.forTableColumn());
        productListController.QuantityCol.setEditable(true);
        productListController.QuantityCol.setOnEditCommit(event -> {
            event.getRowValue().setAvailability(Integer.parseInt(event.getNewValue()));
            // TODO: implements server update on edit
            System.out.println(event.getRowValue().getAvailability());
        });

        // Setting-up filter search
        ObservableList<String> columnFilterString = FXCollections.observableArrayList(
                columnFilterEnum.ID.toString(),
                columnFilterEnum.NAME.toString(),
                columnFilterEnum.BRAND.toString(),
                columnFilterEnum.PRICE.toString(),
                columnFilterEnum.QUANTITY.toString());

        productListController.CbxColumn.setItems(columnFilterString);
        productListController.CbxColumn.setValue(ListaSpeseController.columnFilterEnum.ID.toString());

        FilteredList<Prodotto> flproducts = new FilteredList<>(products, p -> true);

        productListController.Search.setOnKeyReleased(keyEvent ->
        {
            // Switch on choiceBox value
            switch (Objects.requireNonNull(ProductListController.columnFilterEnum.fromString(productListController.CbxColumn.getValue()))) {
                case ID:
                    flproducts.setPredicate(p -> p.getID().toString().contains(productListController.Search.getText().trim()));
                    break;
                case NAME:
                    flproducts.setPredicate(p -> p.getName().toLowerCase().contains(productListController.Search.getText().toLowerCase().trim()));
                    break;
                case BRAND:
                    flproducts.setPredicate(p -> p.getBrand().toLowerCase().contains(productListController.Search.getText().toLowerCase().trim()));
                    break;
                case PRICE:
                    flproducts.setPredicate(p -> p.getPrice().toString().contains(productListController.Search.getText().trim()));
                    break;
                case QUANTITY:
                    flproducts.setPredicate(p -> p.getAvailability().toString().contains(productListController.Search.getText().trim()));
                    break;
            }
        });

        // Wrap the FilteredList in a SortedList.
        SortedList<Prodotto> sortedData = new SortedList<>(flproducts);
        // Bind the SortedList comparator to the TableView comparator.
        sortedData.comparatorProperty().bind(productListController.tableview.comparatorProperty());

        productListController.tableview.setItems(sortedData);

        productListController.setStage(stage);
    }

    private static void handleModifyProduct(Prodotto product, boolean modify) throws IOException {
        GestioneProdottiController gestioneProdottiController = new GestioneProdottiController();
        gestioneProdottiController.showView(product, modify);
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

    // Search filter enum
    public enum columnFilterEnum {
        ID("Id"), NAME("Nome prodotto"), BRAND("Nome brand"), PRICE("Prezzo"), QUANTITY("Quantità");

        private final String column;

        columnFilterEnum(final String column) {
            this.column = column;
        }

        public static ProductListController.columnFilterEnum fromString(String text) {
            for (ProductListController.columnFilterEnum x : ProductListController.columnFilterEnum.values()) {
                if (x.column.equalsIgnoreCase(text)) {
                    return x;
                }
            }
            return null;
        }

        public String toString() {
            return column;
        }
    }
}
