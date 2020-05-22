package org.example.server;


import at.favre.lib.crypto.bcrypt.BCrypt;
import org.example.server.models.User;
import org.json.JSONObject;

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
     * Privare server constructor
     */
    private Server() {
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
        if (user != null &&
                BCrypt.verifyer().verify(password.toCharArray(), user.getPassword().toCharArray()).verified) {
            if (!createSession(user))
                return null;
            return new JSONObject()
                    .put("username", user.getUsername())
                    .put("responsabile", user.getResponsabile())
                    .put("session", user.getSession());
        }
        return null;
    }

    /**
     * Autentica un utente
     * TODO: Implementation session verification
     *
     * @param session User session
     * @return Return JSONObject of user data
     */
    public JSONObject autenticateUser(String session) {
        User user = User.getSession(session);
        if (user != null) {
            return new JSONObject()
                    .put("username", user.getUsername())
                    .put("responsabile", user.getResponsabile())
                    .put("session", user.getSession());
        }
        return null;
    }

    /**
     * Create and sets a session for the authenticated user
     *
     * @param user The user that is logged in
     * @return Return true for session created successfully
     */
    private boolean createSession(User user) {
        user.setSession("session:" + user.getUsername());
        return user.updateUser();
    }


    /**
     * De autenticate a user deleting a user session
     *
     * @param session The session to delete
     * @return True for success
     */
    private boolean deautenticateUser(String session) {
        User user = User.getSession(session);
        if (user != null) {
            user.setSession(null);
            return user.updateUser();
        }
        return false;
    }
}
