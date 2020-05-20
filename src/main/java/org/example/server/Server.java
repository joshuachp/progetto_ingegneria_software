package org.example.server;


import org.hibernate.jpa.HibernateQuery;

public class Server {

    // Signleton del server

    // onStart
    // crea istanza database

    // ENDPOINTS:
    // - autenticazione

    /**
     * Autentica un utente. Controlla la password con l'hash della password nel database, se coincidono ritorna una
     * token sessione utente.
     *
     * @param username Username dell'utente
     * @param password Password dell'utente
     * @return TODO: Define the return data
     */
    boolean autenticateUser(String username, String password) {
        HibernateQuery()
        return BCrypt.checkpw(password, hash);
    }

    // - deautenticazione
}
