package org.example.models;

import junit.framework.TestCase;
import org.example.utils.Utils;
import org.json.JSONObject;

public class FactoryUtenteTest extends TestCase {

    public void testGetUtente() {
        Utente utente = new FactoryUtente().getUtente("admin", "password");
        assertEquals("admin", utente.getUsername());
        assertTrue(utente.isResponsabile());
    }

    public void testGetUtenteSession() {
        JSONObject session = Utils.autenticaWithServer("admin", "password");
        assertNotNull(session);
        Utente utente = new FactoryUtente().getUtente(session.getString("session"));
        assertEquals("admin", utente.getUsername());
        assertTrue(utente.isResponsabile());
    }
}