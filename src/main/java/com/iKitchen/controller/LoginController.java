package com.iKitchen.controller;

import com.iKitchen.exception.DAOException;
import com.iKitchen.model.bean.CredentialsBean;
import com.iKitchen.model.dao.ConnectionFactory;
import com.iKitchen.model.dao.LoginProcedureDAO;
import com.iKitchen.model.dao.RecuperaIngredientiDispensaDAO;
import com.iKitchen.model.dao.RecuperaNomeDaUsernameDAO;
import com.iKitchen.model.domain.Credentials;
import java.io.IOException;
import java.sql.SQLException;

public class LoginController {

    // Dichiarazione
    private final RecuperaNomeDaUsernameDAO recuperaNomeDaUsernameDAO;

    // Costruttore per inizializzare il DAO
    public LoginController() {
        this.recuperaNomeDaUsernameDAO = new RecuperaNomeDaUsernameDAO();
    }

    public Credentials start(CredentialsBean credentialsBean) throws DAOException, IOException, SQLException {

        // Crea un'istanza di Credentials con i dati di login
        Credentials credentials = new Credentials(credentialsBean.getUsername(),credentialsBean.getPassword());

        // Esegui la procedura di login nel database
        try {
            new LoginProcedureDAO().execute(credentials);
        } catch (DAOException | SQLException e) {
            throw new IllegalArgumentException(e);
        }

        // Cambia il ruolo della connessione in base al ruolo dell'utente
        if (Credentials.getRole() != null) {
            try {
                ConnectionFactory.changeRole(Credentials.getRole());
            } catch (SQLException e) {
                throw new IllegalArgumentException(e);
            }
        }

        // Restituisci le credenziali con il ruolo impostato
        return credentials;
    }

    // Metodo per recuperare il nome associato allo username
    public void recuperaNome(CredentialsBean credentialsBean) throws DAOException, SQLException {

        // Estraggo l'informazione dal bean
        String nome = credentialsBean.getUsername();

        // Chiamo il DAO ed ottengo il nome associato allo username
        recuperaNomeDaUsernameDAO.execute(nome);
    }
}