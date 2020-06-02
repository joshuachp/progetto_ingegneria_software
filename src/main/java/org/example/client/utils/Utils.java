package org.example.client.utils;

import org.json.JSONObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Utils {

    public static final String SERVER_URL = "http://localhost.local:8080";
    public static final String SERVER_URL_AUTH = "/api/user/authenticate";
    public static final String SERVER_URL_SESSION = "/api/user/session";

    /**
     * Placeholder util to simulate server authentication with username and password
     *
     * @param username Username to autenticate
     * @param password password to autenticate
     * @return JSONObject of user information or null if failed
     */
    public static JSONObject autenticaWithServer(String username, String password) {
        String requestBody = new JSONObject()
                .put("username", username)
                .put("password", password)
                .toString();
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request;
        try {
            request = HttpRequest.newBuilder(URI.create(SERVER_URL + SERVER_URL_AUTH))
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return new JSONObject(response.body());
        } catch (Exception e) {
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
        String requestBody = new JSONObject()
                .put("username", session)
                .toString();
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request;
        try {
            request = HttpRequest.newBuilder(URI.create(SERVER_URL + SERVER_URL_SESSION))
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return new JSONObject(response.body());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
