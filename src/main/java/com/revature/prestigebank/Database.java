package com.revature.prestigebank;


import com.revature.prestigebank.*;
import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author panam
 */
public class Database {
    private static final String dbLink = "jdbc:mysql://localhost:3306/bank?zeroDateTimeBehavior=convertToNull&serverTimezone=UTC";
    private static final String dbDriver = "com.mysql.cj.jdbc.Driver";
    private static final String dbUser = "jgrayman", dbPass="kcodrab1G$";
    private static Connection conn = null;

    public Connection getInstance() {
        try {
            Class.forName(dbDriver);
            conn = DriverManager.getConnection(dbLink, dbUser, dbPass);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
            }
}
