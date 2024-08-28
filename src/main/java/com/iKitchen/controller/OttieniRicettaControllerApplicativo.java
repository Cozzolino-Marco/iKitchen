package com.iKitchen.controller;

import com.iKitchen.exception.DAOException;
import com.iKitchen.model.bean.BeanRicetta;
import com.iKitchen.model.bean.BeanRicette;
import com.iKitchen.model.bean.CredentialsBean;
import com.iKitchen.model.domain.*;
import java.sql.SQLException;

public class OttieniRicettaControllerApplicativo {

    // Variabili
    FacadeOttieniRicetta facadeOttieniRicetta;
    ListRicette listRicette;
    Ricetta ricetta;

    // Gestione pattern Facade
    public OttieniRicettaControllerApplicativo(){
        facadeOttieniRicetta = new FacadeOttieniRicetta();
    }

    // Restituisce la lista di ricette in base ai filtri scelti
    public BeanRicette mostraRicette(BeanRicette infoPerListaRicette) throws DAOException, SQLException {

        // Estraggo le informazioni dal bean
        String categoria = infoPerListaRicette.getCategoria();
        String provenienza = infoPerListaRicette.getProvenienza();
        String filtro = infoPerListaRicette.getFiltraggio();

        // Uso il facade per centralizzare i DAO delle procedure
        listRicette = facadeOttieniRicetta.mostraRicette(categoria, provenienza, filtro);

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

        // Creo un nuovo bean per restituire la ricetta alla vista
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

    public boolean usaRicetta(CredentialsBean credentials, BeanRicetta beanRicetta) throws DAOException, SQLException {

        // Estraggo le informazioni dal bean
        String username = credentials.getUsername();

        // Recupera la lista degli ingredienti richiesti dalla ricetta
        ListIngredienti ingredientiRicetta = beanRicetta.getIngredienti();

        // Recupera la lista degli ingredienti disponibili nella dispensa dell'utente (centralizzo DAO)
        ListIngredienti ingredientiDispensa = facadeOttieniRicetta.ottieniIngredientiDispensaUtente(username);

        // Itera attraverso gli ingredienti della ricetta
        for (Ingrediente ingredienteRichiesto : ingredientiRicetta.getListaIngredienti()) {
            boolean ingredienteTrovato = false;

            // Cerca l'ingrediente richiesto nella dispensa dell'utente
            for (Ingrediente ingredienteDispensa : ingredientiDispensa.getListaIngredienti()) {
                if (ingredienteDispensa.getCodIngrediente().equals(ingredienteRichiesto.getCodIngrediente())) {
                    ingredienteTrovato = true;

                    // Verifica se la quantità è sufficiente
                    if (ingredienteDispensa.getQuantita() < ingredienteRichiesto.getQuantita()) {
                        // Quantità non sufficiente, restituisci false
                        return false;
                    }

                    // Se necessario, puoi sottrarre la quantità utilizzata dall'ingrediente nella dispensa
                    ingredienteDispensa.setQuantita(ingredienteDispensa.getQuantita() - ingredienteRichiesto.getQuantita());
                }
            }

            // Se l'ingrediente non è stato trovato nella dispensa, restituisci false
            if (!ingredienteTrovato) {
                return false;
            }
        }

        // Se tutti gli ingredienti sono sufficienti, restituisci true
        return true;
    }
}