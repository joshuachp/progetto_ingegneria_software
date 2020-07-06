package org.example.client.models;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum Payment {
    CREDIT_CARD("Credit card"), PAY_PAL("PayPal"), CASH("Cash on delivery");

    private final String pagamento;

    Payment(String pagamento) {
        this.pagamento = pagamento;
    }

    public static List<String> getPayments() {
        return Arrays.stream(Payment.values()).map(Payment::toString).collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return pagamento;
    }
}