/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.revature.prestigebank;

/**
 *
 * @author panam
 */
public class Account {
    
    private String name;
    private double balance;
    public Account(String name) {
        this.name = name;
    }
    public void withdraw(double amount) {
        this.balance -= amount;
    }
    public void deposit(double amount) {
        this.balance += amount;
    }
    public double getBalance() {
        return this.balance;
    }
    @Override
    public String toString() {
        return name;
    }
}
