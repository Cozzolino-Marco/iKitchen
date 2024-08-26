package com.iKitchen.model.dao;

import com.iKitchen.exception.DAOException;
import com.iKitchen.model.domain.Ricetta;
import java.sql.CallableStatement;
import java.sql.SQLException;

public class CreaRicettaDAO {

    public void execute(Object... params) throws DAOException, SQLException {
        Ricetta ricetta = (Ricetta) params[0];
        CallableStatement cs = null;

        /*try {
            Connection conn = ConnectionFactory.getConnection();
            cs = conn.prepareCall("{call crea_ricetta(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");
            cs.setString(1, ricetta.getTitolo());
            cs.setString(2, ricetta.getDescrizione());
            cs.setBlob(3, ricetta.getImageUrl());
            cs.setString(4, ricetta.getCategoria());
            cs.setString(5, ricetta.getProvenienza());
            cs.setString(6, ricetta.getCuoco());
            cs.setFloat(7, ricetta.getDurataPreparazione());
            cs.setList(8, ricetta.getIngredienti());
            cs.setString(9, ricetta.getPassaggi());
            cs.setString(10, ricetta.getVideoUrl());
            cs.setInt(11, ricetta.getLikes());
            cs.executeQuery();

        } catch(SQLException e) {
            throw new DAOException("Errore creazione ricetta: " + e.getMessage());
        } finally {
            if (cs != null){
                cs.close();
            }
        }*/
    }
}
