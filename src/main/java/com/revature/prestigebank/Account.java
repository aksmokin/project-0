package com.revature.prestigebank;

import com.revature.prestigebank.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author panam
 */
public class Account {
    
    private String number, name;
    private double balance;
    public Account(String name) {
        this.name = name;
    }
    public void withdraw(double amount) {
        this.balance -= amount;
    }
    public void setBalance(double amount) {
        this.balance += amount;
    }
    public double getBalance() {
        return this.balance;
    }
    @Override
    public String toString() {
        return name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
