package org.example.client.utils;

import okhttp3.*;
import org.example.client.models.Client;
import org.example.client.models.Manager;
import org.example.client.models.User;
import org.intellij.lang.annotations.RegExp;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;

public class Utils {

    public static final String SERVER_URL = "http://localhost:8080";
    public static final String SERVER_URL_AUTH = "/api/user/authenticate";
    public static final String SERVER_URL_SESSION = "/api/user/session";
    public static final String SERVER_URL_REGISTRATION = "/api/client/register";
    public static final String SERVER_URL_MANAGER_UPDATE = "/api/manager/update";
    public static final String SERVER_URL_CLIENT_UPDATE = "/api/user/update";


    // REGEX String utils
    @RegExp
    public static final String REGEX_CAP = "^\\d{5}$";
    @RegExp
    public static final String REGEX_MAIL = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,}$";
    @RegExp
    public static final String REGEX_PASSWORD = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[\\W_]).{8,}$";
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
     * @return The response from the server
     */
    public static @Nullable Response authenticate(String username, String password) {
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
            return client.newCall(request).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Authenticate with the server authentication with session
     *
     * @param session The session to autenticate
     * @return Response from the server
     */
    public static @Nullable Response authenticate(String session) {
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("session", session)
                .build();
        Request request = new Request.Builder()
                .url(SERVER_URL + SERVER_URL_SESSION)
                .post(body)
                .build();
        try {
            return client.newCall(request).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static int registerClient(String username, String password, String name, String surname,
                                     String address, Integer cap, String city,
                                     String telephone, Integer payment) {
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("username", username)
                .add("password", password)
                .add("name", name)
                .add("surname", surname)
                .add("address", address)
                .add("cap", String.valueOf(cap))
                .add("city", city)
                .add("telephone", telephone)
                .add("payment", String.valueOf(payment))
                .build();
        Request request = new Request.Builder()
                .url(SERVER_URL + SERVER_URL_REGISTRATION)
                .post(body)
                .build();
        try {
            Response response = client.newCall(request).execute();
            return response.code();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
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
            body.add("card_number", client.getCardNumber().toString());
            // TODO: payment method
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
