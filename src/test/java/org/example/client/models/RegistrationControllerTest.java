package org.example.client.models;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.example.client.MockUtils;
import org.example.client.controllers.RegistrazioneController;
import org.example.client.utils.Utils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;


/**
 * Test suit per la classe RegistrazioneController
 */
public class RegistrationControllerTest {
    private MockWebServer server;

    @BeforeEach
    void setUpAll() throws Exception {
        this.server = MockUtils.startServer();
    }

    @AfterEach
    void tearDown() throws IOException {
        this.server.shutdown();
    }

    @Test
    public void testPhoneVerify() {
        RegistrazioneController registrazioneController = new RegistrazioneController();
        assertNotNull(registrazioneController);
        assertTrue(registrazioneController.phoneVerify("+393461650042"));
        //TODO: ignore white spaces
        //assertTrue(registrazioneController.phoneVerify("+39 346 1650042"));
        assertFalse(registrazioneController.phoneVerify("+3934616500425"));
        assertTrue(registrazioneController.phoneVerify("3461650042"));
    }

    @Test
    public void testCapVerify() {
        RegistrazioneController registrazioneController = new RegistrazioneController();
        assertNotNull(registrazioneController);
        assertTrue(registrazioneController.capVerify("37019"));
        assertFalse(registrazioneController.capVerify("370192"));
    }

    @Test
    public void testPasswordVerify() {
        RegistrazioneController registrazioneController = new RegistrazioneController();
        assertNotNull(registrazioneController);
        assertFalse(registrazioneController.passwordVerify("asdse"));
        assertTrue(registrazioneController.passwordVerify("Ax?34c20&@"));
    }

    @Test
    public void testPasswordEquals() {
        RegistrazioneController registrazioneController = new RegistrazioneController();
        assertNotNull(registrazioneController);
        assertTrue(registrazioneController.passwordVerifyEquals("1234abcd", "1234abcd"));
        assertFalse(registrazioneController.passwordVerifyEquals("1234ascd", "1234abcd"));
    }

    @Test
    public void handleConfirmAction() {
        String email = "jondoe@gmail.com";
        String password = "12345678";
        String name = "Jon";
        String surname = "Doe";
        String address = "myHome, 12";
        int cap = 37530;
        String city = "New York";
        String phone = "3490327543";
        int spesa = 1;

        this.server.enqueue(new MockResponse()
                .setBody("OK"));
        assertEquals(200, (Utils.registerClient(email, password, name, surname,
                address, cap, city, phone, spesa)));

    }
}
