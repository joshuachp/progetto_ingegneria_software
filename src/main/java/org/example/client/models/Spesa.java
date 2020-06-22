package org.example.client.models;

import javafx.beans.property.*;
import javafx.scene.control.Button;

import java.util.Date;


public class Spesa {
    Integer ID;
    String dataConsegna;
    String oraConsegna;
    // ArrayList<Prodotto> prodotti;
    String utente;
    Double costoTotale;
    Pagamento pagamento;

    StatoSpesa statoSpesa;

    // metodo per utilizzo delle lambda espressioni nella tabella (Property use: javafx.beans.property)

    public Spesa(Integer ID,
                 String dataConsegna,
                 String oraConsegna,
                 //ArrayList<Prodotto> prodotti;
                 String utente,
                 Double costoTotale,
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

    }

    public String getOraConsegna() {
        return this.oraConsegna;
    }

    public StringProperty propertyOraConsegna() {
        return new SimpleStringProperty(this.oraConsegna);
    }

    public StringProperty propertyPagamento() {
        return new SimpleStringProperty(this.pagamento.toString());
    }


    public String getDataConsegna() {
        return this.dataConsegna;
    }

    public StringProperty propertyDataConsegna() {
        return new SimpleStringProperty(this.dataConsegna);
    }

    public Integer getID() {
        return (Integer) this.ID;
    }

    public IntegerProperty propertyID() {
        return new SimpleIntegerProperty(this.ID);
    }

    public StatoSpesa getStatoSpesa() {
        return this.statoSpesa;
    }

    public void setStatoSpesa(StatoSpesa statoSpesa) {
        this.statoSpesa = statoSpesa;
    }

    public StringProperty propertyStatoSpesa() {
        return new SimpleStringProperty(this.statoSpesa.toString());
    }

    public String getUtente() {
        return utente;
    }

    public String getCostoTotale() {
        return String.format("\u20ac %.2f", costoTotale);
    }

    public DoubleProperty propertyCostoTotale() {
        return new SimpleDoubleProperty(this.costoTotale);
    }

    public Pagamento getPagamento() {
        return pagamento;
    }

    /*
    public StringProperty dateProperty() {
        return this.date;
    }
    public void setDate(String dataConsegna) {
        this.dateProperty().set(dataConsegna);
    }*/


}
