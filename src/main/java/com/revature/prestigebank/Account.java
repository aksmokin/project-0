package com.revature.prestigebank;

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
    
    public static void displayOptions () {
        System.out.println("What would you like to do?"
        +"\n"+"1) Check Your Balance."
        +"\n"+"2) Make A Withdrawal."
        +"\n"+"3) Deposit Money."
        +"\n"+"4) Exit Bank."
        );
    }
    
    public static void getAnswer() { 
        System.out.print("\n"); 
        System.out.println("Response> "); 
    }
}
