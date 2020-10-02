package project.controller;

import org.apache.commons.lang3.SerializationUtils;
import project.model.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class RetrieveFromDatabase extends Database {

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
                Airport airport = SerializationUtils.deserialize(airportBytes);
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
                Airline airline = SerializationUtils.deserialize(airlineBytes);
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
                Route route = SerializationUtils.deserialize(routeBytes);

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
        }
        return recordList;
    }

    /**
     * Provides the user with a list of airlines within the current record that either arrive at or depart from through the specified airport
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
            CreateDatabase.setupDatabase();
        } finally {
            ArrayList<ArrayList<Airline>> airlines = getAllAirlines();
            ArrayList<ArrayList<Airport>> airports = getAllAirports();
            ArrayList<ArrayList<Route>> routes = getAllRoutes();
            ArrayList<Record> records = getRecords(airlines, airports, routes);

            return records;
        }
    }

}
