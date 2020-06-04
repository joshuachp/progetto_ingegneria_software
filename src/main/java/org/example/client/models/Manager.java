package org.example.client.models;

import javafx.stage.Stage;
import org.example.client.controllers.SceltaModalitaController;

import java.io.IOException;

public class Manager extends Utente {

    private String badge;
    private String name;
    private String surname;
    private String address;
    private Integer cap;
    private String city;
    private String telephone;
    private String role;


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

    @Override
    public void redirect(Stage stage) {
        try{
            SceltaModalitaController.showView(stage);
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
