package com.iKitchen.model.dao;

import com.iKitchen.exception.DAOException;
import com.iKitchen.model.domain.FactoryRicetta;
import com.iKitchen.model.domain.ListRicette;
import com.iKitchen.model.domain.Ricetta;
import java.sql.*;

public class MostraRicetteDAO {

    public ListRicette execute(Object... params) throws DAOException, SQLException {

        // Parametri
        ListRicette listRicette = new ListRicette();
        CallableStatement cs = null;
        Ricetta ricetta = (Ricetta) params[0];
        String filtraggio = (String) params[1];

        try {
            Connection conn = ConnectionFactory.getConnection();
            cs = conn.prepareCall("{call mostra_ricette(?, ?, ?)}");
            cs.setString(1, ricetta.getCategoria());
            cs.setString(2, ricetta.getProvenienza());
            cs.setString(3, filtraggio);

            // Esegui la stored procedure
            boolean status = cs.execute();

            // Controllo esecuzione
            if(status) {
                ResultSet rs = cs.getResultSet();
                FactoryRicetta factoryRicetta = new FactoryRicetta();

                // Itera attraverso il ResultSet e popola la lista di ricette
                while (rs.next()) {
                    Ricetta recipe = factoryRicetta.createRicetta(rs.getString("categoria"));
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