package project.controller;

import org.apache.commons.lang3.SerializationUtils;
import project.model.Airline;
import project.model.Airport;
import project.model.Route;

import java.sql.*;

public class ManipulateDatabase extends Database {

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

}
