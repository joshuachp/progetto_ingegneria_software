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
import org.example.client.models.Prodotto;
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

public class CatalogoController {

    public static ObservableList<String> categoryList;
    public ChoiceBox CbxColumn;
    public TextField searchBar;
    public ListView<String> listCategory;
    //public GridPane gridpane;
    public FlowPane flowpane;
    // prodotti Test
    ObservableList<Prodotto> products = FXCollections.observableArrayList(
            new Prodotto(1, "Pasta n10", "Barilla", 1,
                    1.72, "https://images.services.esselunga.it/html/img_prodotti/esselunga/image/965627.jpg", 1,
                    "Pasta", "Alimenti"),
            new Prodotto(2, "Formaggio grattuggiato", "Parmiggiano Reggiano", 1,
                    3.50, "https://images.services.esselunga.it/html/img_prodotti/esselunga/image/934922.jpg", 1,
                    "Formaggi", "Alimenti"),
            new Prodotto(1, "Pasta n10", "Barilla", 1,
                    1.72, "https://images.services.esselunga.it/html/img_prodotti/esselunga/image/965627.jpg", 1,
                    "Pasta", "Alimenti"),
            new Prodotto(1, "Pasta n10", "Barilla", 1,
                    1.72, "https://images.services.esselunga.it/html/img_prodotti/esselunga/image/965627.jpg", 1,
                    "Pasta", "Frutta e verdura"),
            new Prodotto(1, "Pasta n10", "Barilla", 1,
                    1.72, "https://images.services.esselunga.it/html/img_prodotti/esselunga/image/965627.jpg", 1,
                    "Pasta", "Alimenti"),
            new Prodotto(1, "Pasta n10", "Barilla", 1,
                    1.72, "https://images.services.esselunga.it/html/img_prodotti/esselunga/image/965627.jpg", 1,
                    "Pasta", "Alimenti"),
            new Prodotto(1, "Pasta n10", "Barilla", 1,
                    1.72, "https://images.services.esselunga.it/html/img_prodotti/esselunga/image/965627.jpg", 1,
                    "Pasta", "Alimenti"),
            new Prodotto(1, "Pasta n10", "Barilla", 1,
                    1.72, "https://images.services.esselunga.it/html/img_prodotti/esselunga/image/965627.jpg", 1,
                    "Pasta", "Alimneti"),
            new Prodotto(1, "Pasta n10", "Barilla", 1,
                    1.72, "https://images.services.esselunga.it/html/img_prodotti/esselunga/image/965627.jpg", 1,
                    "Pasta", "Alimenti"));
    private Map<String, ArrayList<Prodotto>> sectionMap = new HashMap<>();
    private Stage stage;

    public static void showView(Stage stage) {
        FXMLLoader loader = new FXMLLoader(CatalogoController.class.getResource("/views/catalogo.fxml"));
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
        CatalogoController catalogoController = loader.getController();

        catalogoController.catalogFactory(catalogoController.products,
                catalogoController.listCategory,
                catalogoController.searchBar);

        catalogoController.setStage(stage);

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
                    for (int i = 0; i < products.length(); i++) {
                        Prodotto prodotto = new Prodotto(products.getJSONObject(i));
                        if (!sectionMap.containsKey(prodotto.getSection())) {
                            sectionMap.put(prodotto.getSection(), new ArrayList<>());
                        }
                        sectionMap.get(prodotto.getSection()).add(prodotto);
                    }
                    categoryList = FXCollections.observableArrayList(sectionMap.keySet());
                    listCategory.setItems(categoryList);
                    listCategory.getSelectionModel().selectFirst();
                }

                return null;

            }


        };

        new Thread(task).start();

        this.listCategory.setOnMouseClicked(event -> {
            Category category =
                    Category.fromString(listCategory.getSelectionModel().getSelectedItems().toString());
        });


    }

    // Card builder
    public void catalogFactory(@NotNull ObservableList<Prodotto> products, ListView<String> listCategory,
                               TextField searchBar) {
        // Creo la lista di figli del flow pane
        ObservableList<Node> list = flowpane.getChildren();
        list.clear();
        Category category =
                Category.fromString(listCategory.getSelectionModel().getSelectedItems().toString());
        String search = searchBar.getText();
        if (category == null)
            category = Category.ALIMENTI;
        System.out.println("card generator" + category.toString());
        // Genero i figli sulla base della categoria selezionata e del testo di ricerca nel box di ricerca
        VBox card = null;
        for (Prodotto product : products) {

            if (category == Category.fromString(product.getSection())) {
                if (product.getCharacteristics().contains(search)) {
                    try {
                        card = CardController.generateCard(product);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    list.add(card);
                    //flowpane.setMargin(card, new Insets(5));
                }
            }
        }
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void handlerLogutAction(ActionEvent actionEvent) {
    }

    public void handleBackAction(ActionEvent actionEvent) {
    }

    public void searchHandler(ActionEvent actionEvent) {
        catalogFactory(this.products, this.listCategory, this.searchBar);
    }

    public void backhandler(MouseEvent mouseEvent) {
    }

    public void viewCartHandler(MouseEvent mouseEvent) {
    }

    public void changeCategoryHandler(MouseEvent mouseEvent) {
        catalogFactory(this.products, this.listCategory, this.searchBar);
        System.out.println(Category.fromString(listCategory.getSelectionModel().getSelectedItems().toString()));
        System.out.println(listCategory.getItems().toString());
    }
}
