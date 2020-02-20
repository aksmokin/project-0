/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.revature.prestigebank;

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
    private static final Connection conn = null;

    public Connection getInstance() {
        try {
            Class.forName(dbDriver);
            Connection conn = DriverManager.getConnection(dbLink, dbUser, dbPass);
        } catch (Exception e) {
            e.printStackTrace();
        }
            return conn;
            }
}
