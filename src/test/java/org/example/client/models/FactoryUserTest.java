package org.example.client.models;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.example.client.MockUtils;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;


/**
 * Test suit per la classe FactoryUtente
 */
public class FactoryUserTest {

    private MockWebServer server;

    @BeforeEach
    void setUp() throws Exception {
        this.server = MockUtils.startServer();
    }

    @AfterEach
    void tearDown() throws IOException {
        this.server.shutdown();
    }

    /**
     * Controlla che getUtente sia eseguito correttamente
     */
    @Test
    public void testGetUser() {
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

        User user = new FactoryUser().getUser("admin", "password");
        assertNotNull(user);
        assertEquals("admin", user.getUsername());
        assertEquals("session", user.getSession());
        assertTrue(user.isResponsabile());
    }

    @Test
    public void testGetUserError() {
        this.server.enqueue(new MockResponse().setResponseCode(400));
        User user = new FactoryUser().getUser("test", "test");
        assertNull(user);
    }

    /**
     * Controlla che getUtenteSession sia eseguito correttamente
     */
    @Test
    public void testGetUserSession() {
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

        User user = new FactoryUser().getUser("session");
        assertNotNull(user);
        assertEquals("admin", user.getUsername());
        assertEquals("session", user.getSession());
        assertTrue(user.isResponsabile());
    }

    /**
     * Controlla che l'utente ritornato sia un cliente
     */
    @Test
    public void testGetUserCliente() {
        this.server.enqueue(new MockResponse()
                .setBody(new JSONObject()
                        .put("username", "guest")
                        .put("session", "session")
                        .put("responsabile", false)
                        .put("name", "Name")
                        .put("surname", "Surname")
                        .put("address", "Via Viale 1")
                        .put("cap", 33333)
                        .put("city", "City")
                        .put("telephone", "3334445555")
                        .put("payment", 1)
                        .put("loyalty_card_number", 1234)
                        .toString()));

        User user = new FactoryUser().getUser("guest", "guest");
        assertNotNull(user);
        assertFalse(user.isResponsabile());
        assertTrue(user instanceof Client);
    }

    /**
     * Controlla che l'utente ritornato sia un Responsabile
     */
    @Test
    public void testGetUserResponsabileReparto() {
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
        User user = new FactoryUser().getUser("admin", "password");
        assertNotNull(user);
        assertTrue(user.isResponsabile());
        assertTrue(user instanceof Manager);
    }


}