package org.example.client.models;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

public abstract class AbstractProduct {

    protected final Integer id;
    protected String name;
    protected String brand;
    protected Integer packageSize;
    protected Float price;
    protected String image;
    protected Integer availability;
    protected String characteristics;
    protected String section;
    // For shopping cart
    protected Integer quantity = 0;

    public AbstractProduct(Integer id, String name, String brand, Integer packageSize, Float price, String image,
                           Integer availability, String characteristics, String section) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.packageSize = packageSize;
        this.price = price;
        this.image = image;
        this.availability = availability;
        this.characteristics = characteristics;
        this.section = section;
    }

    public AbstractProduct(@NotNull JSONObject json) {
        this(json.getInt("id"), json.getString("name"), json.getString("brand"), json.getInt("package_size"),
                json.getFloat("price"), json.has("image") ? json.getString("image") : null, json.getInt("availability"
                ), json.getString("characteristics"), json.getString("section"));
    }

    public JSONObject toJSON() {
        JSONObject json = new JSONObject()
                .put("name", this.name)
                .put("brand", this.brand)
                .put("package_size", this.packageSize)
                .put("price", this.price)
                .put("availability", this.availability)
                .put("characteristics", this.characteristics)
                .put("section", this.section);
        if (this.image != null)
            json.put("image", this.image);
        return json;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getBrand() {
        return brand;
    }

    public Integer getPackageSize() {
        return packageSize;
    }

    public Float getPrice() {
        return price;
    }

    public String getImage() {
        return image;
    }

    public Integer getAvailability() {
        return availability;
    }

    public String getCharacteristics() {
        return characteristics;
    }

    public String getSection() {
        return section;
    }

    public Integer getQuantity() {
        return quantity;
    }

}
