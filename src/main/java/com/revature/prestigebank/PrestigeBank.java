package com.revature.prestigebank;

import java.util.Scanner;
import java.sql.*;
import org.apache.logging.log4j.*;
import static com.revature.prestigebank.Account.*;
import static com.revature.prestigebank.Program.*;
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
        boolean opValid = true;
        
        try {
            DB.setAutoCommit(true);
        } catch (SQLException e) { e.printStackTrace(); }
        Scanner s = new Scanner(System.in);
        
        //log4j.trace("Logging activity...\n");
        
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
                    user = new User();
                    
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
                            bank.showUserAccount(user);
                            opValid = true;
                            break;
                        case "2": // Create An Account
                            bank.createUserAccount(user);
                            opValid = true;
                            break;
                        case "3": // Close An Account
                            bank.closeUserAccount(user);
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
                            empChoice = s.next();
                            switch (empChoice) {
                                case "1":
                                    shareBalance();
                                    break;
                                case "2":
                                    approveAccount();
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
}
