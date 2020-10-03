package project.controller;

import org.apache.commons.lang3.SerializationUtils;
import project.model.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class containing methods that only extract information from the database, rather than manipulating it.
 */
public class RetrieveFromDatabase extends Database {

    /**
     * Allows the calculation of how many routes are within the current routes table, that have an airport as either their
     * destination or departure location.
     * @param airport The airport that numRoutes is being calculated for
     * @param sourceOrDest Whether we are searching for routes that depart or arrive at that airport
     * @return An integer; the number of routes arriving/departing at the airport
     */
    public static int getNumRoutes(Airport airport, String sourceOrDest) {
        //Select statement that counts the number of route entries that have airport's id in either their sourceID or destID field
        String query = String.format("SELECT count(*) AS numSource FROM routes WHERE %s = %d", sourceOrDest, airport.getId());

        int totalCount = 0;

        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                totalCount = rs.getInt("numSource");
            }
        } catch (SQLException e) {}

        return totalCount;
    }


    /**
     * Extracts each bytestream representing an airport object from the database, and deserialises each to create
     * each airport object. Store each airport in an ArrayList with other airports from its record.
     * @return A 2D ArrayList - each ArrayList within this ArrayList contains airports that are in a record together.
     */
    public static ArrayList<ArrayList<Airport>> getAllAirports() {
        String query = "SELECT airportObject FROM airports";
        //This hashmap stores the indices of each record's arraylist within recordList
        HashMap<String, Integer> recordNumbers = new HashMap<>();
        ArrayList<ArrayList<Airport>> recordList = new ArrayList<>();

        int currentNumOfRecords = 0;

        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                //Deserialise the bytes to get the airport object, then find its record.
                byte[] airportBytes = rs.getBytes("airportObject");
                Airport airport = SerializationUtils.deserialize(airportBytes);
                String record = airport.getRecordName();
                //Find the equivalent record arraylist and store the airport in there
                if (recordNumbers.get(record) != null) {
                    int recordNum = recordNumbers.get(record);
                    recordList.get(recordNum).add(airport);
                } else {
                    //Create a new arraylist for the record if there is not one currently
                    recordList.add(new ArrayList<>());
                    recordList.get(currentNumOfRecords).add(airport);
                    recordNumbers.put(record, currentNumOfRecords);
                    currentNumOfRecords += 1;
                }
            }
        } catch (SQLException e) {}
        return recordList;
    }

    /**
     * Extracts each bytestream representing an airline object from the database, and deserialises each to create
     * each airline object. Store each airline in an ArrayList with other airlines from its record.
     * @return A 2D ArrayList - each ArrayList within this ArrayList contains airlines that are in a record together.
     */
    public static ArrayList<ArrayList<Airline>> getAllAirlines() {
        String query = "SELECT airlineObject FROM airlines";
        //This hashmap stores the indices of each record's arraylist within recordList
        HashMap<String, Integer> recordNumbers = new HashMap<>();
        ArrayList<ArrayList<Airline>> recordList = new ArrayList<>();
        int currentRecordNum = 0;

        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                //Deserialise the bytes to get an airline object
                byte[] airlineBytes = rs.getBytes("airlineObject");
                Airline airline = SerializationUtils.deserialize(airlineBytes);
                String record = airline.getRecordName();
                //Find the corresponding arraylist for the record, and store the airline there
                if (recordNumbers.get(record) != null) {
                    int recordNum = recordNumbers.get(record);
                    recordList.get(recordNum).add(airline);
                } else {
                    //Create a new arraylist for the record if there is not one currently
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
     * Extracts each bytestream representing a Route object from the database, and deserialises each to create
     * each Route object. Store each Route in an ArrayList with other routes from its record.
     * @return A 2D ArrayList - each ArrayList within this ArrayList contains Routes that are in a record together.
     */
    public static ArrayList<ArrayList<Route>> getAllRoutes() {
        String query = "SELECT routeObject FROM routes";
        //This hashmap stores the indices of each record's arraylist within recordList
        HashMap<String, Integer> recordNumbers = new HashMap<>();
        ArrayList<ArrayList<Route>> recordList = new ArrayList<>();
        int currentRecordNum = 0;


        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                //Deserialise the bytes into a Route object
                byte[] routeBytes = rs.getBytes("routeObject");
                Route route = SerializationUtils.deserialize(routeBytes);

                String record = route.getRecordName();

                //Find the corresponding arraylist fort he route's record
                if (recordNumbers.get(record) != null) {
                    int recordNum = recordNumbers.get(record);
                    recordList.get(recordNum).add(route);
                } else {
                    //Create a new arraylist for the record if one does not exist currently
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
     * Provides the user with a list of airlines within the current record that either arrive at or depart from the specified airport
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


    /**
     * Generates each of the records that each group of airlines, airports, and routes are stored in. Airlines, airports,
     * and routes are stored in the same record together if the recordName attribute of each is the same.
     * @param airlines A 2D arraylist, containing arraylists of airlines that are stored in the same record as each other
     * @param airports A 2d arraylist, containing arraylists of airports that are stored in the same record as each other
     * @param routes A 2D arraylist, containing arraylists of routes that are stored in the same record as each other.
     * @return An arraylist of records, filled with the corresponding airlines, airports, and routes.
     */
    public static ArrayList<Record> getRecords(ArrayList<ArrayList<Airline>> airlines, ArrayList<ArrayList<Airport>> airports, ArrayList<ArrayList<Route>> routes) {
        ArrayList<Flight> flights = new ArrayList<>();
        ArrayList<Record> records = new ArrayList<>();

        for (ArrayList<Airline> airline: airlines) {
            ArrayList<Airport> matchingAirport = new ArrayList<>();
            ArrayList<Route> matchingRoute = new ArrayList<>();
            //Check for airports with a matching record name
            for (ArrayList<Airport> airport : airports) {
                if (airport.get(0).getRecordName().equals(airline.get(0).getRecordName())) {
                    matchingAirport = airport;
                    airports.remove(airport); //Corresponding airport list no longer needs to be stored
                    break;
                }
            }
            //Check for routes with a matching record name
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

        //Check if there are any additional records containing only airports, or only airports and routes
        for (ArrayList<Airport> airport: airports) {
            ArrayList<Route> matchingRoute = new ArrayList<>();
            for (ArrayList<Route> route: routes) {
                if (route.get(0).getRecordName().equals(airport.get(0).getRecordName())) {
                    matchingRoute = route;
                    routes.remove(route); //Corresponding route no longer needs to be stored.
                    //Only one arraylist will have the same record as the airport's record; break when we find it
                    break;
                }
            }
            Record newRecord = new Record(flights, matchingRoute, airport, new ArrayList<>()); //Create record with an empty airline arraylist
            newRecord.setName(airport.get(0).getRecordName());
            records.add(newRecord);
        }

        //Check if there are any additional records containing only routes
        for (ArrayList<Route> route: routes) {
            Record newRecord = new Record(flights, route, new ArrayList<>(), new ArrayList<>()); //Create record with empty airline and airport arraylists
            newRecord.setName(route.get(0).getRecordName());
            records.add(newRecord);
        }

        return records;
    }

    /**
     * Allows the latitude and longitude of airports at either end of a route to be calculated.
     * @param route The route who's source or destination airport lat and long we are determining.
     * @param sourceOrDest Whether we are determining the lat and long of the source airport, or the destination airport of the route.
     * @return An arraylist, containing first the latitude, then the longitude of the route's specified airport.
     */
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
     * Generates an arraylist of the record class from the data within the current database's tables.
     * Initially attempts to connect to the database; if it can't, we assume the database is not present, and is thus created.
     * @return An arraylist containing record classes, containing all the data from the databases tables.
     */
    public static ArrayList<Record> generateRecord() {
        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT id FROM airports"))
        {
            ArrayList<ArrayList<Airline>> airlines = getAllAirlines();
            ArrayList<ArrayList<Airport>> airports = getAllAirports();
            ArrayList<ArrayList<Route>> routes = getAllRoutes();
            return getRecords(airlines, airports, routes);

        } catch (SQLException e) {
            //Create a new database if none exists
            CreateDatabase.setupDatabase();
            //New database is empty: return empty record list.
            return new ArrayList<>();
        }
    }

}
