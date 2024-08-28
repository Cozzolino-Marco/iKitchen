package com.iKitchen.model.domain;

import com.iKitchen.exception.DAOException;
import com.iKitchen.model.dao.MostraRicetteDAO;
import com.iKitchen.model.dao.OttieniDettagliRicettaDAO;

import java.sql.SQLException;

public class FacadeOttieniRicetta {

    // Metodo per mostrare le ricette
    public ListRicette mostraRicette(String categoria, String provenienza, String filtro) throws DAOException, SQLException {

        // Istanzia il DAO per mostrare la lista di ricette filtrate
        MostraRicetteDAO mostraRicetteDAO = new MostraRicetteDAO();

        // Esegui la query usando il DAO e ottieni il risultato
        ListRicette listRicette = mostraRicetteDAO.execute(categoria, provenienza, filtro);

        return listRicette;
    }

    // Metodo per mostrare una ricetta specifica
    public Ricetta ottieniDettagliRicetta(String codRicetta, String categoria) throws DAOException, SQLException {

        // Istanzia il DAO per mostrare la lista di ricette filtrate
        OttieniDettagliRicettaDAO ottieniDettagliRicettaDAO = new OttieniDettagliRicettaDAO();

        // Esegui la query usando il DAO e ottieni il risultato
        Ricetta ricetta = ottieniDettagliRicettaDAO.execute(codRicetta, categoria);

        return ricetta;
    }
}
