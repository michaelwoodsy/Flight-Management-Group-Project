package project.controller;

import java.sql.SQLException;
import java.sql.Connection;
import java.sql.DriverManager;

/**
 * A parent class for each of the Database classes. Holds a class that allows for the creation of a connection
 * to the local database.
 */
public class Database {

    /**
     * Connects to the database stored in the main folder of the project
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
