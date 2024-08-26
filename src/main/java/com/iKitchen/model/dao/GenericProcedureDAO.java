package com.iKitchen.model.dao;

import com.iKitchen.exception.DAOException;
import java.sql.SQLException;

public interface GenericProcedureDAO<P>  {
    P execute(Object... params) throws DAOException, SQLException;
}
