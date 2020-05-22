package org.example.server.models;

import junit.framework.TestCase;
import org.example.server.MockDatabase;

public class UserTest extends TestCase {

    public void testGetUser() {
        MockDatabase.createMockDatabase();
        User user = User.getUser("admin");
        assertNotNull(user);
        assertEquals("admin", user.getUsername());
    }

    public void testUpdateUser() {
        MockDatabase.createMockDatabase();
        User admin = User.getUser("admin");
        assertNotNull(admin);
        admin.setUsername("username");
        admin.setPassword("password");
        admin.setResponsabile(false);
        admin.updateUser();
        User user = User.getUser("username");
        assertNotNull(user);
        assertEquals("username", user.getUsername());
        assertEquals("password", user.getPassword());
        assertFalse(user.getResponsabile());
    }
}