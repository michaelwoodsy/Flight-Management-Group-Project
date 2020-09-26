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
            airportTableColumns.add("dst");
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
            routeTableColumns.add("airlineId");
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

        String insertStatement = String.format("INSERT INTO airports(id, altitude, " +
                "airportName, city, country, iata, icao, dst, timezoneString, latitude, " +
                "longitude, timezone, record) VALUES(%s)", airport.getDatabaseValues());
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(insertStatement)) {
            //Set the name, city, and country fields separately, to handle the presence of apostrophes.
            pstmt.setString(1, airport.getName());
            pstmt.setString(2, airport.getCity());
            pstmt.setString(3, airport.getCountry());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(insertStatement);
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
            System.out.println("Airline created");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void addNewRoute(Route route) {
        String insertStatement = String.format("INSERT INTO routes(id, sourceID, destID, numStops, airline, sourceAirport, " +
                "destAirport, equipment, codeshare, record, airlineId) VALUES(?,?,?,?,?,?,?,?,?,?,?)");
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
            pstmt.setInt(11, route.getAirlineId());
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
    public static void removeAirport(String column, String value, String record) throws NoSuchFieldException {

        if (!airportTableColumns.contains(column)) {
            throw new NoSuchFieldException("Provided column value is not a column in the airport table");
        }

        System.out.println(value);
        System.out.println(record);
        String deleteStatement = String.format("DELETE FROM airports WHERE %s = ? AND record = ?", column);

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(deleteStatement)) {
            pstmt.setString(1, value);
            pstmt.setString(2, record);
            pstmt.executeUpdate();
            System.out.println("Airport removed");
        } catch (SQLException e) {
            System.out.println("Whoops");
            System.out.println(e.getMessage());
        }
    }

    public static void removeAirline(String column, String value, String record) throws NoSuchFieldException {
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

    public static void removeRoute(String column, String value, String record) throws NoSuchFieldException {
        if (!routeTableColumns.contains(column)) {
            throw new NoSuchFieldException("Column name does not exist in the routes table.");
        }

        System.out.println(record);
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(String.format("DELETE FROM routes WHERE %s = ? AND record = ?", column))) {
            pstmt.setString(1, value);
            pstmt.setString(2, record);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void updateRoute(Route newRoute) {
        int codeshare;
        if (newRoute.isCodeshare()) {
            codeshare = 1;
        } else {
            codeshare = 0;
        }
        String columnUpdates = String.format("airline = '%s', sourceAirport = '%s', sourceID = %d, destAirport = '%s', " +
                "destID = '%s', numStops = %d, equipment = '%s', codeshare = %d", newRoute.getAirline(), newRoute.getSourceAirport(),
        newRoute.getSourceID(), newRoute.getDestAirport(), newRoute.getDestID(), newRoute.getNumStops(), newRoute.getEquipment(), codeshare);

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(String.format("UPDATE routes SET %s WHERE id = ? and record = ?", columnUpdates))) {
            pstmt.setInt(1, newRoute.getId());
            pstmt.setString(2, newRoute.getRecordName());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void updateAirport(Airport airport) {
        String columnUpdates = "airportName = ?,\ncity = ?,\ncountry = ?,\niata = ?,\nicao = ?,\nlatitude = ?,\n" +
                        " longitude = ?,\naltitude = ?,\ntimezone = ?,\ndst = ?,\ntimezoneString = ?";

        String pstmtString = String.format("UPDATE airports\nSET %s\nWHERE id = ? AND record = ?", columnUpdates);
        System.out.println(pstmtString);
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(pstmtString)) {
            pstmt.setString(1, airport.getName());
            pstmt.setString(2, airport.getCity());
            pstmt.setString(3, airport.getCountry());
            pstmt.setString(4, airport.getIata());
            pstmt.setString(5, airport.getIcao());
            pstmt.setDouble(6, airport.getLatitude());
            pstmt.setDouble(7, airport.getLongitude());
            pstmt.setInt(8, airport.getAltitude());
            pstmt.setDouble(9, airport.getTimezone());
            pstmt.setString(10, airport.getDst());
            pstmt.setString(11, airport.getTimezoneString());
            pstmt.setInt(12, airport.getId());
            pstmt.setString(13, airport.getRecordName());
            System.out.println(pstmt.toString());
            pstmt.executeUpdate();
            System.out.println("Airport Updated");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void updateAirline(Airline airline) {
        int active;
        if (airline.isActive()) {
            active = 1;
        } else {
            active = 0;
        }
        String columnUpdates = "airlineName = ?, active = ?, country = ?, alias = ?, callsign = ?, iata = ?, icao = ?";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(String.format("UPDATE airlines SET %s WHERE id = ? AND record = ?;", columnUpdates))) {
            pstmt.setString(1, airline.getName());
            pstmt.setInt(2, active);
            pstmt.setString(3, airline.getCountry());
            pstmt.setString(4, airline.getAlias());
            pstmt.setString(5, airline.getCallSign());
            pstmt.setString(6, airline.getIata());
            pstmt.setString(7, airline.getIcao());
            pstmt.setInt(8, airline.getId());
            pstmt.setString(9, airline.getRecordName());
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
                + " dst text,\n"
                + " timezoneString text,\n"
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
                + "id integer PRIMARY KEY,\n"
                + " airlineId integer,\n"
                + " sourceID integer,\n"
                + " destID integer,\n"
                + " numStops integer,\n"
                + " airline text,\n"
                + " sourceAirport text,\n"
                + " destAirport text,\n"
                + " equipment text,\n"
                + " codeshare integer,\n" //Boolean value, can only be either 1 or 0
                + " record integer"
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
        String query = "SELECT id, altitude, numRoutesSource, numRoutesDest, airportName, city, " +
                "country, iata, icao, dst, timezoneString, latitude, longitude, " +
                "timezone, record FROM airports";
        HashMap<String, Integer> recordNumbers = new HashMap<>();
        ArrayList<ArrayList<Airport>> recordList = new ArrayList<>();
        int currentNumOfRecords = 0;

        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Airport newAirport = new Airport(rs.getInt("id"),
                        rs.getString("airportName"), rs.getString("city"), rs.getString("country"),
                        rs.getString("iata"), rs.getString("icao"), rs.getDouble("latitude"),
                        rs.getDouble("longitude"), rs.getInt("altitude"), rs.getDouble("timezone"),
                        rs.getString("dst"), rs.getString("timezoneString"),
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
        String query = "SELECT id, airlineID, sourceID, destID, numStops, airline, sourceAirport, destAirport, equipment, codeshare, " +
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
                Route newRoute = new Route(rs.getInt("id"), rs.getString("airline"), rs.getInt("airlineId"),
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

    public static void populateTables() {
        populateRouteTableColumns();
        populateAirportTableColumns();
        populateAirlineTableColumns();
    }
}
