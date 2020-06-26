package org.example.client.models;

import javafx.stage.Stage;
import org.example.client.controllers.CatalogoController;

/**
 * Cliente class
 */
public class Client extends User {

    private final String name;
    private final String surname;
    private final String address;
    private final Integer cap;
    private final String city;
    private final String telephone;
    // TODO: payment method

    public Client(String username, String session, String name, String surname, String address,
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

    public String getName() {
        return this.name;
    }

    public String getSurname() {
        return surname;
    }

    public String getAddress() {
        return address;
    }

    public Integer getCap() {
        return cap;
    }

    public String getCity() {
        return city;
    }

    public String getTelephone() {
        return telephone;
    }
}
