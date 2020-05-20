package org.example.models;


import org.example.utils.Utils;

public class FactoryUtente {

    // Tipologia dell'utente autenticato
    private UtenteType type;

    /**
     * Autentica un Utente tramite username e password. Ritorna una istanza della classe Utente in base alla tipologia
     * data dal server.
     *
     * @param username Username dell'utente
     * @param password Password dell'utente
     * @return Istanza della classe Utente
     */
    public Utente getUtente(String username, String password) {
        String session = this.autenticazione(username, password);
        if (this.type == UtenteType.ResponsabileReparto) {
            return new ResponsabileReparto(username, session);
        }
        return new Cliente(username, session);
    }

    /**
     * Autentica un Utente tramite username e password. Ritorna una istanza della classe Utente in base alla tipologia
     * data dal server.
     *
     * @param session Il token di sessione
     * @return Istanza della classe Utente
     */
    public Utente getUtente(String session) {
        String username = this.autenticazione(session);
        if (this.type == UtenteType.ResponsabileReparto) {
            return new ResponsabileReparto(username, session);
        }
        return new Cliente(username, session);
    }


    /**
     * Autentica l'utente con username e password al server
     *
     * @param username Username dell'utente
     * @param password Password dell'utente
     * @return Il token della sessione
     */
    private String autenticazione(String username, String password) {
        this.type = username.equals("admin") ? UtenteType.ResponsabileReparto : UtenteType.Cliente;
        return Utils.autenticaWithServer(username, password);
    }

    /**
     * Autentica l'utente con il token di sessione al server
     *
     * @param sessione Il token di sessione
     * @return L'username dell'utente
     */
    private String autenticazione(String sessione) {
        // TODO: Il controllo dovrà essere fatto dal server
        this.type = sessione.equals("admin") ? UtenteType.ResponsabileReparto : UtenteType.Cliente;
        return Utils.autenticaWithServer(sessione);
    }
}
