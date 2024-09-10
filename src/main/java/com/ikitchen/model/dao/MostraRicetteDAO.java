package com.ikitchen.model.dao;

import com.ikitchen.exception.DAOException;
import com.ikitchen.model.domain.FactoryRicetta;
import com.ikitchen.model.domain.ListRicette;
import com.ikitchen.model.domain.Ricetta;
import com.ikitchen.model.utility.Credentials;

import java.sql.*;

public class MostraRicetteDAO {

    public ListRicette mostraRicetteExecute(Ricetta ricetta, String filtro) throws DAOException, SQLException {

        // Parametri
        ListRicette listRicette = new ListRicette();
        CallableStatement cs = null;

        try {
            Connection conn = ConnectionFactory.getConnection();
            cs = conn.prepareCall("{call mostra_ricette(?, ?, ?, ?)}");
            cs.setString(1, ricetta.getCategoria());
            cs.setString(2, ricetta.getProvenienza());
            cs.setString(3, filtro);
            cs.setString(4, Credentials.getUsername());

            // Esegui la stored procedure
            boolean status = cs.execute();

            // Controllo esecuzione
            if(status) {
                ResultSet rs = cs.getResultSet();

                // Itera attraverso il ResultSet e popola la lista di ricette
                while (rs.next()) {
                    Ricetta recipe = FactoryRicetta.createRicetta(rs.getString("categoria"));
                    recipe.setCodice(rs.getString("codRicetta"));
                    recipe.setTitolo(rs.getString("titolo"));
                    recipe.setImmagine(rs.getBlob("immagine"));
                    recipe.setCuoco(rs.getString("cuoco"));
                    recipe.setDurataPreparazione(rs.getInt("durataPreparazione"));
                    recipe.setCalorie(rs.getInt("calorie"));

                    // Aggiungi ricetta alla lista ricette
                    listRicette.addRicetta(recipe);
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Errore lista ricette: " + e.getMessage());
        } finally {
            if (cs != null) {
                cs.close();
            }
        }
        return listRicette;
    }
}