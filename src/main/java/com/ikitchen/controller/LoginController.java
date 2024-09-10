package com.ikitchen.controller;

import com.ikitchen.exception.DAOException;
import com.ikitchen.model.bean.CredentialsBean;
import com.ikitchen.model.dao.ConnectionFactory;
import com.ikitchen.model.dao.LoginProcedureDAO;
import com.ikitchen.model.dao.RecuperaNomeDaUsernameDAO;
import com.ikitchen.model.utility.Credentials;
import java.sql.SQLException;

public class LoginController {

    // Costruttore per inizializzare il DAO
    private final RecuperaNomeDaUsernameDAO recuperaNomeDaUsernameDAO;
    public LoginController() {
        this.recuperaNomeDaUsernameDAO = new RecuperaNomeDaUsernameDAO();
    }

    // Metodo per effettuare il login
    public void start(CredentialsBean credB) throws DAOException, SQLException {

        // Setta le credenziali dal bean
        Credentials.setUsername(credB.getUsername());
        Credentials.setPassword(credB.getPassword());

        // Esegui la procedura di login nel database
        new LoginProcedureDAO().loginExecute();

        // Cambia il ruolo della connessione in base al ruolo dell'utente
        if (Credentials.getRole() != null) {
            try {
                ConnectionFactory.changeRole(Credentials.getRole());
            } catch (SQLException e) {
                throw new IllegalArgumentException(e);
            }
        }
    }

    // Metodo per recuperare il nome associato allo username
    public void recuperaNome(CredentialsBean credentialsBean) throws DAOException, SQLException {

        // Estraggo l'informazione dal bean
        String username = credentialsBean.getUsername();

        // Chiamo il DAO ed ottengo il nome associato allo username
        recuperaNomeDaUsernameDAO.recuperaNomeExecute(username);
    }
}