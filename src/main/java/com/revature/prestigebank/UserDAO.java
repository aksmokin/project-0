package com.revature.prestigebank;

import java.util.List;
import java.util.Map;

/**
 *
 * @author panam
 */
public interface UserDAO {
    public Map<String, User> getAllUsers();
    public User getUser(String ssn);
    public void addUser(User user);
    public void deleteUser(User user);
}
