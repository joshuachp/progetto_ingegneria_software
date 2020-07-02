package org.example.client.controllers;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import okhttp3.Response;
import org.example.client.models.Product;
import org.example.client.utils.Session;
import org.example.client.utils.Utils;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CatalogController {

    private final Map<String, ArrayList<Product>> sectionMap = new HashMap<>();

    public ObservableList<String> categoryList;
    public ListView<String> listCategory;

    @FXML
    public TextField searchBar;
    @FXML
    public FlowPane flowPaneProducts;
    @FXML
    public Text textCartQuantity;

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
        catalogController.setStage(stage);
    }

    @FXML
    public void initialize() {
        Session session = Session.getInstance();

        Task<Void> task = new Task<>() {
            @Override
            protected Void call() throws Exception {
                // Request all products
                Response response = Utils.getAllProducts(session.getUser().getSession());
                // Check for successful response
                if (response != null && response.code() == 200 && response.body() != null) {
                    JSONObject json = new JSONObject(Objects.requireNonNull(response.body()).string());
                    // Test for products array
                    assert json.has("products");
                    // For each product map it to section - array of products
                    for (Object jsonProduct : json.getJSONArray("products")) {
                        Product product = new Product((JSONObject) jsonProduct);
                        // Create a new array if section doesn't exists
                        if (!sectionMap.containsKey(product.getSection())) {
                            sectionMap.put(product.getSection(), new ArrayList<>());
                        }
                        sectionMap.get(product.getSection()).add(product);
                    }
                    // Run on application thread
                    Platform.runLater(() -> {
                        categoryList = FXCollections.observableArrayList(sectionMap.keySet());
                        listCategory.setItems(categoryList);
                        listCategory.getSelectionModel().selectFirst();
                        catalogFactory(listCategory.getSelectionModel().getSelectedItem(), searchBar.getText());
                    });
                }
                return null;
            }
        };
        new Thread(task).start();

        this.textCartQuantity.textProperty().bind(session.getCartQuantity().asString());
    }

    // Card builder
    public void catalogFactory(String category, String search) {

        // Creo la lista di figli del flow pane
        String newSearch = search.trim().toLowerCase();
        ObservableList<Node> list = flowPaneProducts.getChildren();
        list.clear();

        // Controllo la lista dela sezione
        assert this.sectionMap.containsKey(category);
        for (Product product : this.sectionMap.get(category)) {
            if (newSearch.isEmpty() || product.getName().contains(newSearch)) {
                list.add(CardController.generateCard(product));
            }
        }
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void handlerLogoutAction(ActionEvent actionEvent) {
    }


    public void searchHandler(ActionEvent actionEvent) {
        catalogFactory(this.listCategory.getSelectionModel().getSelectedItem(),
                this.searchBar.getText());
    }


    public void changeCategoryHandler(MouseEvent mouseEvent) {
        catalogFactory(this.listCategory.getSelectionModel().getSelectedItem(),
                this.searchBar.getText());
    }

    @FXML
    public void handleCartAction() {
        CartController.showView(stage);
    }
}
