package com.ikitchen.controller;

import com.ikitchen.exception.DAOException;
import com.ikitchen.model.bean.BeanIngrediente;
import com.ikitchen.model.bean.BeanIngredienti;
import com.ikitchen.model.bean.BeanRicetta;
import com.ikitchen.model.bean.BeanRicette;
import com.ikitchen.model.utility.Credentials;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance (TestInstance.Lifecycle.PER_CLASS)
class OttieniRicettaControllerApplicativoTest {

    OttieniRicettaControllerApplicativo ricetta = null;

    @BeforeAll
    void setup() {
        ricetta = new OttieniRicettaControllerApplicativo();
        Credentials.setUsername("prova.prova@prova.com");
    }

    @Test
    void mostraRicetteSuccess() {

        // Inizializzazione di un oggetto BeanRicetta
        BeanRicette infoPerListaRicette = new BeanRicette();

        // Impostazione manuale dei valori del bean
        infoPerListaRicette.setCategoria("Primi piatti");
        infoPerListaRicette.setProvenienza("Da chef");
        infoPerListaRicette.setFiltraggio("Tutte le ricette");
        infoPerListaRicette.setStorage("Solo dal database");

        // Variabile per salvare il risultato del metodo mostraRicette
        BeanRicette listaRicette = null;

        // Tentativo di recuperare le ricette tramite il metodo del controller applicativo
        try {
            listaRicette = ricetta.mostraRicette(infoPerListaRicette);
        } catch (Exception e) {
            fail();
        }

        // Verifica che il risultato non sia null, il che indica che è presente almeno una ricetta nel DB
        assertNotEquals(null, listaRicette);
    }

    @Test
    void ottieniDettagliRicettaSuccess() {

        // Inizializzazione di un oggetto BeanRicetta
        BeanRicetta infoPerRicetta = new BeanRicetta();

        // Impostazione manuale dei valori del bean
        infoPerRicetta.setCodice("6");
        infoPerRicetta.setCategoria("Primi piatti");

        // Variabile per salvare il risultato del metodo ottieniDettagliRicetta
        BeanRicetta dettagliRicetta = null;

        // Tentativo di recuperare i dettagli della ricetta tramite il metodo del controller applicativo
        try {
            dettagliRicetta = ricetta.ottieniDettagliRicetta(infoPerRicetta);
        } catch (Exception e) {
            fail();
        }

        // Verifica che i risultati principali della ricetta siano quelli aspettati
        assertEquals("Amatriciana", dettagliRicetta.getTitolo());
        assertEquals("Gennaro Olivieri", dettagliRicetta.getCuoco());
        assertEquals(45, dettagliRicetta.getDurataPreparazione());
        assertEquals(650, dettagliRicetta.getCalorie());
    }

    @Test
    void verificaQuantitaSuccess() throws DAOException, SQLException {

        // Inizializzazione di un oggetto BeanRicetta per l'amatriciana
        BeanRicetta infoPerRicetta = new BeanRicetta();
        infoPerRicetta.setCodice("6");
        infoPerRicetta.setCategoria("Primi piatti");

        // Recupero i dettagli della ricetta
        BeanRicetta dettagliRicetta = ricetta.ottieniDettagliRicetta(infoPerRicetta);

        // Tentativo di verificare la quantità degli ingredienti della dispensa per la ricetta "Amatriciana"
        BeanIngredienti ingredientiValidi = null;
        try {
            ingredientiValidi = ricetta.verificaQuantita(dettagliRicetta);
        } catch (Exception e) {
            fail("Errore nella verifica delle quantità degli ingredienti.");
        }

        // Verifica che il risultato non sia null
        assertNotNull(ingredientiValidi);

        // Verifica che la lista degli ingredienti contenga esattamente 7 ingredienti per l'amatriciana
        assertEquals(7, ingredientiValidi.getListIngredienti().size());

        // Verifica che ogni ingrediente abbia la validità corretta
        for (BeanIngrediente ingrediente : ingredientiValidi.getListIngredienti()) {
            switch (ingrediente.getNome()) {
                case "Pomodoro":
                    assertTrue(ingrediente.isValido(), "Pomodoro dovrebbe esser valido");
                    break;
                case "Spaghetti":
                    assertTrue(ingrediente.isValido(), "Spaghetti dovrebbero essere validi");
                    break;
                case "Guanciale":
                    assertTrue(ingrediente.isValido(), "Guanciale dovrebbe essere valido");
                    break;
                case "Vino bianco":
                    assertTrue(ingrediente.isValido(), "Vino bianco dovrebbe esser valido");
                    break;
                case "Peperoncino":
                    assertTrue(ingrediente.isValido(), "Peperoncino dovrebbe essere valido");
                    break;
                case "Pecorino":
                    assertTrue(ingrediente.isValido(), "Pecorino dovrebbe esser valido");
                    break;
                case "Sale":
                    assertTrue(ingrediente.isValido(), "Sale dovrebbe essere valido");
                    break;
                default:
                    fail("Ingrediente sconosciuto trovato: " + ingrediente.getNome());
            }
        }
    }
}