package project;

import org.junit.Before;
import org.junit.Test;
import project.controller.Database;
import project.model.Airline;
import project.model.Airport;
import project.model.Route;

import java.nio.file.NoSuchFileException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class DatabaseTest {

    Airline testAirline1 = new Airline(100, "Test1", true, "New Zealand", "Test1", "Test1", "Test1", "Test1");
    Airline testAirline2 = new Airline(101, "Test2", false, "New Zealand", "Test2", "Test2", "Test2", "Test2");
    Airline testAirline3 = new Airline(102, "Test3", true, "Australia", "Test3", "Test3", "Test3", "Test3");
    Airline testAirline4 = new Airline(103, "Test4", false, "New Zealand", "Test4", "Test4", "Test4", "Test4");
    Airline testAirline5 = new Airline(104, "Test5", true, "U.K.", "Test5", "Test5", "Test5", "Test5");

    Route testRoute1 = new Route("Air NZ", 500, "NZWN", 411, "NZCH", 511, 0, "DXa134", false);
    Route testRoute2 = new Route("Air NZ", 501, "NZCH", 411, "WLG", 511, 0, "DXa34", false);
    Route testRoute3 = new Route("Air NZ", 502, "NZAA", 411, "SYD", 511, 2, "DXa34", false);
    Route testRoute4 = new Route("Air NZ", 503, "NZCH", 411, "SYD", 511, 1, "DXa34", false);
    Route testRoute5 = new Route("Air NZ", 504, "NZWN", 411, "SYD", 511, 1, "DXa34", false);
    Route testRoute6 = new Route("Air NZ", 505, "CHC", 411, "YSSY", 511, 4, "DXa34", false);


    Airport testAirport1 = new Airport(101, 500, "Test1", "Christchurch", "New Zealand", "CHC", "NZCH", 40.0, 40.0, 50, 0, "Test1", "Test1", "Test1", "Openflights", 1, 1);
    Airport testAirport2 = new Airport(102, 500, "Test2", "Christchurch", "New Zealand", "CHC", "NZCH", 40.0, 40.0,50, 0, "Test2", "Test2", "Test2", "Openflights", 4, 4);
    Airport testAirport3 = new Airport(103, 500, "Test3", "Sydney", "Australia", "SYD", "YSSY", 40.0, 40.0,50, 0, "Test3", "Test3", "Test3", "Openflights", 10, 10);
    Airport testAirport4 = new Airport(104, 500, "Test4", "Sydney", "Australia", "SYD", "YSSY", 40.0, 40.0,50, 0, "Test4", "Test4", "Test4", "Openflights", 2, 2);
    Airport testAirport5 = new Airport(105, 500, "Test5", "Christchurch", "New Zealand", "CHC", "NZCH", 40.0, 40.0,50, 0, "Test5", "Test5", "Test5", "Openflights", 0, 0);

    @Before
    public void setUp() {
        try (Connection conn = Database.connect();
             Statement stmt = conn.createStatement()) {
            String dropStatement = "DROP TABLE airports";
            stmt.executeUpdate(dropStatement);
            dropStatement = "DROP TABLE airlines";
            stmt.executeUpdate(dropStatement);
            dropStatement = "DROP TABLE routes";
            stmt.executeUpdate(dropStatement);
            System.out.println("Tables dropped");
        } catch (SQLException e) {}
        Database.setupDatabase();
    }


    @Test
    public void getAllAirportsTest() {
        Database.addNewAirport(testAirport1);
        Database.addNewAirport(testAirport2);
        Database.addNewAirport(testAirport5);

        ArrayList<Airport> airports = new ArrayList<Airport>();
        airports.add(testAirport1);
        airports.add(testAirport2);
        airports.add(testAirport5);

        ArrayList<Airport> retrievedAirports = Database.getAllAirports();
        assertEquals(retrievedAirports, airports);

        Database.addNewAirport(testAirport3);
        Database.addNewAirport(testAirport4);
        airports.add(testAirport3);
        airports.add(testAirport4);

        retrievedAirports = Database.getAllAirports();
        assertEquals(retrievedAirports, airports);
    }

    @Test
    public void getAllAirlinesTest() {
        Database.addNewAirline(testAirline1);
        Database.addNewAirline(testAirline3);
        Database.addNewAirline(testAirline4);

        ArrayList<Airline> airlines = new ArrayList<Airline>();
        airlines.add(testAirline1);
        airlines.add(testAirline3);
        airlines.add(testAirline4);

        ArrayList<Airline> retrievedAirlines = Database.getAllAirlines();
        assertEquals(retrievedAirlines, airlines);

        Database.addNewAirline(testAirline5);
        Database.addNewAirline(testAirline2);
        airlines.add(testAirline5);
        airlines.add(testAirline2);

        retrievedAirlines = Database.getAllAirlines();
        assertEquals(retrievedAirlines, airlines);
    }

    @Test
    public void getAllRoutesTest() {
        Database.addNewRoute(testRoute2);
        Database.addNewRoute(testRoute3);
        Database.addNewRoute(testRoute4);

        ArrayList<Route> routes = new ArrayList<Route>();
        routes.add(testRoute2);
        routes.add(testRoute3);
        routes.add(testRoute4);

        ArrayList<Route> retrievedRoutes = Database.getAllRoutes();
        assertEquals(retrievedRoutes, routes);

        Database.addNewRoute(testRoute5);
        Database.addNewRoute(testRoute1);
        Database.addNewRoute(testRoute6);
        routes.add(testRoute5);
        routes.add(testRoute1);
        routes.add(testRoute6);

        retrievedRoutes = Database.getAllRoutes();
        assertEquals(retrievedRoutes, routes);
    }

    @Test
    public void generateRecordTest() {}

    @Test
    public void removeAirportTest() {
        Database.addNewAirport(testAirport1);
        Database.addNewAirport(testAirport2);
        Database.addNewAirport(testAirport3);
        Database.addNewAirport(testAirport4);
        Database.addNewAirport(testAirport5);

        try {
            Database.removeAirport("id", "101", "int");
            Database.removeAirport("airportName", "Test4", "string");
            Database.removeAirport("city", "Christchurch", "string");
            Database.removeAirport("city", "New Zealand", "string");
        } catch (NoSuchFieldException e) {
            fail("Exception thrown when not appropriate");
        }

        try {
            Database.removeAirport("Not an appropriate column", "Not an appropriate value", "string");
            fail("Exception not thrown with inappropriate data value");
        } catch (NoSuchFieldException e) {
            System.out.println(e.getMessage());
        }

        ArrayList<Airport> resultingAirports = Database.getAllAirports();
        assertTrue(resultingAirports.size() == 1);
        assertEquals(resultingAirports.get(0), testAirport4);
    }

    @Test
    public void removeAirlineTest() {}

    @Test
    public void removeRouteTest() {

    }

    @Test
    public void addNewAirportTest() {
        Database.addNewAirport(testAirport1);
        Database.addNewAirport(testAirport2);
        Database.addNewAirport(testAirport4);

        ArrayList<Integer> airportIDs = new ArrayList<Integer>();
        airportIDs.add(testAirport1.getId());
        airportIDs.add(testAirport2.getId());
        airportIDs.add(testAirport4.getId());

        String testQuery = "SELECT id FROM airports";
        ArrayList<Integer> retrievedIDs = new ArrayList<Integer>();
        try (Connection conn = Database.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(testQuery)) {
            while (rs.next()) {
                retrievedIDs.add(rs.getInt("id"));
            }
        } catch (SQLException e) {
            fail("SQLException occurred");
            System.err.println(e.getMessage());
        }

        assertEquals(retrievedIDs, airportIDs);

    }
}