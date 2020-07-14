package org.example.client.models;

import javafx.stage.Stage;
import org.example.client.controllers.ChoiceModeController;

public class Manager extends User {

    private final String badge;
    private final String name;
    private final String surname;
    private final String address;
    private final Integer cap;
    private final String city;
    private final String telephone;
    private final String role;

    public Manager(String username, String session, String badge, String name, String surname,
                   String address, Integer cap, String city, String telephone, String role) {
        super(username, session, true);
        this.badge = badge;
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.cap = cap;
        this.city = city;
        this.telephone = telephone;
        this.role = role;
    }

    public String getBadge() {
        return badge;
    }

    public String getName() {
        return name;
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

    public String getRole() {
        return role;
    }

    @Override
    public void redirect(Stage stage) {
        ChoiceModeController.showView(stage);
    }
}
