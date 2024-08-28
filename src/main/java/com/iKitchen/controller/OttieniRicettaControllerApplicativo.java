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
    Ricetta ricetta;

    // Gestione pattern Facade
    public OttieniRicettaControllerApplicativo(){
        facadeOttieniRicetta = new FacadeOttieniRicetta();
    }

    /*public void verificaQuantita(BeanRicetta beanRicetta) throws DAOException, SQLException {
    }*/

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

    /*public void usaRicetta(BeanRicetta beanRicetta) throws DAOException, SQLException {
    }*/
}