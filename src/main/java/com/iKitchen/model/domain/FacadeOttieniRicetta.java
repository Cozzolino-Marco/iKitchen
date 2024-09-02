package com.iKitchen.model.domain;

import com.iKitchen.exception.DAOException;
import com.iKitchen.model.dao.*;

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

    /* Metodo per mostrare le ricette
    public ListRicette mostraRicette(String categoria, String provenienza, String filtro) throws DAOException, SQLException {

        // Istanzia il DAO per mostrare la lista di ricette filtrate ed eseguo la query usando il DAO
        MostraRicetteDAO mostraRicetteDAO = new MostraRicetteDAO();
        ListRicette listRicette = mostraRicetteDAO.execute(categoria, provenienza, filtro);

        // Istanzia il File System per mostrare la lista di ricette filtrate
        MostraRicetteFS mostraRicetteFS = new MostraRicetteFS();

        return listRicette;
    }*/

    // Metodo per mostrare le ricette
    public ListRicette mostraRicette(String categoria, String provenienza, String filtro, String storage) throws DAOException, SQLException {

        // Istazio lista di ricette
        ListRicette listRicette = new ListRicette();

        // Controllo dove recuperare le ricette in base al parametro 'storage'
        if (storage.equalsIgnoreCase("DB")) {
            // Mostrare le ricette solo dal DB
            MostraRicetteDAO mostraRicetteDAO = new MostraRicetteDAO();
            listRicette = mostraRicetteDAO.execute(categoria, provenienza, filtro);
        }
        else if (storage.equalsIgnoreCase("FS")) {
            // Mostrare le ricette solo dal file system
            MostraRicetteFS mostraRicetteFS = new MostraRicetteFS();
            listRicette = mostraRicetteFS.recuperaRicetteDaFile(categoria, provenienza, filtro);
        }
        else if (storage.equalsIgnoreCase("DB+FS")) {
            // Mostrare le ricette dal DB e dal file system
            MostraRicetteDAO mostraRicetteDAO = new MostraRicetteDAO();
            listRicette = mostraRicetteDAO.execute(categoria, provenienza, filtro);
            MostraRicetteFS mostraRicetteFS = new MostraRicetteFS();
            listRicette = mostraRicetteFS.recuperaRicetteDaFile(categoria, provenienza, filtro, listRicette);
        }
        return listRicette;
    }

    // Metodo per mostrare una ricetta specifica
    public Ricetta ottieniDettagliRicetta(String codRicetta, String categoria) throws DAOException, SQLException {

        // Istanzia il DAO per mostrare i dettagli della ricetta
        OttieniDettagliRicettaDAO ottieniDettagliRicettaDAO = new OttieniDettagliRicettaDAO();

        // Eseguo la query usando il DAO e ottengo il risultato
        Ricetta ricetta = ottieniDettagliRicettaDAO.execute(codRicetta, categoria);

        return ricetta;
    }

    // Metodo per scalare le quantità degli ingredienti usati per la ricetta scelta
    public void usaRicetta(String codRicetta) throws DAOException, SQLException {

        // Istanzia il DAO per aggiornamento quantità
        UsaRicettaDAO usaRicettaDAO = new UsaRicettaDAO();

        // Eseguo la query usando il DAO
        usaRicettaDAO.execute(codRicetta);
    }
}