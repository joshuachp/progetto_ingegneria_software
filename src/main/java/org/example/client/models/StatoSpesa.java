package org.example.client.models;

public enum StatoSpesa {
    CONFERMATA("Confermata"), INPREPARAZIONE("In preparazione"), CONSEGNATA("Consegnata");


    private final String stato;

    StatoSpesa(final String stato) {
        this.stato = stato;
    }

    public String toString() {
        return stato;
    }

    public static StatoSpesa fromString(String text) {
        for (StatoSpesa x : StatoSpesa.values()) {
            if (x.stato.equalsIgnoreCase(text)) {
                return x;
            }
        }
        return null;
    }

    /*@Override
    public String toString() {
        return stato.toString();
    }*/
}
