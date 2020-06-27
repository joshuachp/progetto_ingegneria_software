package org.example.client.models;

import javafx.stage.Stage;

public abstract class User {

    public abstract void redirect(Stage stage);

    private final String username;
    private final String session;
    private final boolean responsabile;

    public User(String username, String session, boolean responsabile) {
        this.username = username;
        this.session = session;
        this.responsabile = responsabile;
    }

    public String getUsername() {
        return username;
    }

    public boolean isResponsabile() {
        return responsabile;
    }

    public String getSession() {
        return session;
    }
}
