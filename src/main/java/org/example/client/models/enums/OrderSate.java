package org.example.client.models.enums;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

    public static List<String> getLabels() {
        return Arrays.stream(OrderSate.values()).map(OrderSate::toString).collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return this.label;
    }

    public Paint getColor() {
        return color;
    }
}
