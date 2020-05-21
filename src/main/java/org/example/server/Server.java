package org.example.server;


import org.example.server.models.User;
import org.json.JSONObject;

public class Server {

    // TODO: trova alternativa nuovo thread per il server
    private static Server server = null;

    private Server() {
        server = new Server();
    }

    public static Server getInstance() {
        return server;
    }

    /**
     * Autentica un utente. Controlla la password con l'hash della password nel
     * database, se coincidono ritorna una token sessione utente.
     *
     * @param username Username dell'utente
     * @param password Password dell'utente
     * @return TODO: Define the return data
     */
    public JSONObject autenticateUser(String username, String password) {
        User user = User.getUser(username);
        if (user != null && BCrypt.checkpw(password, user.getPassword())) {
            createSession(user);
            System.out.println(user.getSession());
            return new JSONObject()
                    .put("username", user.getUsername())
                    .put("responsabile", user.getResponsabile())
                    .put("sessione", user.getSession());
        }
        return null;
    }


    private void createSession(User user){
        user.setSession("session:"+user.getUsername());
    }


    // - de autenticazione
}
