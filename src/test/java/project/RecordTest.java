package project;

import org.junit.Test;;
import project.model.*;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class RecordTest {

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

    private Loader loader = new Loader();


    /**
     * Tried to do a @BeforeEach but couldn't, might need changing later
     */
    public Record setUp() throws IOException {

        Flight testFlight1 = loader.loadFlightFile("data/flight.csv");
        Flight testFlight2 = loader.loadFlightFile("data/flighttest.csv");

        ArrayList<Flight> testFlightList = new ArrayList<Flight>();
        testFlightList.add(testFlight1);
        testFlightList.add(testFlight1);
        testFlightList.add(testFlight2);
        testFlightList.add(testFlight1);
        testFlightList.add(testFlight2);

        ArrayList<Route> testRouteList = new ArrayList<Route>();
        testRouteList.add(testRoute1);
        testRouteList.add(testRoute2);
        testRouteList.add(testRoute3);
        testRouteList.add(testRoute4);
        testRouteList.add(testRoute5);
        testRouteList.add(testRoute6);

        ArrayList<Airport> testAirportList = new ArrayList<Airport>();
        testAirportList.add(testAirport1);
        testAirportList.add(testAirport2);
        testAirportList.add(testAirport3);
        testAirportList.add(testAirport4);
        testAirportList.add(testAirport5);

        ArrayList<Airline> testAirlineList = new ArrayList<Airline>();
        testAirlineList.add(testAirline1);
        testAirlineList.add(testAirline2);
        testAirlineList.add(testAirline3);
        testAirlineList.add(testAirline4);
        testAirlineList.add(testAirline5);
        Record testRecord = new Record(testFlightList, testRouteList, testAirportList, testAirlineList);

        return testRecord;
    }

    @Test
    public void filterAirportsTest() throws IOException {
        Record testRecord = setUp();

        ArrayList<Airport> filteredTestAirportList = testRecord.filterAirports("New Zealand");

        ArrayList<Airport> comparisonAirportList = new ArrayList<Airport>();

        comparisonAirportList.add(testAirport1);
        comparisonAirportList.add(testAirport2);
        comparisonAirportList.add(testAirport5);

        assertEquals(comparisonAirportList, filteredTestAirportList);

        filteredTestAirportList = testRecord.filterAirports("Australia");

        comparisonAirportList = new ArrayList<Airport>();
        comparisonAirportList.add(testAirport3);
        comparisonAirportList.add(testAirport4);

        assertEquals(comparisonAirportList, filteredTestAirportList);
    }

    @Test
    public void filterAirlinesTest() throws IOException {
        Record testRecord = setUp();

        ArrayList<Airline> filteredTestAirlineList = testRecord.filterAirlines(true);

        ArrayList<Airline> comparisonAirlineList = new ArrayList<Airline>();
        comparisonAirlineList.add(testAirline1);
        comparisonAirlineList.add(testAirline3);
        comparisonAirlineList.add(testAirline5);

        assertEquals(comparisonAirlineList, filteredTestAirlineList);

        filteredTestAirlineList = testRecord.filterAirlines(false);

        comparisonAirlineList = new ArrayList<Airline>();
        comparisonAirlineList.add(testAirline2);
        comparisonAirlineList.add(testAirline4);

        assertEquals(comparisonAirlineList, filteredTestAirlineList);

    }

    @Test
    public void filterAirlinesCountryTest() throws IOException {
        Record testRecord = setUp();

        ArrayList<Airline> filteredTestAirlineList = testRecord.filterAirlinesCountry("New Zealand");

        ArrayList<Airline> comparisonAirlineList = new ArrayList<Airline>();
        comparisonAirlineList.add(testAirline1);
        comparisonAirlineList.add(testAirline2);
        comparisonAirlineList.add(testAirline4);

        assertEquals(comparisonAirlineList, filteredTestAirlineList);

        filteredTestAirlineList = testRecord.filterAirlinesCountry("Australia");

        comparisonAirlineList = new ArrayList<Airline>();
        comparisonAirlineList.add(testAirline3);

        assertEquals(comparisonAirlineList, filteredTestAirlineList);
    }

    @Test
    public void filterRoutesDepartureTest() throws IOException {
        Record testRecord = setUp();

        ArrayList<Route> filteredRouteList = testRecord.filterRoutesDeparture("NZWN");

        ArrayList<Route> comparisonRouteList = new ArrayList<Route>();
        comparisonRouteList.add(testRoute1);
        comparisonRouteList.add(testRoute5);

        assertEquals(comparisonRouteList, filteredRouteList);

        filteredRouteList = testRecord.filterRoutesDeparture("NZCH");

        comparisonRouteList = new ArrayList<Route>();
        comparisonRouteList.add(testRoute2);
        comparisonRouteList.add(testRoute4);

        assertEquals(comparisonRouteList, filteredRouteList);
    }

    @Test
    public void filterRoutesDestinationTest() throws IOException {
        Record testRecord = setUp();

        ArrayList<Route> filteredRouteList = testRecord.filterRoutesDestination("SYD");

        ArrayList<Route> comparisonRouteList = new ArrayList<Route>();
        comparisonRouteList.add(testRoute3);
        comparisonRouteList.add(testRoute4);
        comparisonRouteList.add(testRoute5);

        assertEquals(comparisonRouteList, filteredRouteList);
    }

    @Test
    public void filterRoutesStopsTest() throws IOException {
        Record testRecord = setUp();

        ArrayList<Route> filteredRouteList = testRecord.filterRoutesStops(true);

        ArrayList<Route> comparisonRouteList = new ArrayList<Route>();
        comparisonRouteList.add(testRoute1);
        comparisonRouteList.add(testRoute2);

        assertEquals(comparisonRouteList, filteredRouteList);

        filteredRouteList = testRecord.filterRoutesStops(false);
        comparisonRouteList = new ArrayList<Route>();
        comparisonRouteList.add(testRoute3);
        comparisonRouteList.add(testRoute4);
        comparisonRouteList.add(testRoute5);
        comparisonRouteList.add(testRoute6);

        assertEquals(comparisonRouteList, filteredRouteList);

    }

    @Test
    public void filterRoutesEquipmentTest() throws IOException {
        Record testRecord = setUp();

        ArrayList<Route> filteredRouteList = testRecord.filterRoutesEquipment("DXa34");

        ArrayList<Route> comparisonRouteList = new ArrayList<Route>();
        comparisonRouteList.add(testRoute2);
        comparisonRouteList.add(testRoute3);
        comparisonRouteList.add(testRoute4);
        comparisonRouteList.add(testRoute5);
        comparisonRouteList.add(testRoute6);

        assertEquals(comparisonRouteList, filteredRouteList);
    }

    @Test
    public void rankAirportsTest() throws IOException {
        Record testRecord = setUp();

        ArrayList<Airport> rankedAirports = testRecord.rankAirports(false);

        ArrayList<Airport> comparisonAirportList = new ArrayList<Airport>();

        comparisonAirportList.add(testAirport5);
        comparisonAirportList.add(testAirport1);
        comparisonAirportList.add(testAirport4);
        comparisonAirportList.add(testAirport2);
        comparisonAirportList.add(testAirport3);

        assertEquals(comparisonAirportList, rankedAirports);

        rankedAirports = testRecord.rankAirports(true);

        comparisonAirportList = new ArrayList<Airport>();

        comparisonAirportList.add(testAirport3);
        comparisonAirportList.add(testAirport2);
        comparisonAirportList.add(testAirport4);
        comparisonAirportList.add(testAirport1);
        comparisonAirportList.add(testAirport5);

        assertEquals(comparisonAirportList, rankedAirports);

    }

    @Test
    public void searchRoutesTest() throws IOException {
        Record testRecord = setUp();
        Route testRoute = testRecord.searchRoutes(500);
        assertEquals(testRoute1, testRoute);

        testRoute = testRecord.searchRoutes(999);
        assertEquals(null, testRoute);
    }

    @Test
    public void searchAirportsTest() throws IOException {
        Record testRecord = setUp();
        Airport testAirport = testRecord.searchAirports(101);
        assertEquals(testAirport1, testAirport);

        testAirport = testRecord.searchAirports(999);
        assertEquals(null, testAirport);
    }

    @Test
    public void setNumRoutesTest() throws IOException {

        ArrayList<Airline> testAirlineList = new ArrayList<Airline>();
        ArrayList<Flight> testFlightList = new ArrayList<Flight>();

        ArrayList<Airport> testAirportList = new ArrayList<Airport>();
        Airport testAirport6 = new Airport(101, 500, "Test1", "Christchurch", "New Zealand", "CHC", "NZCH", 40.0, 40.0, 50, 0, "Test1", "Test1", "Test1", "Openflights", 0, 0);
        Airport testAirport7 = new Airport(102, 500, "Test2", "Christchurch", "New Zealand", "CHC", "NZCH", 40.0, 40.0,50, 0, "Test2", "Test2", "Test2", "Openflights", 0, 0);
        Airport testAirport8 = new Airport(103, 500, "Test3", "Sydney", "Australia", "SYD", "YSSY", 40.0, 40.0,50, 0, "Test3", "Test3", "Test3", "Openflights", 0, 0);
        Airport testAirport9 = new Airport(104, 500, "Test4", "Sydney", "Australia", "SYD", "YSSY", 40.0, 40.0,50, 0, "Test4", "Test4", "Test4", "Openflights", 0, 0);
        Airport testAirport10 = new Airport(105, 500, "Test5", "Christchurch", "New Zealand", "CHC", "NZCH", 40.0, 40.0,50, 0, "Test5", "Test5", "Test5", "Openflights", 0, 0);
        Airport testAirport11 = new Airport(105, 500, "Test5", "Christchurch", "New Zealand", "YEET", "YEET", 40.0, 40.0,50, 0, "Test5", "Test5", "Test5", "Openflights", 0, 0);


        ArrayList<Route> testRouteList = new ArrayList<Route>();
        Route testRoute1 = new Route("Air NZ", 500, "YSSY", 411, "NZCH", 511, 0, "DXa134", false);
        Route testRoute2 = new Route("Air NZ", 501, "NZCH", 411, "WLG", 511, 0, "DXa34", false);
        Route testRoute3 = new Route("Air NZ", 502, "NZAA", 411, "SYD", 511, 2, "DXa34", false);
        Route testRoute4 = new Route("Air NZ", 503, "NZCH", 411, "SYD", 511, 1, "DXa34", false);
        Route testRoute5 = new Route("Air NZ", 504, "SYD", 411, "SYD", 511, 1, "DXa34", false);
        Route testRoute6 = new Route("Air NZ", 505, "CHC", 411, "YSSY", 511, 4, "DXa34", false);

        testAirportList.add(testAirport6);
        testAirportList.add(testAirport7);
        testAirportList.add(testAirport8);
        testAirportList.add(testAirport9);
        testAirportList.add(testAirport10);
        testAirportList.add(testAirport11);

        testRouteList.add(testRoute1);
        testRouteList.add(testRoute2);
        testRouteList.add(testRoute3);
        testRouteList.add(testRoute4);
        testRouteList.add(testRoute5);
        testRouteList.add(testRoute6);

        Record testRecord = new Record(testFlightList, testRouteList, testAirportList, testAirlineList);

        testRecord.setNumRoutesDest();
        testRecord.setNumRoutesSource();

        assertEquals(6, testRecord.rankAirports(true).get(0).getTotalRoutes());
        assertEquals(4, testRecord.rankAirports(true).get(4).getTotalRoutes());
        assertEquals(0, testRecord.rankAirports(true).get(5).getTotalRoutes());

    }

    @Test
    public void searchFlightsTest() throws IOException {
        Record testRecord = setUp();

        ArrayList<Flight> flightList = testRecord.searchFlights(true, "Test1");
        assertEquals(3, flightList.size());

        flightList = testRecord.searchFlights(false, "Test4");
        assertEquals(2, flightList.size());

        flightList = testRecord.searchFlights(false, "???");
        assertEquals(0, flightList.size());

    }
}
