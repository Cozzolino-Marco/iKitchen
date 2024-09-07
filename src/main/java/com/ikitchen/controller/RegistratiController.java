package com.ikitchen.controller;

import com.ikitchen.exception.DAOException;
import com.ikitchen.model.bean.CredentialsBean;
import com.ikitchen.model.dao.RegistrazioneDAO;
import com.ikitchen.model.domain.Credentials;
import com.ikitchen.model.domain.Role;
import java.sql.SQLException;

public class RegistratiController {

    // Dichiarazione
    private final RegistrazioneDAO registrazioneDAO;

    // Costruttore per inizializzare il DAO
    public RegistratiController() {
        this.registrazioneDAO = new RegistrazioneDAO();
    }

    // Metodo per effettuare la registrazione
    public void effettuaRegistrazione() throws DAOException, SQLException {

        // Estraggo le informazioni dal bean
        String nome = CredentialsBean.getNome();
        String cognome = CredentialsBean.getCognome();
        Role ruolo = CredentialsBean.getRole();
        String username = CredentialsBean.getUsername();
        String password = CredentialsBean.getPassword();
        String ripetiPassword = CredentialsBean.getRipetiPassword();

        // Verifica che le password coincidano e lancia un'eccezione se non coincidono
        if (!password.equals(ripetiPassword)) {
            throw new IllegalArgumentException("Le password non coincidono!");
        }

        // Crea un'istanza di Credentials con i dati del profilo registrato
        Credentials credentials = new Credentials(username, password, ripetiPassword, ruolo, nome, cognome);

        // Eseguo la query usando il DAO e ottengo il risultato
        registrazioneDAO.execute(credentials);
    }
}