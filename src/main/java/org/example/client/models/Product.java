package org.example.client.models;

import org.json.JSONObject;


public class Product extends ROProduct {

    public Product(Integer id, String name, String brand, Integer packageSize, Float price, String image,
                   Integer availability, String characteristics, String section) {
        super(id, name, brand, packageSize, price, image, availability, characteristics, section);
    }

    public Product(JSONObject json) {
        super(json);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setPackageSize(Integer packageSize) {
        this.packageSize = packageSize;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setAvailability(Integer availability) {
        this.availability = availability;
    }

    public void setCharacteristics(String characteristics) {
        this.characteristics = characteristics;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}

