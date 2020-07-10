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
import jdk.jshell.execution.Util;
import okhttp3.Response;
import org.example.client.models.Product;
import org.example.client.utils.Session;
import org.example.client.utils.Utils;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;

public class ProductListController {

    public ChoiceBox<String> CbxColumn;
    public TextField Search;
    public TableView<Product> tableview;
    public TableColumn<Product, Integer> IDCol;
    public TableColumn<Product, String> NameCol;
    public TableColumn<Product, String> QuantityCol;
    public TableColumn<Product, Float> PriceCol;
    public TableColumn<Product, String> BrandCol;
    public Button buttonimportproductlist;
    public Button buttonaddproduct;

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
        productListController.setStage(stage);
    }

    public void initialize() throws IOException {

        // TODO: get products from server
        String session = Session.getInstance().getUser().getSession();
        Response response = Utils.getAllProducts(session);
        ObservableList<Product> products = FXCollections.observableArrayList();
        if (response != null) {
            if (response.code() == 200 && response.body() != null) {
                JSONObject json = new JSONObject(Objects.requireNonNull(response.body()).string());
                Objects.requireNonNull(response.body()).close();
                // Test for products array
                assert json.has("products");
                for (Object t : json.getJSONArray("products")) {
                    JSONObject jsonProduct = (JSONObject) t;
                    Product product = new Product((JSONObject)t);
                    products.add(product);
                }
            }
        }
        // Test products
        /*ObservableList<Product> products = FXCollections.observableArrayList(

                new Product(123, "Prova", "Test", 2, (float) 12.50, null,
                        3, "Vegan", "Verdura"));*/

        // Clickable link for ID column
        this.IDCol.setCellFactory(new Callback<TableColumn<Product, Integer>,
                TableCell<Product, Integer>>() {
            @Override
            public TableCell<Product, Integer> call(TableColumn<Product, Integer> col) {
                final TableCell<Product, Integer> cell = new TableCell<>() {
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

        this.tableview.setEditable(true); // To allow modify quantity directly in tableview
        // Columns initialization
        this.IDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        this.NameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        this.BrandCol.setCellValueFactory(new PropertyValueFactory<>("brand"));
        this.PriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        this.QuantityCol.setCellValueFactory(new PropertyValueFactory<>("availability"));
        this.QuantityCol.setCellFactory(TextFieldTableCell.forTableColumn());
        this.QuantityCol.setEditable(true);
        this.QuantityCol.setOnEditCommit(event -> {
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

        this.CbxColumn.setItems(columnFilterString);
        this.CbxColumn.setValue(ListaSpeseController.columnFilterEnum.ID.toString());

        FilteredList<Product> flproducts = new FilteredList<>(products, p -> true);

        this.Search.setOnKeyReleased(keyEvent ->
        {
            // Switch on choiceBox value
            switch (Objects.requireNonNull(columnFilterEnum.fromString(this.CbxColumn.getValue()))) {
                case ID:
                    flproducts.setPredicate(p -> p.getId().toString().contains(this.Search.getText().trim()));
                    break;
                case NAME:
                    flproducts.setPredicate(p -> p.getName().toLowerCase().contains(this.Search.getText().toLowerCase().trim()));
                    break;
                case BRAND:
                    flproducts.setPredicate(p -> p.getBrand().toLowerCase().contains(this.Search.getText().toLowerCase().trim()));
                    break;
                case PRICE:
                    flproducts.setPredicate(p -> p.getPrice().toString().contains(this.Search.getText().trim()));
                    break;
                case QUANTITY:
                    flproducts.setPredicate(p -> p.getAvailability().toString().contains(this.Search.getText().trim()));
                    break;
            }
        });

        // Wrap the FilteredList in a SortedList.
        SortedList<Product> sortedData = new SortedList<>(flproducts);
        // Bind the SortedList comparator to the TableView comparator.
        sortedData.comparatorProperty().bind(this.tableview.comparatorProperty());

        this.tableview.setItems(sortedData);

        this.setStage(stage);

    }

    private static void handleModifyProduct(Product product, boolean modify) throws IOException {
        GestioneProdottiController gestioneProdottiController = new GestioneProdottiController();
        Stage stage = new Stage();
        gestioneProdottiController.showView(stage, product, modify);
    }

    private void setStage(Stage stage) {
        this.stage = stage;
    }

    public void handlerAddProduct(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        GestioneProdottiController.showView(stage, null, false );
    }

    public void handleBackAction(ActionEvent actionEvent) throws IOException {
        SceltaModalitaController.showView(this.stage);
    }

    public void handlerLogutAction(ActionEvent actionEvent) {
    }

    public void handlerAddManagerAction(ActionEvent actionEvent) {
    }

    public void handleModifyQuantityProduct(TableColumn.CellEditEvent<Product, String> productStringCellEditEvent) {
        // TODO: send product with
        Product product =
                productStringCellEditEvent.getRowValue().setAvailability(Integer.parseInt(productStringCellEditEvent.getNewValue()));

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
