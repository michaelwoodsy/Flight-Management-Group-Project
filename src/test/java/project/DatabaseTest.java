package project;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import project.controller.Database;
import project.model.Airline;
import project.model.Airport;
import project.model.Record;
import project.model.Route;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class DatabaseTest {

    private Airline testAirline1 = new Airline(100, "Test1", true, "New Zealand", "Test1", "Test1", "Test1", "Test1");
    private Airline testAirline2 = new Airline(101, "Test2", false, "New Zealand", "Test2", "Test2", "Test2", "Test2");
    private Airline testAirline3 = new Airline(102, "Test3", true, "Australia", "Test3", "Test3", "Test3", "Test3");
    private Airline testAirline4 = new Airline(103, "Test4", false, "New Zealand", "Test4", "Test4", "Test4", "Test4");
    private Airline testAirline5 = new Airline(104, "Test5", true, "U.K.", "Test5", "Test5", "Test5", "Test5");

    private Route testRoute1 = new Route(1,"Air NZ", 100, "NZWN", 101, "NZCH", 102, 0, "DXa134", false);
    private Route testRoute2 = new Route(2,"Air NZ", 104, "NZCH", 102, "WLG", 101, 0, "DXa34", false);
    private Route testRoute3 = new Route(3,"Air NZ", 101, "NZAA", 101, "SYD", 103, 2, "DXa34", false);
    private Route testRoute4 = new Route(4,"Air NZ", 102, "NZCH", 102, "SYD", 101, 1, "DXa34", false);
    private Route testRoute5 = new Route(5,"Air NZ", 100, "NZWN", 104, "SYD", 103, 1, "DXa34", false);
    private Route testRoute6 = new Route(6,"Air NZ", 103, "CHC", 102, "YSSY", 104, 4, "DXa34", false);

    private Airport testAirport1 = new Airport(101, "Test1", "Christchurch", "New Zealand", "CHC", "NZCH", 40.0, 40.0, 50, 0, "Test1", "Test1", 1, 1);
    private Airport testAirport2 = new Airport(102, "Test2", "Christchurch", "New Zealand", "CHC", "NZCH", 40.0, 40.0,50, 0, "Test2", "Test2",  4, 4);
    private Airport testAirport3 = new Airport(103,  "Test3", "Sydney", "Australia", "SYD", "YSSY", 40.0, 40.0,50, 0, "Test3", "Test3", 10, 10);
    private Airport testAirport4 = new Airport(104,  "Test4", "Sydney", "Australia", "SYD", "YSSY", 40.0, 40.0,50, 0, "Test4", "Test4", 2, 2);
    private Airport testAirport5 = new Airport(105, "Test5", "Christchurch", "New Zealand", "CHC", "NZCH", 40.0, 40.0,50, 0, "Test5", "Test5", 0, 0);

    private Record record = new Record("Test");

    public void assignRecords() {
        testAirline1.setRecordName("Test");
        testAirline2.setRecordName("Test");
        testAirline3.setRecordName("Test");
        testAirline4.setRecordName("Test");
        testAirline5.setRecordName("Test");
        testRoute1.setRecordName("Test");
        testRoute2.setRecordName("Test");
        testRoute3.setRecordName("Test");
        testRoute4.setRecordName("Test");
        testRoute5.setRecordName("Test");
        testRoute6.setRecordName("Test");
        testAirport1.setRecordName("Test");
        testAirport2.setRecordName("Test");
        testAirport3.setRecordName("Test");
        testAirport4.setRecordName("Test");
        testAirport5.setRecordName("Test");
    }

    @Before
    public void setUp() {
        //Drop each of the current tables in the database to ensure a clear testing database
        Database.clearDatabase();
        Database.setupDatabase();
        assignRecords();
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

        ArrayList<Airport> retrievedAirports = Database.getAllAirports().get(0);
        assertEquals(retrievedAirports, airports);

        Database.addNewAirport(testAirport3);
        Database.addNewAirport(testAirport4);
        airports.add(testAirport3);
        airports.add(testAirport4);

        retrievedAirports = Database.getAllAirports().get(0);
        assertEquals(retrievedAirports, airports);
    }

    @Test
    public void getAllAirlinesTest() {
        Database.addNewAirline(testAirline4);
        Database.addNewAirline(testAirline1);
        Database.addNewAirline(testAirline3);

        //Create an arrayList in order of ID's, as SQL will order the data points in this way
        ArrayList<Airline> airlines = new ArrayList<Airline>();
        airlines.add(testAirline4);
        airlines.add(testAirline1);
        airlines.add(testAirline3);

        ArrayList<Airline> retrievedAirlines = Database.getAllAirlines().get(0);
        assertEquals(retrievedAirlines, airlines);

        Database.addNewAirline(testAirline5);
        Database.addNewAirline(testAirline2);
        airlines.add(testAirline5);
        airlines.add(testAirline2);

        retrievedAirlines = Database.getAllAirlines().get(0);
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

        ArrayList<Route> retrievedRoutes = Database.getAllRoutes().get(0);
        assertEquals(retrievedRoutes, routes);

        Database.addNewRoute(testRoute5);
        Database.addNewRoute(testRoute1);
        Database.addNewRoute(testRoute6);
        routes.add(testRoute5);
        routes.add(testRoute1);
        routes.add(testRoute6);

        retrievedRoutes = Database.getAllRoutes().get(0);
        assertEquals(retrievedRoutes, routes);
    }

    @Test
    public void removeAirportTest() {
        Database.addNewAirport(testAirport1);
        Database.addNewAirport(testAirport2);
        Database.addNewAirport(testAirport3);
        Database.addNewAirport(testAirport4);
        Database.addNewAirport(testAirport5);

        Database.removeData(101, "Test", "airports");
        Database.removeData(102, "Test", "airports");
        Database.removeData(105, "Test", "airports");
        Database.removeData(104, "Test", "airports");
        Database.removeData(-1, "Bad Record", "airports");


        ArrayList<Airport> resultingAirports = Database.getAllAirports().get(0);
        assertEquals(resultingAirports.size(), 1);
        assertEquals(resultingAirports.get(0), testAirport3);
    }

    @Test
    public void removeAirlineTest() {
        Database.addNewAirline(testAirline1);
        Database.addNewAirline(testAirline2);
        Database.addNewAirline(testAirline3);
        Database.addNewAirline(testAirline4);
        Database.addNewAirline(testAirline5);

        Database.removeData(101, "Test", "airlines");
        Database.removeData(100, "Test", "airlines");
        Database.removeData(102, "Test", "airlines");
        Database.removeData(104, "Test", "airlines");
        Database.removeData(1000, "Test", "airlines");

        ArrayList<Airline> resultingAirlines = Database.getAllAirlines().get(0);
        assertTrue(resultingAirlines.size() == 1);
        assertEquals(resultingAirlines.get(0), testAirline4);
    }

    @Test
    public void removeRouteTest() {
        Database.addNewRoute(testRoute1);
        Database.addNewRoute(testRoute2);
        Database.addNewRoute(testRoute3);
        Database.addNewRoute(testRoute4);
        Database.addNewRoute(testRoute5);
        Database.addNewRoute(testRoute6);

        Database.removeData(1, "Test", "routes");
        Database.removeData(2, "Test", "routes");
        Database.removeData(5, "Test", "routes");
        Database.removeData(3, "Test", "routes");
        Database.removeData(4, "Test", "routes");
        Database.removeData(8, "Test", "routes");

        ArrayList<Route> resultingRoutes = Database.getAllRoutes().get(0);
        assertEquals(resultingRoutes.size(), 1);
        assertEquals(resultingRoutes.get(0), testRoute6);

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

    @Test
    public void addNewAirlineTest() {
        Database.addNewAirline(testAirline1);
        Database.addNewAirline(testAirline2);
        Database.addNewAirline(testAirline5);

        ArrayList<Integer> airlineIDs = new ArrayList<Integer>();
        airlineIDs.add(testAirline1.getId());
        airlineIDs.add(testAirline2.getId());
        airlineIDs.add(testAirline5.getId());

        String testQuery = "SELECT id FROM airlines";
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

        assertEquals(retrievedIDs, airlineIDs);

    }

    @Test
    public void addNewRouteTest() {
        Database.addNewRoute(testRoute2);
        Database.addNewRoute(testRoute5);
        Database.addNewRoute(testRoute3);

        ArrayList<Integer> routeIDs = new ArrayList<Integer>();
        routeIDs.add(testRoute2.getId());
        routeIDs.add(testRoute3.getId());
        routeIDs.add(testRoute5.getId());

        String testQuery = "SELECT id FROM routes";
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

        assertEquals(retrievedIDs, routeIDs);
    }

    @Test
    public void generateRecordTest() {
        System.out.println(testRoute1.getRecordName());
        Database.addNewRoute(testRoute5);
        Database.addNewRoute(testRoute2);
        Database.addNewRoute(testRoute4);
        ArrayList<Route> routes = new ArrayList<Route>();
        routes.add(testRoute5);
        routes.add(testRoute2);
        routes.add(testRoute4);

        Database.addNewAirline(testAirline3);
        Database.addNewAirline(testAirline1);
        ArrayList<Airline> airlines = new ArrayList<Airline>();
        airlines.add(testAirline3);
        airlines.add(testAirline1);

        Database.addNewAirport(testAirport4);
        Database.addNewAirport(testAirport5);
        Database.addNewAirport(testAirport2);
        Database.addNewAirport(testAirport1);
        ArrayList<Airport> airports = new ArrayList<Airport>();
        airports.add(testAirport4);
        airports.add(testAirport5);
        airports.add(testAirport2);
        airports.add(testAirport1);

        Record testRecord = Database.generateRecord().get(0);
        ArrayList<Airline> recordAirlines = testRecord.getAirlineList();
        ArrayList<Airport> recordAirports = testRecord.getAirportList();
        ArrayList<Route> recordRoutes = testRecord.getRouteList();

        assertEquals(recordAirlines, airlines);
        assertEquals(recordAirports, airports);
        assertEquals(recordRoutes, routes);

    }

    @Test
    public void testGetNumRoutes() {
        Airport testAirport11 = new Airport(1,  "Wellington Int.", "Wellington", "New Zealand", "WLG", "WGTN", 1, 1, 1, 1, "U", "Yes", 0, 0);
        Route testRoute11 = new Route(1, "IF", 12, "Wellington Intl.", 1, "Nevermind", 0, 0, "DAN", true);
        Route testRoute12 = new Route(2, "IF", 12, "Wellington Intl.", 1, "Nevermind", 0, 0, "DAN", true);
        Route testRoute13 = new Route(3, "IF", 12, "NeverMind", 0, "Wellington Intl.", 1, 0, "DAN", true);
        Route testRoute14 = new Route(4, "IF", 12, "Nevermind", 0, "Wellington Intl.", 1, 0, "DAN", true);
        Route testRoute15 = new Route(5, "IF", 12, "Nevermind", 0, "Wellington Intl.", 1, 0, "DAN", true);
        Route testRoute16 = new Route(6, "IF", 12, "Nevermind", 0, "Nevermind", 0, 0, "DAN", true);
        testAirport11.setRecordName("Test");
        testRoute11.setRecordName("Test");
        testRoute12.setRecordName("Test");
        testRoute13.setRecordName("Test");
        testRoute14.setRecordName("Test");
        testRoute15.setRecordName("Test");
        testRoute16.setRecordName("Test");

        Database.addNewRoute(testRoute11);
        Database.addNewRoute(testRoute12);
        Database.addNewRoute(testRoute13);
        Database.addNewRoute(testRoute14);
        Database.addNewRoute(testRoute15);
        Database.addNewRoute(testRoute16);

        assertEquals(Database.getNumRoutes(testAirport11, "sourceID"), 2);
        assertEquals(Database.getNumRoutes(testAirport11, "destID"), 3);

    }

    @Test
    public void testGetAirlinesThroughAirport() {
        Database.addNewAirport(testAirport1);
        Database.addNewAirport(testAirport3);
        Database.addNewAirline(testAirline1);
        Database.addNewAirline(testAirline2);
        Database.addNewAirline(testAirline3);
        Database.addNewAirline(testAirline4);
        Database.addNewAirline(testAirline5);
        Database.addNewRoute(testRoute1);
        Database.addNewRoute(testRoute2);
        Database.addNewRoute(testRoute3);
        Database.addNewRoute(testRoute4);
        Database.addNewRoute(testRoute5);
        Database.addNewRoute(testRoute6);

        ArrayList<String> airport1airlineNames = Database.getAirlinesThroughAirport(testAirport1);
        ArrayList<String> airport3airlineNames = Database.getAirlinesThroughAirport(testAirport3);

        ArrayList<String> expectedAirlines1 = new ArrayList<>();
        ArrayList<String> expectedAirlines3 = new ArrayList<>();
        expectedAirlines1.add("Test1");
        expectedAirlines1.add("Test2");
        expectedAirlines1.add("Test3");
        expectedAirlines1.add("Test5");
        expectedAirlines3.add("Test1");
        expectedAirlines3.add("Test2");


        assertEquals(airport1airlineNames.size(), 4);
        assertEquals(airport3airlineNames.size(), 2);
        assertTrue(airport1airlineNames.containsAll(expectedAirlines1));
        assertTrue(airport3airlineNames.containsAll(expectedAirlines3));

    }

    /**
     * Clears the database of test data. This causes any user-stored data to be removed.
     */
    @AfterClass
    public static void cleanUp() {
        Database.clearDatabase();
    }
}