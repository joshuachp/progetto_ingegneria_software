package org.example.client.models;

public enum Pagamento {
    CARTADICREDITO("Carta di credito"), PAYPAL("Paypal"), CONTANTI("Contanti alla consegna");

    private final String pagamento;

    Pagamento(String pagamento) {
        this.pagamento = pagamento;
    }

    public String getPagamento() {
        return pagamento;
    }

    @Override
    public String toString() {
        return pagamento;
    }

    public static Pagamento fromString(String text) {
        for (Pagamento x : Pagamento.values()) {
            if (x.pagamento.equalsIgnoreCase(text)) {
                return x;
            }
        }
        return null;
    }
}