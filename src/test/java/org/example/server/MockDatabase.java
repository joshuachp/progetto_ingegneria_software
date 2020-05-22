package org.example.server;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class MockDatabase {

    private MockDatabase() {
    }

    public static void createMockDatabase() {
        Database database = Database.getInstance();
        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite::memory:");
            database.setConnection(connection);
            setUpDatabase(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void setUpDatabase(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        statement.addBatch("CREATE TABLE users ( " +
                "id INTEGER PRIMARY KEY, " +
                "username TEXT NOT NULL, " +
                "password TEXT NOT NULL, " +
                "responsabile BOOLEAN NOT NULL)");
        statement.addBatch("INSERT INTO users (username, password, responsabile) " +
                "VALUES('admin', '$2b$10$swPp91a8qj40VkcBEn704eIFNOQ1Tvwxc2lZlQppIq/VgyLFLfzpS', 1)");
        statement.executeBatch();
    }
}
