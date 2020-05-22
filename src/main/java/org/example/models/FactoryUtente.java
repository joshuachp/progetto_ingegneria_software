package org.example.models;


import org.example.utils.Utils;
import org.jetbrains.annotations.Nullable;
import org.json.JSONObject;

/**
 * Implementazione della factory per la classe Utente
 */
public class FactoryUtente {

    /**
     * Autentica un Utente tramite username e password. Ritorna una istanza della classe Utente in base alla tipologia
     * data dal server.
     *
     * @param username Username dell'utente
     * @param password Password dell'utente
     * @return Istanza della classe Utente
     */
    public Utente getUtente(String username, String password) {
        JSONObject json = Utils.autenticaWithServer(username, password);
        return createUtente(json);
    }

    /**
     * Autentica un Utente tramite username e password. Ritorna una istanza della classe Utente in base alla tipologia
     * data dal server.
     *
     * @param session Il token di sessione
     * @return Istanza della classe Utente
     */
    public Utente getUtente(String session) {
        JSONObject json = Utils.autenticaWithServer(session);
        return createUtente(json);
    }

    /**
     * Crea un utente dalla risposta del server
     *
     * @param json Risposta dell'autenticazione del server
     * @return Istanza della classe Utente
     */
    private Utente createUtente(@Nullable JSONObject json) {
        if (json == null)
            return null;
        String username = json.getString("username");
        String session = json.getString("session");
        if (json.getBoolean("responsabile")) {
            return new ResponsabileReparto(username, session);
        }
        return new Cliente(username, session);
    }
}
