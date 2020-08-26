package project.controller;

import java.sql.*;

public class Database {

    /**
     * Creates a new database if the database does not already exist
     * @param url The location within the application where the database should be created
     */
    public static void createNewDatabase(String url) {

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

    /**
     * Creates a new table within the database for airport data
     * @param url The location of the database within the program
     */
    public static void createAirportTable(String url) {
        String sql = "CREATE TABLE IF NOT EXISTS airports (\n"
                + " id integer PRIMARY KEY,\n"
                + " risk integer NOT NULL,\n"
                + " altitude integer,\n"
                + " numRoutesSource integer,\n"
                + " numRoutesDest integer,\n"
                + " totalRoutes integer,\n"
                + " airportName text,\n"
                + " city text,\n"
                + " country text,\n"
                + " iata text,\n"
                + " icao text,\n"
                + " destination text,\n"
                + " timezoneString text,\n"
                + " airportType text,\n"
                + " airportSource text,\n"
                + " latitude real,\n"
                + " longitude real,\n"
                + " timezone real\n"
                + ");";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Creates a new table within the database to hold airline data
     * @param url The location of the database within the program
     */
    public static void createAirlineTable(String url) {
        String sql = "CREATE TABLE IF NOT EXISTS airlines (\n"
                + " id integer PRIMARY KEY,\n"
                + " airlineName text,\n"
                + " country text,\n"
                + " alias text,\n"
                + " callsign text,\n"
                + " icao text,\n"
                + " iata text,\n"
                + " active integer\n" //Boolean value, can only be either 1 or 0
                + ");";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Creates a new table within the database for route data
     * @param url The location of the database within the program
     */
    public static void createRouteTable(String url) {
        String sql = "CREATE TABLE IF NOT EXISTS routes (\n"
                + " id integer PRIMARY KEY,\n"
                + " sourceID integer,\n"
                + " destID integer,\n"
                + " numStops integer,\n"
                + " airline text,\n"
                + " sourceAirport text,\n"
                + " destAirport text,\n"
                + " equipment text,\n"
                + " codeshare integer\n," //Boolean value, can only be either 1 or 0
                + " FOREIGN KEY (sourceID)"
                + "     REFERENCES airports (id)"
                + " FOREIGN KEY (destID)"
                + "     REFERENCES airports (id)"
                + ");";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public static void main(String[] args) {
        String url = "jdbc:sqlite:./data/database.db";
        createNewDatabase(url);
        createAirportTable(url);
        createAirlineTable(url);
        createRouteTable(url);
    }
}
