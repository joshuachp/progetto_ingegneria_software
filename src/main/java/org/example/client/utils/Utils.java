package org.example.client.utils;

import okhttp3.*;
import org.intellij.lang.annotations.RegExp;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;

public class Utils {

    public static final String SERVER_URL = "http://localhost:8080";
    public static final String SERVER_URL_AUTH = "/api/user/authenticate";
    public static final String SERVER_URL_SESSION = "/api/user/session";


    // REGEX String utils
    @RegExp
    public static final String REGEX_CAP = "^\\d{5}$";
    @RegExp // TODO
    public static final String REGEX_TELEPHONE = "^(\\((00|\\+)39\\)|(00|\\+)39)?" +
            "(38[890]|" +
            "34[6-90]|" +
            "36[680]|" +
            "33[3-90]|" +
            "32[89])" +
            "\\d{7}$";

    /**
     * Placeholder util to simulate server authentication with username and password
     *
     * @param username Username to autenticate
     * @param password password to autenticate
     * @return JSONObject of user information or null if failed
     */
    public static JSONObject autenticaWithServer(String username, String password) {
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("username", username)
                .add("password", password)
                .build();
        Request request = new Request.Builder()
                .url(SERVER_URL + SERVER_URL_AUTH)
                .post(body)
                .build();
        try {
            Response response = client.newCall(request).execute();
            // NOTE: Added to remove error
            String responseBody = Objects.requireNonNull(response.body()).string();
            return new JSONObject(responseBody);
        } catch (IOException e) {
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
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("session", session)
                .build();
        Request request = new Request.Builder()
                .url(SERVER_URL + SERVER_URL_SESSION)
                .post(body)
                .build();
        try {
            Response response = client.newCall(request).execute();
            // NOTE: Added to remove error
            String responseBody = Objects.requireNonNull(response.body()).string();
            return new JSONObject(responseBody);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
