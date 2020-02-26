package com.revature.prestigebank;

import static com.revature.prestigebank.PrestigeBank.DB;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
    public int withdraw(double amount) {
        int res=0;
        this.balance -= amount;
        String reduce = "UPDATE accounts SET balance=? WHERE number=?";

        try {
            PreparedStatement stmt = DB.prepareStatement(reduce);
            stmt.setString(1, this.balance+"");
            stmt.setString(2, this.number);

            res = stmt.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }

        return res;
    }
    public int setBalance(double amount) {
        int res=0;
        this.balance += amount;
        String deposit = "UPDATE accounts SET balance=? WHERE number=?";

        try {
            PreparedStatement stmt = DB.prepareStatement(deposit);
            stmt.setString(1, this.balance+"");
            stmt.setString(2, this.number);

            res = stmt.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }

        return res;
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
        +"\n"+"3) Deposit/Transfer Money."
        +"\n"+"4) Exit Screen."
        );
    }
    
    public static void getAnswer() {  
        System.out.println("\nResponse> "); 
    }
}
