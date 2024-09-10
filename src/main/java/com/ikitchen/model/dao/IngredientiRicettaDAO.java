package com.ikitchen.model.dao;

import com.ikitchen.exception.DAOException;
import com.ikitchen.model.domain.Ingrediente;
import com.ikitchen.model.domain.ListIngredienti;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class IngredientiRicettaDAO {

    public ListIngredienti getIngredientiPerRicettaExecute(String idRicetta) throws DAOException, SQLException {
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
                    ingrediente.setTipo(rs.getString("tipo"));
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
