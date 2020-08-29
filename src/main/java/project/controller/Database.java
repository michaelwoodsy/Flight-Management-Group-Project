package project.controller;

import project.model.*;

import java.sql.*;
import java.util.ArrayList;

public class Database {

    /**
     * Connects to the database stored in the data folder
     * @return conn - A Connection object referencing the database
     */
    public static Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:./data/database.db");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return conn;
    }

    /**
     * Creates a new database if the database does not already exist
     */
    public static void createNewDatabase() {

        try (Connection conn = connect()) {
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
     */
    public static void createAirportTable() {
        String sql = "CREATE TABLE IF NOT EXISTS airports (\n"
                + " id integer PRIMARY KEY,\n"
                + " risk integer NOT NULL,\n"
                + " altitude integer,\n"
                + " numRoutesSource integer,\n"
                + " numRoutesDest integer,\n"
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

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Creates a new table within the database to hold airline data
     */
    public static void createAirlineTable() {
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

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Creates a new table within the database for route data
     */
    public static void createRouteTable() {
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

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    //public static ArrayList<Flight> getAllFlights() {}

    /**
     * Extracts each airport from the database, and creates a new Airport object for each of them
     * @return airports - an ArrayList containing an object for each airport in the database
     */
    public static ArrayList<Airport> getAllAirports() {
        String query = "SELECT id, risk , altitude, numRoutesSource, numRoutesDest, airportName, city, " +
                "country, iata, icao, destination, timezoneString, airportType, airportSource, latitude, longitude, " +
                "timezone FROM airports";
        ArrayList<Airport> airports = new ArrayList<Airport>();

        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Airport newAirport = new Airport(rs.getInt("id"), rs.getInt("risk"),
                        rs.getString("airportName"), rs.getString("city"), rs.getString("country"),
                        rs.getString("iata"), rs.getString("icao"), rs.getDouble("latitude"),
                        rs.getDouble("longitude"), rs.getInt("altitude"), rs.getDouble("timezone"),
                        rs.getString("destination"), rs.getString("timezoneString"),
                        rs.getString("airportType"), rs.getString("airportSource"),
                        rs.getInt("numRoutesSource"), rs.getInt("numRoutesDest"));
                airports.add(newAirport);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return airports;
    }

    /**
     * Extracts each airline data point from the database, and creates an airline object for each point
     * @return airlines - an ArrayList containing each an object for each airline in the database
     */
    public static ArrayList<Airline> getAllAirlines() {
        String query = "SELECT id, airlineName, country, alias, callsign, icao, iata, active FROM airlines";
        ArrayList<Airline> airlines = new ArrayList<Airline>();

        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                boolean active = false;
                if (rs.getInt("active") == 1) {
                    active = true;
                }
                Airline newAirline = new Airline(rs.getInt("id"), rs.getString("airlineName"), active,
                        rs.getString("country"), rs.getString("alias"), rs.getString("callsign"),
                        rs.getString("iata"), rs.getString("icao"));
                airlines.add(newAirline);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return airlines;
    }

    /**
     * Retrieves all information from the route table, and creates Route objects for each existing data point
     * @return routes - an ArrayList containing a route object for each route in the database
     */
    public static ArrayList<Route> getAllRoutes() {
        String query = "SELECT id, sourceID, destID, numStops, airline, sourceAirport, destAirport, equipment, codeshare FROM routes";
        ArrayList<Route> routes = new ArrayList<Route>();

        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                boolean codeshare = false;
                if (rs.getInt("codeshare") == 1) {
                    codeshare = true;
                }
                Route newRoute = new Route(rs.getString("airline"), rs.getInt("id"),
                        rs.getString("sourceAirport"), rs.getInt("sourceID"), rs.getString("destAirport"),
                        rs.getInt("destID"), rs.getInt("numStops"), rs.getString("equipment"),
                        codeshare);
                routes.add(newRoute);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return routes;
    }

    /**
     * Generates a record class from the data within the current database's tables.
     * Attempts to connect to the database; if it can't, the database is not present, and is created.
     * @return A record class, containing all the data from the databases tables.
     */
    public static Record generateRecord() {
        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT id FROM airports"))
        {} catch (SQLException e) {
            setupDatabase();
        } finally {
            ArrayList<Airline> airlines = getAllAirlines();
            ArrayList<Airport> airports = getAllAirports();
            ArrayList<Route> routes = getAllRoutes();
            ArrayList<Flight> flights = new ArrayList<Flight>();
            ArrayList<Covid> covid = new ArrayList<>();
            Record record = new Record(flights, routes, airports, airlines, covid);
            return record;
        }
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
