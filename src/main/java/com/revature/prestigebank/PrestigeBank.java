package com.revature.prestigebank;




import com.revature.prestigebank.*;
import java.util.Scanner;
import java.sql.*;
import java.util.Random;
import java.util.TreeMap;

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
        
        while (s.hasNextLine()) {
            String userChoice = s.nextLine();
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
                                    PreparedStatement stmt = DB.prepareStatement(query);

                                    // execute the query, and get a java resultset
                                    ResultSet rs = stmt.executeQuery();

                                    // iterate through the java resultset
                                    if (rs.next())
                                    {
                                      System.out.print("\n");
                                      System.out.println("Welcome, "+rs.getString("name")+"!");
                                      
                                      user = new User();
                                      user.setUsername(username);
                                      user.setPassword(password);
                                      user.setName(rs.getString("name"));
                                      user.setSsn(rs.getString("ssn"));
                                      
                                      int uid = rs.getInt("id");
                                      stmt.close();
                                      rs.close();

                                      String recs = "SELECT COUNT(*) FROM accounts WHERE customerID='"+uid+"'";
                                      PreparedStatement stmt2 = DB.prepareStatement(recs);
                                      ResultSet rs2 = stmt2.executeQuery();
                                      
                                      stmt2.close();
                                      
                                      while (rs2.next()) {
                                          if (rs2.getString("type").equals("Checking")) {
                                              user.checking = new Account(rs2.getString("customerName"));
                                              user.checking.setBalance(
                                                      Double.parseDouble(rs2.getString("balance")
                                                      ));
                                              user.checking.setNumber(rs2.getString("number"));
                                              user.accounts.put(user.checking.getNumber(), "Checking");
                                          } else if (rs2.getString("type").equals("Savings")) {
                                              user.savings = new Account(rs2.getString("customerName"));
                                              user.savings.setBalance(
                                                      Double.parseDouble(rs2.getString("balance")
                                                      ));
                                              user.savings.setNumber(rs2.getString("number"));
                                              user.accounts.put(user.checking.getNumber(), "Savings");
                                          }
                                      }                                                              
                                                      
                                      
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
                                                  double d = (user.accounts.containsValue("Checking")) 
                                                          ? user.checking.getBalance() 
                                                          : user.savings.getBalance();
                                                  System.out.println("Your current balance is $"+d+".");
                                                  break;
                                              case "2": // Make A Withdrawal 
                                                  double d1 = 0.0;
                                                  System.out.println("Please, enter the dollar amount(w/o dollar signs) you wish to withdraw.");
                                                     if (s.hasNextDouble()) {
                                                         d1 = s.nextDouble();
                                                     }
                                                  if (user.accounts.containsValue("Checking")) {
                                                      if (user.checking.getBalance()>= d1) {
                                                          user.checking.withdraw(d1);
                                                      } else {
                                                          System.out.println("Unable to complete transaction. Insufficient funds.");
                                                          System.exit(0);
                                                      }
                                                  } else {
                                                      if (user.savings.getBalance()>= d1) {
                                                          user.savings.withdraw(d1);
                                                      } else {
                                                          System.out.println("Unable to complete transaction. Insufficient funds.");
                                                          System.exit(0);
                                                      }
                                                  }
                                                  System.out.println("Withdrawal successful! Thank you for your business!");
                                                  op2Valid = true;
                                                  break;
                                              case "3": // Deposit Money
                                                  double dm = 0.0;
                                                  System.out.println("Please, enter the dollar amount(w/o dollar signs) you wish to deposit.");
                                                     if (s.hasNextDouble()) {
                                                         dm = s.nextDouble();
                                                     }
                                                  if (user.accounts.containsValue("Checking")) {
                                                      user.checking.setBalance(dm);
                                                  } else {
                                                      user.savings.setBalance(dm);
                                                  }
                                                  System.out.println("Deposit successful! Thank you for your business!");
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
                                              double d2=0.0;
                                              System.out.println("Which account would you like to access?"
                                                +"\n"+"1) Your Checking Account"
                                                +"\n"+"2) Your Savings Account"
                                               );
                                              getAnswer();
                                              if (s.hasNext()) {
                                                  String tChoice = s.next();
                                                  switch (tChoice) {
                                                      case "1":
                                                          System.out.println("Please, enter the dollar amount(w/o dollar signs) you wish to withdraw.");
                                                          if (user.checking.getBalance()>= d2) {
                                                              user.checking.withdraw(d2);
                                                          } else {
                                                              System.out.println("Unable to complete transaction. Insufficient funds.");
                                                              System.exit(0);
                                                          }
                                                          break;
                                                      case "2":
                                                          System.out.println("Please, enter the dollar amount(w/o dollar signs) you wish to withdraw.");
                                                          if (user.savings.getBalance()>= d2) {
                                                          user.savings.withdraw(d2);
                                                      } else {
                                                          System.out.println("Unable to complete transaction. Insufficient funds.");
                                                          System.exit(0);
                                                      }
                                                          break;
                                                      default:
                                                          System.out.println(tChoice+" is an invalid choice. Try again, later. Goodbye!");
                                                          System.exit(0);
                                                  }
                                              }
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
                            String typeofAccount, name="";
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

                            System.out.println("What is your social security number?");
                            getAnswer();
                                if (s.hasNext()) {
                                getAnswer();
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

                                System.out.println("What is your first name?");
                                getAnswer();
                                if (s.hasNext()) {
                                    name = s.next();
                                
                                System.out.print("\n");
                                System.out.println("Submit your initial deposit amount. (w/o dollar signs)");
                                getAnswer();

                                if (s.hasNext()) {
                                    String deposit = s.next();

                                    System.out.println("Your application has been submitted!\n");
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
                                             + "VALUES ('"+username+"', '"+password+"', '"+name+"', '"+ssn+"')";

                                        Statement stmt = DB.createStatement();
                                        int userStatus = stmt.executeUpdate(newUser);

                                        if (userStatus > 0) {
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
                                            
                                            String newAccount = "INSERT INTO accounts (type, balance, customerName, customerID, number, status) "
                                             + "VALUES ('"+typeofAccount+"', '"+deposit+"', '"+name+"', '"+ssn+"', '"+randNum+"', 'Pending')";
                                            
                                            int accountStatus = stmt.executeUpdate(newAccount);
                                            
                                            if (accountStatus > 0) {
                                                System.out.println("Successful Submission! Thank you for your submission.");
                                                System.out.println("\nYour new account number will be sent to you once you're approved!");
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
                                String getRec = "SELECT * users WHERE username='"+username+"' AND password='"+password+"'";
                                String dUpdate = "DELETE FROM users WHERE username='"+username+"' AND password='"+password+"'";

                                Statement dStmt = DB.createStatement();
                                
                                ResultSet toClose = dStmt.executeQuery(getRec);
                                toClose.next();
                                String CID = toClose.getString("ssn");
                                
                                int dStatus = dStmt.executeUpdate(dUpdate);
                                String dUpdate2 = "DELETE FROM accounts WHERE customerID='"+CID+"'"; 
                                dStmt.executeUpdate(dUpdate2);
                            } catch (SQLException e) { e.printStackTrace(); }
                                }
                            }
                            System.out.println("Your account has been closed. Thank you for your business!");
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
                                        getAnswer();
                                        qNum = s.next();
                                        try {                                    
                                            String query = "SELECT * FROM accounts WHERE number='"+qNum+"'";
                                            Statement stmt = DB.createStatement();
                                            ResultSet rs = stmt.executeQuery(query);
                                            rs.first();
                                            System.out.println("The balance is $"+rs.getString("balance")+".");
                                            System.exit(0);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                            System.out.println("Oops, there's been a problem.");
                                        } 
                                        break;
                                    case "2":
                                        System.out.println("Please, enter an existing account number.");
                                        getAnswer();
                                        qNum = s.next();
                                        try {                                    
                                            String query = "SELECT * FROM accounts WHERE number='"+qNum+"'";
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
