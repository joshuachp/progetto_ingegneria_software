package org.example.models;

public enum StatoSpesa {
    CONFERMATA("Confermata"), INPREPARAZIONE("In preparazione"), CONSEGNATA("Consegnata");

    private String stato;

    private StatoSpesa(String stato){
        this.stato = stato;
    }
    public String stato() { return stato; }

    public String getStatoSpesa() {
        return stato;
    }

}
