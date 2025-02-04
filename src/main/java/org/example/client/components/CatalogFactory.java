package org.example.client.components;

import javafx.scene.Node;
import javafx.stage.Stage;
import org.example.client.controllers.CatalogItemController;
import org.example.client.models.ROProduct;
import org.example.client.models.enums.SortOrder;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class CatalogFactory {

    /**
     * Generate a list of nodes of the products in the catalog, filtered by the section and the search
     *
     * @param productMap Map of the products
     * @param section    Section
     * @param search     Search
     * @return List of nodes
     */
    public List<Node> getCatalogList(Stage stage, @NotNull Map<String, ArrayList<ROProduct>> productMap,
                                     @NotNull String section, @NotNull String search, SortOrder sort) {
        // Format the search string
        String newSearch = search.trim().toLowerCase();

        // Generate an array list with a size for performances if there is no section
        ArrayList<ROProduct> products = productMap.getOrDefault(section, new ArrayList<>());
        switch (sort) {
            case ASCENDING:
                products.sort(Comparator.comparing(ROProduct::getPrice));
                break;
            case DESCENDING:
                products.sort(Comparator.comparing(ROProduct::getPrice).reversed());
                break;
            default:
                products.sort(Comparator.comparing(ROProduct::getBrand));
        }
        List<Node> nodes = new ArrayList<>(products.size());
        // Check each product
        for (ROProduct product : products) {
            if (newSearch.isEmpty() ||
                    product.getName().toLowerCase().contains(newSearch) ||
                    product.getCharacteristics().toLowerCase().contains(newSearch)) {
                nodes.add(CatalogItemController.createCatalogItem(stage, product));
            }
        }
        return nodes;
    }

}
