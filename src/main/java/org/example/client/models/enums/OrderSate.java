package org.example.client.models.enums;

public enum OrderSate {
    CONFIRMED("Confirmed"), IN_PREPARATION("In preparation"), CONSEGNATA("Delivered");

    private final String label;

    OrderSate(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return this.label;
    }
}
