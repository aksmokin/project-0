package com.revature.prestigebank;

import static com.revature.prestigebank.PrestigeBank.DB;
import static com.revature.prestigebank.Account.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.TreeMap;

/**
 *
 * @author panam
 */
public class Employee implements UserDAO {
    
    private Map<String, User> users;
    
    public Employee() {
        users = new TreeMap<String, User>();
    }

    @Override
    public void showUserAccount(User user) {
        Scanner s = new Scanner(System.in);
        boolean done=false;
        int i=0;
        ResultSet rs;
        String opChoice="", tChoice="";
        
        System.out.println("\nPlease enter your username.");
        
        getAnswer();
        
        if (s.hasNext()) {
            user.setUsername(s.next());

            System.out.println("\nPlease enter your password.");
            getAnswer();

            if (s.hasNext()) {
                user.setPassword(s.next());

                try {
                    rs = authenticateUser(user);

                    if (rs.next())  {
                      user.setName(rs.getString("customerName"));
                      user.setSsn(rs.getString("customerID"));

                  System.out.println("\nWelcome, "+user.getName()+"!");

                  rs.beforeFirst();
                  while (rs.next()) {
                      if (rs.getString("type").equals("Checking")) {
                          user.checking = new Account(rs.getString("customerName"));
                          user.checking.setBalance(
                                  Double.parseDouble(rs.getString("balance")
                                  ));
                          user.checking.setNumber(rs.getString("number"));
                          user.accounts.put(user.checking.getNumber(), "Checking");
                      } else if (rs.getString("type").equals("Savings")) {
                          user.savings = new Account(rs.getString("customerName"));
                          user.savings.setBalance(
                                  Double.parseDouble(rs.getString("balance")
                                  ));
                          user.savings.setNumber(rs.getString("number"));
                          user.accounts.put(user.checking.getNumber(), "Savings");
                      }
                  }                                                             

                  int k = 0;
                  rs.beforeFirst();
                  while (rs.next()) k++;

                  do {
                  switch (k) {
                      case 1:
                          displayOptions();
                          getAnswer();

                          q: while (s.hasNext()) {
                              opChoice = s.next();
                              switch (opChoice) {
                                  case "1": // Check Your Balance
                                      double d = (user.accounts.containsValue("Checking")) 
                                          ? user.checking.getBalance() 
                                          : user.savings.getBalance();
                                      System.out.println("Your current balance is $"+d+".\n");
                                      Thread.sleep(3000);
                                      break q;
                                  case "2": // Make A Withdrawal 
                                      double d1 = 0.0;
                                      System.out.println("\nPlease, enter the dollar amount(w/o dollar signs) you wish to withdraw.\n");
                                      getAnswer();
                                          if (s.hasNextDouble()) {
                                              d1 = s.nextDouble();
                                          }
                                          if (user.accounts.containsValue("Checking")) {
                                              if (user.checking.getBalance()>= d1) {
                                                  i = user.checking.withdraw(d1);
                                                  if (i > 0) {
                                                    System.out.println("\nWithdrawal successful! Thank you for your business!\n");
                                                  } else {
                                                    System.out.println("\nThank you! Your withdrawal is pending.\n");
                                              }
                                              } else {
                                                  System.out.println("Unable to complete transaction. Insufficient funds.\n");
                                              }
                                           } else {
                                          if (user.savings.getBalance()>= d1) {
                                              i = user.savings.withdraw(d1);
                                              if (i > 0) {
                                                  System.out.println("\nWithdrawal successful! Thank you for your business!\n");
                                              } else {
                                                  System.out.println("\nThank you! Your withdrawal is pending.\n");
                                              }
                                          } else {
                                              System.out.println("Unable to complete transaction. Insufficient funds.\n");
                                          }
                                           }
                                          Thread.sleep(3000);
                                          break q;
                                  case "3": // Deposit Money
                                      double dm = 0.0;
                                      System.out.println("\nPlease, enter the dollar amount(w/o dollar signs) you wish to deposit.\n");
                                      getAnswer();
                                         if (s.hasNextDouble()) {
                                             dm = s.nextDouble();
                                         }
                                      if (user.accounts.containsValue("Checking")) {
                                         i = user.checking.setBalance(dm);
                                      } else {
                                         i = user.savings.setBalance(dm);
                                      }
                                      if (i > 0) {
                                        System.out.println("\nDeposit successful! Thank you for your business!\n");
                                      } else {
                                        System.out.println("\nThank you! Your deposit is pending.\n");
                                      }
                                      Thread.sleep(3000);
                                      break q;
                                  case "4": // Exit Menu
                                      return;
                                  default:
                                      System.out.println(opChoice+" is an invalid choice. Try again.\n");
                                }
                            } 
                          break;
                      case 2:
                          System.out.println("Which account would you like to access?"
                            +"\n"+"1) Your Checking Account"
                            +"\n"+"2) Your Savings Account"
                           );
                          getAnswer();
                          if (s.hasNext()) {
                              tChoice = s.next();
                              switch (tChoice) {
                                  case "1":
                                      displayOptions();
                                      getAnswer();
                                      
                               r: while (s.hasNext()) {
                                  opChoice = s.next();
                                  switch (opChoice) {
                                      case "1": // Check Your Balance
                                          if (user.accounts.containsValue("Checking")) {
                                               double d = user.checking.getBalance();
                                               System.out.println("Your current balance is $"+d+".\n");
                                          }
                                          Thread.sleep(3000);
                                          break r;
                                      case "2": // Make A Withdrawal 
                                          double d1 = 0.0;
                                          System.out.println("\nPlease, enter the dollar amount(w/o dollar signs) you wish to withdraw.\n");
                                          getAnswer();
                                              if (s.hasNextDouble()) {
                                                  d1 = s.nextDouble();
                                              }
                                              if (user.accounts.containsValue("Checking")) {
                                                  if (user.checking.getBalance()>= d1) {
                                                     i = user.checking.withdraw(d1);
                                                     if (i > 0) {
                                                        System.out.println("\nWithdrawal successful! Thank you for your business!\n");
                                                     } else {
                                                        System.out.println("\nThank you! Your withdrawal is pending.\n");
                                                     }
                                                  } else {
                                                      System.out.println("Unable to complete transaction. Insufficient funds.\n");
                                                  }
                                               }
                                              Thread.sleep(3000);
                                              break r;
                                      case "3": // Deposit Money
                                          double dm = 0.0;
                                          System.out.println("\nPlease, enter the dollar amount(w/o dollar signs) you wish to deposit.\n");
                                          getAnswer();
                                             if (s.hasNextDouble()) {
                                                 dm = s.nextDouble();
                                             }
                                          if (user.accounts.containsValue("Checking")) {
                                              i = user.checking.setBalance(dm);
                                              if (i > 0) {
                                                 System.out.println("\nDeposit successful! Thank you for your business!\n");
                                              } else {
                                                 System.out.println("\nThank you! Your deposit is pending.\n");
                                              };
                                          }
                                          Thread.sleep(3000);
                                          break r;
                                      case "4": // Exit Menu
                                          return;
                                      default:
                                          System.out.println(opChoice+" is an invalid choice. Try again.\n");
                                  }
                                } 
                                      break;
                                  case "2":
                                      displayOptions();
                                      getAnswer();
                                   t: while (s.hasNext()) {
                                      opChoice = s.next();
                                      switch (opChoice) {
                                          case "1": // Check Your Balance
                                              if (user.accounts.containsValue("Savings")) {
                                                   double d = user.savings.getBalance();
                                                   System.out.println("Your current balance is $"+d+".\n");
                                              }
                                              Thread.sleep(3000);
                                              break t;
                                          case "2": // Make A Withdrawal 
                                              double d1 = 0.0;
                                              System.out.println("\nPlease, enter the dollar amount(w/o dollar signs) you wish to withdraw.\n");
                                              getAnswer();
                                                  if (s.hasNextDouble()) {
                                                      d1 = s.nextDouble();
                                                  }
                                                  if (user.accounts.containsValue("Savings")) {
                                                      if (user.savings.getBalance()>= d1) {
                                                         i = user.savings.withdraw(d1);
                                                          if (i > 0) {
                                                              System.out.println("\nWithdrawal successful! Thank you for your business!\n");
                                                          } else {
                                                              System.out.println("\nThank you! Your withdrawal is pending.\n");
                                                          }
                                                      } else {
                                                          System.out.println("\nUnable to complete transaction. Insufficient funds.\n");
                                                      }
                                                   }
                                                  Thread.sleep(3000);
                                                  break t;
                                          case "3": // Deposit Money
                                              double dm = 0.0;
                                              System.out.println("\nPlease, enter the dollar amount(w/o dollar signs) you wish to deposit.\n");
                                              getAnswer();
                                                 if (s.hasNextDouble()) {
                                                     dm = s.nextDouble();
                                                 }
                                              if (user.accounts.containsValue("Savings")) {
                                                  i = user.savings.setBalance(dm);
                                                  if (i > 0) {
                                                     System.out.println("\nDeposit successful! Thank you for your business!\n");
                                                  } else {
                                                     System.out.println("\nThank you! Your deposit is pending.\n");
                                                  }
                                              }
                                              Thread.sleep(3000); 
                                              break t;
                                          case "4": // Exit Menu
                                              return;
                                          default:
                                              System.out.println(opChoice+" is an invalid choice. Try again.\n");
                                      }
                                    } 
                                      break;
                                  default:
                                      System.out.println(tChoice+" is an invalid choice. Try again, later. Goodbye!\n");
                              }
                          }
                          break;
                      default:
                  }
                 } while (!done);
                } else {
                    System.out.println("Sorry, your username and/or password is incorrect.\n");
                }
              }
              catch (Exception e) { System.err.println(e.getMessage()); }
              }
        }
    }

