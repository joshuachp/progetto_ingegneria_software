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
public class FactoryUtenteTest {

    private MockWebServer server;

    @BeforeEach
    void setUp() throws IOException {
        this.server = new MockWebServer();
        this.server.start(InetAddress.getByName("localhost.local"), 8080);
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
        server.enqueue(new MockResponse()
                .setBody(new JSONObject()
                        .put("username", "admin")
                        .put("session", "session")
                        .put("responsabile", true)
                        .toString()));

        Utente utente = new FactoryUtente().getUtente("admin", "password");
        assertNotNull(utente);
        assertEquals("admin", utente.getUsername());
        assertEquals("session", utente.getSession());
        assertTrue(utente.isResponsabile());
    }

    /**
     * Controlla che getUtenteSession sia eseguito correttamente
     */
    @Test
    public void testGetUtenteSession() {
        server.enqueue(new MockResponse()
                .setBody(new JSONObject()
                        .put("username", "admin")
                        .put("session", "session")
                        .put("responsabile", true)
                        .toString()));
        server.enqueue(new MockResponse()
                .setBody(new JSONObject()
                        .put("username", "admin")
                        .put("session", "session")
                        .put("responsabile", true)
                        .toString()));

        JSONObject session = Utils.autenticaWithServer("admin", "password");
        assertNotNull(session);
        Utente utente = new FactoryUtente().getUtente(session.getString("session"));
        assertEquals("admin", utente.getUsername());
        assertEquals("session", utente.getSession());
        assertTrue(utente.isResponsabile());
    }

    /**
     * Controlla che l'utente ritornato sia un cliente
     */
    @Test
    public void testGetUtenteCliente() {
        server.enqueue(new MockResponse()
                .setBody(new JSONObject()
                        .put("username", "guest")
                        .put("session", "session")
                        .put("responsabile", false)
                        .toString()));

        Utente utente = new FactoryUtente().getUtente("guest", "guest");
        assertFalse(utente.isResponsabile());
        assertTrue(utente instanceof Cliente);
    }

    /**
     * Controlla che l'utente ritornato sia un Responsabile
     */
    @Test
    public void testGetUtenteResponsabileReparto() {
        server.enqueue(new MockResponse()
                .setBody(new JSONObject()
                        .put("username", "admin")
                        .put("session", "session")
                        .put("responsabile", true)
                        .toString()));

        Utente utente = new FactoryUtente().getUtente("admin", "password");
        assertTrue(utente.isResponsabile());
        assertTrue(utente instanceof ResponsabileReparto);
    }


}