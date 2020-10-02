package project.controller;

import java.sql.*;


public class Database {

    /**
     * Connects to the database stored in the resources folder
     * @return conn - A Connection object referencing the database
     */
    public static Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:database.db");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return conn;
    }
}
