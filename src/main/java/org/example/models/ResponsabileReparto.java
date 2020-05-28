package org.example.models;

import javafx.stage.Stage;
import org.example.controllers.CatalogoController;
import org.example.controllers.GestioneProdottiController;

import java.io.IOException;

public class ResponsabileReparto extends Utente {
    public ResponsabileReparto(String username, String session) {
        super(username, session, true);
    }

    // TODO: temporary choice, only to check the function
    @Override
    public void redirect(Stage stage) {
        GestioneProdottiController gestioneProdotti = new GestioneProdottiController();
        try {
            gestioneProdotti.showView(stage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
