package project;

import org.junit.Test;
import project.model.*;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class RecordTest {
    private ArrayList<Flight> defaultFlightList = new ArrayList<Flight>();
    private ArrayList<Route> defaultRouteList = new ArrayList<Route>();
    private ArrayList<Airport> defaultAirportList = new ArrayList<Airport>();
    private ArrayList<Airline> defaultAirlineList = new ArrayList<Airline>();

    @Test
    public void filterAirportsTest() {

        Airport testAirport1 = new Airport(100, 500, "Test1", "New Zealand", "Test1", "Test1", 50, 0.0, "Test1", "Test1", "Test1", "Openflights", 50);
        Airport testAirport2 = new Airport(100, 500, "Test2", "New Zealand", "Test2", "Test2", 50, 0.0, "Test2", "Test2", "Test2", "Openflights", 50);
        Airport testAirport3 = new Airport(100, 500, "Test3", "Australia", "Test3", "Test3", 50, 0.0, "Test3", "Test3", "Test3", "Openflights", 50);
        Airport testAirport4 = new Airport(100, 500, "Test4", "Australia", "Test4", "Test4", 50, 0.0, "Test4", "Test4", "Test4", "Openflights", 50);
        Airport testAirport5 = new Airport(100, 500, "Test5", "New Zealand", "Test5", "Test5", 50, 0.0, "Test5", "Test5", "Test5", "Openflights", 50);


        ArrayList<Airport> testAirportList = new ArrayList<Airport>();
        testAirportList.add(testAirport1);
        testAirportList.add(testAirport2);
        testAirportList.add(testAirport3);
        testAirportList.add(testAirport4);
        testAirportList.add(testAirport5);

        Record testRecord = new Record(defaultFlightList, defaultRouteList, testAirportList, defaultAirlineList);

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
        Airline testAirline1 = new Airline(100, "Test1", true, "Test1", "Test1", "Test1", "Test1", "Test1");
        Airline testAirline2 = new Airline(100, "Test2", false, "Test2", "Test2", "Test2", "Test2", "Test2");
        Airline testAirline3 = new Airline(100, "Test3", true, "Test3", "Test3", "Test3", "Test3", "Test3");
        Airline testAirline4 = new Airline(100, "Test4", false, "Test4", "Test4", "Test4", "Test4", "Test4");
        Airline testAirline5 = new Airline(100, "Test5", true, "Test5", "Test5", "Test5", "Test5", "Test5");

        ArrayList<Airline> testAirlineList = new ArrayList<Airline>();

        testAirlineList.add(testAirline1);
        testAirlineList.add(testAirline2);
        testAirlineList.add(testAirline3);
        testAirlineList.add(testAirline4);
        testAirlineList.add(testAirline5);

        Record testRecord = new Record(defaultFlightList, defaultRouteList, defaultAirportList, testAirlineList);

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
        Airline testAirline1 = new Airline(100, "Test1", true, "New Zealand", "Test1", "Test1", "Test1", "Test1");
        Airline testAirline2 = new Airline(100, "Test2", false, "New Zealand", "Test2", "Test2", "Test2", "Test2");
        Airline testAirline3 = new Airline(100, "Test3", true, "Australia", "Test3", "Test3", "Test3", "Test3");
        Airline testAirline4 = new Airline(100, "Test4", false, "New Zealand", "Test4", "Test4", "Test4", "Test4");
        Airline testAirline5 = new Airline(100, "Test5", true, "U.K.", "Test5", "Test5", "Test5", "Test5");

        ArrayList<Airline> testAirlineList = new ArrayList<Airline>();

        testAirlineList.add(testAirline1);
        testAirlineList.add(testAirline2);
        testAirlineList.add(testAirline3);
        testAirlineList.add(testAirline4);
        testAirlineList.add(testAirline5);

        Record testRecord = new Record(defaultFlightList, defaultRouteList, defaultAirportList, testAirlineList);

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
}
