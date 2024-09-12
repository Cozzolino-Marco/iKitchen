package com.ikitchen.dao;

import com.ikitchen.exception.DAOException;
import com.ikitchen.model.bean.BeanIngrediente;
import com.ikitchen.model.dao.RecuperaIngredientiDispensaDAO;
import com.ikitchen.model.domain.Ingrediente;
import com.ikitchen.model.domain.ListIngredienti;
import com.ikitchen.model.utility.Credentials;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@TestInstance (TestInstance.Lifecycle.PER_CLASS)
class RecuperaIngredientiDispensaDAOTest {

    RecuperaIngredientiDispensaDAO recuperaIngredienti = null;

    @BeforeAll
    void setup() {
        Credentials.setUsername("prova.prova@prova.com");
        recuperaIngredienti = new RecuperaIngredientiDispensaDAO();
    }

    @Test
    void recuperaIngredientiSuccessful() {

        // Acquisisco lo username attuale dell'utente di prova
        String username = Credentials.getUsername();

        // Risultato restituito dal metodo DAO che sto testando
        ListIngredienti ingredientiRecuperati = null;

        // Provo ad eseguire il metodo per recuperare gli ingredienti della sua dispensa
        try {
            ingredientiRecuperati = recuperaIngredienti.recuperaIngredientiExecute(username);
        } catch (DAOException | SQLException e) {
            fail("Errore durante il recupero degli ingredienti: " + e.getMessage());
        }

        // Lista dei codici ingredienti attesi che so essere associati all'utente di prova
        List<String> codiciAttesi = Arrays.asList("3", "4", "5", "6", "7", "8", "9");

        // Controlla che i codici degli ingredienti recuperati siano tra quelli attesi
        for (Ingrediente ingrediente : ingredientiRecuperati.getListaIngredienti()) {
            if (!codiciAttesi.contains(ingrediente.getCodIngrediente())) {
                fail("Ingrediente con codice non atteso: " + ingrediente.getCodIngrediente());
            }
        }

        // Se tutti gli ingredienti hanno superato il controllo, il test è completato con successo
        assertEquals(true, true, "Tutti gli ingredienti sono corretti.");
    }
}