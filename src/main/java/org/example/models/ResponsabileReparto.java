package org.example.models;

import javafx.stage.Stage;

public class ResponsabileReparto extends Utente {
    public ResponsabileReparto(String username, String session) {
        super(username, session, true);
    }

    @Override
    public void redirect(Stage stage) {

    }
}
