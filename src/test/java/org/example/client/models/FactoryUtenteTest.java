package org.example.client.models;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.example.client.utils.Utils;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.InetAddress;

import static org.junit.jupiter.api.Assertions.*;


/**
 * Test suit per la classe FactoryUtente
 */
public class FactoryUtenteTest {

    private MockWebServer server;

    @Before
    public void setupTest() throws IOException {
        this.server = new MockWebServer();
        this.server.start(InetAddress.getByName("localhost.local"), 8080);
    }

    /**
     * Controlla che getUtente sia eseguito correttamente
     */
    @Test
    public void testGetUtente() throws IOException {
        server.enqueue(new MockResponse()
                .setBody(new JSONObject()
                        .put("username", "admin")
                        .put("session", "session")
                        .put("responsabile", true)
                        .toString()));

        Utente utente = new FactoryUtente().getUtente("admin", "password");
        assertNotNull(utente);
        assertEquals("admin", utente.getUsername());
        assertTrue(utente.isResponsabile());
    }

    /**
     * Controlla che getUtenteSession sia eseguito correttamente
     */
    @Test
    public void testGetUtenteSession() {
        JSONObject session = Utils.autenticaWithServer("admin", "password");
        assertNotNull(session);
        Utente utente = new FactoryUtente().getUtente(session.getString("session"));
        assertEquals("admin", utente.getUsername());
        assertTrue(utente.isResponsabile());
    }

    /**
     * Controlla che l'utente ritornato sia un cliente
     */
    @Test
    public void testGetUtenteCliente() {
        Utente utente = new FactoryUtente().getUtente("guest", "guest");
        assertFalse(utente.isResponsabile());
        assertTrue(utente instanceof Cliente);
    }

    /**
     * Controlla che l'utente ritornato sia un Responsabile
     */
    @Test
    public void testGetUtenteResponsabileReparto() {
        Utente utente = new FactoryUtente().getUtente("admin", "password");
        assertTrue(utente.isResponsabile());
        assertTrue(utente instanceof ResponsabileReparto);
    }

}