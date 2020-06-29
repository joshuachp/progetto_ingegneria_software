package org.example.client.models;

import javafx.stage.Stage;
import org.example.client.controllers.CatalogoController;

/**
 * Cliente class
 */
public class Client extends User {

    private String name;
    private String surname;
    private String address;
    private Integer cap;
    private String city;
    private String telephone;
    private Integer cardNumber;
    // TODO: payment method

    public Client(String username, String session, String name, String surname, String address,
                  Integer cap, String city, String telephone, Integer cardNumber) {
        super(username, session, false);
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.cap = cap;
        this.city = city;
        this.telephone = telephone;
        this.cardNumber = cardNumber;
    }

    @Override
    public void redirect(Stage stage) {
        CatalogoController.showView(stage);
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getCap() {
        return cap;
    }

    public void setCap(Integer cap) {
        this.cap = cap;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Integer getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(Integer cardNumber) {
        this.cardNumber = cardNumber;
    }
}
