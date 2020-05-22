package org.example.server;


import org.example.server.models.User;
import org.example.server.utils.Utils;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


/**
 * Server for database interaction
 */
public class Server {

    /**
     * Static instance of the server, lazy initialized
     * TODO: find alternative implementation of server
     */
    private static Server server = null;

    /**
     * Map of the authenticated users. It's in memory since we don't have a lot of users.
     */
    private final Map<String, User> userSessions;

    /**
     * Privare server constructor
     */
    private Server() {
        this.userSessions = new HashMap<>();
    }


    /**
     * Returns or creates an instance of the server
     *
     * @return The server instance
     */
    public static Server getInstance() {
        if (server == null) {
            server = new Server();
        }
        return server;
    }

    /**
     * Autentica un utente. Controlla la password con l'hash della password nel
     * database, se coincidono ritorna una token sessione utente.
     *
     * @param username Username dell'utente
     * @param password Password dell'utente
     * @return Return JSONObject of user data
     */
    public JSONObject autenticateUser(String username, String password) {
        User user = User.getUser(username);
        if (user != null && Utils.checkPassword(password, user.getPassword())) {
            String session = Utils.createSession();
            // TODO: Decide what to do for already authenticated user
            userSessions.put(session, user);
            return new JSONObject()
                    .put("username", user.getUsername())
                    .put("responsabile", user.getResponsabile())
                    .put("session", session);
        }
        return null;
    }

    /**
     * Autentica un utente with session token.
     *
     * @param session User session
     * @return Return JSONObject of user data
     */
    public JSONObject autenticateUser(String session) {
        if (userSessions.containsKey(session)) {
            User user = userSessions.get(session);
            return new JSONObject()
                    .put("username", user.getUsername())
                    .put("responsabile", user.getResponsabile())
                    .put("session", session);
        }
        return null;
    }


    /**
     * De autenticate a user deleting a user session
     *
     * @param session The session to delete
     * @return True for success
     */
    private boolean deautenticateUser(String session) {
        if (userSessions.containsKey(session)) {
            userSessions.remove(session);
            return true;
        }
        return false;
    }
}
