package org.example.client.models;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.example.client.utils.Utils;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.InetAddress;

import static org.junit.jupiter.api.Assertions.*;


/**
 * Test suit per la classe FactoryUtente
 */
public class FactoryUserTest {

    private MockWebServer server;

    @BeforeEach
    void setUp() throws Exception {
        this.server = new MockWebServer();
        this.server.start(InetAddress.getByName("localhost"), 8080);
    }

    @AfterEach
    void tearDown() throws IOException {
        this.server.shutdown();
    }

    /**
     * Controlla che getUtente sia eseguito correttamente
     */
    @Test
    public void testGetUtente() {
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

        User user = new FactoryUser().getUtente("admin", "password");
        assertNotNull(user);
        assertEquals("admin", user.getUsername());
        assertEquals("session", user.getSession());
        assertTrue(user.isResponsabile());
    }

    /**
     * Controlla che getUtenteSession sia eseguito correttamente
     */
    @Test
    public void testGetUtenteSession() {
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

        JSONObject session = Utils.authenticate("admin", "password");
        assertNotNull(session);
        User user = new FactoryUser().getUtente(session.getString("session"));
        assertEquals("admin", user.getUsername());
        assertEquals("session", user.getSession());
        assertTrue(user.isResponsabile());
    }

    /**
     * Controlla che l'utente ritornato sia un cliente
     */
    @Test
    public void testGetUtenteCliente() {
        this.server.enqueue(new MockResponse()
                .setBody(new JSONObject()
                        .put("username", "guest")
                        .put("session", "session")
                        .put("responsabile", false)
                        .put("badge", "D34DB33F")
                        .put("name", "Name")
                        .put("surname", "Surname")
                        .put("address", "Via Viale 1")
                        .put("cap", 33333)
                        .put("city", "City")
                        .put("telephone", "3334445555")
                        .put("role", "Admin")
                        .toString()));

        User user = new FactoryUser().getUtente("guest", "guest");
        assertFalse(user.isResponsabile());
        assertTrue(user instanceof Client);
    }

    /**
     * Controlla che l'utente ritornato sia un Responsabile
     */
    @Test
    public void testGetUtenteResponsabileReparto() {
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


        User user = new FactoryUser().getUtente("admin", "password");
        assertTrue(user.isResponsabile());
        assertTrue(user instanceof Manager);
    }


}