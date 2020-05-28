package org.example.models;

import javafx.stage.Stage;
import org.example.controllers.CatalogoController;

import java.io.IOException;

public class Cliente extends Utente {
    public Cliente(String username, String session) {
        super(username, session, false);
    }

    @Override
    public void redirect(Stage stage) {
        CatalogoController catalogo = new CatalogoController();
        try {
            catalogo.showView(stage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
