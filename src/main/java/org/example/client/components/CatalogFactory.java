package org.example.client.components;

import javafx.scene.Node;
import org.example.client.controllers.CardController;
import org.example.client.models.Product;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
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
    public List<Node> getCatalogList(@NotNull Map<String, ArrayList<Product>> productMap, @NotNull String section,
                                     @NotNull String search) {
        // Format the search string
        String newSearch = search.trim().toLowerCase();

        // Generate an array list with a size for performances if there is no section
        Collection<Product> products = productMap.getOrDefault(section, new ArrayList<>());
        List<Node> nodes = new ArrayList<>(products.size());
        // Check each product
        for (Product product : products) {
            if (newSearch.isEmpty() ||
                    product.getName().contains(newSearch) ||
                    product.getCharacteristics().contains(newSearch)) {
                nodes.add(CardController.generateCard(product));
            }
        }
        return nodes;
    }

}
