package org.example.client.models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class Spesa {

    int ID;
    String dataConsegna;
    String oraConsegna;
    //ArrayList<Prodotto> prodotti;
    String utente;
    float costoTotale;
    Pagamento pagamento;
    StatoSpesa statoSpesa;
    // metodo per utilizzo delle lambda espressioni nella tablla (Property use: javafx.beans.property)
    private StringProperty date = new SimpleStringProperty();

    public Spesa(int ID,
                 String dataConsegna,
                 String oraConsegna,
                 //ArrayList<Prodotto> prodotti;
                 String utente,
                 float costoTotale,
                 Pagamento pagamento,
                 StatoSpesa statoSpesa) {

        this.ID = ID;
        this.dataConsegna = dataConsegna;
        this.oraConsegna = oraConsegna;
        //ArrayList<Prodotto> prodotti;
        this.utente = utente;
        this.costoTotale = costoTotale;
        this.pagamento = pagamento;
        this.statoSpesa = statoSpesa;
        //this.prodotti = prodotti;
        setName(dataConsegna);
    }

    public Integer getID() {
        return (Integer) this.ID;
    }

    public StatoSpesa getStatoSpesa() {
        return this.statoSpesa;
    }

    public StringProperty nameProperty() {
        return this.date;
    }

    public String getName() {
        return this.nameProperty().get();
    }

    public void setName(String dataConsegna) {
        this.nameProperty().set(dataConsegna);
    }
}
