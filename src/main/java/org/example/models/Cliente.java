package org.example.models;

import javafx.stage.Stage;
import org.example.controllers.AutenticazioneController;

public class Cliente extends Utente {
    public Cliente(String username, String session) {
        super(username, session, false);
    }

    @Override
    public void redirect(Stage stage) {
    }

}
