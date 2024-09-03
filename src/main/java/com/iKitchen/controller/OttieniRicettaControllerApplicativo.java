package com.iKitchen.controller;

import com.iKitchen.exception.DAOException;
import com.iKitchen.model.bean.*;
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

    // Restituisce al controller grafico la lista di ricette in base ai filtri scelti
    public BeanRicette mostraRicette(BeanRicette infoPerListaRicette) throws DAOException, SQLException, IOException {

        // Estraggo le informazioni dal bean
        String categoria = infoPerListaRicette.getCategoria();
        String provenienza = infoPerListaRicette.getProvenienza();
        String filtro = infoPerListaRicette.getFiltraggio();
        String storage = infoPerListaRicette.getStorage();

        // Chiamata alla boundary dell'attore esterno
        if (provenienza.equals("Dal web")) {
            OttieniRicettaControllerGraficoAPI controllerAttoreSecondario = new OttieniRicettaControllerGraficoAPI();
            return controllerAttoreSecondario.recuperaRicette(infoPerListaRicette);
        }

        // Uso il facade per centralizzare i DAO delle procedure
        listRicette = facadeOttieniRicetta.mostraRicette(categoria, provenienza, filtro, storage);

        // Creo un nuovo bean per restituire le ricette alla vista
        BeanRicette ricetteBean = new BeanRicette(categoria, provenienza, filtro, storage);

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

    // Restituisce al controller grafico tutte le informazioni di una ricetta specifica
    public BeanRicetta ottieniDettagliRicetta(BeanRicetta infoPerRicetta) throws DAOException, SQLException {
        // Estraggo le informazioni dal bean
        String codRicetta = infoPerRicetta.getCodice();
        String categoria = infoPerRicetta.getCategoria();

        // Uso il facade per centralizzare i DAO delle procedure
        ricetta = facadeOttieniRicetta.ottieniDettagliRicetta(codRicetta, categoria);

        // Creo un nuovo bean completo per restituire la ricetta alla vista
        return new BeanRicetta(
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
    }

    // Restituisce al controller grafico la lista degli ingredienti della dispensa che rispettano i parametri di validazione per la ricetta
    public BeanIngredienti verificaQuantita(CredentialsBean credentials, BeanRicetta beanRicetta) throws DAOException, SQLException {

        // Estraggo le informazioni dal bean
        String username = credentials.getUsername();

        // Recupera la lista degli ingredienti disponibili nella dispensa dell'utente
        ListIngredienti ingredientiDispensa = facadeOttieniRicetta.ottieniIngredientiDispensaUtente(username);

        // Ottieni la data corrente
        Date currentDate = new Date();

        // Crea un BeanIngredienti per contenere gli ingredienti validi
        BeanIngredienti ingredientiValidi = new BeanIngredienti();

        // Itera attraverso gli ingredienti della ricetta
        for (Ingrediente ingredienteRichiesto : beanRicetta.getIngredienti().getListaIngredienti()) {

            // Cerca l'ingrediente richiesto nella dispensa dell'utente
            for (Ingrediente ingredienteDispensa : ingredientiDispensa.getListaIngredienti()) {
                if (ingredienteDispensa.getCodIngrediente().equals(ingredienteRichiesto.getCodIngrediente())) {

                    // Verifica se la quantità è insufficiente oppure se è scaduto
                    if (ingredienteDispensa.getQuantita() >= ingredienteRichiesto.getQuantita() && ingredienteDispensa.getScadenza().after(currentDate)) {
                        BeanIngrediente beanIngredienteValido = new BeanIngrediente();
                        beanIngredienteValido.setNome(ingredienteDispensa.getNome());
                        beanIngredienteValido.setQuantita(ingredienteDispensa.getQuantita());
                        beanIngredienteValido.setScadenza(ingredienteDispensa.getScadenza());
                        ingredientiValidi.getListIngredienti().add(beanIngredienteValido);
                    }

                    break;  // Esci dal loop interno poiché l'ingrediente è stato trovato e aggiunto
                }
            }
        }

        // Ritorna il BeanIngredienti contenente gli ingredienti validi
        return ingredientiValidi;
    }

    // Metodo che aggiorna le quantità richiamando il DAO con il facade
    public void usaRicetta(BeanRicetta beanRicetta) throws DAOException, SQLException {
        facadeOttieniRicetta.usaRicetta(beanRicetta.getCodice());
    }
}