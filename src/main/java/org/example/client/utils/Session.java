package org.example.client.utils;

import javafx.beans.property.SimpleIntegerProperty;
import org.example.client.models.FactoryUser;
import org.example.client.models.Product;
import org.example.client.models.User;

import java.util.HashMap;
import java.util.Map;
import java.util.prefs.Preferences;

// XXX: NOT THREAD SAFE
public class Session {

    public static final String PREFERENCE_SAVE_SESSION = "SAVE_SESSION";
    public static final String PREFERENCE_USER_SESSION = "USER_SESSION";
    public static final String PREFERENCE_PAYMENT_DATA = "PAYMENT_DATA";

    private static Session session = null;
    // Shopping cart
    private final Map<Integer, Product> mapProducts = new HashMap<>();
    private final SimpleIntegerProperty cartQuantity = new SimpleIntegerProperty(this, "cartQuantity", 0);
    private User user;
    private boolean saveSession;
    // Payment data
    private String paymentData;

    /**
     * Create a new session, if a session is saved in the preferences. It will ask the server for the user information.
     */
    private Session() {
        Preferences preferences = Preferences.userNodeForPackage(Session.class);

        this.saveSession = preferences.getBoolean(PREFERENCE_SAVE_SESSION, true);
        this.paymentData = preferences.get(PREFERENCE_PAYMENT_DATA, "0123 4567 8910 1112");
        String sessionToken = preferences.get(PREFERENCE_USER_SESSION, null);
        if (sessionToken == null || sessionToken.isEmpty()) {
            this.user = null;
        } else {
            this.user = new FactoryUser().getUser(sessionToken);
        }
        // Create empty product list
    }

    /**
     * Create or return a singleton for the session instance
     *
     * @return The Session singleton instance
     */
    public static Session getInstance() {
        if (session == null)
            session = new Session();
        return session;
    }

    /**
     * Destroy all the data of the current session, it will cleanup all the application preferences.
     */
    public static void destroyInstance() {
        // Remove saved preferences
        Preferences preferences = Preferences.userNodeForPackage(Session.class);
        preferences.remove(PREFERENCE_USER_SESSION);
        preferences.remove(PREFERENCE_SAVE_SESSION);
        preferences.remove(PREFERENCE_PAYMENT_DATA);
        // Reset the session
        session = null;
    }

    /**
     * Add the product to the list of products if not present and increments its quantity by 1.
     *
     * @param product The product to add.
     * @return The current product quantity
     */
    public Integer addProduct(Product product, Integer quantity) {
        mapProducts.putIfAbsent(product.getId(), product);
        Product prod = mapProducts.get(product.getId());
        prod.setQuantity(prod.getQuantity() + quantity);

        // Increase the cart total quantity
        this.cartQuantity.set(this.cartQuantity.get() + quantity);

        return product.getQuantity();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;

        // Save session only if needed
        if (this.saveSession) {
            Preferences preferences = Preferences.userNodeForPackage(Session.class);
            preferences.put(PREFERENCE_USER_SESSION, user.getSession());
        }
    }

    /**
     * Check if the session information should be saved.
     *
     * @return True if save session
     */
    public boolean isSaveSession() {
        return saveSession;
    }

    public void setSaveSession(boolean saveSession) {
        this.saveSession = saveSession;

        Preferences preferences = Preferences.userNodeForPackage(Session.class);
        preferences.putBoolean(PREFERENCE_SAVE_SESSION, saveSession);
    }

    /**
     * Returns true if the user is authenticated or a session is saved.
     *
     * @return True if authenticated
     */
    public boolean isAuth() {
        return this.user != null;
    }

    public SimpleIntegerProperty getCartQuantity() {
        return cartQuantity;
    }

    /**
     * Return list of products in the cart
     *
     * @return List of products
     */
    public Map<Integer, Product> getMapProducts() {
        return mapProducts;
    }

    public String getPaymentData() {
        return this.paymentData;
    }

    public void setPaymentData(String paymentData) {
        this.paymentData = paymentData;
    }
}
