package org.example.client.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import okhttp3.Response;
import org.example.client.models.Category;
import org.example.client.models.Product;
import org.example.client.utils.Session;
import org.example.client.utils.Utils;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CatalogController {

    public static ObservableList<String> categoryList;
    public ChoiceBox CbxColumn;
    public TextField searchBar;
    public ListView<String> listCategory;
    //public GridPane gridpane;
    public FlowPane flowpane;
    // prodotti Test
    ObservableList<Product> products = FXCollections.observableArrayList(
            new Product(1, "Pasta n10", "Barilla", 1,
                    1.72, "https://images.services.esselunga.it/html/img_prodotti/esselunga/image/965627.jpg", 1,
                    "Pasta", "Alimenti"),
            new Product(2, "Formaggio grattuggiato", "Parmiggiano Reggiano", 1,
                    3.50, "https://images.services.esselunga.it/html/img_prodotti/esselunga/image/934922.jpg", 1,
                    "Formaggi", "Alimenti"),
            new Product(1, "Pasta n10", "Barilla", 1,
                    1.72, "https://images.services.esselunga.it/html/img_prodotti/esselunga/image/965627.jpg", 1,
                    "Pasta", "Alimenti"),
            new Product(1, "Pasta n10", "Barilla", 1,
                    1.72, "https://images.services.esselunga.it/html/img_prodotti/esselunga/image/965627.jpg", 1,
                    "Pasta", "Frutta e verdura"),
            new Product(1, "Pasta n10", "Barilla", 1,
                    1.72, "https://images.services.esselunga.it/html/img_prodotti/esselunga/image/965627.jpg", 1,
                    "Pasta", "Alimenti"),
            new Product(1, "Pasta n10", "Barilla", 1,
                    1.72, "https://images.services.esselunga.it/html/img_prodotti/esselunga/image/965627.jpg", 1,
                    "Pasta", "Alimenti"),
            new Product(1, "Pasta n10", "Barilla", 1,
                    1.72, "https://images.services.esselunga.it/html/img_prodotti/esselunga/image/965627.jpg", 1,
                    "Pasta", "Alimenti"),
            new Product(1, "Pasta n10", "Barilla", 1,
                    1.72, "https://images.services.esselunga.it/html/img_prodotti/esselunga/image/965627.jpg", 1,
                    "Pasta", "Alimneti"),
            new Product(1, "Pasta n10", "Barilla", 1,
                    1.72, "https://images.services.esselunga.it/html/img_prodotti/esselunga/image/965627.jpg", 1,
                    "Pasta", "Alimenti"));
    private Map<String, ArrayList<Product>> sectionMap = new HashMap<>();
    private Stage stage;

    public static void showView(Stage stage) {
        FXMLLoader loader = new FXMLLoader(CatalogController.class.getResource("/views/catalog.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert root != null;
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Catalogo");
        stage.show();
        CatalogController catalogController = loader.getController();

        catalogController.catalogFactory(
                catalogController.listCategory.getSelectionModel().getSelectedItems().toString(),
                catalogController.searchBar.getText(), catalogController.sectionMap);

        catalogController.setStage(stage);
    }

    @FXML
    public void initialize() {

        Task<Void> task = new Task<>() {
            @Override
            protected Void call() throws Exception {
                Session session = Session.getInstance();
                Response response = Utils.getAllProducts(session.getUser().getSession());
                if (response != null && response.code() == 200 && response.body() != null) {
                    JSONObject json = new JSONObject(Objects.requireNonNull(response.body()).string());
                    JSONArray products = json.getJSONArray("products");

                    // Crea mappa prodotti - sezione
                    for (int i = 0; i < products.length(); i++) {
                        Product product = new Product(products.getJSONObject(i));
                        if (!sectionMap.containsKey(product.getSection())) {
                            sectionMap.put(product.getSection(), new ArrayList<>());
                        }
                        sectionMap.get(product.getSection()).add(product);
                    }
                    categoryList = FXCollections.observableArrayList(sectionMap.keySet());
                    listCategory.setItems(categoryList);
                    listCategory.getSelectionModel().selectFirst();
                }

                return null;

            }


        };

        new Thread(task).start();

        /*this.listCategory.setOnMouseClicked(event -> {
            Category category =
                    Category.fromString(listCategory.getSelectionModel().getSelectedItems().toString());
        });*/


    }

    // Card builder
    public void catalogFactory(String category, String search, Map<String, ArrayList<Product>> sectionMap) {

        // Creo la lista di figli del flow pane
        String newSearch = search.trim().toLowerCase();
        ObservableList<Node> list = flowpane.getChildren();
        list.clear();


        for(Product product : sectionMap.get(category)){
            if (product.getName().contains(newSearch)){
                try {
                    list.add(CardController.generateCard(product));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        //VBox card = null;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void handlerLogutAction(ActionEvent actionEvent) {
    }

    public void handleBackAction(ActionEvent actionEvent) {
    }

    public void searchHandler(ActionEvent actionEvent) {
        catalogFactory(this.listCategory.getSelectionModel().getSelectedItems().toString(), this.searchBar.getText(),
                this.sectionMap);
    }

    public void backhandler(MouseEvent mouseEvent) {
    }

    public void viewCartHandler(MouseEvent mouseEvent) {
    }

    public void changeCategoryHandler(MouseEvent mouseEvent) {
        catalogFactory(this.listCategory.getSelectionModel().getSelectedItems().toString(), this.searchBar.getText(),
                this.sectionMap);
    }
}
