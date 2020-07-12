package org.example.client.models;

import javafx.beans.property.*;
import org.json.JSONObject;

public class Product {

    private final Integer id;
    private String name;
    private String brand;
    private Integer packageSize;
    private Float price;
    private String image;
    private Integer availability;
    private String characteristics;
    private String section;
    // For shopping cart
    private Integer quantity = 0;

    public Product(Integer id, String name, String brand, Integer packageSize, Float price, String image,
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

    public Product(JSONObject json) {
        this.id = json.getInt("id");
        this.name = json.getString("name");
        this.brand = json.getString("brand");
        this.packageSize = json.getInt("package_size");
        this.price = json.getFloat("price");
        if (json.has("image"))
            this.image = json.getString("image");
        else
            this.image = null;
        this.characteristics = json.getString("characteristics");
        this.section = json.getString("section");
        this.availability = json.getInt("availability");
    }

    // Product to create
    public Product( String name, String brand, Integer packageSize, Float price, String image,
                   Integer availability, String characteristics, String section) {
        this.id = null;
        this.name = name;
        this.brand = brand;
        this.packageSize = packageSize;
        this.price = price;
        this.image = image;
        this.availability = availability;
        this.characteristics = characteristics;
        this.section = section;
    }

    public IntegerProperty idProperty(){return new SimpleIntegerProperty(this.id);}

    public Integer getId() {
        return this.id;
    }


    public StringProperty nameProperty(){return new SimpleStringProperty(this.name);}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public StringProperty brandProperty(){return new SimpleStringProperty(this.brand);}

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Integer getPackageSize() {
        return packageSize;
    }

    public void setPackageSize(Integer packageSize) {
        this.packageSize = packageSize;
    }

    public  FloatProperty priceProperty(){return new SimpleFloatProperty(this.price);}

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String path) {
        this.image = path;
    }

    public StringProperty availabilityProperty(){return new SimpleStringProperty(this.availability.toString());}

    public Integer getAvailability() {
        return availability;
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
                packageSize.toString() + " " +
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
}
