package org.example.client.models.enums;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum SortOrder {
    ASCENDING("Price ascending"), DESCENDING("Price descending"), ALPHABETICAL("Brand");

    private final String label;


    SortOrder(String label) {
        this.label = label;
    }

    public static List<String> getSortOrders() {
        return Arrays.stream(SortOrder.values()).map(SortOrder::toString).collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return this.label;
    }
}
