package project;

import org.junit.Test;;
import project.model.*;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class RecordTest {

    Airline testAirline1 = new Airline(100, "Test1", true, "New Zealand", "Test1", "Test1", "Test1", "Test1");
    Airline testAirline2 = new Airline(100, "Test2", false, "New Zealand", "Test2", "Test2", "Test2", "Test2");
    Airline testAirline3 = new Airline(100, "Test3", true, "Australia", "Test3", "Test3", "Test3", "Test3");
    Airline testAirline4 = new Airline(100, "Test4", false, "New Zealand", "Test4", "Test4", "Test4", "Test4");
    Airline testAirline5 = new Airline(100, "Test5", true, "U.K.", "Test5", "Test5", "Test5", "Test5");

    Route testRoute1 = new Route("Air NZ", 500, "Wellington International Airport", 411, "Christchurch International Airport", 511, 0, "DXa134", false);
    Route testRoute2 = new Route("Air NZ", 500, "Christchurch International Airport", 411, "Wellington International Airport", 511, 0, "DXa34", false);
    Route testRoute3 = new Route("Air NZ", 500, "Auckland International Airport", 411, "Sydney International Airport", 511, 2, "DXa34", false);
    Route testRoute4 = new Route("Air NZ", 500, "Christchurch International Airport", 411, "Sydney International Airport", 511, 1, "DXa34", false);
    Route testRoute5 = new Route("Air NZ", 500, "Wellington International Airport", 411, "Sydney International Airport", 511, 1, "DXa34", false);

    Airport testAirport1 = new Airport(100, 500, "Test1", "New Zealand", "Test1", "Test1", 50, 0.0, "Test1", "Test1", "Test1", "Openflights", 1);
    Airport testAirport2 = new Airport(100, 500, "Test2", "New Zealand", "Test2", "Test2", 50, 0.0, "Test2", "Test2", "Test2", "Openflights", 4);
    Airport testAirport3 = new Airport(100, 500, "Test3", "Australia", "Test3", "Test3", 50, 0.0, "Test3", "Test3", "Test3", "Openflights", 10);
    Airport testAirport4 = new Airport(100, 500, "Test4", "Australia", "Test4", "Test4", 50, 0.0, "Test4", "Test4", "Test4", "Openflights", 2);
    Airport testAirport5 = new Airport(100, 500, "Test5", "New Zealand", "Test5", "Test5", 50, 0.0, "Test5", "Test5", "Test5", "Openflights", 0);

    /**
     * Tried to do a @BeforeEach but couldn't, might need changing later
     */
    public Record setUp() {

        ArrayList<Flight> testFlightList = new ArrayList<Flight>();

        ArrayList<Route> testRouteList = new ArrayList<Route>();
        testRouteList.add(testRoute1);
        testRouteList.add(testRoute2);
        testRouteList.add(testRoute3);
        testRouteList.add(testRoute4);
        testRouteList.add(testRoute5);

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
    public void filterAirportsTest() {
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
    public void filterAirlinesTest() {
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
    public void filterAirlinesCountryTest() {
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
    public void filterRoutesDepartureTest() {
        Record testRecord = setUp();

        ArrayList<Route> filteredRouteList = testRecord.filterRoutesDeparture("Wellington International Airport");

        ArrayList<Route> comparisonRouteList = new ArrayList<Route>();
        comparisonRouteList.add(testRoute1);
        comparisonRouteList.add(testRoute5);

        assertEquals(comparisonRouteList, filteredRouteList);

        filteredRouteList = testRecord.filterRoutesDeparture("Christchurch International Airport");

        comparisonRouteList = new ArrayList<Route>();
        comparisonRouteList.add(testRoute2);
        comparisonRouteList.add(testRoute4);

        assertEquals(comparisonRouteList, filteredRouteList);
    }

    @Test
    public void filterRoutesDestinationTest() {
        Record testRecord = setUp();

        ArrayList<Route> filteredRouteList = testRecord.filterRoutesDestination("Sydney International Airport");

        ArrayList<Route> comparisonRouteList = new ArrayList<Route>();
        comparisonRouteList.add(testRoute3);
        comparisonRouteList.add(testRoute4);
        comparisonRouteList.add(testRoute5);

        assertEquals(comparisonRouteList, filteredRouteList);
    }

    @Test
    public void filterRoutesStopsTest() {
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

        assertEquals(comparisonRouteList, filteredRouteList);

    }

    @Test
    public void filterRoutesEquipmentTest() {
        Record testRecord = setUp();

        ArrayList<Route> filteredRouteList = testRecord.filterRoutesEquipment("DXa34");

        ArrayList<Route> comparisonRouteList = new ArrayList<Route>();
        comparisonRouteList.add(testRoute2);
        comparisonRouteList.add(testRoute3);
        comparisonRouteList.add(testRoute4);
        comparisonRouteList.add(testRoute5);

        assertEquals(comparisonRouteList, filteredRouteList);
    }

    @Test
    public void rankAirportsTest() {
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

}
