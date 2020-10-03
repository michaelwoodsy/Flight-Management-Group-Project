package project.controller;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * A class that creates the database file, and the tables stored within that file
 */
public class CreateDatabase extends Database {

    /**
     * Creates a new database if the database does not already exist
     */
    public static void createNewDatabase() {

        try (Connection conn = connect()) {
            if (conn == null) {
                System.err.println("No connection established to database");
            }
        } catch (SQLException e) {}
    }

    /**
     * Creates the airport table within the database
     */
    public static void createAirportTable() {
        String sql = "CREATE TABLE IF NOT EXISTS airports (\n"
                + " id integer NOT NULL,\n"
                + " airportObject blob,\n" //Store the airport object itself, instead of each of its attributes
                + " latitude real,\n" //Store additional attributes that can be accessed without having to deserialise the airport bytes
                + " longitude real,\n"
                + " record text NOT NULL,\n"
                + " PRIMARY KEY(id, record)\n"
                + ");";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {}
    }

    /**
     * Creates a new table within the database to hold airline data
     */
    public static void createAirlineTable() {
        String sql = "CREATE TABLE IF NOT EXISTS airlines (\n"
                + " id integer NOT NULL,\n"
                + " airlineName text,\n" //Store additional attributes that can be accessed without having to deserialise the airline bytes
                + " airlineObject blob,\n" //Store the airline object itself, instead of each of its attributes
                + " record text NOT NULL,\n"
                + " PRIMARY KEY(id, record)\n"
                + ");";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {}
    }

    /**
     * Creates a new table within the database for route data
     */
    public static void createRouteTable() {
        String sql = "CREATE TABLE IF NOT EXISTS routes (\n"
                + " id integer NOT NULL,\n"
                + " airlineId integer,\n" //Store additional attributes that can be accessed without having to deserialise the route bytes
                + " sourceID integer,\n"
                + " destID integer,\n"
                + " routeObject blob,\n" //Store the route object itself, instead of each of its attributes
                + " record text NOT NULL,\n"
                + " PRIMARY KEY(id, record)\n"
                + ");";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {}
    }


    /**
     * Calls support functions to establish a database and create tables within it.
     * Called on first time database is built.
     */
    public static void setupDatabase() {
        createNewDatabase();
        createAirportTable();
        createAirlineTable();
        createRouteTable();
    }
}
