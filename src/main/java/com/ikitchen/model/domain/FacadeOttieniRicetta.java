package com.ikitchen.model.domain;

import com.ikitchen.exception.DAOException;
import com.ikitchen.model.dao.*;
import java.sql.SQLException;

public class FacadeOttieniRicetta {

    // Variabile per mantenere la lista degli ingredienti globalmente in memoria
    private ListIngredienti globalListIngredienti = null;

    // Metodo per recuperare la lista dei prodotti nella dispensa dell'utente
    public ListIngredienti ottieniIngredientiDispensaUtente(String username) throws DAOException, SQLException {

        // Se la lista è già in memoria, restituiscila senza chiamare il database
        if (globalListIngredienti != null) {
            return globalListIngredienti;
        }

        // Se la lista non è presente in memoria, chiamare il DAO per recuperare gli ingredienti
        RecuperaIngredientiDispensaDAO recuperaIngredientiDispensaDAO = new RecuperaIngredientiDispensaDAO();
        globalListIngredienti = recuperaIngredientiDispensaDAO.execute(username);

        // Restituisco la lista appena recuperata
        return globalListIngredienti;
    }

    // Metodo per mostrare le ricette
    public ListRicette mostraRicette(Ricetta ricetta, String filtro, String storage) throws DAOException, SQLException {

        // Istazio lista di ricette
        ListRicette listRicette = new ListRicette();

        // Controllo dove recuperare le ricette in base al parametro 'storage'
        if (storage.equalsIgnoreCase("Solo dal database")) {
            // Mostrare le ricette solo dal DB
            MostraRicetteDAO mostraRicetteDAO = new MostraRicetteDAO();
            listRicette = mostraRicetteDAO.execute(ricetta, filtro);
        }
        else if (storage.equalsIgnoreCase("Solo dal file system")) {
            // Mostrare le ricette solo dal file system
            MostraRicetteFS mostraRicetteFS = new MostraRicetteFS();
            listRicette = mostraRicetteFS.recuperaRicetteDaFile(ricetta);
        }
        else if (storage.equalsIgnoreCase("Da entrambi")) {
            // Mostrare le ricette dal DB e dal file system
            MostraRicetteDAO mostraRicetteDAO = new MostraRicetteDAO();
            listRicette = mostraRicetteDAO.execute(ricetta, filtro);
            MostraRicetteFS mostraRicetteFS = new MostraRicetteFS();
            listRicette = mostraRicetteFS.recuperaRicetteDaFile(ricetta, listRicette);
        }
        return listRicette;
    }

    // Metodo per mostrare una ricetta specifica
    public Ricetta ottieniDettagliRicetta(Ricetta ricetta) throws DAOException, SQLException {

        // Istanzia il DAO per mostrare i dettagli della ricetta
        OttieniDettagliRicettaDAO ottieniDettagliRicettaDAO = new OttieniDettagliRicettaDAO();

        // Eseguo la query usando il DAO e restituisco il risultato
        return ottieniDettagliRicettaDAO.execute(ricetta);
    }

    // Metodo per scalare le quantità degli ingredienti usati per la ricetta scelta
    public void usaRicetta(String codRicetta) throws DAOException, SQLException {

        // Istanzia il DAO per aggiornamento quantità
        UsaRicettaDAO usaRicettaDAO = new UsaRicettaDAO();

        // Eseguo la query usando il DAO
        usaRicettaDAO.execute(codRicetta);
    }
}