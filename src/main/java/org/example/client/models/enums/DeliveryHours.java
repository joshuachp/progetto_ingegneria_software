package org.example.client.models.enums;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum DeliveryHours {
    MORNING("8:00 - 12:00", 8, 12), AFTERNOON("14:00 - 18:00", 14, 18);

    private final String label;
    private final Integer startHour;
    private final Integer endHour;

    DeliveryHours(String label, Integer startHour, Integer endHour) {
        this.label = label;
        this.startHour = startHour;
        this.endHour = endHour;
    }

    public static List<String> getDeliveryHours() {
        return Arrays.stream(DeliveryHours.values()).map(DeliveryHours::toString).collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return this.label;
    }

    public Integer getStartHour() {
        return startHour;
    }

    public Integer getEndHour() {
        return endHour;
    }
}
