package org.example.client.models;

import junit.framework.TestCase;
import org.example.server.MockDatabase;
import org.example.client.utils.Utils;
import org.json.JSONObject;

/**
 * Test suit per la classe FactoryUtente
 */
public class FactoryUtenteTest extends TestCase {

    /**
     * Controlla che getUtente sia eseguito correttamente
     */
    public void testGetUtente() {
        MockDatabase.createMockDatabase();
        Utente utente = new FactoryUtente().getUtente("admin", "password");
        assertEquals("admin", utente.getUsername());
        assertTrue(utente.isResponsabile());
    }

    /**
     * Controlla che getUtenteSession sia eseguito correttamente
     */
    public void testGetUtenteSession() {
        MockDatabase.createMockDatabase();
        JSONObject session = Utils.autenticaWithServer("admin", "password");
        assertNotNull(session);
        Utente utente = new FactoryUtente().getUtente(session.getString("session"));
        assertEquals("admin", utente.getUsername());
        assertTrue(utente.isResponsabile());
    }

    /**
     * Controlla che l'utente ritornato sia un cliente
     */
    public void testGetUtenteCliente() {
        MockDatabase.createMockDatabase();
        Utente utente = new FactoryUtente().getUtente("guest", "guest");
        assertFalse(utente.isResponsabile());
        assertTrue(utente instanceof Cliente);
    }

    /**
     * Controlla che l'utente ritornato sia un Responsabile
     */
    public void testGetUtenteResponsabileReparto() {
        MockDatabase.createMockDatabase();
        Utente utente = new FactoryUtente().getUtente("admin", "password");
        assertTrue(utente.isResponsabile());
        assertTrue(utente instanceof ResponsabileReparto);
    }

}