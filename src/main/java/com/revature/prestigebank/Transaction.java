package com.revature.prestigebank;

import com.revature.prestigebank.*;

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
	                to.setBalance(amount);
	            }
	            // Release the lock of Account 'to'
	        }
	        // Release the lock of Account 'from'
	        System.out.println(amount + "is transfered from " + from + " to " + to);
	    }
}
