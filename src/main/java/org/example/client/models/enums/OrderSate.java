package org.example.client.models.enums;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public enum OrderSate {
    // TODO: Better colors
    CONFIRMED("Confirmed", Color.YELLOWGREEN),
    IN_PREPARATION("In preparation", Color.AQUA),
    CONSEGNATA("Delivered", Color.GREEN),
    CANCELLED("Cancelled", Color.RED);

    private final String label;
    private final Paint color;

    OrderSate(String label, Paint color) {
        this.label = label;
        this.color = color;
    }

    @Override
    public String toString() {
        return this.label;
    }

    public Paint getColor() {
        return color;
    }
}
