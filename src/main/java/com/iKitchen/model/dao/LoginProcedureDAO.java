package com.iKitchen.model.dao;

import com.iKitchen.exception.DAOException;
import com.iKitchen.model.domain.Credentials;
import com.iKitchen.model.domain.Role;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

public class LoginProcedureDAO {

    public void execute(Object... params) throws DAOException, SQLException {
        Credentials cred = (Credentials) params[0];
        int role;
        CallableStatement cs = null;

        try {
            Connection conn = ConnectionFactory.getConnection();
            cs = conn.prepareCall("{call login(?,?,?)}");

            // Imposta i parametri IN
            cs.setString(1, cred.getUsername());
            cs.setString(2, cred.getPassword());

            // Registra il parametro OUT
            cs.registerOutParameter(3, Types.NUMERIC);

            // Esegui la chiamata
            cs.executeQuery();

            // Ottieni il valore del parametro OUT (ruolo)
            role = cs.getInt(3);

            /* Verifica se il ruolo è valido
            if (role == -1) {  // Supponiamo che -1 sia l'indicatore di credenziali non valide
                throw new DAOException("Credenziali non valide: username o password errati.");
            }*/

            // Imposta il ruolo nelle credenziali
            cred.setRole(Role.fromInt(role));

        } catch (SQLException e) {
            throw new DAOException("Login error: " + e.getMessage());
        } finally {
            if (cs!= null){
                cs.close();
            }
        }
    }
}