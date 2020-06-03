package org.example.client.models;

import javafx.stage.Stage;
import org.example.client.controllers.CatalogoController;

/**
 * Cliente class
 */
public class Cliente extends Utente {

    private String name;
    private String surname;
    private String address;
    private Integer cap;
    private String city;
    private String telephone;
    // TODO: payment method

    public Cliente(String username, String session, String name, String surname, String address,
                   Integer cap, String city, String telephone) {
        super(username, session, false);
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.cap = cap;
        this.city = city;
        this.telephone = telephone;
    }

    @Override
    public void redirect(Stage stage) {
        CatalogoController.showView(stage);
    }

}
