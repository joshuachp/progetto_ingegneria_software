package org.example.models;

import junit.framework.TestCase;

public class FactoryUtenteTest extends TestCase {

    public void testGetUtente() {
        Utente utente = new FactoryUtente().getUtente("admin", "password");
        assertEquals("admin", utente.getUsername());
        assertEquals("session:admin", utente.getSession());
        assertTrue(utente.isResponsabile());
    }

    public void testGetUtenteSession() {
        Utente utente = new FactoryUtente().getUtente("session:admin");
        assertEquals("admin", utente.getUsername());
        assertEquals("session:admin", utente.getSession());
        assertTrue(utente.isResponsabile());
    }
}