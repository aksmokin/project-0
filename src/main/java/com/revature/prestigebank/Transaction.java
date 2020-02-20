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
public class Transaction extends Thread {
	    private  String id;
	    private  Account from;
	    private  Account to;
	    private  double amount;
	    public Transaction(String id, Account from, Account to, double amount) {
	        this.id = id;
	        this.from = from;
	        this.to = to;
	        this.amount = amount;
	    }
	    @Override
	    public void run() {
	        // Acquire the lock of Account 'from'
	        synchronized (from) {
	            from.withdraw(amount);
	            try {
	                Thread.sleep(500);
	            } catch (InterruptedException e) { }
	       // }
	            // Acquire the lock of Account 'to'
	            synchronized (to) {
	                to.deposit(amount);
	            }
	            // Release the lock of Account 'to'
	        }
	        // Release the lock of Account 'from'
	        System.out.println(amount + "is transfered from " + from + " to " + to);
	    }
}
