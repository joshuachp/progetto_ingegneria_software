package org.example.client.models;

import org.example.client.controllers.RegistrazioneController;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test suit per la classe RegistrazioneController
 */
public class RegistrationControllerTest {
    @Test
    public void testPhoneVerify(){
        RegistrazioneController registrazioneController = new RegistrazioneController();
        assertNotNull(registrazioneController);
        assertTrue(registrazioneController.phoneVerify("+393461650042"));
        //TODO: ignore white spaces
        //assertTrue(registrazioneController.phoneVerify("+39 346 1650042"));
        assertFalse(registrazioneController.phoneVerify("+3934616500425"));
        assertTrue(registrazioneController.phoneVerify("3461650042"));
    }

    @Test
    public void testCapVerify(){
        RegistrazioneController registrazioneController = new RegistrazioneController();
        assertNotNull(registrazioneController);
        assertTrue(registrazioneController.capVerify("37019"));
        assertFalse(registrazioneController.capVerify("370192"));
    }

    @Test
    public void testPasswordVerify(){
        RegistrazioneController registrazioneController = new RegistrazioneController();
        assertNotNull(registrazioneController);
        assertFalse(registrazioneController.passwordVerify("asdse"));
        assertTrue(registrazioneController.passwordVerify("x?34c20&@"));
    }

    @Test
    public void testPasswordEquals(){
        RegistrazioneController registrazioneController = new RegistrazioneController();
        assertNotNull(registrazioneController);
        assertTrue(registrazioneController.passwordVerifyEquals("1234abcd", "1234abcd"));
        assertFalse(registrazioneController.passwordVerifyEquals("1234ascd", "1234abcd"));
    }
}
