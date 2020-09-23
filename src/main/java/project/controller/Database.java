package project.controller;

import project.model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Database {

    private static ArrayList<String> airportTableColumns = new ArrayList<String>();
    private static ArrayList<String> airlineTableColumns = new ArrayList<String>();
    private static ArrayList<String> routeTableColumns = new ArrayList<String>();

    public static void populateAirportTableColumns() {
        if (airportTableColumns.size() == 0) {
            airportTableColumns.add("id");
            airportTableColumns.add("altitude");
            airportTableColumns.add("numRoutesSource");
            airportTableColumns.add("numRoutesDest");
            airportTableColumns.add("airportName");
            airportTableColumns.add("city");
            airportTableColumns.add("country");
            airportTableColumns.add("iata");
            airportTableColumns.add("icao");
            airportTableColumns.add("destination");
            airportTableColumns.add("timezoneString");
            airportTableColumns.add("airportType");
            airportTableColumns.add("airportSource");
            airportTableColumns.add("latitude");
            airportTableColumns.add("longitude");
            airportTableColumns.add("timezone");
            airportTableColumns.add("record");
        }
    }

    public static void populateAirlineTableColumns() {
        if (airlineTableColumns.size() == 0) {
            airlineTableColumns.add("id");
            airlineTableColumns.add("airlineName");
            airlineTableColumns.add("country");
            airlineTableColumns.add("alias");
            airlineTableColumns.add("callsign");
            airlineTableColumns.add("icao");
            airlineTableColumns.add("iata");
            airlineTableColumns.add("active");
            airlineTableColumns.add("record");
        }
    }

    public static void populateRouteTableColumns() {
        if (routeTableColumns.size() == 0) {
            routeTableColumns.add("id");
            routeTableColumns.add("sourceID");
            routeTableColumns.add("destID");
            routeTableColumns.add("numStops");
            routeTableColumns.add("airline");
            routeTableColumns.add("sourceAirport");
            routeTableColumns.add("destAirport");
            routeTableColumns.add("equipment");
            routeTableColumns.add("codeshare");
            routeTableColumns.add("record");
        }
    }


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

    /**
     * Creates a new database if the database does not already exist
     */
    public static void createNewDatabase() {

        try (Connection conn = connect()) {
            if (conn != null) {
                System.out.println("A new database has been created");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Adds a new airport into the database's airport table.
     * It is assumed that the table has already been created.
     * @param airport The airport object to be added to the database.
     */
    public static void addNewAirport(Airport airport) {

        String insertStatement = String.format("INSERT INTO airports(id, altitude, numRoutesSource, numRoutesDest, " +
                "airportName, city, country, iata, icao, destination, timezoneString, airportType, airportSource, latitude, " +
                "longitude, timezone, record) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(insertStatement)) {
            pstmt.setInt(1, airport.getId());
            pstmt.setInt(2, airport.getAltitude());
            pstmt.setInt(3, airport.getNumRoutesSource());
            pstmt.setInt(4, airport.getNumRoutesDest());
            pstmt.setString(5, airport.getName());
            pstmt.setString(6, airport.getCity());
            pstmt.setString(7, airport.getCountry());
            pstmt.setString(8, airport.getIata());
            pstmt.setString(9, airport.getIcao());
            pstmt.setString(10, airport.getDst());
            pstmt.setString(11, airport.getTimezoneString());
            pstmt.setString(12, airport.getType());
            pstmt.setString(13, airport.getSource());
            pstmt.setDouble(14, airport.getLatitude());
            pstmt.setDouble(15, airport.getLongitude());
            pstmt.setDouble(16, airport.getTimezone());
            pstmt.setString(17, airport.getRecordName());
            pstmt.executeUpdate();
            System.out.println("Airport added");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void addNewAirline(Airline airline) {

        String insertStatement = String.format("INSERT INTO airlines(id, airlineName, country, alias, callsign, icao, iata, " +
                "active, record) VALUES(?,?,?,?,?,?,?,?,?)");
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(insertStatement)) {
            pstmt.setInt(1, airline.getId());
            pstmt.setString(2, airline.getName());
            pstmt.setString(3, airline.getCountry());
            pstmt.setString(4, airline.getAlias());
            pstmt.setString(5, airline.getCallSign());
            pstmt.setString(6, airline.getIcao());
            pstmt.setString(7, airline.getIata());
            if (airline.isActive()) {
                pstmt.setInt(8, 1);
            } else {
                pstmt.setInt(8, 0);
            }
            pstmt.setString(9, airline.getRecordName());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void addNewRoute(Route route) {
        String insertStatement = String.format("INSERT INTO routes(id, sourceID, destID, numStops, airline, sourceAirport, " +
                "destAirport, equipment, codeshare, record) VALUES(?,?,?,?,?,?,?,?,?,?)");
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(insertStatement)) {
            pstmt.setInt(1, route.getId());
            pstmt.setInt(2, route.getSourceID());
            pstmt.setInt(3, route.getDestID());
            pstmt.setInt(4, route.getNumStops());
            pstmt.setString(5, route.getAirline());
            pstmt.setString(6, route.getSourceAirport());
            pstmt.setString(7, route.getDestAirport());
            pstmt.setString(8, route.getEquipment());
            if (route.isCodeshare()) {
                pstmt.setInt(9, 1);
            } else {
                pstmt.setInt(9, 0);
            }
            pstmt.setString(10, route.getRecordName());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Removes an amount of airports from the database that match the provided criteria.
     * @param column If a data point has 'value' in this column, it will be deleted.
     * @param value If a data point has this value in 'column', it will be deleted.
     */
    public static void removeAirport(String column, String value) throws NoSuchFieldException {

        if (!airportTableColumns.contains(column)) {
            throw new NoSuchFieldException("Provided column value is not a column in the airport table");
        }

        String deleteStatement = String.format("DELETE FROM airports WHERE %s = ?", column);

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(deleteStatement)) {
            pstmt.setString(1, value);
            pstmt.executeUpdate();
            System.out.println("Airport removed");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void removeAirline(String column, String value) throws NoSuchFieldException {
        if (!airlineTableColumns.contains(column)) {
            throw new NoSuchFieldException("Column name does not exist in the airline table.");
        }

        String deleteStatement = String.format("DELETE FROM airlines WHERE %s = ?", column);

        try (Connection conn = connect();
            PreparedStatement pstmt = conn.prepareStatement(deleteStatement)) {
            pstmt.setString(1, value);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }


    }

    public static void removeRoute(String column, String value) throws NoSuchFieldException {
        if (!routeTableColumns.contains(column)) {
            throw new NoSuchFieldException("Column name does not exist in the routes table.");
        }

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(String.format("DELETE FROM routes WHERE %s = ?", column))) {
            pstmt.setString(1, value);
            pstmt.executeUpdate();
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
                + " timezone real,\n"
                + " record integer\n"
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
                + " active integer,\n" //Boolean value, can only be either 1 or 0
                + " record integer\n"
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
                + " codeshare integer,\n" //Boolean value, can only be either 1 or 0
                + " record integer,\n"
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

    /**
     * Extracts each airport from the database, and creates a new Airport object for each of them
     * @return airports - an ArrayList containing an object for each airport in the database
     */
    public static ArrayList<ArrayList<Airport>> getAllAirports() {
        String query = "SELECT id, altitude, numRoutesSource, numRoutesDest, airportName, city, " +
                "country, iata, icao, destination, timezoneString, airportType, airportSource, latitude, longitude, " +
                "timezone, record FROM airports";
        HashMap<String, Integer> recordNumbers = new HashMap<>();
        ArrayList<ArrayList<Airport>> recordList = new ArrayList<>();
        int currentNumOfRecords = 0;

        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Airport newAirport = new Airport(rs.getInt("id"), 0,
                        rs.getString("airportName"), rs.getString("city"), rs.getString("country"),
                        rs.getString("iata"), rs.getString("icao"), rs.getDouble("latitude"),
                        rs.getDouble("longitude"), rs.getInt("altitude"), rs.getDouble("timezone"),
                        rs.getString("destination"), rs.getString("timezoneString"),
                        rs.getString("airportType"), rs.getString("airportSource"),
                        rs.getInt("numRoutesSource"), rs.getInt("numRoutesDest"));
                String record = rs.getString("record");
                newAirport.setRecordName(record);
                if (recordNumbers.get(record) != null) {
                    int recordNum = recordNumbers.get(record);
                    recordList.get(recordNum).add(newAirport);
                } else {
                    recordList.add(new ArrayList<>());
                    recordList.get(currentNumOfRecords).add(newAirport);
                    recordNumbers.put(record, currentNumOfRecords);
                    currentNumOfRecords += 1;
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return recordList;
    }

    /**
     * Extracts each airline data point from the database, and creates an airline object for each point
     * @return airlines - an ArrayList containing each an object for each airline in the database
     */
    public static ArrayList<ArrayList<Airline>> getAllAirlines() {
        String query = "SELECT id, airlineName, country, alias, callsign, icao, iata, active, record FROM airlines";
        HashMap<String, Integer> recordNumbers = new HashMap<>();
        ArrayList<ArrayList<Airline>> recordList = new ArrayList<>();
        int currentRecordNum = 0;

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
                String record = rs.getString("record");
                newAirline.setRecordName(record);
                if (recordNumbers.get(record) != null) {
                    int recordNum = recordNumbers.get(record);
                    recordList.get(recordNum).add(newAirline);
                } else {
                    recordList.add(new ArrayList<>());
                    recordList.get(currentRecordNum).add(newAirline);
                    recordNumbers.put(record, currentRecordNum);
                    currentRecordNum += 1;
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return recordList;
    }

    /**
     * Retrieves all information from the route table, and creates Route objects for each existing data point
     * @return routes - an ArrayList containing a route object for each route in the database
     */
    public static ArrayList<ArrayList<Route>> getAllRoutes() {
        String query = "SELECT id, sourceID, destID, numStops, airline, sourceAirport, destAirport, equipment, codeshare, " +
                "record FROM routes";

        HashMap<String, Integer> recordNumbers = new HashMap<>();
        ArrayList<ArrayList<Route>> recordList = new ArrayList<>();
        int currentRecordNum = 0;


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

                String record = rs.getString("record");
                newRoute.setRecordName(record);
                if (recordNumbers.get(record) != null) {
                    int recordNum = recordNumbers.get(record);
                    recordList.get(recordNum).add(newRoute);
                } else {
                    recordList.add(new ArrayList<>());
                    recordList.get(currentRecordNum).add(newRoute);
                    recordNumbers.put(record, currentRecordNum);
                    currentRecordNum += 1;
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return recordList;
    }

    public static ArrayList<Record> getRecords(ArrayList<ArrayList<Airline>> airlines, ArrayList<ArrayList<Airport>> airports, ArrayList<ArrayList<Route>> routes) {
        ArrayList<Flight> flights = new ArrayList<>();
        ArrayList<Record> records = new ArrayList<>();
        for (ArrayList<Airline> airline: airlines) {
            System.out.println(airline.get(0).getRecordName());
            ArrayList<Airport> matchingAirport = new ArrayList<>();
            ArrayList<Route> matchingRoute = new ArrayList<>();
            for (ArrayList<Airport> airport : airports) {
                if (airport.get(0).getRecordName().equals(airline.get(0).getRecordName())) {
                    matchingAirport = airport;
                    airports.remove(airport); //Corresponding airport list no longer needs to be stored
                    break;
                }
            }
            for (ArrayList<Route> route : routes) {
                if (route.get(0).getRecordName().equals(airline.get(0).getRecordName())) {
                    matchingRoute = route;
                    routes.remove(route); //Corresponding route list no longer needs to be stored - remove it so searching for others is faster
                    break;
                }
            }
            records.add(new Record(flights, matchingRoute, matchingAirport, airline));
        }
        //Check if there are any additional records containing only airports, or airports and routes
        for (ArrayList<Airport> airport: airports) {
            ArrayList<Route> matchingRoute = new ArrayList<>();
            for (ArrayList<Route> route: routes) {
                if (route.get(0).getRecordName().equals(airport.get(0).getRecordName())) {
                    matchingRoute = route;
                    routes.remove(route);
                    break;
                }
            }
            records.add(new Record(flights, matchingRoute, airport, new ArrayList<>()));
        }

        //Check if there are any additional records containing only routes
        for (ArrayList<Route> route: routes) {
            records.add(new Record(flights, route, new ArrayList<>(), new ArrayList<>()));
        }

        return records;
    }

    /**
     * Generates a record class from the data within the current database's tables.
     * Attempts to connect to the database; if it can't, the database is not present, and is created.
     * @return A record class, containing all the data from the databases tables.
     */
    public static ArrayList<Record> generateRecord() {
        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT id FROM airports"))
        {} catch (SQLException e) {
            setupDatabase();
        } finally {
            ArrayList<ArrayList<Airline>> airlines = getAllAirlines();
            ArrayList<ArrayList<Airport>> airports = getAllAirports();
            ArrayList<ArrayList<Route>> routes = getAllRoutes();
            ArrayList<Record> records = getRecords(airlines, airports, routes);

            return records;
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
