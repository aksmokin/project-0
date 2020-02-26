package com.revature.prestigebank;

import java.util.Scanner;
import java.sql.*;
import org.apache.logging.log4j.*;
import static com.revature.prestigebank.Account.*;
import static com.revature.prestigebank.Program.*;
import java.io.File;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
/**
 *
 * @author panam
 */
public class PrestigeBank {
    static User user;
    static Employee bank = new Employee();
    static Logger log4j = LogManager.getLogger(PrestigeBank.class);
    static Connection DB = new Database().getInstance();

    public static void main(String[] args) {
        String opChoice;
        String empChoice="";
        Scanner s;
        
        log4j.log(Level.ALL, "Logging activity...");
        log4j.info("Entering application...");
        
        System.out.println("\nWelcome to Prestige Bank!");
        
        do {
        System.out.println("\nWhat would you like to do?"
                +"\n"+"1) Sign in as a customer"
                +"\n"+"2) Sign in as an employee"
                +"\n"+"3) Exit Bank"
                );
        getAnswer();
        
        s = new Scanner(System.in);
        if (s.hasNext()) {
            String userChoice = s.nextLine();
            switch (userChoice) {
                case "1":
                    user = new User();
                    
                    System.out.println("\nWhat would you like to do?"
                    +"\n"+"1) Access Your Account."
                    +"\n"+"2) Create An Account."
                    +"\n"+"3) Close An Account."
                    );
                    getAnswer();
                    
                    if (s.hasNext()) {
                    opChoice = s.next();
                    switch (opChoice) {
                        case "1": // Access Your Account
                            bank.showUserAccount(user);
                            break;
                        case "2": // Create An Account
                            bank.createUserAccount(user);
                            break;
                        case "3": // Close An Account
                            bank.closeUserAccount(user);
                            break;
                        default:
                            System.out.println(opChoice+" is an invalid choice. Try again.");
                        }
                   } 
                        break;
                        case "2": // User is an employee
                        System.out.println("\nWhat would you like to do, next?"
                        +"\n"+"1) View Account Balance"
                        +"\n"+"2) Approve/Deny An Account"
                        +"\n"+"3) View Transaction History"
                        );
                        getAnswer();
                        
                         if (s.hasNext()) {
                            empChoice = s.next();
                            switch (empChoice) {
                                case "1":
                                    shareBalance();
                                    break;
                                case "2":
                                    approveAccount();
                                    break;
                                case "3":
                                    getTransactionHistory(user.checking);
                                    break;
                                default:
                                    System.out.println(empChoice+" is an invalid choice. Try again, later. Goodbye!");
                            }
                        }
                        break;
                        case "3":
                          System.out.println("\nThank you for your business. Goodbye!");
                          System.exit(0);
                          break;
                    default:
                }
            }
    } while (true);
    }
}
