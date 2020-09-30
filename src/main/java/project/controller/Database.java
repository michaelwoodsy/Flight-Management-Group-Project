package project.controller;

import org.apache.commons.lang3.SerializationUtils;
import project.model.*;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

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

    public static void clearDatabase() {
        try (Connection conn = Database.connect();
             Statement stmt = conn.createStatement()) {
            String dropStatement = "DROP TABLE airports";
            stmt.executeUpdate(dropStatement);
            dropStatement = "DROP TABLE airlines";
            stmt.executeUpdate(dropStatement);
            dropStatement = "DROP TABLE routes";
            stmt.executeUpdate(dropStatement);
            System.out.println("Tables dropped");
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

        byte[] airportAsBytes = SerializationUtils.serialize(airport);

        String insertStatement = "INSERT INTO airports(id, airportObject, latitude, " +
                "longitude, record) VALUES(?,?,?,?,?)";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(insertStatement)) {
            pstmt.setInt(1, airport.getId());
            pstmt.setBytes(2, airportAsBytes);
            pstmt.setDouble(3, airport.getLatitude());
            pstmt.setDouble(4, airport.getLongitude());
            pstmt.setString(5, airport.getRecordName());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void addNewAirline(Airline airline) {

        byte[] airlineAsBytes = SerializationUtils.serialize(airline);

        String insertStatement = "INSERT INTO airlines(id, airlineName, airlineObject, record) VALUES(?,?,?,?)";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(insertStatement)) {
            pstmt.setInt(1, airline.getId());
            pstmt.setString(2, airline.getName());
            pstmt.setBytes(3, airlineAsBytes);
            pstmt.setString(4, airline.getRecordName());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void addNewRoute(Route route) {

        byte[] routeAsBytes = SerializationUtils.serialize(route);

        String insertStatement = "INSERT INTO routes(id, airlineId, sourceID, destID, routeObject, record) VALUES(?,?,?,?,?,?)";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(insertStatement)) {
            pstmt.setInt(1, route.getId());
            pstmt.setInt(2, route.getAirlineId());
            pstmt.setInt(3, route.getSourceID());
            pstmt.setInt(4, route.getDestID());
            pstmt.setBytes(5, routeAsBytes);
            pstmt.setString(6, route.getRecordName());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void removeData(int id, String record, String table) {
        String deleteStatement = String.format("DELETE FROM %s WHERE id = ? AND record = ?", table);

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(deleteStatement)) {
            pstmt.setInt(1, id);
            pstmt.setString(2, record);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void updateRoute(Route route) {

        byte[] routeInBytes = SerializationUtils.serialize(route);
        String columnUpdates = "airlineId = ?, sourceID = ?, destID = ?, routeObject = ?";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(String.format("UPDATE routes SET %s WHERE id = ? and record = ?", columnUpdates))) {
            pstmt.setInt(1, route.getAirlineId());
            pstmt.setInt(2, route.getSourceID());
            pstmt.setInt(3, route.getDestID());
            pstmt.setBytes(4, routeInBytes);
            pstmt.setInt(5, route.getId());
            pstmt.setString(6, route.getRecordName());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void updateAirport(Airport airport) {

        byte[] airportInBytes = SerializationUtils.serialize(airport);
        String columnUpdates = "airportObject = ?, latitude = ?, longitude = ?";

        String pstmtString = String.format("UPDATE airports SET WHERE id = ? AND record = ?", columnUpdates);
        System.out.println(pstmtString);
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(pstmtString)) {
            pstmt.setBytes(1, airportInBytes);
            pstmt.setDouble(2, airport.getLatitude());
            pstmt.setDouble(3, airport.getLongitude());
            pstmt.setInt(4, airport.getId());
            pstmt.setString(5, airport.getRecordName());
            System.out.println(pstmt.toString());
            pstmt.executeUpdate();
            System.out.println("Airport Updated");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void updateAirline(Airline airline) {

        byte[] airlineInBytes = SerializationUtils.serialize(airline);
        String columnUpdates = "airlineName = ?, airlineObject = ?";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(String.format("UPDATE airlines SET %s WHERE id = ? AND record = ?;", columnUpdates))) {
            pstmt.setString(1, airline.getName());
            pstmt.setBytes(2, airlineInBytes);
            pstmt.setInt(3, airline.getId());
            pstmt.setString(4, airline.getRecordName());
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
                + " id integer NOT NULL,\n"
                + " airportObject blob,\n"
                + " latitude real,\n"
                + " longitude real,\n"
                + " record text NOT NULL,\n"
                + " PRIMARY KEY(id, record)\n"
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
                + " id integer NOT NULL,\n"
                + " airlineName text,\n"
                + " airlineObject blob,\n"
                + " record text NOT NULL,\n"
                + " PRIMARY KEY(id, record)\n"
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
                + " id integer NOT NULL,\n"
                + " airlineId integer,\n"
                + " sourceID integer,\n"
                + " destID integer,\n"
                + " routeObject blob,\n"
                + " record text NOT NULL,\n"
                + " PRIMARY KEY(id, record)\n"
                + ");";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    /**
     * Function that allows an airport to calculate how many routes are within the current routes table, that have the airport as their
     * destination or departure location.
     * @param airport The airport that numRoutes is being calculated for
     * @param sourceOrDest Whether we are searching for routes that depart or arrive at that airport
     * @return An integer; the number of routes arriving/departing at the airport
     */
    public static int getNumRoutes(Airport airport, String sourceOrDest) {
        String query = String.format("SELECT count(*) AS numSource FROM routes WHERE %s = %d", sourceOrDest, airport.getId());

        int totalCount = 0;

        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                totalCount = rs.getInt("numSource");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return totalCount;
    }


    /**
     * Extracts each airport from the database, and creates a new Airport object for each of them
     * @return airports - an ArrayList containing an object for each airport in the database
     */
    public static ArrayList<ArrayList<Airport>> getAllAirports() {
        String query = "SELECT airportObject FROM airports";
        HashMap<String, Integer> recordNumbers = new HashMap<>();
        ArrayList<ArrayList<Airport>> recordList = new ArrayList<>();
        int currentNumOfRecords = 0;

        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                byte[] airportBytes = rs.getBytes("airportObject");
                ByteArrayInputStream baip = new ByteArrayInputStream(airportBytes);
                ObjectInputStream ois = new ObjectInputStream(baip);
                Airport airport = (Airport) ois.readObject();
                String record = airport.getRecordName();
                if (recordNumbers.get(record) != null) {
                    int recordNum = recordNumbers.get(record);
                    recordList.get(recordNum).add(airport);
                } else {
                    recordList.add(new ArrayList<>());
                    recordList.get(currentNumOfRecords).add(airport);
                    recordNumbers.put(record, currentNumOfRecords);
                    currentNumOfRecords += 1;
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return recordList;
    }

    /**
     * Extracts each airline data point from the database, and creates an airline object for each point
     * @return airlines - an ArrayList containing each an object for each airline in the database
     */
    public static ArrayList<ArrayList<Airline>> getAllAirlines() {
        String query = "SELECT airlineObject FROM airlines";
        HashMap<String, Integer> recordNumbers = new HashMap<>();
        ArrayList<ArrayList<Airline>> recordList = new ArrayList<>();
        int currentRecordNum = 0;

        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                byte[] airlineBytes = rs.getBytes("airlineObject");
                ByteArrayInputStream baip = new ByteArrayInputStream(airlineBytes);
                ObjectInputStream ois = new ObjectInputStream(baip);
                Airline airline = (Airline) ois.readObject();
                String record = airline.getRecordName();
                if (recordNumbers.get(record) != null) {
                    int recordNum = recordNumbers.get(record);
                    recordList.get(recordNum).add(airline);
                } else {
                    recordList.add(new ArrayList<>());
                    recordList.get(currentRecordNum).add(airline);
                    recordNumbers.put(record, currentRecordNum);
                    currentRecordNum += 1;
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return recordList;
    }

    /**
     * Retrieves all information from the route table, and creates Route objects for each existing data point
     * @return routes - an ArrayList containing a route object for each route in the database
     */
    public static ArrayList<ArrayList<Route>> getAllRoutes() {
        String query = "SELECT routeObject FROM routes";

        HashMap<String, Integer> recordNumbers = new HashMap<>();
        ArrayList<ArrayList<Route>> recordList = new ArrayList<>();
        int currentRecordNum = 0;


        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {

                byte[] routeBytes = rs.getBytes("routeObject");
                ByteArrayInputStream baip = new ByteArrayInputStream(routeBytes);
                ObjectInputStream ois = new ObjectInputStream(baip);
                Route route = (Route) ois.readObject();

                String record = route.getRecordName();
                if (recordNumbers.get(record) != null) {
                    int recordNum = recordNumbers.get(record);
                    recordList.get(recordNum).add(route);
                } else {
                    recordList.add(new ArrayList<>());
                    recordList.get(currentRecordNum).add(route);
                    recordNumbers.put(record, currentRecordNum);
                    currentRecordNum += 1;
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return recordList;
    }

    /**
     * Provides the user with a lsit of airlines within the current record that either arrive at or depart from through the specified airport
     * @param airport The airport that the user wants to know the airlines of
     * @return An arraylist containing the names of each of the airlines that use the airport
     */
    public static ArrayList<String> getAirlinesThroughAirport(Airport airport) {
        String sql = String.format("SELECT DISTINCT airlineName FROM airlines WHERE id IN (SELECT airlineID FROM routes WHERE sourceID = %s OR destID = %s)", airport.getId(), airport.getId());

        ArrayList<String> airlineNames = new ArrayList<>();

        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                airlineNames.add(rs.getString("airlineName"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return airlineNames;
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
            Record newRecord = new Record(flights, matchingRoute, matchingAirport, airline);
            newRecord.setName(airline.get(0).getRecordName());
            records.add(newRecord);
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
            Record newRecord = new Record(flights, matchingRoute, airport, new ArrayList<>());
            newRecord.setName(airport.get(0).getRecordName());
            records.add(newRecord);
        }

        //Check if there are any additional records containing only routes
        for (ArrayList<Route> route: routes) {
            Record newRecord = new Record(flights, route, new ArrayList<>(), new ArrayList<>());
            newRecord.setName(route.get(0).getRecordName());
            records.add(newRecord);
        }

        return records;
    }


    public static ArrayList<Double> getLatLong(Route route, String sourceOrDest) {

        String selectStatement = "SELECT latitude, longitude FROM airports WHERE id = %s";

        if (sourceOrDest.equals("source")) {
            selectStatement = String.format(selectStatement, route.getSourceID());
        } else {
            selectStatement = String.format(selectStatement, route.getDestID());
        }
        ArrayList<Double> latsAndLongs = new ArrayList<>();

        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(selectStatement)) {
            while (rs.next()) {
                latsAndLongs.add(rs.getDouble("latitude"));
                latsAndLongs.add(rs.getDouble("longitude"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return latsAndLongs;
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
