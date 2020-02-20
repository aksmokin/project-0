/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.revature.prestigebank;

import java.util.TreeMap;
import java.util.Map;

/**
 *
 * @author panam
 */
public class User {
    private String username, password;
    private Map<String,String> accounts = new TreeMap<>();
    public Account checking, savings;
    private double balance;

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

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
    
    
}