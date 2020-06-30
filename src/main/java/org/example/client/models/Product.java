package org.example.client.models;

import org.json.JSONObject;

public class Product {
    private final Integer id;
    private String name;
    private String brand;
    private Integer package_size;
    private Double price;
    private String image;
    private Integer availability;
    private String characteristics;
    private String section;
    // For shopping cart
    private Integer quantity = 0;

    public Product(Integer id, String name, String brand, Integer package_size, Double price, String image,
                   Integer availability, String characteristics, String section) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.package_size = package_size;
        this.price = price;
        this.image = image;
        this.availability = availability;
        this.characteristics = characteristics;
        this.section = section;
    }

    public Product(JSONObject json) {
        this.id = json.getInt("id");
        this.name = json.getString("name");
        this.brand = json.getString("brand");
        this.package_size = json.getInt("package_size");
        this.price = json.getDouble("price");
        if (json.has("image"))
            this.image = json.getString("image");
        else
            this.image = null;
        this.characteristics = json.getString("characteristics");
        this.section = json.getString("section");
        this.availability = json.getInt("availability");
    }

    public Integer getID() {
        return this.id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Integer getPackage_size() {
        return package_size;
    }

    public void setPackage_size(Integer package_size) {
        this.package_size = package_size;
    }

    public String getPrice() {
        return String.format("\u20ac %.2f", price);
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String path) {
        this.image = path;
    }

    public String getAvailability() {
        return availability.toString();
    }

    public void setAvailability(Integer availability) {
        this.availability = availability;
    }

    public String getCharacteristics() {
        return characteristics;
    }

    public void setCharacteristics(String characteristics) {
        this.characteristics = characteristics;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    @Override
    public String toString() {
        return name + " " +
                brand + " " +
                package_size.toString() + " " +
                price.toString() + " " +
                image + " " +
                availability.toString() + " " +
                characteristics + " " +
                section;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
