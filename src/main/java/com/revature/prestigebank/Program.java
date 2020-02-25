/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.revature.prestigebank;

import static com.revature.prestigebank.Account.getAnswer;
import static com.revature.prestigebank.PrestigeBank.DB;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

/**
 *
 * @author panam
 */
public class Program {
    public static void approveAccount() {
        Scanner s = new Scanner(System.in);
        String acctDecision, n="";
        
        System.out.println("Please, enter an existing account number.");
        getAnswer();
        
        if (s.hasNext()) {
            n = s.next();
            try {                                    
                String query = "SELECT * FROM accounts WHERE number='"+n+"'";
                Statement stmt = DB.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                rs.first();
                System.out.print("\n");
                System.out.println("First Name: "+rs.getString("customerName")
                        +"\n"+"SSN: "+rs.getString("customerID")
                        +"\n"+"Deposit/Balance: $"+rs.getString("balance")
                        +"\n"+"Account Type: "+rs.getString("type")
                );

                System.out.print("\n");
                System.out.println("Enter '1' to approve or '2' to deny"
                +"\n"+"1) Approve"
                +"\n"+"2) Deny"
                );
                acctDecision = s.next();
                switch(acctDecision) {
                    case "1":
                        String query2 = "UPDATE accounts SET status='Active' WHERE number='"+rs.getString("number")+"'";
                        PreparedStatement pstmt = DB.prepareStatement(query2);
                        pstmt.executeUpdate(query2);
                        System.out.println("Success! Account "+rs.getString("number")+" has been approved.");
                        System.exit(0);
                        break;
                    case "2":
                        String query3 = "UPDATE accounts SET status='Denied' WHERE number='"+rs.getString("number")+"'";
                        PreparedStatement pstmt2 = DB.prepareStatement(query3);
                        pstmt2.executeUpdate(query3);
                        System.out.println("This application for account "+rs.getString("number")+" has been denied.");
                        System.exit(0);
                        break;
                    default:
                        System.out.println(acctDecision+" is an invalid choice. Try again, later. Goodbye!");
                        System.exit(0);
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Oops, there's been a problem.");
            }
    }
    }
    
    public static void getTransactionHistory(Account A) {
        
    }
    
    public static void shareBalance() {
        Scanner s = new Scanner(System.in);
        String n="";
        
        System.out.println("Please, enter an existing account number.");
        getAnswer();
        
        if (s.hasNext()) {
            n = s.next();
            try {                                    
                String query = "SELECT * FROM accounts WHERE number=?";
                PreparedStatement stmt = DB.prepareStatement(query);
                stmt.setString(1, n);
                ResultSet rs = stmt.executeQuery();
                rs.first();
                System.out.println("The balance is $"+rs.getString("balance")+".");
                System.exit(0);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Oops, there's been a problem.");
            } 
        }
    }
}
