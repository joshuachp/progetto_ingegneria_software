package org.example.client.models;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.example.client.MockUtils;
import org.example.client.controllers.RegistrationController;
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
        RegistrationController registrationController = new RegistrationController();
        assertNotNull(registrationController);
        assertTrue(registrationController.phoneVerify("+393461650042"));
        //TODO: ignore white spaces
        //assertTrue(registrazioneController.phoneVerify("+39 346 1650042"));
        assertFalse(registrationController.phoneVerify("+3934616500425"));
        assertTrue(registrationController.phoneVerify("3461650042"));
    }

    @Test
    public void testCapVerify() {
        RegistrationController registrationController = new RegistrationController();
        assertNotNull(registrationController);
        assertTrue(registrationController.capVerify("37019"));
        assertFalse(registrationController.capVerify("370192"));
    }

    @Test
    public void testPasswordVerify() {
        RegistrationController registrationController = new RegistrationController();
        assertNotNull(registrationController);
        assertFalse(registrationController.passwordVerify("asdse"));
        assertTrue(registrationController.passwordVerify("Ax?34c20&@"));
    }

    @Test
    public void testPasswordEquals() {
        RegistrationController registrationController = new RegistrationController();
        assertNotNull(registrationController);
        assertTrue(registrationController.passwordVerifyEquals("1234abcd", "1234abcd"));
        assertFalse(registrationController.passwordVerifyEquals("1234ascd", "1234abcd"));
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
