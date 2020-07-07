package org.example.client.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import okhttp3.Response;
import org.example.client.components.CatalogFactory;
import org.example.client.models.Product;
import org.example.client.utils.Session;
import org.example.client.utils.Utils;
import org.json.JSONObject;

import java.io.IOException;
import java.util.*;

public class CatalogController {

    public final static String SECTION_ALL = "All";

    private final Map<String, ArrayList<Product>> sectionMap = new HashMap<>();

    public ObservableList<String> categoryList = FXCollections.observableArrayList();
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
        stage.setTitle("Catalog");
        stage.show();
        CatalogController catalogController = loader.getController();
        catalogController.setStage(stage);
    }

    @FXML
    public void initialize() {
        Session session = Session.getInstance();
        this.listCategory.setItems(this.categoryList);
        this.sectionMap.put(SECTION_ALL, new ArrayList<>());

        Task<List<Node>> task = new Task<>() {
            @Override
            protected List<Node> call() throws Exception {
                // Request all products
                Response response = Utils.getAllProducts(session.getUser().getSession());
                // Check for successful response
                if (response != null) {
                    if (response.code() == 200 && response.body() != null) {
                        JSONObject json = new JSONObject(Objects.requireNonNull(response.body()).string());
                        Objects.requireNonNull(response.body()).close();
                        // Test for products array
                        assert json.has("products");

                        // For each product map it to section - array of products
                        for (Object t : json.getJSONArray("products")) {
                            JSONObject jsonProduct = (JSONObject) t;

                            // Get a reference to an existing product in the cart or create a new one, because we need
                            // the current quantity of the product in the cart
                            Product product;
                            if (session.getMapProducts().containsKey(jsonProduct.getInt("id"))) {
                                product = session.getMapProducts().get(jsonProduct.getInt("id"));
                            } else {
                                product = new Product(jsonProduct);
                            }

                            // Create a new array if section doesn't exists
                            if (!sectionMap.containsKey(product.getSection())) {
                                sectionMap.put(product.getSection(), new ArrayList<>());
                            }
                            sectionMap.get(product.getSection()).add(product);
                            // Add to section all
                            sectionMap.get(SECTION_ALL).add(product);
                        }

                        // Set category list (since doesn't change the view structure)
                        categoryList.addAll(sectionMap.keySet());
                        // The  section can not be null
                        return new CatalogFactory().getCatalogList(stage, sectionMap,
                                categoryList.size() > 0 ? categoryList.get(0) : "", searchBar.getText());
                    }
                   
                    Objects.requireNonNull(response.body()).close();
                }

                // Task failed
                failed();
                return null;
            }
        };
        task.setOnSucceeded((event -> {
            // Set section map to the task value
            List<Node> nodes = task.getValue();

            // Run on application thread
            this.listCategory.getSelectionModel().selectFirst();

            // Add all nodes
            ObservableList<Node> list = this.flowPaneProducts.getChildren();
            list.clear();
            list.addAll(nodes);
        }));
        new Thread(task).start();

        // Bind badge with cart total quantity
        this.textCartQuantity.textProperty().bind(session.getCartQuantity().asString());
    }

    // Card builder
    public void refreshProducts() {
        ObservableList<Node> list = this.flowPaneProducts.getChildren();
        list.clear();
        list.addAll(new CatalogFactory().getCatalogList(this.stage, this.sectionMap,
                this.listCategory.getSelectionModel().getSelectedItem(), this.searchBar.getText()));
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void handlerLogoutAction() {
    }


    @FXML
    public void handleSearchAction() {
        refreshProducts();
    }


    @FXML
    public void changeCategoryHandler() {
        refreshProducts();
    }

    @FXML
    public void handleCartAction() {
        CartController.showView(stage);
    }

    @FXML
    public void handleProfileAction() {
        ProfileController.showView(this.stage);
    }
}
