package org.example.client.utils;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.example.client.MockUtils;
import org.example.client.models.FactoryUser;
import org.example.client.models.Manager;
import org.example.client.models.Product;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import java.util.prefs.Preferences;

import static org.junit.jupiter.api.Assertions.*;

class SessionTest {


    private MockWebServer server;

    /**
     * Generate an authenticated user session
     *
     * @throws Exception Error setting up mock response
     */
    @BeforeEach
    void setUp() throws Exception {
        this.server = MockUtils.startServer();

        this.server.enqueue(new MockResponse()
                .setBody(new JSONObject()
                        .put("username", "guest")
                        .put("session", "session")
                        .put("responsabile", false)
                        .put("name", "Name")
                        .put("surname", "Surname")
                        .put("address", "Via Viale 1")
                        .put("cap", 33333)
                        .put("city", "City")
                        .put("telephone", "3334445555")
                        .put("payment", 1)
                        .put("loyalty_card_number", 1234)
                        .toString()));
        Session session = Session.getInstance();
        // Set the user session
        session.setUser(new FactoryUser().getUser("session"));
    }

    @AfterEach
    void tearDown() throws IOException {
        this.server.shutdown();
        Session.destroyInstance();
    }


    @Test
    void addProduct() {
        Session session = Session.getInstance();
        Product product = new Product(1, "Name", "Brand", 1, 1., null,
                1, "", "Section");

        assertEquals(0, session.getCartQuantity().get());
        assertEquals(1, session.addProduct(product, 1));
        assertEquals(1, session.getCartQuantity().get());
        assertEquals(3, session.addProduct(product, 2));
        assertEquals(3, session.getCartQuantity().get());

        product = new Product(2, "Name", "Brand", 1, 1., null,
                1, "", "Section");

        assertEquals(2, session.addProduct(product, 2));
        assertEquals(5, session.getCartQuantity().get());
    }

    @Test
    void getInstance() {
        assertNotNull(Session.getInstance());
    }


    @Test
    void getInstanceSavedPreference() {
        this.server.enqueue(new MockResponse()
                .setBody(new JSONObject()
                        .put("username", "guest")
                        .put("session", "session")
                        .put("responsabile", false)
                        .put("name", "Name")
                        .put("surname", "Surname")
                        .put("address", "Via Viale 1")
                        .put("cap", 33333)
                        .put("city", "City")
                        .put("telephone", "3334445555")
                        .put("payment", 1)
                        .put("loyalty_card_number", 1234)
                        .toString()));
        Session.destroyInstance();
        // Simulate the saved session
        Preferences preferences = Preferences.userNodeForPackage(Session.class);
        preferences.put(Session.PREFERENCE_USER_SESSION, "session");
        Session session = Session.getInstance();
        assertTrue(session.isAuth());
    }

    @Test
    void isAuth() {
        assertTrue(Session.getInstance().isAuth());
    }

    @Test
    void getCartQuantity() {
        Session session = Session.getInstance();
        assertEquals(0, session.getCartQuantity().get());
    }

    @Test
    void destroyInstance() {
        Session.destroyInstance();
        Preferences preferences = Preferences.userNodeForPackage(Session.class);
        assertNull(preferences.get(Session.PREFERENCE_USER_SESSION, null));
        assertNull(preferences.get(Session.PREFERENCE_SAVE_SESSION, null));
    }

    @Test
    void getUser() {
        assertNotNull(Session.getInstance().getUser());
    }

    @Test
    void setUser() {
        Session session = Session.getInstance();
        Manager manager = new Manager("admin", "session", "D34DB33F", "Name", "Surname", "Via Viale 1", 33333, "City",
                "3334445555", "Admin");
        session.setUser(manager);
        assertEquals(manager, session.getUser());
    }

    @Test
    void isSaveSession() {
        assertTrue(Session.getInstance().isSaveSession());
    }

    @Test
    void setSaveSession() {
        Session session = Session.getInstance();
        session.setSaveSession(false);
        assertFalse(session.isSaveSession());
        Preferences preferences = Preferences.userNodeForPackage(Session.class);
        assertFalse(preferences.getBoolean(Session.PREFERENCE_SAVE_SESSION, true));
    }

    @Test
    void getProducts() {
        Session session = Session.getInstance();
        Product product = new Product(1, "Name", "Brand", 1, 1., null,
                1, "", "Section");

        assertEquals(1, session.addProduct(product, 1));
        assertEquals(2, session.addProduct(product, 1));
        List<Product> products = session.getProducts();
        assertEquals(1, products.size());
        assertEquals(product, products.get(0));
    }
}