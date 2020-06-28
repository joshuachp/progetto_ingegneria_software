package org.example.client.models;

import javafx.beans.property.StringProperty;

public class Prodotto {
    private  Integer id;

    public void setName(String name) {
        this.name = name;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setPackage_size(Integer package_size) {
        this.package_size = package_size;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setCharacteristics(String characteristics) {
        this.characteristics = characteristics;
    }

    public void setSection(String section) {
        this.section = section;
    }

    private  String name;
    private  String brand;
    private  Integer package_size;
    private  Double price;
    private  String image;
    private  Integer availability;
    private  String characteristics;
    private  String section;

    public Prodotto(Integer id, String name, String brand, Integer package_size, Double price, String image,
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

    public Integer getID() {
        return (Integer) this.id;
    }

    public String getName() {
        return name;
    }

    public String getBrand() {
        return brand;
    }

    public Integer getPackage_size() {
        return package_size;
    }

    public String getPrice() {
        return String.format("\u20ac %.2f",price);
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

    public String getSection() {
        return section;
    }

    @Override
    public String toString(){
      return  name + " " +
              brand + " " +
              package_size.toString() + " " +
              price.toString() + " " +
              image + " " +
              availability.toString() + " " +
              characteristics + " " +
              section;
    }
}
