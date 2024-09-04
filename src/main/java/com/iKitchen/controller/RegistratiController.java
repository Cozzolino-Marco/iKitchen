package com.iKitchen.controller;

import com.iKitchen.exception.DAOException;
import com.iKitchen.model.bean.BeanRegistrazione;
import com.iKitchen.model.dao.RegistrazioneDAO;
import com.iKitchen.model.utility.Popup;

import java.sql.SQLException;

public class RegistratiController {

    // Dichiarazione
    private RegistrazioneDAO registrazioneDAO;

    // Costruttore per inizializzare il DAO
    public RegistratiController() {
        this.registrazioneDAO = new RegistrazioneDAO();
    }

    public void effettuaRegistrazione(BeanRegistrazione beanRegistrazione) throws DAOException, SQLException {

        // Estraggo le informazioni dal bean
        String nome = beanRegistrazione.getNome();
        String cognome = beanRegistrazione.getCognome();
        String ruolo = beanRegistrazione.getRuolo();
        String username = beanRegistrazione.getUsername();
        String password = beanRegistrazione.getPassword();
        String ripetiPassword = beanRegistrazione.getRipetiPassword();

        // Verifica che le password coincidano e lancia un'eccezione se non coincidono
        if (!password.equals(ripetiPassword)) {
            throw new IllegalArgumentException("Le password non coincidono!");
        }

        // Eseguo la query usando il DAO e ottengo il risultato
        registrazioneDAO.execute(nome, cognome, ruolo, username, password);
    }
}
