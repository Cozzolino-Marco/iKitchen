package com.iKitchen.model.dao;

import com.iKitchen.exception.DAOException;
import com.iKitchen.model.domain.FactoryRicetta;
import com.iKitchen.model.domain.ListRicette;
import com.iKitchen.model.domain.Ricetta;
import java.sql.*;

public class MostraRicetteDAO implements GenericProcedureDAO<ListRicette> {

    @Override
    public ListRicette execute(Object... params) throws DAOException, SQLException {

        // Parametri
        ListRicette listRicette = new ListRicette();
        CallableStatement cs = null;
        IngredientiDAO ingredientiDAO = new IngredientiDAO();
        String categoria = (String) params[0];
        String provenienza = (String) params[1];
        String filtraggio = (String) params[2];

        try {
            Connection conn = ConnectionFactory.getConnection();
            cs = conn.prepareCall("{call mostra_ricette(?, ?, ?)}");
            cs.setString(1, categoria);
            cs.setString(2, provenienza);
            cs.setString(3, filtraggio);

            // Esegui la stored procedure
            boolean status = cs.execute();

            // Controllo esecuzione
            if(status) {
                ResultSet rs = cs.getResultSet();
                FactoryRicetta factoryRicetta = new FactoryRicetta();

                // Itera attraverso il ResultSet e popola la lista di ricette
                while (rs.next()) {
                    Ricetta ricetta = factoryRicetta.createRicetta(rs.getString("categoria"));
                    ricetta.setCodice(rs.getString("codRicetta"));
                    ricetta.setTitolo(rs.getString("titolo"));
                    ricetta.setImmagine(rs.getBlob("immagine"));
                    ricetta.setCuoco(rs.getString("cuoco"));
                    ricetta.setDurataPreparazione(rs.getInt("durataPreparazione"));
                    ricetta.setCalorie(rs.getInt("calorie"));

                    // Aggiungi ricetta alla lista ricette
                    listRicette.addRicetta(ricetta);
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
