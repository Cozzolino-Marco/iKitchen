package com.ikitchen.model.dao;

import com.ikitchen.exception.DAOException;
import com.ikitchen.model.utility.Credentials;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

public class RegistrazioneDAO {

    public void execute() throws DAOException, SQLException {

        // Parametri
        CallableStatement cs = null;

        try {
            Connection conn = ConnectionFactory.getConnection();
            cs = conn.prepareCall("{call registrazione(?, ?, ?, ?, ?)}");
            cs.setString(1, Credentials.getNome());
            cs.setString(2, Credentials.getCognome());
            cs.setInt(3, Credentials.getRole().getId());
            cs.setString(4, Credentials.getUsername());
            cs.setString(5, Credentials.getPassword());

            // Esegui la stored procedure
            cs.execute();

        } catch (SQLException e) {
            throw new DAOException("Errore registrazione: " + e.getMessage());
        } finally {
            if (cs != null) {
                cs.close();
            }
        }
    }
}