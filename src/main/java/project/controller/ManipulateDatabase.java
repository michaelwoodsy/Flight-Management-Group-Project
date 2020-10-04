package project.controller;

import org.apache.commons.lang3.SerializationUtils;
import project.model.Airline;
import project.model.Airport;
import project.model.Route;

import java.sql.*;

/**
 * A class containing methods that allow the manipulation of data within the Database.
 *  This includes addition, removal, and updating of data points, as well as the clearing of the entire database.
 */
public class ManipulateDatabase extends Database {

    /**
     * Empties out every data point from the database. Deletes the tables too, so tables need to be recreated before any other
     * database operations occur.
     */
    public static void clearDatabase() {
        try (Connection conn = Database.connect();
             Statement stmt = conn.createStatement()) {
            String dropStatement = "DROP TABLE airports";
            stmt.executeUpdate(dropStatement);
            dropStatement = "DROP TABLE airlines";
            stmt.executeUpdate(dropStatement);
            dropStatement = "DROP TABLE routes";
            stmt.executeUpdate(dropStatement);
        } catch (SQLException e) {}
    }

    /**
     * Adds a new airport object into the database's airport table.
     * It is assumed that the table has already been created.
     * @param airport The airport object to be added to the database.
     */
    public static void addNewAirport(Airport airport) {

        //Serialise the airport into bytes, to store the entire object as a BLOB
        byte[] airportAsBytes = SerializationUtils.serialize(airport);

        //Store additional attributes to allow accessing of additional airport information without needing to deserialise
        String insertStatement = "INSERT INTO airports(id, airportObject, latitude, " +
                "longitude, airportName, record) VALUES(?,?,?,?,?)";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(insertStatement)) {
            pstmt.setInt(1, airport.getId());
            pstmt.setBytes(2, airportAsBytes);
            pstmt.setDouble(3, airport.getLatitude());
            pstmt.setDouble(4, airport.getLongitude());
            pstmt.setString(5, airport.getName());
            pstmt.setString(6, airport.getRecordName());
            pstmt.executeUpdate();
        } catch (SQLException e) {}
    }

    /**
     * Adds a new airline object into the database's airline table.
     * It is assumed that the table has already been created.
     * @param airline The airline object to be added to the database.
     */
    public static void addNewAirline(Airline airline) {

        //Serialise the airline object itself, to store as a BLOB
        byte[] airlineAsBytes = SerializationUtils.serialize(airline);

        //Store additional attributes, so they can be accessed without deserialisation
        String insertStatement = "INSERT INTO airlines(id, airlineName, airlineObject, record) VALUES(?,?,?,?)";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(insertStatement)) {
            pstmt.setInt(1, airline.getId());
            pstmt.setString(2, airline.getName());
            pstmt.setBytes(3, airlineAsBytes);
            pstmt.setString(4, airline.getRecordName());
            pstmt.executeUpdate();
        } catch (SQLException e) {}
    }

    /**
     * Adds a new route object into the database's routes table.
     * It is assumed that the rotues table has already been created.
     * @param route The route object to be added to the database.
     */
    public static void addNewRoute(Route route) {

        //Serialise the route into bytes, to store as BLOB
        byte[] routeAsBytes = SerializationUtils.serialize(route);

        //Store id attributes to access them without deserialising the routeObject
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
        } catch (SQLException e) {}
    }

    /**
     * Remove data from the database, depending on which id and record it is
     * @param id The id of the data that we wish to delete
     * @param record The record that the deleting data is stored in
     * @param table The table that the data is stored in - also the type of data that the data is (airports, airline etc.)
     */
    public static void removeData(int id, String record, String table) {
        String deleteStatement = String.format("DELETE FROM %s WHERE id = ? AND record = ?", table);

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(deleteStatement)) {
            pstmt.setInt(1, id);
            pstmt.setString(2, record);
            pstmt.executeUpdate();
        } catch (SQLException e) {}
    }


    /**
     * Update a particular route within the database with new values
     * @param route The route that we wish to update
     */
    public static void updateRoute(Route route) {

        //Serialise the route into bytes
        byte[] routeInBytes = SerializationUtils.serialize(route);
        String columnUpdates = "airlineId = ?, sourceID = ?, destID = ?, routeObject = ?";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(String.format("UPDATE routes SET %s WHERE id = ? and record = ?", columnUpdates))) {
            //Set new versions of each of the stored attributes
            pstmt.setInt(1, route.getAirlineId());
            pstmt.setInt(2, route.getSourceID());
            pstmt.setInt(3, route.getDestID());
            pstmt.setBytes(4, routeInBytes);
            pstmt.setInt(5, route.getId());
            pstmt.setString(6, route.getRecordName());
            pstmt.executeUpdate();
        } catch (SQLException e) {}
    }

    /**
     * Update a particular airport within the database with new values
     * @param airport The airport that we wish to update
     */
    public static void updateAirport(Airport airport) {

        //Serialise the airport into bytes.
        byte[] airportInBytes = SerializationUtils.serialize(airport);
        String columnUpdates = "airportObject = ?, latitude = ?, longitude = ?";

        String pstmtString = String.format("UPDATE airports SET WHERE id = ? AND record = ?", columnUpdates);
        System.out.println(pstmtString);
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(pstmtString)) {
            //Set the new values of the stored airport attributes
            pstmt.setBytes(1, airportInBytes);
            pstmt.setDouble(2, airport.getLatitude());
            pstmt.setDouble(3, airport.getLongitude());
            pstmt.setInt(4, airport.getId());
            pstmt.setString(5, airport.getRecordName());
            System.out.println(pstmt.toString());
            pstmt.executeUpdate();
        } catch (SQLException e) {}
    }

    /**
     * Update a particular airline within the database with new values
     * @param airline The airline that we wish to update
     */
    public static void updateAirline(Airline airline) {

        //Serialise the airline into bytes
        byte[] airlineInBytes = SerializationUtils.serialize(airline);
        String columnUpdates = "airlineName = ?, airlineObject = ?";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(String.format("UPDATE airlines SET %s WHERE id = ? AND record = ?;", columnUpdates))) {
            //Set the new values of each stored attribute
            pstmt.setString(1, airline.getName());
            pstmt.setBytes(2, airlineInBytes);
            pstmt.setInt(3, airline.getId());
            pstmt.setString(4, airline.getRecordName());
            pstmt.executeUpdate();
        } catch (SQLException e) {}
    }

}
