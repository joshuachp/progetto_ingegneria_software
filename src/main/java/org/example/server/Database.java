package org.example.server;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Singleton of the server database
 */
public class Database {

    /**
     * Url for the database connection
     */
    private static final String DATABASE_URL = "jdbc:sqlite:app.db";
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
        this.connection = DriverManager.getConnection(DATABASE_URL);
    }

    /**
     * Returns or creates an instance of the database
     *
     * @return The database instance
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


    /**
     * Imposta la connessione al database. Usato solamente nel test.
     *
     * @param connection Nuova connesione al database
     */
    protected void setConnection(Connection connection) {
        this.connection = connection;
    }

}
