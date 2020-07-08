package org.example.client.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import org.example.client.components.CatalogFactory;
import org.example.client.models.Product;
import org.example.client.tasks.TaskCatalog;
import org.example.client.utils.Session;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private List<Node> catalogNodes;

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

        TaskCatalog taskCatalog = new TaskCatalog(this.sectionMap, this.categoryList, this.stage, this.searchBar);
        taskCatalog.setOnSucceeded((event -> {
            // Set section map to the task value
            this.catalogNodes = taskCatalog.getValue();

            // Run on application thread
            this.listCategory.getSelectionModel().selectFirst();

            // Add all nodes
            ObservableList<Node> list = this.flowPaneProducts.getChildren();
            list.addAll(this.catalogNodes);
        }));
        new Thread(taskCatalog).start();

        // Bind badge with cart total quantity
        this.textCartQuantity.textProperty().bind(session.getCartQuantity().asString());
    }

    // Card builder
    public void refreshProducts() {
        ObservableList<Node> list = this.flowPaneProducts.getChildren();
        list.removeAll(this.catalogNodes);
        this.catalogNodes = new CatalogFactory().getCatalogList(this.stage, this.sectionMap,
                this.listCategory.getSelectionModel().getSelectedItem(), this.searchBar.getText());
        list.addAll(this.catalogNodes);
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
