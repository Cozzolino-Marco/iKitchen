package com.iKitchen.model.dao;

import com.iKitchen.exception.DAOException;
import com.iKitchen.model.domain.Ingrediente;
import com.iKitchen.model.domain.ListIngredienti;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class IngredientiDAO {

    public ListIngredienti getIngredientiPerRicetta(String idRicetta) throws DAOException, SQLException {
        ListIngredienti listIngredienti = new ListIngredienti();
        CallableStatement cs = null;

        try {
            Connection conn = ConnectionFactory.getConnection();
            cs = conn.prepareCall("{call mostra_ingredienti(?)}");
            cs.setString(1, idRicetta);

            // Esegui la stored procedure
            boolean status = cs.execute();

            if(status) {
                ResultSet rs = cs.getResultSet();

                // Itera attraverso il ResultSet e popola la lista di ingredienti
                while (rs.next()) {
                    Ingrediente ingrediente = new Ingrediente();
                    ingrediente.setCodIngrediente(rs.getString("codIngrediente"));
                    ingrediente.setNome(rs.getString("nome"));
                    ingrediente.setScadenza(rs.getDate("scadenza"));
                    ingrediente.setQuantita(rs.getInt("quantitaRichiesta"));
                    ingrediente.setLimite(rs.getInt("limite"));
                    listIngredienti.addIngrediente(ingrediente);
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Errore lista ingredienti: " + e.getMessage());
        } finally {
            if (cs != null) {
                cs.close();
            }
        }
        return listIngredienti;
    }
}
