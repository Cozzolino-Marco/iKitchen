package com.ikitchen.controller;

import com.ikitchen.exception.DAOException;
import com.ikitchen.model.bean.*;
import com.ikitchen.model.domain.*;
import com.ikitchen.model.utility.Credentials;
import com.ikitchen.view_standard.OttieniRicettaControllerGraficoAPI;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;

public class OttieniRicettaControllerApplicativo {

    // Variabili
    FacadeOttieniRicetta facadeOttieniRicetta;
    ListRicette listRicette;

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

        // Creo un oggetto Ricetta usando la factory
        Ricetta ricetta = FactoryRicetta.createRicetta(categoria);
        ricetta.setProvenienza(provenienza);

        // Uso il facade per centralizzare i DAO delle procedure
        listRicette = facadeOttieniRicetta.mostraRicette(ricetta, filtro, storage);

        // Creo un nuovo bean per restituire le ricette alla vista
        BeanRicette ricetteBean = new BeanRicette(categoria, provenienza, filtro, storage);

        // Itero attraverso la lista di ricette e le aggiungo al BeanRicette
        for (Ricetta recipe : listRicette.getListaRicette()) {
            BeanRicetta beanRicetta = new BeanRicetta(
                    recipe.getCodice(),
                    recipe.getTitolo(),
                    recipe.getImmagine(),
                    recipe.getCategoria(),
                    recipe.getCuoco(),
                    recipe.getDurataPreparazione(),
                    recipe.getCalorie()
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

        // Creo un oggetto Ricetta usando la factory
        Ricetta ricetta = FactoryRicetta.createRicetta(categoria);
        ricetta.setCodice(codRicetta);

        // Uso il facade per centralizzare i DAO delle procedure
        Ricetta recipe = facadeOttieniRicetta.ottieniDettagliRicetta(ricetta);

        // Creo un nuovo bean completo per restituire la ricetta alla vista (max 7 parametri + settaggi manuali)
        BeanRicetta beanRicetta = new BeanRicetta(recipe.getTitolo(), recipe.getDescrizione(), recipe.getCategoria(), recipe.getDurataPreparazione(), recipe.getCalorie(), recipe.getIngredienti(), recipe.getLikes());
        beanRicetta.setCodice(recipe.getCodice());
        beanRicetta.setImmagine(recipe.getImmagine());
        beanRicetta.setCuoco(recipe.getCuoco());
        beanRicetta.setPassaggi(recipe.getPassaggi());
        beanRicetta.setVideoUrl(recipe.getVideoUrl());
        return beanRicetta;
    }

    /* Restituisce al controller grafico la lista degli ingredienti della dispensa che rispettano i parametri di validazione per la ricetta
    public BeanIngredienti verificaQuantita(BeanRicetta beanRicetta) throws DAOException, SQLException {

        // Recupero lo username
        String username = Credentials.getUsername();

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
     */

    public BeanIngredienti verificaQuantita(BeanRicetta beanRicetta) throws DAOException, SQLException {
        String username = Credentials.getUsername();
        ListIngredienti ingredientiDispensa = facadeOttieniRicetta.ottieniIngredientiDispensaUtente(username);
        Date currentDate = new Date();
        BeanIngredienti ingredientiValidi = new BeanIngredienti();

        for (Ingrediente ingredienteRichiesto : beanRicetta.getIngredienti().getListaIngredienti()) {
            boolean valido = false;

            for (Ingrediente ingredienteDispensa : ingredientiDispensa.getListaIngredienti()) {
                if (ingredienteDispensa.getCodIngrediente().equals(ingredienteRichiesto.getCodIngrediente())) {
                    if (ingredienteDispensa.getQuantita() >= ingredienteRichiesto.getQuantita() && ingredienteDispensa.getScadenza().after(currentDate)) {
                        valido = true;
                    }
                    BeanIngrediente beanIngrediente = new BeanIngrediente();
                    beanIngrediente.setNome(ingredienteDispensa.getNome());
                    beanIngrediente.setQuantita(ingredienteDispensa.getQuantita());
                    beanIngrediente.setScadenza(ingredienteDispensa.getScadenza());
                    beanIngrediente.setValidita(valido);
                    ingredientiValidi.getListIngredienti().add(beanIngrediente);
                    break;
                }
            }
        }

        return ingredientiValidi;
    }

    // Metodo che aggiorna le quantità richiamando il DAO con il facade
    public void usaRicetta(BeanRicetta beanRicetta) throws DAOException, SQLException {

        // Estraggo le informazioni dal bean
        String codRicetta = beanRicetta.getCodice();
        String categoria = beanRicetta.getCategoria();
        ListIngredienti ingredienti = beanRicetta.getIngredienti();

        // Creo un oggetto Ricetta usando la factory e lo popolo con i dati di beanRicetta
        Ricetta ricetta = FactoryRicetta.createRicetta(categoria);
        ricetta.setCodice(codRicetta);
        ricetta.setIngredienti(ingredienti);

        // Chiamata al facade per aggiornare le quantità nel DB e nella lista globale
        facadeOttieniRicetta.usaRicetta(ricetta, beanRicetta.getCodice());
    }
}