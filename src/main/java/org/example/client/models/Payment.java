package org.example.client.models;

import org.jetbrains.annotations.Nullable;

public enum Payment {
    CREDIT_CARD("Credit card"), PAY_PAL("PayPal"), CASH("Cash on delivery");

    private final String pagamento;

    Payment(String pagamento) {
        this.pagamento = pagamento;
    }

    public static @Nullable Payment fromString(String text) {
        for (Payment x : Payment.values()) {
            if (x.pagamento.equalsIgnoreCase(text)) {
                return x;
            }
        }
        try {
            throw new Exception("Value \"" + text + "\" not present in Enum.");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String toString() {
        return pagamento;
    }
}