package com.iKitchen.model.dao;

import com.iKitchen.exception.DAOException;
import com.iKitchen.model.domain.FactoryRicetta;
import com.iKitchen.model.domain.ListIngredienti;
import com.iKitchen.model.domain.Ricetta;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OttieniDettagliRicettaDAO {

    public Ricetta execute(Object... params) throws DAOException, SQLException {

        // Parametri
        Ricetta recipe = null;
        CallableStatement cs = null;
        IngredientiDAO ingredientiDAO = new IngredientiDAO();
        Ricetta ricetta = (Ricetta) params[0];

        try {
            Connection conn = ConnectionFactory.getConnection();
            cs = conn.prepareCall("{call ottieni_dettagli_ricetta(?, ?)}");
            cs.setString(1, ricetta.getCodice());
            cs.setString(2, ricetta.getCategoria());

            // Esegui la stored procedure
            boolean status = cs.execute();

            // Controllo esecuzione
            if(status) {
                ResultSet rs = cs.getResultSet();
                FactoryRicetta factoryRicetta = new FactoryRicetta();

                // Popola la ricetta
                if (rs.next()) {
                    recipe = factoryRicetta.createRicetta(rs.getString("categoria"));
                    recipe.setCodice(rs.getString("codRicetta"));
                    recipe.setTitolo(rs.getString("titolo"));
                    recipe.setDescrizione(rs.getString("descrizione"));
                    recipe.setImmagine(rs.getBlob("immagine"));
                    recipe.setCuoco(rs.getString("cuoco"));
                    recipe.setDurataPreparazione(rs.getInt("durataPreparazione"));
                    recipe.setCalorie(rs.getInt("calorie"));
                    recipe.setPassaggi(rs.getString("passaggi"));
                    recipe.setVideoUrl(rs.getString("videoUrl"));
                    recipe.setLikes(rs.getInt("likes"));

                    // Recupera e imposta gli ingredienti
                    String idRicetta = rs.getString("codRicetta");
                    ListIngredienti ingredienti = ingredientiDAO.getIngredientiPerRicetta(idRicetta);
                    recipe.setIngredienti(ingredienti);
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Errore ricetta: " + e.getMessage());
        } finally {
            if (cs != null) {
                cs.close();
            }
        }
        return recipe;
    }
}