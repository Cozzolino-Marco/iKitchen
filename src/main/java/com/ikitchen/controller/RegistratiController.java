package com.ikitchen.controller;

import com.ikitchen.exception.DAOException;
import com.ikitchen.model.bean.CredentialsBean;
import com.ikitchen.model.dao.RegistrazioneDAO;
import com.ikitchen.model.utility.Credentials;

import java.sql.SQLException;

public class RegistratiController {

    // Dichiarazione
    private final RegistrazioneDAO registrazioneDAO;

    // Costruttore per inizializzare il DAO
    public RegistratiController() {
        this.registrazioneDAO = new RegistrazioneDAO();
    }

    // Metodo per effettuare la registrazione
    public void effettuaRegistrazione(CredentialsBean credentialsBean) throws DAOException, SQLException {

        // Estraggo le informazioni dal bean
        String password = credentialsBean.getPassword();
        String ripetiPassword = credentialsBean.getRipetiPassword();

        // Verifica che le password coincidano e lancia un'eccezione se non coincidono
        if (!password.equals(ripetiPassword)) {
            throw new IllegalArgumentException("Le password non coincidono!");
        }

        // Imposta i valori su Credentials
        Credentials.setNome(credentialsBean.getNome());
        Credentials.setCognome(credentialsBean.getCognome());
        Credentials.setRole(credentialsBean.getRole());
        Credentials.setUsername(credentialsBean.getUsername());
        Credentials.setPassword(credentialsBean.getPassword());

        // Eseguo la query usando il DAO e ottengo il risultato
        registrazioneDAO.registrazioneExecute();
    }
}