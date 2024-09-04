package com.iKitchen.model.dao;

import com.iKitchen.exception.DAOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

public class RegistrazioneDAO {

    public void execute(Object... params) throws DAOException, SQLException {

        // Parametri
        CallableStatement cs = null;
        String nome = (String) params[0];
        String cognome = (String) params[1];
        String ruolo = (String) params[2];
        String username = (String) params[3];
        String password = (String) params[4];

        try {
            Connection conn = ConnectionFactory.getConnection();
            cs = conn.prepareCall("{call registrazione(?, ?, ?, ?, ?)}");
            cs.setString(1, nome);
            cs.setString(2, cognome);
            cs.setString(3, ruolo);
            cs.setString(4, username);
            cs.setString(5, password);

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