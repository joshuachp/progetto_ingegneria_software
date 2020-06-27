package org.example.client.utils;

import org.example.client.models.FactoryUser;
import org.example.client.models.User;

import java.util.prefs.Preferences;

public class Session {

    public static final String PREFERENCE_SAVE_SESSION = "SAVE_SESSION";
    public static final String PREFERENCE_USER_SESSION = "USER_SESSION";

    private static Session session = null;
    private User user;
    private boolean saveSession;

    private Session() {
        Preferences preferences = Preferences.systemNodeForPackage(Session.class);

        this.saveSession = preferences.getBoolean(PREFERENCE_SAVE_SESSION, true);
        String sessionToken = preferences.get(PREFERENCE_USER_SESSION, "");
        if (sessionToken == null || sessionToken.isEmpty()) {
            this.user = null;
        } else {
            this.user = new FactoryUser().getUtente(sessionToken);
        }
    }


    public static Session getInstance() {
        if (session == null)
            session = new Session();
        return session;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;

        Preferences preferences = Preferences.systemNodeForPackage(Session.class);
        preferences.put(PREFERENCE_USER_SESSION, user.getSession());
    }

    public boolean isSaveSession() {
        return saveSession;
    }

    public void setSaveSession(boolean saveSession) {
        this.saveSession = saveSession;

        Preferences preferences = Preferences.systemNodeForPackage(Session.class);
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
}
