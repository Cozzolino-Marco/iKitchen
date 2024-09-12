package com.ikitchen.controller;

import static org.junit.jupiter.api.Assertions.*;
import com.ikitchen.exception.DAOException;
import com.ikitchen.model.bean.*;
import com.ikitchen.model.domain.Role;
import com.ikitchen.model.utility.Credentials;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import java.sql.SQLException;

@TestInstance (TestInstance.Lifecycle.PER_CLASS)
class LoginControllerTest {

    LoginController loginController = new LoginController();

    @Test
    void loginSuccessfulUtenteDomestico() {

        // Inizializzo un bean con le credenziali di un utente domestico che so che esiste
        CredentialsBean credentialsBean = new CredentialsBean();
        credentialsBean.setUsername("prova.prova@prova.com");
        credentialsBean.setPassword("prova");

        try {
            // Eseguo il metodo di login
            loginController.start(credentialsBean);
        } catch (DAOException | SQLException e) {
            fail("Errore durante il login: " + e.getMessage());
        }

        // Verifica che il ruolo dell'utente sia impostato correttamente
        assertEquals(Role.UTENTE, Credentials.getRole());
    }

    @Test
    void loginUnsuccessfulUtenteDomestico() {

        // Inizializzo un bean con le credenziali di un utente domestico che so che non esiste
        CredentialsBean credentialsBean = new CredentialsBean();
        credentialsBean.setUsername("utente.inesistente@nonesiste.com");
        credentialsBean.setPassword("nonesisto");

        // Azzero il ruolo perchè settato dal test di prima
        Credentials.setRole(null);

        try {
            // Eseguo il metodo di login
            loginController.start(credentialsBean);
        } catch (DAOException e) {
            // Verifica che il ruolo dell'utente sia impostato correttamente a null
            assertEquals(null, Credentials.getRole());
        } catch (SQLException e) {
            fail("Errore durante il login: " + e.getMessage());
        }
    }
}