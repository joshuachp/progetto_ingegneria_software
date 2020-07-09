package org.example.client.models.enums;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum Payment {
    CASH("Cash on delivery"), CREDIT_CARD("Credit card"), PAY_PAL("PayPal");

    private final String pagamento;

    Payment(String pagamento) {
        this.pagamento = pagamento;
    }

    public static List<String> getPayments() {
        return Arrays.stream(Payment.values()).map(Payment::toString).collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return this.pagamento;
    }
}