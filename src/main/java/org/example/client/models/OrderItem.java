package org.example.client.models;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

public class OrderItem {

    private final String name;
    private final Float price;
    private final Integer quantity;
    private final Integer productId;

    public OrderItem(String name, Float price, Integer quantity, Integer productId) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.productId = productId;
    }

    public OrderItem(@NotNull JSONObject json) {
        this(json.getString("name"), json.getFloat("price"), json.getInt("quantity"), json.getInt("productId"));
    }

    public String getName() {
        return name;
    }

    public Float getPrice() {
        return price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Integer getProductId() {
        return productId;
    }
}
