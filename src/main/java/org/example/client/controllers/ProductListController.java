package org.example.client.controllers;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.converter.IntegerStringConverter;
import org.example.client.models.Product;
import org.example.client.utils.Session;
import org.example.client.utils.Utils;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductListController {

    private final ObservableList<Product> products = FXCollections.observableArrayList();
    public TextField searchField;
    public TableView<Product> tableView;
    public TableColumn<Product, Integer> idCol;
    public TableColumn<Product, String> nameCol;
    public TableColumn<Product, String> brandCol;
    public TableColumn<Product, String> characteristicsCol;
    public TableColumn<Product, String> priceCol;
    public TableColumn<Product, Integer> availabilityCol;
    public Button buttonAddProduct;
    @FXML
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

    public void refresh() {
        String session = Session.getInstance().getUser().getSession();
        Task<List<Product>> task = new Task<>() {
            @Override
            protected List<Product> call() {
                JSONObject json;
                try {
                    json = Utils.getAllProducts(session);
                } catch (Exception e) {
                    e.printStackTrace();
                    failed();
                    return null;
                }
                ArrayList<Product> list = new ArrayList<>();
                // Test for products array
                if (json.has("products")) {
                    for (Object t : json.getJSONArray("products")) {
                        list.add(new Product((JSONObject) t));
                    }
                }
                return list;
            }
        };
        task.setOnSucceeded(event -> {
            this.products.clear();
            this.products.addAll(task.getValue());
        });
        new Thread(task).start();

        products.clear();
    }

    public void initialize() {
        refresh();

        // Clickable link for ID column
        this.idCol.setCellFactory(new Callback<>() {
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

                cell.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                    if (event.getClickCount() > 0) {
                        handleModifyProduct(cell.getTableRow().getItem());
                    }
                });
                return cell;
            }
        });

        this.tableView.setEditable(true); // To allow modify quantity directly in tableView
        // Columns initialization
        this.idCol.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getId()));
        this.nameCol.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getName()));
        this.brandCol.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getBrand()));
        this.characteristicsCol.setCellValueFactory(param ->
                new SimpleStringProperty(param.getValue().getCharacteristics())
        );
        this.priceCol.setCellValueFactory(
                param -> new SimpleStringProperty(String.format("€ %.2f", param.getValue().getPrice()))
        );
        this.availabilityCol.setCellValueFactory(
                param -> new SimpleObjectProperty<>(param.getValue().getAvailability())
        );
        this.availabilityCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        this.availabilityCol.setEditable(true);
        this.availabilityCol.setOnEditCommit(event -> {
            Product product = event.getRowValue();
            Session session = Session.getInstance();
            product.setAvailability(event.getNewValue());
            try {
                Utils.updateProduct(session.getUser().getSession(), product.getId(), product);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        FilteredList<Product> filteredList = new FilteredList<>(products, p -> true);
        this.searchField.textProperty().addListener((observable, oldValue, newValue) ->
                filteredList.setPredicate(value -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String search = newValue.toLowerCase().trim();
                    return value.getId().toString().contains(search) ||
                            value.getName().toLowerCase().contains(search) ||
                            value.getPrice().toString().contains(search) ||
                            value.getBrand().toLowerCase().contains(search) ||
                            value.getSection().toLowerCase().contains(search) ||
                            value.getCharacteristics().toLowerCase().contains(search);
                }));

        // Wrap the FilteredList in a SortedList.
        SortedList<Product> sortedData = new SortedList<>(filteredList);
        // Bind the SortedList comparator to the TableView comparator.
        sortedData.comparatorProperty().bind(this.tableView.comparatorProperty());

        this.tableView.setItems(sortedData);
    }

    private void handleModifyProduct(Product product) {
        ManageProductController.showView(this.stage, product, true);
        refresh();
    }

    private void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void handlerAddProduct() {
        ManageProductController.showView(this.stage, null, false);
        refresh();
    }

    @FXML
    public void handleBackAction() {
        ChoiceModeController.showView(this.stage);
    }

    @FXML
    public void handlerLogoutAction() {
        Utils.logOut(this.stage);
    }

    @FXML
    public void handleRemoveProducts() {
        if (tableView.getSelectionModel().getSelectedItem() != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText(String.format(
                    "The operation is irreversible.\nAre you sure to delete product ID %d? ",
                    tableView.getSelectionModel().getSelectedItem().getId())
            );
            alert.setTitle("Remove products");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                Session session = Session.getInstance();
                try {
                    Utils.deleteProduct(session.getUser().getSession(),
                            tableView.getSelectionModel().getSelectedItem().getId());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No product selected");
            alert.setContentText("Please, select product to remove.");
            alert.showAndWait();
        }

        refresh();
    }

}
