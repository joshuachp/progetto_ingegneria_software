package org.example.client.models;

import org.example.client.controllers.RegistrazioneController;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.InetAddress;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test suit per la classe RegistrazioneController
 */
public class RegistrationControllerTest {
    @Test
    public void testVerifyPhone(){
        RegistrazioneController registrazioneController = new RegistrazioneController();
        assertNotNull(registrazioneController);
        registrazioneController.Phone.setText("3461958742");
        assertTrue(registrazioneController.phoneVerify());
    }
}
