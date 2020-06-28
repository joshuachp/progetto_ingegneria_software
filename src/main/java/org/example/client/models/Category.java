package org.example.client.models;

public enum Category {
    PESCE("Pesce"), FRUTTA_VERDURA("Frutta e verdura"), CARNE("Carne"), ALIMENTI("Alimenti");

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

    public static Category fromString(String text) {
        for (Category x : Category.values()) {
            if (x.category.equalsIgnoreCase(text)) {
                return x;
            }
        }
        return null;
    }
}
