package com.ikitchen.model.dao;

import com.ikitchen.exception.DAOException;
import com.ikitchen.model.domain.Credentials;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

public class RegistrazioneDAO {

    public void execute(Object... params) throws DAOException, SQLException {

        // Parametri
        CallableStatement cs = null;
        Credentials cred = (Credentials) params[0];

        try {
            Connection conn = ConnectionFactory.getConnection();
            cs = conn.prepareCall("{call registrazione(?, ?, ?, ?, ?)}");
            cs.setString(1, cred.getNome());
            cs.setString(2, cred.getCognome());
            cs.setInt(3, cred.getRole().getId());
            cs.setString(4, cred.getUsername());
            cs.setString(5, cred.getPassword());

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