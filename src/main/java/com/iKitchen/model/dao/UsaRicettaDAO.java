package com.iKitchen.model.dao;

import com.iKitchen.exception.DAOException;
import com.iKitchen.model.domain.FactoryRicetta;
import com.iKitchen.model.domain.ListIngredienti;
import com.iKitchen.model.domain.Ricetta;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsaRicettaDAO {

    public Ricetta execute(Object... params) throws DAOException, SQLException {

        // Parametri
        Ricetta ricetta = null;
        CallableStatement cs = null;
        IngredientiDAO ingredientiDAO = new IngredientiDAO();
        String codRicetta = (String) params[0];
        String categoria = (String) params[1];

        try {
            Connection conn = ConnectionFactory.getConnection();
            cs = conn.prepareCall("{call usa_ricetta(?, ?)}");
            cs.setString(1, codRicetta);
            cs.setString(2, categoria);

            // Esegui la stored procedure
            boolean status = cs.execute();

            // Controllo esecuzione
            if(status) {
                ResultSet rs = cs.getResultSet();
                FactoryRicetta factoryRicetta = new FactoryRicetta();

                // Popola la ricetta
                if (rs.next()) {
                    ricetta = factoryRicetta.createRicetta(rs.getString("categoria"));
                    ricetta.setCodice(rs.getString("codRicetta"));
                    ricetta.setTitolo(rs.getString("titolo"));
                    ricetta.setDescrizione(rs.getString("descrizione"));
                    ricetta.setImmagine(rs.getBlob("immagine"));
                    ricetta.setCuoco(rs.getString("cuoco"));
                    ricetta.setDurataPreparazione(rs.getInt("durataPreparazione"));
                    ricetta.setCalorie(rs.getInt("calorie"));
                    ricetta.setPassaggi(rs.getString("passaggi"));
                    ricetta.setVideoUrl(rs.getString("videoUrl"));
                    ricetta.setLikes(rs.getInt("likes"));

                    // Recupera e imposta gli ingredienti
                    String idRicetta = rs.getString("codRicetta");
                    ListIngredienti ingredienti = ingredientiDAO.getIngredientiPerRicetta(idRicetta);
                    ricetta.setIngredienti(ingredienti);
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Errore ricetta: " + e.getMessage());
        } finally {
            if (cs != null) {
                cs.close();
            }
        }
        return ricetta;
    }
}

