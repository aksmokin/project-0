package com.revature.prestigebank;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author panam
 */
public interface UserDAO {
    public void showUserAccount(User user) throws SQLException;
    public void createUserAccount(User user)  throws SQLException;
    public ResultSet authenticateUser (User user) throws SQLException;
    public void closeUserAccount(User user) throws SQLException;
}
