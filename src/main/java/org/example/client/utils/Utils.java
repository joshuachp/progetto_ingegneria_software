package org.example.client.utils;

import okhttp3.*;
import org.example.client.models.Client;
import org.example.client.models.Manager;
import org.example.client.models.User;
import org.intellij.lang.annotations.RegExp;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;

public class Utils {

    public static final String SERVER_URL = "http://localhost:8080";
    public static final String SERVER_URL_AUTH = "/api/user/authenticate";
    public static final String SERVER_URL_SESSION = "/api/user/session";
    public static final String SERVER_URL_MANAGER_UPDATE = "/api/manager/update";
    public static final String SERVER_URL_CLIENT_UPDATE = "/api/user/update";


    // REGEX String utils
    @RegExp
    public static final String REGEX_CAP = "^\\d{5}$";
    @RegExp
    public static final String REGEX_TELEPHONE = "^(\\((00|\\+)39\\)|(00|\\+)39)?" +
            "(38[890]|" +
            "34[6-90]|" +
            "36[680]|" +
            "33[3-90]|" +
            "32[89])" +
            "\\d{7}$";

    /**
     * Authenticate with the server authentication with username and password
     *
     * @param username Username to autenticate
     * @param password password to autenticate
     * @return JSONObject of user information or null if failed
     */
    public static @Nullable JSONObject authenticate(String username, String password) {
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
     * Authenticate with the server authentication with session
     *
     * @param session The session to autenticate
     * @return JSONObject of user information or null if failed
     */
    public static @Nullable JSONObject authenticate(String session) {
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

    /**
     * Update the user information on the server
     *
     * @param user     The user information
     * @param password The new password if wants to changed
     * @return The response or null on error
     */
    public static @Nullable Response updateUser(@NotNull User user, @Nullable String password) {
        OkHttpClient httpClient = new OkHttpClient();
        FormBody.Builder body = new FormBody.Builder();
        Request.Builder request = new Request.Builder();
        // Authentication
        body.add("session", user.getSession());
        if (password != null && !password.isEmpty())
            body.add("password", password);
        if (user.isResponsabile()) {
            request.url(SERVER_URL + SERVER_URL_MANAGER_UPDATE);
            Manager manager = (Manager) user;
            body.add("badge", manager.getBadge());
            body.add("name", manager.getName());
            body.add("surname", manager.getSurname());
            body.add("address", manager.getAddress());
            body.add("cap", manager.getCap().toString());
            body.add("city", manager.getCity());
            body.add("telephone", manager.getTelephone());
            body.add("role", manager.getRole());
        } else {
            request.url(SERVER_URL + SERVER_URL_CLIENT_UPDATE);
            Client client = (Client) user;
            body.add("name", client.getName());
            body.add("surname", client.getSurname());
            body.add("address", client.getAddress());
            body.add("cap", client.getCap().toString());
            body.add("city", client.getCity());
            body.add("telephone", client.getTelephone());
            // TODO: payment method e loyalty card
        }
        // Send request
        try {
            return httpClient.newCall(request.post(body.build()).build()).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
