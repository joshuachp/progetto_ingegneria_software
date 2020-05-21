package org.example.utils;

import org.example.server.Server;
import org.json.JSONObject;

import static java.lang.Thread.sleep;

public class Utils {

    /**
     * Placeholder util to simulate server authentication with username and password
     *
     * @param username Username to autenticate
     * @param password password to autenticate
     * @return JSONObject of user information or null if failed
     */
    public static JSONObject autenticaWithServer(String username, String password) {
        try {
            sleep(1);
            return Server.getInstance().autenticateUser(username, password);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Placeholder util to simulate server authentication with session
     *
     * @param session The session to autenticate
     * @return JSONObject of user information or null if failed
     */
    public static JSONObject autenticaWithServer(String session) {
        try {
            sleep(1);
            return Server.getInstance().autenticateUser(session);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
