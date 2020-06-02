package org.example.client.models;

import javafx.stage.Stage;
import org.example.client.controllers.SceltaModalitaController;

import java.io.IOException;

public class ResponsabileReparto extends Utente {
    public ResponsabileReparto(String username, String session) {
        super(username, session, true);
    }
    
    @Override
    public void redirect(Stage stage) {
        SceltaModalitaController sceltamodalita = new SceltaModalitaController();
        try {
            sceltamodalita.showView(stage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
