package project.controller;

import java.sql.*;

public class Database {

    public static void createNewDatabase() {

        String url = "jdbc:sqlite:./data/database.db";

        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void createAirportTable(String tableName) {
        String url = "jdbc:sqlite:./data/database.db";
        String sql = "CREATE TABLE IF NOT EXISTS "+tableName+" (\n"
                ;

        try (Connection conn = DriverManager.getConnection(url); Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }



    public static void main(String[] args) {
        createNewDatabase();
    }

}
