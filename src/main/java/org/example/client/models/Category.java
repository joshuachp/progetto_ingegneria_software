package org.example.client.models;

public enum Category {
    PESCE("Pesce"), FRUTTA_VERDURA("Frutta e verdura"), CARNE("Carne");

    private final String category;

    Category(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    @Override
    public String toString() {
        return category;
    }
}
