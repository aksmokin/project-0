
package com.revature.prestigebank;

import java.util.Scanner;
import java.sql.*;
import java.util.Random;

/**
 *
 * @author panam
 */
public class PrestigeBank {
    static User user;
    static Connection DB = new Database().getInstance();
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String password, username, opChoice, op2Choice, qNum, number=null;
        String acctDecision;
        int numRecords = 0;
        int randNum = 0;
        boolean opValid = false, op2Valid = false;
        
        Scanner s = new Scanner(System.in);
        System.out.println("Welcome to Prestige Bank!\n");
        
                
        System.out.println("Are you an employee or customer?"
                +"\n"+"1) Customer"
                +"\n"+"2) Employee"
                );
        getAnswer();
        
        while (s.hasNext()) {
            String userChoice = s.next();
            switch (userChoice) {
                case "1":
                    System.out.println("What would you like to do?"
                    +"\n"+"1) Access Your Account."
                    +"\n"+"2) Create An Account."
                    +"\n"+"3) Close An Account."
                    );
                    getAnswer();
                    while (s.hasNext()) {
                    opChoice = s.next();
                    switch (opChoice) {
                        case "1": // Access Your Account
                            opValid = true;
                            System.out.print("\n");
                            System.out.println("Please enter your username.");
                            getAnswer();
                            if (s.hasNext()) {
                                username = s.next();

                                System.out.print("\n");
                                System.out.println("Please enter your password.");
                                getAnswer();

                                if (s.hasNext()) {
                                    password = s.next();

                                    try {
                                    // our SQL SELECT query. 
                                    // if you only need a few columns, specify them by name instead of using "*"
                                    String query = "SELECT * FROM users WHERE username='"+username+"' AND password='"+password+"'";

                                    // create the java statement
                                    Statement stmt = DB.createStatement();

                                    // execute the query, and get a java resultset
                                    ResultSet rs = stmt.executeQuery(query);

                                    // iterate through the java resultset
                                    if (rs.next())
                                    {
                                      System.out.print("\n");
                                      System.out.println("Welcome, "+rs.getString("name")+"!");
                                      
                                      user.setUsername(username);
                                      user.setPassword(password);

                                      String recs = "SELECT COUNT(*) FROM accounts WHERE customerID='"+rs.getInt("id")+"'";

                                      ResultSet rs2 = stmt.executeQuery(recs);

                                      int k = 0;
                                      while (rs2.next()) k++;

                                      switch (k) {
                                          case 1:
                                              displayOptions();
                                              getAnswer();

                                              while (s.hasNext()) {
                                         op2Choice = s.next();
                                          switch (op2Choice) {
                                              case "1": // Check Your Balance
                                                  op2Valid = true;
                                                  System.out.println("Your current balance is "+rs2.getRowId("balance")+".");
                                                  break;
                                              case "2": // Make A Withdrawal 
                                                  op2Valid = true;
                                                  break;
                                              case "3": // Deposit Money
                                                  op2Valid = true;
                                                  break;
                                              case "4": // Exit Bank
                                                  op2Valid = true;
                                                  System.out.print("\n");
                                                  System.out.println("Goodbye!");
                                                  return;
                                              default:
                                                  op2Valid = false;
                                                  System.out.println(opChoice+" is an invalid choice. Try again.");
                                                  displayOptions();
                                          }
                                          if (op2Valid) break;
                                      } 
                                              break;
                                          case 2:
                                              //ask which account needs to be accessed, then
                                              System.out.println("Which account would you like to access?"
                                                +"\n"+"1) Your Checking Account"
                                                +"\n"+"2) Your Savings Account"
                                               );
                                              getAnswer();
                                              break;
                                          default:

                                      }
                                      //ask which account needs to be accessed, then 
                                    } else {
                                        System.err.println("Sorry, your username and/or password is incorrect.\n");
                                        stmt.close();
                                        return;
                                    }
                                    stmt.close();
                                  }
                                  catch (Exception e) { System.err.println(e.getMessage()); }
                                  }
                            }
                            break;
                        case "2": // Create An Account
                            opValid = true;
                            String typeofAccount;
                            System.out.println("What kind of account do you want to open?\n"
                            +"\n"+"1) Checking Account"
                            +"\n"+"2) Savings Account"
                            );
                            getAnswer();
                            if (s.hasNext()) {
                                switch (s.next()) {
                                    case "1":
                                        typeofAccount = "Checking";
                                        break;
                                    case "2":
                                        typeofAccount = "Savings";
                                        break;
                                    default:
                                        typeofAccount = "Checking";
                                }

                            System.out.println("What is your name?");
                            getAnswer();
                            if (s.hasNext()) {
                                String name = s.next();

                                System.out.println("What is your social security number?");
                                getAnswer();
                                if (s.hasNext()) {
                                String ssn = s.next();

                                try {
                                    Statement stmt = DB.createStatement();
                                    String dupCheck = "SELECT * FROM accounts WHERE customerID='"+ssn+"'";
                                    ResultSet rs = stmt.executeQuery(dupCheck);

                                    int c = 0;
                                    boolean hasChecking = false;
                                    boolean hasSavings = false; 

                                    while (rs.next()) {
                                        if (rs.getString("type").equals("Checking")) { hasChecking=true; }
                                        else if (rs.getString("type").equals("Savings")) { hasSavings=true; }
                                        c++;
                                        if (c==2&&hasChecking&&hasSavings) {
                                            System.out.println("Sorry, our bank only allows two accounts per customer. Goodbye!");
                                            return;
                                        } else if (hasChecking&&typeofAccount.equals("Checking")) {
                                            System.out.println("Sorry, our bank only allows one checking account per customer. Goodbye!");
                                            return;
                                        } else if (hasSavings&typeofAccount.equals("Savings")) {
                                            System.out.println("Sorry, our bank only allows one savings account per customer. Goodbye!");
                                            return;
                                        }
                                    }
                                } catch (Exception e) { e.printStackTrace(); }

                                System.out.println("Submit your initial deposit amount.");
                                getAnswer();

                                if (s.hasNext()) {
                                    String deposit = s.next();

                                    System.out.println("You're application has been submitted!\n");
                                    System.out.println("Please, create a username for your account.\n");
                                    getAnswer();

                                    if (s.hasNext()) {
                                        username = s.next();
                                        System.out.println("Please, create a password for your account.\n");
                                        getAnswer();

                                        if (s.hasNext()) {
                                            password = s.next();
                                    try {
                                        String newUser = "INSERT INTO users (username, password, name, ssn) "
                                             + "VALUES ('"+username+"', '"+password+"', '"+name+"', '"+ssn+"'";

                                        Statement stmt = DB.createStatement();
                                        int userStatus = stmt.executeUpdate(newUser);

                                        if (userStatus > 0) {
                                            String newAccount = "INSERT INTO accounts (type, balance, customerName, customerID, number) "
                                             + "VALUES ('"+typeofAccount+"', '"+deposit+"', '"+name+"', '"+userStatus+"', "+number+"'";

                                        // create the java statement
                                        int accountStatus = stmt.executeUpdate(newAccount);

                                        if (userStatus > 0 && accountStatus > 0) {
                                            String accts = "SELECT * FROM accounts";

                                            ResultSet alist = stmt.executeQuery(accts);
                                            boolean exists;

                                            do {
                                                exists=false;
                                                randNum = new Random().nextInt(10000000);
                                                alist.beforeFirst();
                                                while (alist.next()) {
                                                    if (randNum == alist.getInt("number")) exists=true;
                                                }
                                                } while (exists);                  

                                            System.out.println("Successful Submission! Thank you for your submission.");
                                            System.out.println("\nYour new account number will be sent to you once you're approved!");
                                        } else {
                                            System.out.println("Thank you, "+name+"!"+" Your account is pending.");
                                        }
                                        } else {
                                            System.out.println("Thank you, "+name+"!"+" Your account is pending.");
                                        }
                                    } catch (Exception e) { e.printStackTrace(); }
                                            }
                                        }
                                     }
                                    }
                                }
                            }
                            break;
                        case "3": // Close An Account
                            opValid = true;
                            break;
                        default:
                            opValid = false;
                            System.out.println(opChoice+" is an invalid choice. Try again.");
                            displayOptions();
                            getAnswer();
                    }
                    if (opValid) break;
                   } 
                            break;
                            case "2": // User is an employee
                            System.out.println("What would you like to do, next?"
                            +"\n"+"1) View Account Balance"
                            +"\n"+"2) Approve/Deny An Account"
                            +"\n"+"3) View Transaction History"
                            );
                            getAnswer();
                             while (s.hasNext()) {
                                String empChoice = s.next();
                                switch (empChoice) {
                                    case "1":
                                        System.out.println("Please, enter an existing account number.");
                                        qNum = s.next();
                                        try {                                    
                                            String query = "SELECT * FROM accounts WHERE number='"+qNum+"'";
                                            Statement stmt = DB.createStatement();
                                            ResultSet rs = stmt.executeQuery(query);
                                            System.out.println(rs.getRowId("balance"));
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                            System.out.println("Oops, there's been a problem.");
                                        } 
                                        break;
                                    case "2":
                                        System.out.println("Please, enter an existing account number.");
                                        qNum = s.next();
                                        try {                                    
                                            String query = "SELECT * FROM accounts WHERE number='"+qNum+"'";
                                            Statement stmt = DB.createStatement();
                                            ResultSet rs = stmt.executeQuery(query);
                                            System.out.println("Name: "+rs.getRowId("customerName")
                                                    +"\n"+"SSN: "+rs.getRowId("customerID")
                                                    +"\n"+"Deposit/Balance: "+rs.getRowId("balance")
                                                    +"\n"+"Account Type: "+rs.getRowId("type")
                                            );
                                            System.out.println("Enter '1' to approve or '2' to deny"
                                            +"\n"+"1) Approve"
                                            +"\n"+"2) Deny"
                                            );
                                            acctDecision = s.next();
                                            switch(acctDecision) {
                                                case "1":
                                                    rs.updateString("status", "Active");
                                                    rs.updateRow();
                                                    System.out.println("Success! Account "+rs.getRowId("number")+" has been approved.");
                                                    break;
                                                case "2":
                                                    rs.updateString("status", "Denied");
                                                    rs.updateRow();
                                                    System.out.println("This application for account "+rs.getRowId("number")+" has been denied.");
                                                default:
                                                    System.out.println(acctDecision+" is an invalid choice. Try again, later. Goodbye!");
                                                    System.exit(0);
                                            }
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                            System.out.println("Oops, there's been a problem.");
                                        } 
                                        break;
                                    case "3":
                                        System.out.println("Please, enter an existing account number.");
                                        break;
                                    default:
                                        System.out.println(empChoice+" is an invalid choice. Try again, later. Goodbye!");
                                        System.exit(0);
                                }
                            }
                            break;
                        default:
                    }
                }
    }
    
    private static final void displayOptions () {
                System.out.println("What would you like to do?"
                +"\n"+"1) Check Your Balance."
                +"\n"+"2) Make A Withdrawal."
                +"\n"+"3) Deposit Money."
                +"\n"+"4) Exit Bank."
                );
    }
    
    private static final void getAnswer () { 
        System.out.print("\n"); 
        System.out.println("Response> "); 
    }
    
    
}
