package com.ikitchen.model.dao;

import com.ikitchen.exception.DAOException;
import com.ikitchen.model.utility.Credentials;
import com.ikitchen.model.domain.Role;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

public class LoginProcedureDAO {

    public void loginExecute() throws DAOException, SQLException {
        int role;
        CallableStatement cs = null;

        try {
            Connection conn = ConnectionFactory.getConnection();
            cs = conn.prepareCall("{call login(?,?,?)}");

            // Imposta i parametri IN
            cs.setString(1, Credentials.getUsername());
            cs.setString(2, Credentials.getPassword());

            // Registra il parametro OUT
            cs.registerOutParameter(3, Types.NUMERIC);

            // Esegui la chiamata
            cs.executeQuery();

            // Ottieni il valore del parametro OUT (ruolo)
            role = cs.getInt(3);

            // Verifica se il ruolo è valido
            if (role == 3) {
                throw new DAOException("Credenziali non valide: username o password errati.");
            }

            // Imposta il ruolo nelle credenziali
            Credentials.setRole(Role.fromInt(role));

        } catch (SQLException e) {
            throw new DAOException("Login error: " + e.getMessage());
        } finally {
            if (cs!= null){
                cs.close();
            }
        }
    }
}