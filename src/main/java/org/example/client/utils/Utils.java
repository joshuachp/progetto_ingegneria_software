package org.example.client.utils;

import javafx.stage.Stage;
import okhttp3.*;
import org.example.client.controllers.AuthController;
import org.example.client.models.*;
import org.example.client.models.enums.OrderSate;
import org.intellij.lang.annotations.RegExp;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Utils {

    public static final String SERVER_URL = "http://localhost:8080";
    public static final String SERVER_URL_AUTH = "/api/user/authenticate";
    public static final String SERVER_URL_SESSION = "/api/user/session";
    public static final String SERVER_URL_LOGOUT = "/api/user/logout";
    public static final String SERVER_URL_REGISTRATION = "/api/client/register";
    public static final String SERVER_URL_MANAGER_UPDATE = "/api/manager/update";
    public static final String SERVER_URL_CLIENT_UPDATE = "/api/client/update";
    // Format for URL `/api/product/{productId}`
    public static final String SERVER_URL_GET_PRODUCT = "/api/product/%d";
    public static final String SERVER_URL_GET_ALL_PRODUCT = "/api/product/all";
    public static final String SERVER_URL_CREATE_ORDER = "/api/order/create";
    public static final String SERVER_URL_GET_USER_ORDERS = "/api/order/user";
    public static final String SERVER_URL_GET_ALL_ORDERS = "/api/order/all";
    // Format for URL `/api/order/{orderId}/update`
    public static final String SERVER_URL_ORDER_STATE_UPDATE = "/api/order/%d/update";
    // Format for URL `/api/card/{cardNumber}`
    public static final String SERVER_URL_GET_LOYALTY_CARD = "/api/card/%d";
    // Format for URL `/api/order-item/all/{orderId}`
    public static final String SERVER_URL_GET_ALL_ORDER_ITEMS = "/api/order-item/all/%d";

    public static final String SERVER_URL_CREATE_PRODUCT = "/api/product/create";
    public static final String SERVER_URL_UPDATE_PRODUCT = "/api/product/update";
    public static final String SERVER_URL_REMOVE_PRODUCT = "/api/product/remove";

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


    /**
     * Register a new client on the server
     *
     * @param username  Username
     * @param password  Password
     * @param name      Name
     * @param surname   Surname
     * @param address   Address
     * @param cap       CAP
     * @param city      City
     * @param telephone Telephone
     * @param payment   Payment
     * @return The response of the server or null on error
     */
    public static int registerClient(String username, String password, String name, String surname,
                                     String address, Integer cap, String city,
                                     String telephone, Integer payment, Integer cardNumber) {
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
                .add("loyalty_card_number", cardNumber.toString())
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
            body.add("payment", client.getPaymentInteger().toString());
            body.add("card_number", client.getCardNumber().toString());
        }
        // Send request
        try {
            return httpClient.newCall(request.post(body.build()).build()).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Get a list of all products. The response is a JSON with an array called products.
     *
     * @param session User session
     * @return Server response null on error
     */
    public static @Nullable Response getAllProducts(String session) {
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("session", session)
                .build();
        Request request = new Request.Builder()
                .url(SERVER_URL + SERVER_URL_GET_ALL_PRODUCT)
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
     * Get the loyalty card information. The card must be register to the user.
     *
     * @param session User session
     * @return Server response null on error
     */
    public static @Nullable Response getLoyaltyCard(String session, Integer cardNumber) {
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("session", session)
                .build();
        Request request = new Request.Builder()
                .url(String.format(SERVER_URL + SERVER_URL_GET_LOYALTY_CARD, cardNumber))
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
     * Create Order.
     *
     * @param session       Session
     * @param products      Product
     * @param deliveryStart delivery start
     * @param deliveryEnd   Delivery end
     * @throws Exception Exception
     */
    public static void createOrder(String session, List<Product> products, Date deliveryStart,
                                   Date deliveryEnd) throws Exception {
        Map<Integer, Integer> productMap = products.stream().collect(Collectors.toMap(Product::getId,
                Product::getQuantity));

        JSONObject json = new JSONObject()
                .put("session", session)
                .put("products", productMap)
                .put("deliveryStart", deliveryStart.getTime())
                .put("deliveryEnd", deliveryEnd.getTime());
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(json.toString(), MediaType.get("application/json; charset=utf-8"));
        Request request = new Request.Builder()
                .url(SERVER_URL + SERVER_URL_CREATE_ORDER)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        if (response.code() != 200) {
            String error = Objects.requireNonNull(response.body()).string();
            Objects.requireNonNull(response.body()).close();
            throw new Exception(error);
        }
    }

    /**
     * Logout a user and return to authentication
     *
     * @param stage Main stage to redirect to auth
     */
    public static void logOut(Stage stage) {
        Session session = Session.getInstance();
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("session", session.getUser().getSession())
                .build();
        Request request = new Request.Builder()
                .url(SERVER_URL + SERVER_URL_LOGOUT)
                .post(body)
                .build();
        try {
            Response response = client.newCall(request).execute();
            if (response.code() == 200) {
                Session.destroyInstance();
                AuthController.showView(stage);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Request all the orders of a user to the server.
     *
     * @param session User session
     * @return List of the user orders
     * @throws Exception {@link IOException} if request fails and {@link Exception} if the requests returns
     *                   error code. Sets the request body as the exception message.
     */
    public static @NotNull ArrayList<Order> getUserOrders(String session) throws Exception {
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("session", session)
                .build();
        Request request = new Request.Builder()
                .url(SERVER_URL + SERVER_URL_GET_USER_ORDERS)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        if (response.code() != 200) {
            String error = Objects.requireNonNull(response.body()).string();
            Objects.requireNonNull(response.body()).close();
            throw new Exception(error);
        }
        JSONObject json = new JSONObject(Objects.requireNonNull(response.body()).string());
        Objects.requireNonNull(response.body()).close();
        JSONArray array = json.getJSONArray("orders");
        ArrayList<Order> list = new ArrayList<>(array.length());
        for (int i = 0; i < array.length(); i++) {
            list.add(new Order(array.getJSONObject(i)));
        }
        return list;
    }

    /**
     * Request all the orders to the server
     *
     * @param session User session
     * @return List of the user orders
     * @throws Exception {@link IOException} if request fails and {@link Exception} if the requests returns
     *                   error code. Sets the request body as the exception message.
     */
    public static @NotNull ArrayList<Order> getAllOrders(String session) throws Exception {
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("session", session)
                .build();
        Request request = new Request.Builder()
                .url(SERVER_URL + SERVER_URL_GET_ALL_ORDERS)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        if (response.code() != 200) {
            String error = Objects.requireNonNull(response.body()).string();
            Objects.requireNonNull(response.body()).close();
            throw new Exception(error);
        }
        JSONObject json = new JSONObject(Objects.requireNonNull(response.body()).string());
        Objects.requireNonNull(response.body()).close();
        JSONArray array = json.getJSONArray("orders");
        ArrayList<Order> list = new ArrayList<>(array.length());
        for (int i = 0; i < array.length(); i++) {
            list.add(new Order(array.getJSONObject(i)));
        }
        return list;
    }

    /**
     * Request all the order items of an order to the server.
     *
     * @param session User session
     * @return List of the order items
     * @throws Exception {@link IOException} if request fails and {@link Exception} if the requests returns error
     *                   code. Sets the request body as the exception message
     */
    public static @NotNull ArrayList<OrderItem> getOrderItems(String session, Integer orderId) throws Exception {
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("session", session)
                .build();
        Request request = new Request.Builder()
                .url(SERVER_URL + String.format(SERVER_URL_GET_ALL_ORDER_ITEMS, orderId))
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        if (response.code() != 200) {
            String error = Objects.requireNonNull(response.body()).string();
            Objects.requireNonNull(response.body()).close();
            throw new Exception(error);
        }
        JSONObject json = new JSONObject(Objects.requireNonNull(response.body()).string());
        Objects.requireNonNull(response.body()).close();
        JSONArray array = json.getJSONArray("orderItems");
        ArrayList<OrderItem> list = new ArrayList<>(array.length());
        for (int i = 0; i < array.length(); i++) {
            list.add(new OrderItem(array.getJSONObject(i)));
        }
        return list;
    }

    /**
     * Request product information of a specific product id to the server
     *
     * @param session User session
     * @return Product information
     * @throws Exception {@link IOException} if request fails and {@link Exception} if the requests returns error
     *                   code. Sets the request body as the exception message
     */
    public static @NotNull Product getProduct(String session, Integer productId) throws Exception {
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("session", session)
                .build();
        Request request = new Request.Builder()
                .url(SERVER_URL + String.format(SERVER_URL_GET_PRODUCT, productId))
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        if (response.code() != 200) {
            String error = Objects.requireNonNull(response.body()).string();
            Objects.requireNonNull(response.body()).close();
            throw new Exception(error);
        }
        Product product = new Product(new JSONObject(Objects.requireNonNull(response.body()).string()));
        response.close();
        return product;
    }

    public static void createProduct(String session, List<Product> products) throws Exception {

        JSONArray jsonProducts = new JSONArray();
        for (Product x : products) {
            jsonProducts.put(x.toJSON());
        }

        JSONObject json = new JSONObject()
                .put("session", session)
                .put("products", jsonProducts);

        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(json.toString(), MediaType.get("application/json; charset=utf-8"));

        Request request = new Request.Builder()
                .url(SERVER_URL + SERVER_URL_CREATE_PRODUCT)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        if (response.code() != 200) {
            String error = Objects.requireNonNull(response.body()).string();
            Objects.requireNonNull(response.body()).close();
            throw new Exception(error);
        }
    }

    public static void updateProduct(String session, Integer productId) throws Exception {

        JSONObject json = new JSONObject()
                .put("session", session)
                .put("products", productId);

        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(json.toString(), MediaType.get("application/json; charset=utf-8"));

        Request request = new Request.Builder()
                .url(SERVER_URL + SERVER_URL_UPDATE_PRODUCT)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        if (response.code() != 200) {
            String error = Objects.requireNonNull(response.body()).string();
            Objects.requireNonNull(response.body()).close();
            throw new Exception(error);
        }
    }

    // TODO: do as form
    public static void removeProduct(String session, Integer productId) throws Exception {
        JSONObject json = new JSONObject()
                .put("session", session)
                .put("product_id", productId);
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(json.toString(), MediaType.get("application/json; charset=utf-8"));
        Request request = new Request.Builder()
                .url(SERVER_URL + SERVER_URL_REMOVE_PRODUCT)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        if (response.code() != 200) {
            String error = Objects.requireNonNull(response.body()).string();
            Objects.requireNonNull(response.body()).close();
            throw new Exception(error);
        }
    }


    /**
     * Request the server to update a order state, throw the order id
     *
     * @param session  User session
     * @param orderId  Order id
     * @param newState New order state
     * @throws Exception {@link IOException} if request fails and {@link Exception} if the requests returns error
     *                   code. Sets the request body as the exception message
     */
    public static void updateOrderState(String session, Integer orderId, OrderSate newState) throws Exception {
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("session", session)
                .add("newState", newState.toString())
                .build();
        Request request = new Request.Builder()
                .url(SERVER_URL + String.format(SERVER_URL_ORDER_STATE_UPDATE, orderId))
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        if (response.code() != 200) {
            String error = Objects.requireNonNull(response.body()).string();
            Objects.requireNonNull(response.body()).close();
            throw new Exception(error);
        }
    }
}