package org.example.server;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Singleton of the server database
 */
public class Database {

    /**
     * Static instance of the database, lazy initialized
     */
    private static Database database = null;

    /**
     * Connection to the SQL database
     */
    private Connection connection;

    /**
     * Create an instance of the database and connects it to the SQL database
     *
     * @throws SQLException The connection to the database failed
     */
    private Database() throws SQLException {
        // TODO: Better handling of the location of the database, for test remember to initialize the database. The
        //  database should be in the main folder of the program.
        this.connection = DriverManager.getConnection("jdbc:sqlite:app.db");
    }

    /**
     * Ritorna o genera una istanza del database
     *
     * @return Il database instantiate
     */
    public static Database getInstance() {
        // If the database is not initialized it will create a new instance
        if (database == null) {
            // We check here the exception to simplify the constructor
            try {
                database = new Database();
            } catch (SQLException e) {
                // if the error message is "out of memory",
                // it probably means no database file is found
                e.printStackTrace();
            }
        }
        return database;
    }

    public Connection getConnection() {
        return connection;
    }

}
