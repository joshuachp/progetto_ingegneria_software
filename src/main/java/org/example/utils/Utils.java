package org.example.utils;

import org.example.server.Server;
import org.json.JSONObject;

import static java.lang.Thread.sleep;

public class Utils {

    /**
     *
     * @param username
     * @param password
     * @return
     */
    public static JSONObject autenticaWithServer(String username, String password){
        JSONObject res;
        try {
            sleep(1);
            res = Server.getInstance().autenticateUser(username, password);
            return res;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *
     * @param session
     * @return
     */
    public static String autenticaWithServer(String session){
        try {
            sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "username";
    }
}
