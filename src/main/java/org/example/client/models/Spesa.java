package org.example.client.models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Button;

import java.util.Date;


public class Spesa {
    int ID;
    String dataConsegna;
    String oraConsegna;
    //ArrayList<Prodotto> prodotti;
    String utente;
    float costoTotale;
    Pagamento pagamento;
    StatoSpesa statoSpesa;
    Button button;
    // metodo per utilizzo delle lambda espressioni nella tablla (Property use: javafx.beans.property)
    private final StringProperty date;
    // TEST
    private final SimpleStringProperty firstName;

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
        //setDate(dataConsegna);
        this.date = new SimpleStringProperty(dataConsegna);
        //TEST
        this.firstName = new SimpleStringProperty("Prova");
    }

    public String getFirstName() {
        return firstName.get();
    }

    public void setFirstName(String fName) {
        firstName.set(fName);
    }

    public Integer getID() {
        return (Integer) this.ID;
    }

    public StatoSpesa getStatoSpesa() {
        return this.statoSpesa;
    }

    public void setStatoSpesa(StatoSpesa statoSpesa) {
        this.statoSpesa = statoSpesa;
    }

    public StringProperty dateProperty() {
        return this.date;
    }

    public String getDate() {
        return this.dateProperty().get();
    }

    public void setDate(String dataConsegna) {
        this.dateProperty().set(dataConsegna);
    }

    // testo la creaazione dei bottoni
    public Button buttonProperty() {
        return new Button("Try It");
    }

}
