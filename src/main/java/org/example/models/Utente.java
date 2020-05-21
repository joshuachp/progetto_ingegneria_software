package org.example.models;

public abstract class Utente {


    private final String username;
    private final String session;
    private final boolean responsabile;

    public Utente(String username, String session, boolean responsabile) {
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
