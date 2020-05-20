package org.example.models;

public abstract class Utente {

    private String username;
    private String session;

    public Utente(String username, String session) {
        this.username = username;
        this.session = session;
    }

    public Utente() {
    }

    public String getUsername() {
        return username;
    }

}
