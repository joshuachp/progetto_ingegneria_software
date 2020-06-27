package org.example.client.utils;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.example.client.models.Client;
import org.example.client.models.Manager;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.InetAddress;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class UtilsTest {

    private MockWebServer server;

    @BeforeEach
    void setUpAll() throws Exception {
        this.server = new MockWebServer();
        this.server.start(InetAddress.getByName("localhost"), 8080);
    }

    @AfterEach
    void tearDown() throws IOException {
        this.server.shutdown();
    }

    @Test
    void testAutenticaWithServerUsernamePassword() {
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
    void testAutenticaWithServerSession() {
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
    void testUpdateUser() {
        this.server.enqueue(new MockResponse()
                .setBody("OK"));
        this.server.enqueue(new MockResponse()
                .setBody("OK"));

        Client client = new Client("username", "session", "name", "surname",
                "address", 33333, "City", "3334445555");
        assertNotNull(Utils.updateUser(client, "password"));

        Manager manager = new Manager("username", "session", "badge", "name", "surname",
                "address", 33333, "City", "3334445555", "admin");
        assertNotNull(Utils.updateUser(manager, null));
    }
}