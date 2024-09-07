package com.ikitchen.model.dao;

import com.ikitchen.exception.DAOException;
import com.ikitchen.model.domain.Credentials;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RecuperaNomeDaUsernameDAO {

    public void execute(Object... params) throws DAOException, SQLException {

        // Parametri
        CallableStatement cs = null;
        String username = (String) params[0];

        try {
            Connection conn = ConnectionFactory.getConnection();
            cs = conn.prepareCall("{call recupera_nome_utente(?)}");
            cs.setString(1, username);

            // Esegui la stored procedure
            boolean status = cs.execute();

            if(status) {
                ResultSet rs = cs.getResultSet();

                // Setting del nome recuperato
                if (rs.next()) {
                    Credentials.setNome(rs.getString("nome"));
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Errore recupero del nome: " + e.getMessage());
        } finally {
            if (cs != null) {
                cs.close();
            }
        }
    }
}