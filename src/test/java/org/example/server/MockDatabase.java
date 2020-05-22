package org.example.server;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Classe per la creazione di un database per i test
 */
public class MockDatabase {

    private MockDatabase() {
    }

    /**
     * Crea il database per i test
     */
    public static void createMockDatabase() {
        Database database = Database.getInstance();
        try {
            // Si connette in un database in memoria (":memory:") non su file
            Connection connection = DriverManager.getConnection("jdbc:sqlite::memory:");
            database.setConnection(connection);
            setUpDatabase(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Inserisce le informazioni nel database di test
     *
     * @param connection Connessione al database di test
     * @throws SQLException Connessione al database fallita
     */
    private static void setUpDatabase(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        // Create user table
        statement.addBatch("CREATE TABLE users ( " +
                "id INTEGER PRIMARY KEY, " +
                "username TEXT NOT NULL, " +
                "password TEXT NOT NULL, " +
                "responsabile BOOLEAN NOT NULL)");
        // Add user responsabile admin:password
        statement.addBatch("INSERT INTO users (username, password, responsabile) " +
                "VALUES('admin', '$2b$10$swPp91a8qj40VkcBEn704eIFNOQ1Tvwxc2lZlQppIq/VgyLFLfzpS', 1)");
        // Add user cliente guest:guest
        statement.addBatch("INSERT INTO users (username, password, responsabile) " +
                "VALUES('guest', '$2y$12$34AOvePv2yzpQN9aN0ixD.DGmVUaBjWOLq5PImEo0wCfD3iB89HwK', 0)");
        statement.executeBatch();
    }
}
