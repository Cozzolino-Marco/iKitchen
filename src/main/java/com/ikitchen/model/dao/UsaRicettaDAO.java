package com.ikitchen.model.dao;

import com.ikitchen.exception.DAOException;
import java.sql.*;

public class UsaRicettaDAO {

    public void execute(Object... params) throws DAOException, SQLException {

        // Parametri
        CallableStatement cs = null;
        String codRicetta = (String) params[0];

        try {
            Connection conn = ConnectionFactory.getConnection();
            cs = conn.prepareCall("{call usa_ricetta(?, ?)}");
            cs.setString(1, codRicetta);

            // Definisci il parametro di output per recuperare il valore restituito
            cs.registerOutParameter(2, Types.BOOLEAN);

            // Esegui la stored procedure
            cs.execute();

            // Recupera il valore restituito dal parametro di output
            boolean valoreRestituito = cs.getBoolean(2);

            // Gestione restituzione del valore di ritorno
            if(!valoreRestituito) {
                throw new IllegalArgumentException("Errore query!");
            }

        } catch (SQLException e) {
            throw new DAOException("Errore ricetta: " + e.getMessage());
        } finally {
            if (cs != null) {
                cs.close();
            }
        }
    }
}