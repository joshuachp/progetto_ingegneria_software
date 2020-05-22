package org.example.server.models;

import junit.framework.TestCase;
import org.example.server.MockDatabase;

/**
 * Test suit per la classe User
 */
public class UserTest extends TestCase {

    /**
     * Controlla che getUser sia eseguito correttamente
     */
    public void testGetUser() {
        MockDatabase.createMockDatabase();
        User user = User.getUser("admin");
        assertNotNull(user);
        assertEquals("admin", user.getUsername());
    }

    /**
     * Controlla che updateUser sia eseguito correttamente
     */
    public void testUpdateUser() {
        MockDatabase.createMockDatabase();
        User admin = User.getUser("admin");
        assertNotNull(admin);
        admin.setUsername("username");
        admin.setPassword("password");
        admin.setResponsabile(false);
        assertTrue(admin.updateUser());
        User user = User.getUser("username");
        assertNotNull(user);
        assertEquals("username", user.getUsername());
        assertEquals("password", user.getPassword());
        assertFalse(user.getResponsabile());
    }
}