package org.example.client.tasks;

import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import okhttp3.Response;
import org.example.client.components.CatalogFactory;
import org.example.client.controllers.CatalogController;
import org.example.client.models.Product;
import org.example.client.models.enums.SortOrder;
import org.example.client.utils.Session;
import org.example.client.utils.Utils;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;


public class TaskCatalog extends Task<List<Node>> {

    private final ObservableList<String> categoryList;
    private final Map<String, ArrayList<Product>> sectionMap;
    private final Stage stage;
    private final TextField searchBar;
    private final SortOrder sortOrder;

    public TaskCatalog(Map<String, ArrayList<Product>> sectionMap, ObservableList<String> categoryList, Stage stage,
                       TextField searchBar, SortOrder sortOrder) {
        this.sectionMap = sectionMap;
        this.categoryList = categoryList;
        this.stage = stage;
        this.searchBar = searchBar;
        this.sortOrder = sortOrder;
    }

    @Override
    protected List<Node> call() throws Exception {
        Session session = Session.getInstance();
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
                json.getJSONArray("products").forEach(t -> {
                    JSONObject jsonProduct = (JSONObject) t;

                    // Get a reference to an existing product in the cart or create a new one, because we need
                    // the current quantity of the product in the cart
                    Product product = session.checkProduct(jsonProduct);

                    // Create a new array if section doesn't exists
                    if (!sectionMap.containsKey(product.getSection())) {
                        sectionMap.put(product.getSection(), new ArrayList<>());
                    }
                    sectionMap.get(product.getSection()).add(product);
                    // Add to section all
                    sectionMap.get(CatalogController.SECTION_ALL).add(product);
                });

                // Set category list (since doesn't change the view structure)
                categoryList.addAll(sectionMap.keySet());
                // The  section can not be null
                return new CatalogFactory().getCatalogList(stage, sectionMap,
                        categoryList.size() > 0 ? categoryList.get(0) : "", searchBar.getText(), this.sortOrder);
            }

            Objects.requireNonNull(response.body()).close();
        }

        // Task failed
        failed();
        return null;
    }
}
