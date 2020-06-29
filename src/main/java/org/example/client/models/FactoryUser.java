package org.example.client.models;


import okhttp3.Response;
import org.example.client.utils.Utils;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;

/**
 * Implementazione della factory per la classe Utente
 */
public class FactoryUser {

    /**
     * Autentica un Utente tramite username e password. Ritorna una istanza della classe Utente in base alla tipologia
     * data dal server.
     *
     * @param username Username dell'utente
     * @param password Password dell'utente
     * @return Istanza della classe Utente null on error
     */
    public @Nullable User getUser(String username, String password) {
        Response response = Utils.authenticate(username, password);
        if (response != null && response.code() == 200) {
            try {
                return createUtente(new JSONObject(Objects.requireNonNull(response.body()).string()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * Autentica un Utente tramite username e password. Ritorna una istanza della classe Utente in base alla
     * tipologia
     * data dal server.
     *
     * @param session Il token di sessione
     * @return Istanza della classe Utente null on error
     */
    public @Nullable User getUser(String session) {
        Response response = Utils.authenticate(session);
        if (response != null && response.code() == 200) {
            try {
                return createUtente(new JSONObject(Objects.requireNonNull(response.body()).string()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * Crea un utente dalla risposta del server
     *
     * @param json Risposta dell'autenticazione del server
     * @return Istanza della classe Utente
     */
    @Contract("_ -> new")
    private @NotNull User createUtente(@NotNull JSONObject json) {
        String username = json.getString("username");
        String session = json.getString("session");
        if (json.getBoolean("responsabile")) {
            return new Manager(username, session, json.getString("badge"), json.getString("name"),
                    json.getString("surname"), json.getString("address"), json.getInt("cap"),
                    json.getString("city"), json.getString("telephone"), json.getString("role"));
        }
        return new Client(username, session, json.getString("name"), json.getString("surname"),
                json.getString("address"), json.getInt("cap"), json.getString("city"),
                json.getString("telephone"), json.getInt("loyalty_card_number"));
    }
}
