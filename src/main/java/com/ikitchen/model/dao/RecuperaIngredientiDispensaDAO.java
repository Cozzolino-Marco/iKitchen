package com.ikitchen.model.dao;

import com.ikitchen.exception.DAOException;
import com.ikitchen.model.domain.Ingrediente;
import com.ikitchen.model.domain.ListIngredienti;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RecuperaIngredientiDispensaDAO {

    public ListIngredienti execute(Object... params) throws DAOException, SQLException {

        // Parametri
        ListIngredienti listIngredienti = new ListIngredienti();
        CallableStatement cs = null;
        String username = (String) params[0];

        try {
            Connection conn = ConnectionFactory.getConnection();
            cs = conn.prepareCall("{call recupera_ingredienti_dispensa(?)}");
            cs.setString(1, username);

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
                    ingrediente.setQuantita(rs.getInt("quantita"));
                    ingrediente.setLimite(rs.getInt("limite"));
                    ingrediente.setImmagine(rs.getBlob("immagine"));
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