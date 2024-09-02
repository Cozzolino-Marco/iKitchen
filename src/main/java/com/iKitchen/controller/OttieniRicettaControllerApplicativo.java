package com.iKitchen.controller;

import com.iKitchen.exception.DAOException;
import com.iKitchen.model.bean.BeanRicetta;
import com.iKitchen.model.bean.BeanRicette;
import com.iKitchen.model.bean.CredentialsBean;
import com.iKitchen.model.domain.*;
import com.iKitchen.view.OttieniRicettaControllerGraficoAPI;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;

public class OttieniRicettaControllerApplicativo {

    // Variabili
    FacadeOttieniRicetta facadeOttieniRicetta;
    ListRicette listRicette;
    Ricetta ricetta;

    // Gestione pattern Facade
    public OttieniRicettaControllerApplicativo() {
        facadeOttieniRicetta = new FacadeOttieniRicetta();
    }

    // Restituisce la lista di ricette in base ai filtri scelti
    public BeanRicette mostraRicette(BeanRicette infoPerListaRicette) throws DAOException, SQLException, IOException {

        // Estraggo le informazioni dal bean
        String categoria = infoPerListaRicette.getCategoria();
        String provenienza = infoPerListaRicette.getProvenienza();
        String filtro = infoPerListaRicette.getFiltraggio();
        String storage = infoPerListaRicette.getStorage(); // TODO: Implementa il metodo

        // Chiamata alla boundary dell'attore esterno
        if (provenienza.equals("Dal web")) {
            OttieniRicettaControllerGraficoAPI controllerAttoreSecondario = new OttieniRicettaControllerGraficoAPI();
            BeanRicette ricetteBean = controllerAttoreSecondario.recuperaRicette(infoPerListaRicette);
            return ricetteBean;
        }

        // Uso il facade per centralizzare i DAO delle procedure
        listRicette = facadeOttieniRicetta.mostraRicette(categoria, provenienza, filtro, storage);

        // Creo un nuovo bean per restituire le ricette alla vista
        BeanRicette ricetteBean = new BeanRicette(categoria, provenienza, filtro);

        // Itero attraverso la lista di ricette e le aggiungo al BeanRicette
        for (Ricetta ricetta : listRicette.getListaRicette()) {
            BeanRicetta beanRicetta = new BeanRicetta(
                    ricetta.getCodice(),
                    ricetta.getTitolo(),
                    ricetta.getImmagine(),
                    ricetta.getCategoria(),
                    ricetta.getCuoco(),
                    ricetta.getDurataPreparazione(),
                    ricetta.getCalorie()
            );
            ricetteBean.addRicetta(beanRicetta);
        }
        return ricetteBean;
    }

    public BeanRicetta ottieniDettagliRicetta(BeanRicetta infoPerRicetta) throws DAOException, SQLException {
        // Estraggo le informazioni dal bean
        String codRicetta = infoPerRicetta.getCodice();
        String categoria = infoPerRicetta.getCategoria();

        // Uso il facade per centralizzare i DAO delle procedure
        ricetta = facadeOttieniRicetta.ottieniDettagliRicetta(codRicetta, categoria);

        // Creo un nuovo bean completo per restituire la ricetta alla vista
        BeanRicetta beanRicetta = new BeanRicetta(
                ricetta.getCodice(),
                ricetta.getTitolo(),
                ricetta.getDescrizione(),
                ricetta.getImmagine(),
                ricetta.getCategoria(),
                ricetta.getCuoco(),
                ricetta.getDurataPreparazione(),
                ricetta.getCalorie(),
                ricetta.getIngredienti(),
                ricetta.getPassaggi(),
                ricetta.getVideoUrl(),
                ricetta.getLikes()
        );
        return beanRicetta;
    }

    public void usaRicetta(CredentialsBean credentials, BeanRicetta beanRicetta) throws DAOException, SQLException {

        // Estraggo le informazioni dal bean
        String username = credentials.getUsername();

        // Recupera la lista degli ingredienti disponibili nella dispensa dell'utente (centralizzo DAO con il facade)
        ListIngredienti ingredientiDispensa = facadeOttieniRicetta.ottieniIngredientiDispensaUtente(username);

        // Ottieni la data corrente
        Date currentDate = new Date();

        // Itera attraverso gli ingredienti della ricetta
        for (Ingrediente ingredienteRichiesto : beanRicetta.getIngredienti().getListaIngredienti()) {
            boolean ingredienteTrovato = false;

            // Cerca l'ingrediente richiesto nella dispensa dell'utente
            for (Ingrediente ingredienteDispensa : ingredientiDispensa.getListaIngredienti()) {
                if (ingredienteDispensa.getCodIngrediente().equals(ingredienteRichiesto.getCodIngrediente())) {
                    ingredienteTrovato = true;

                    // Verifica se la quantità è insufficiente oppure se è scaduto
                    if (ingredienteDispensa.getQuantita() < ingredienteRichiesto.getQuantita() || ingredienteDispensa.getScadenza().before(currentDate)) {
                        throw new IllegalArgumentException("La quantità è insufficiente oppure è scaduto!");
                    }
                    break;  // Esci dal loop interno poiché l'ingrediente è stato trovato
                }
            }

            // Se l'ingrediente non è stato trovato nella dispensa allora lancia l'eccezione
            if (!ingredienteTrovato) {
                throw new IllegalArgumentException("L'ingrediente non è stato trovato nella dispensa!");
            }
        }

        // Se tutti gli ingredienti sono sufficienti, aggiorna le quantità richiamando il DAO con il facade
        facadeOttieniRicetta.usaRicetta(beanRicetta.getCodice());
    }
}