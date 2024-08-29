package com.iKitchen.model.domain;

import com.iKitchen.exception.DAOException;
import com.iKitchen.model.dao.MostraRicetteDAO;
import com.iKitchen.model.dao.OttieniDettagliRicettaDAO;
import com.iKitchen.model.dao.RecuperaIngredientiDispensaDAO;
import com.iKitchen.model.dao.UsaRicettaDAO;

import java.sql.SQLException;

public class FacadeOttieniRicetta {

    // Metodo per recuperare la lista dei prodotti nella dispensa dell'utente
    public ListIngredienti ottieniIngredientiDispensaUtente(String username) throws DAOException, SQLException {

        // Istanzia il DAO per recuperare gli ingredienti della dispensa
        RecuperaIngredientiDispensaDAO recuperaIngredientiDispensaDAO = new RecuperaIngredientiDispensaDAO();

        // Eseguo la query usando il DAO e ottengo il risultato
        ListIngredienti listProdottiDispensa = recuperaIngredientiDispensaDAO.execute(username);

        return listProdottiDispensa;
    }

    // Metodo per mostrare le ricette
    public ListRicette mostraRicette(String categoria, String provenienza, String filtro) throws DAOException, SQLException {

        // Istanzia il DAO per mostrare la lista di ricette filtrate
        MostraRicetteDAO mostraRicetteDAO = new MostraRicetteDAO();

        // Eseguo la query usando il DAO e ottengo il risultato
        ListRicette listRicette = mostraRicetteDAO.execute(categoria, provenienza, filtro);

        return listRicette;
    }

    // Metodo per mostrare una ricetta specifica
    public Ricetta ottieniDettagliRicetta(String codRicetta, String categoria) throws DAOException, SQLException {

        // Istanzia il DAO per mostrare la lista di ricette filtrate
        OttieniDettagliRicettaDAO ottieniDettagliRicettaDAO = new OttieniDettagliRicettaDAO();

        // Eseguo la query usando il DAO e ottengo il risultato
        Ricetta ricetta = ottieniDettagliRicettaDAO.execute(codRicetta, categoria);

        return ricetta;
    }

    // Metodo per mostrare una ricetta specifica
    public void usaRicetta() throws DAOException, SQLException {

        // Istanzia il DAO per mostrare la lista di ricette filtrate
        UsaRicettaDAO usaRicettaDAO = new UsaRicettaDAO();

        // Eseguo la query usando il DAO e ottengo il risultato
        usaRicettaDAO.execute();
    }
}