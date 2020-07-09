package org.example.client.utils;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.example.client.MockUtils;
import org.example.client.models.Client;
import org.example.client.models.Manager;
import org.example.client.models.Order;
import org.example.client.models.enums.OrderSate;
import org.example.client.models.enums.Payment;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class UtilsTest {

    private MockWebServer server;

    @BeforeEach
    void setUpAll() throws Exception {
        this.server = MockUtils.startServer();
    }

    @AfterEach
    void tearDown() throws IOException {
        this.server.shutdown();
    }

    @Test
    void autenticaWithServerUsernamePassword() {
        this.server.enqueue(new MockResponse()
                .setBody(new JSONObject()
                        .put("username", "admin")
                        .put("session", "session")
                        .put("responsabile", true)
                        .put("badge", "D34DB33F")
                        .put("name", "Name")
                        .put("surname", "Surname")
                        .put("address", "Via Viale 1")
                        .put("cap", 33333)
                        .put("city", "City")
                        .put("telephone", "3334445555")
                        .put("role", "Admin")
                        .toString()));
        assertNotNull(Utils.authenticate("admin", "password"));
    }

    @Test
    void autenticaWithServerSession() {
        this.server.enqueue(new MockResponse()
                .setBody(new JSONObject()
                        .put("username", "admin")
                        .put("session", "session")
                        .put("responsabile", true)
                        .put("badge", "D34DB33F")
                        .put("name", "Name")
                        .put("surname", "Surname")
                        .put("address", "Via Viale 1")
                        .put("cap", 33333)
                        .put("city", "City")
                        .put("telephone", "3334445555")
                        .put("role", "Admin")
                        .toString()));
        assertNotNull(Utils.authenticate("session"));
    }

    @Test
    void updateUser() {
        this.server.enqueue(new MockResponse()
                .setBody("OK"));
        this.server.enqueue(new MockResponse()
                .setBody("OK"));

        Client client = new Client("username", "session", "name", "surname",
                "address", 33333, "City", "3334445555", 0, 1234);
        assertNotNull(Utils.updateUser(client, "password"));

        Manager manager = new Manager("username", "session", "badge", "name", "surname",
                "address", 33333, "City", "3334445555", "admin");
        assertNotNull(Utils.updateUser(manager, null));
    }

    @Test
    public void registerClient() {
        this.server.enqueue(new MockResponse()
                .setBody("OK"));
        assertEquals(200, (Utils.registerClient("username", "password", "Name", "Surname", "Address", 33333, "City",
                "3334445555", 1, 1234)));
    }


    @Test
    void getAllProducts() {
        // TODO
    }

    @Test
    void getLoyaltyCard() {
        // TODO
    }

    @Test
    void logOut() {
        // TODO
    }

    @Test
    void getAllOrders() throws Exception {
        JSONObject body = new JSONObject();
        body.append("orders", new JSONObject()
                .put("id", 1)
                .put("total", 3.80f)
                .put("payment", 0)
                .put("deliveryStart", 0)
                .put("deliveryEnd", 0)
                .put("state", 0));
        this.server.enqueue(new MockResponse()
                .setBody(body.toString()));

        List<Order> orders = Utils.getAllOrders("session");
        assertEquals(1, orders.size());
        Order order = orders.get(0);
        assertEquals(1, order.getId());
        assertEquals(3.80f, order.getTotal());
        assertEquals(Payment.CASH, order.getPayment());
        assertEquals(new Date(0), order.getDeliveryStart());
        assertEquals(new Date(0), order.getDeliveryEnd());
        assertEquals(OrderSate.CONFIRMED, order.getState());
    }
}