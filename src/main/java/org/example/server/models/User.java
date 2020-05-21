package org.example.server.models;

import org.example.server.Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class User {

    private final Integer id;
    private String username;
    private String password;
    private boolean responsabile;
    private String session;

    public User(Integer id, String username, String password, boolean responsabile, String session) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.responsabile = responsabile;
        this.session = session;
    }

    public static User getUser(String username) {
        Database database = Database.getInstance();
        try {
            PreparedStatement statement = database.getConnection().prepareStatement("SELECT id, username, password, " +
                    "responsabile, session FROM users WHERE username = ?");
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new User(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3),
                        resultSet.getBoolean(4), resultSet.getString(5));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean getResponsabile() {
        return responsabile;
    }

    public void setResponsabile(boolean responsabile) {
        this.responsabile = responsabile;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public Integer getId() {
        return id;
    }
}
