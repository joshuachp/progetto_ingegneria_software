package org.example.models;

public enum StatoSpesa {
    CONFERMATA("Confermata"), INPREPARAZIONE("In preparazione"), CONSEGNATA("Consegnata");


    private String stato;

    StatoSpesa(String stato){
        this.stato = stato;
    }

    public String getStatoSpesa() {
        return stato;
    }


}
