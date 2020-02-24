package com.revature.prestigebank;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author panam
 */
public class Employee implements UserDAO {
    
    private Map<String, User> users;
    
    public Employee() {
        users = new TreeMap<String, User>();
    }
    
    @Override
    public Map<String, User> getAllUsers() {
        return users;
    }

    @Override
    public User getUser(String ssn) {
        return users.get(ssn);
    }

    @Override
    public void addUser(User user) {
        users.put(user.getSsn(), user);
    }

    @Override
    public void deleteUser(User user) {
        users.remove(user.getSsn());
    }
    
}
