package org.example.client.utils;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.example.client.models.Client;
import org.example.client.models.Manager;
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
    void autenticaWithServer() {
    }

    @Test
    void testAutenticaWithServer() {
    }

    @Test
    void testUpdateUser() {
        this.server.enqueue(new MockResponse()
                .setBody("OK"));
        this.server.enqueue(new MockResponse()
                .setBody("OK"));
        Client client = new Client("username", "session", "name", "surname",
                "address", 33333, "City", "3334445555");
        assertNotNull(Utils.updateUser(client));
        Manager manager = new Manager("username", "session", "badge", "name", "surname",
                "address", 33333, "City", "3334445555", "admin");
        assertNotNull(Utils.updateUser(manager));
    }
}