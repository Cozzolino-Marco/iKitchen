package com.ikitchen.controller;

import com.ikitchen.model.bean.BeanRicetta;
import com.ikitchen.model.bean.BeanRicette;
import com.ikitchen.model.utility.Credentials;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance (TestInstance.Lifecycle.PER_CLASS)
class OttieniRicettaControllerApplicativoTest {

    OttieniRicettaControllerApplicativo ricetta = null;

    @BeforeAll
    void setup() {
        ricetta = new OttieniRicettaControllerApplicativo();
        Credentials.setUsername("prova");
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
}