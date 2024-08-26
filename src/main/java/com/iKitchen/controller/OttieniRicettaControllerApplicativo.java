package com.iKitchen.controller;

import com.iKitchen.exception.DAOException;
import com.iKitchen.model.bean.BeanRicetta;
import com.iKitchen.model.bean.BeanRicette;
import com.iKitchen.model.domain.ListRicette;
import com.iKitchen.model.domain.Ricetta;
import com.iKitchen.model.domain.FacadeOttieniRicetta;
import java.sql.SQLException;

public class OttieniRicettaControllerApplicativo {

    // Variabili
    FacadeOttieniRicetta facadeOttieniRicetta;
    ListRicette listRicette;

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

        /* Itero attraverso la lista di ricette e le aggiungo al BeanRicette
        for (Ricetta ricetta : listRicette.getListaRicette()) {
            BeanRicetta beanRicetta = new BeanRicetta(
                    ricetta.getTitolo(),
                    ricetta.getDescrizione(),
                    ricetta.getImageUrl(),
                    ricetta.getCategoria(),
                    ricetta.getProvenienza(),
                    ricetta.getCuoco(),
                    ricetta.getDurataPreparazione(),
                    ricetta.getIngredienti(),
                    ricetta.getPassaggi(),
                    ricetta.getVideoUrl(),
                    ricetta.getLikes()
            );
            ricetteBean.addRicetta(beanRicetta);
        }*/

        // Itero attraverso la lista di ricette e le aggiungo al BeanRicette
        for (Ricetta ricetta : listRicette.getListaRicette()) {
            BeanRicetta beanRicetta = new BeanRicetta(
                    ricetta.getTitolo(),
                    ricetta.getCategoria(),
                    ricetta.getCuoco(),
                    ricetta.getDurataPreparazione()
            );
            ricetteBean.addRicetta(beanRicetta);
        }
        return ricetteBean;
    }

    /*public void ottieniRicetta(BeanRicette beanRicette) throws DAOException, SQLException {
    }

    public void verificaQuantita(BeanRicetta beanRicetta) throws DAOException, SQLException {
    }

    public void usaRicetta(BeanRicetta beanRicetta) throws DAOException, SQLException {
    }*/
}