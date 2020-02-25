package com.revature.prestigebank;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 *
 * @author panam
 */
public interface UserDAO {
    public Map<String, User> getAllUsers();
    public void showUserAccount(User user) throws SQLException;
    public void createUserAccount(User user)  throws SQLException;
    public ResultSet authenticateUser (User user) throws SQLException;
    public void closeUserAccount(User user) throws SQLException;
}