    @Override
    public void createUserAccount(User user) {
        Scanner s = new Scanner(System.in);
        boolean invalid;
        String typeofAccount, name="";
        int randNum = 0;
        
        do {
            invalid = false;
            
            System.out.println("\nWhat kind of account do you want to open?\n"
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
                String ssn = s.next();

            try {
                String dupCheck = "SELECT * FROM accounts WHERE customerID=?";
                PreparedStatement stmt = DB.prepareStatement(dupCheck);
                stmt.setString(1, ssn);
                ResultSet rs = stmt.executeQuery();

                int c = 0;
                boolean hasChecking = false;
                boolean hasSavings = false; 

                while (rs.next()) {
                    if (rs.getString("type").equals("Checking")) { hasChecking=true; }
                    else if (rs.getString("type").equals("Savings")) { hasSavings=true; }
                    c++;
                    if (c==2&&hasChecking&&hasSavings) {
                        System.out.println("Sorry, our bank only allows two accounts per customer. Please, try again!");
                        invalid = true;
                    } else if (hasChecking&&typeofAccount.equals("Checking")) {
                        System.out.println("Sorry, our bank only allows one checking account per customer. Please, try again!");
                        invalid = true;
                    } else if (hasSavings&typeofAccount.equals("Savings")) {
                        System.out.println("Sorry, our bank only allows one savings account per customer. Please, try again!");
                        invalid = true;
                    }
                }
            } catch (Exception e) { e.printStackTrace(); }

            System.out.println("What is your first name?");
            getAnswer();
            if (s.hasNext()) {
                name = s.next();

            System.out.println("\nSubmit your initial deposit amount. (w/o dollar signs)");
            getAnswer();

            if (s.hasNext()) {
                String deposit = s.next();

                System.out.println("Your application has been submitted!\n");
                System.out.println("Please, create a username for your account.\n");
                getAnswer();

                if (s.hasNext()) {
                    user.setUsername(s.next());
                    System.out.println("\nPlease, create a password for your account.\n");
                    getAnswer();

                    if (s.hasNext()) {
                        user.setPassword(s.next());
                    try {
                        String newUser = "INSERT INTO users (username, password, name, ssn) "
                             + "VALUES (?, ?, ?, ?)";

                        PreparedStatement stmt = DB.prepareStatement(newUser);
                        stmt.setString(1, user.getUsername());
                        stmt.setString(2, user.getPassword());
                        stmt.setString(3, name);
                        stmt.setString(4, ssn);

                        int userStatus = stmt.executeUpdate();

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

                        stmt.close();

                        String newAccount = "INSERT INTO accounts (type, balance, customerName, customerID, number, status) "
                         + "VALUES (?, ?, ?, ?, ?, ?)";

                        stmt = DB.prepareStatement(newAccount);

                        stmt.setString(1, typeofAccount);
                        stmt.setString(2, deposit);
                        stmt.setString(3, name);
                        stmt.setString(4, ssn);
                        stmt.setInt(5, randNum);
                        stmt.setString(6, "Pending");

                        int accountStatus = stmt.executeUpdate();

                        if (accountStatus > 0) {
                            System.out.println("\nSuccessful Submission! Thank you for your submission.");
                            System.out.println("\nYour new account number will be sent to you once you're approved!");
                        }
                    } else {
                        System.out.println("\nThank you, "+name+"!"+" Your account is pending.");
                    }
                        Thread.sleep(5000);
                } catch (Exception e) { e.printStackTrace(); }
                        }
                    }
                }
             }
            }
        }
    } while (invalid);
    }

    @Override
    public void closeUserAccount(User user) {
        Scanner s = new Scanner(System.in);
        
        System.out.println("\nPlease enter your username.");
        
        getAnswer();

        if (s.hasNext()) {
            user.setUsername(s.next());

            System.out.println("\nPlease enter your password.");
            getAnswer();

            if (s.hasNext()) {
                user.setPassword(s.next());

            try {
                String getRec = "SELECT * users WHERE username='"+user.getUsername()+"' AND password='"+user.getPassword()+"'";
                String dUpdate = "DELETE FROM users WHERE username='"+user.getUsername()+"' AND password='"+user.getPassword()+"'";

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
        System.out.println("\nYour account has been closed. Thank you for your business!");
    }

    @Override
    public ResultSet authenticateUser(User user) throws SQLException {
        PreparedStatement pstmt;
        String query;
        ResultSet rs;
        
        query = "SELECT * FROM accounts WHERE customerID="
        + "(SELECT ssn FROM users WHERE username=? "
        + "AND password=?)";

        pstmt = DB.prepareStatement(query);
        pstmt.setString(1, user.getUsername());
        pstmt.setString(2, user.getPassword());
        
        rs = pstmt.executeQuery();

        return rs;
    }
    
}
