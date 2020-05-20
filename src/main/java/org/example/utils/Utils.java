package org.example.utils;

import static java.lang.Thread.sleep;

public class Utils {

    /**
     *
     * @param username
     * @param password
     * @return
     */
    public static String autenticaWithServer(String username, String password){
        try {
            sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "sessione";
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
