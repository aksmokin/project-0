package com.revature.prestigebank;

import java.util.TreeMap;
import java.util.Map;

/**
 *
 * @author panam
 */
public class User {
    private String name, ssn, username, password;
    public Map<String,String> accounts = new TreeMap<>();
    public Account checking, savings;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Map<String,String> getAccounts() {
        return accounts;
    }

    public void setAccounts(Map<String,String> accounts) {
        this.accounts = accounts;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }
    
}
