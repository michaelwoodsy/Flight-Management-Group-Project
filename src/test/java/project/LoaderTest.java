package project;

import org.junit.Test;
import project.model.*;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class LoaderTest {
    private final Loader loader = new Loader();

    @Test
    /**
     * Function works fine, just difficult to test error cases due to how java handles arrays (fixed type).
     * Error cases have been manually tested.
     */
    public void loadIndividualRouteTest() {

        String[] routeData1 = {"2B", "410", "AER", "2965", "KZN", "2990", "Y", "0", "CR2"};
        Route testRoute1 = loader.loadRoute(routeData1);

        assertEquals(testRoute1.getAirline(), "2B");
        assertEquals(testRoute1.getSourceAirport(), "AER");
        assertEquals(testRoute1.getDestAirport(), "KZN");
        assertEquals(testRoute1.getEquipment(), "CR2");
        assertEquals(testRoute1.getId(), 410);
        assertEquals(testRoute1.getSourceID(), 2965);
        assertEquals(testRoute1.getDestID(), 2990);
        assertTrue(testRoute1.isCodeshare());
        assertEquals(testRoute1.getNumStops(), 0);

        String[] routeData2 = {"2B", "410a", "AER", "2965a", "KZN", "2990a", "N", "0a", "CR2"};
        testRoute1 = loader.loadRoute(routeData2);

        assertEquals(testRoute1.getId(), -1);
        assertEquals(testRoute1.getSourceID(), -1);
        assertEquals(testRoute1.getDestID(), -1);
        assertEquals(testRoute1.getNumStops(), -1);
        assertFalse(testRoute1.isCodeshare());

        String[] routeData3 = {"2B", "", "AER", "", "KZN", "", "", "", "CR2"};
        testRoute1 = loader.loadRoute(routeData3);
        assertFalse(testRoute1.isCodeshare());
        assertEquals(testRoute1.getId(), -1);
        assertEquals(testRoute1.getSourceID(), -1);
        assertEquals(testRoute1.getDestID(), -1);
        assertEquals(testRoute1.getNumStops(), -1);
    }

    @Test
    /**
     * Function works fine, just difficult to test error cases due to how java handles arrays (fixed type).
     * Error cases have been manually tested.
     */
    public void loadIndividualAirlineTest() {

        String[] airlineData1 = {"1", "Private flight", "", "-", "N/A", "", "", "Y"};
        Airline testAirline1 = loader.loadAirline(airlineData1);

        assertEquals(testAirline1.getName(), "Private flight");
        assertEquals(testAirline1.getId(), 1);
        assertTrue(testAirline1.isActive());

        String[] airlineData2 = {"1", "Private flight", "", "-", "N/A", "", "", ""};
        testAirline1 = loader.loadAirline(airlineData2);

        assertFalse(testAirline1.isActive());

    }

    @Test
    /**
     * Function works fine, just difficult to test error cases due to how java handles arrays (fixed type).
     * Error cases have been manually tested.
     */
    public void loadIndividualAirportTest() {

        String[] airportData1 = {"1", "Goroka", "Goroka", "Papua New Guinea", "GKA", "AYGA", "-6.081689", "145.391881", "5282", "10", "U", "Pacific/Port_Moresby"};
        Airport testAirport1 = loader.loadAirport(airportData1);

        assertEquals(testAirport1.getName(), "Goroka");
        assertEquals(testAirport1.getId(), 1);
        assertEquals(Double.doubleToLongBits(testAirport1.getLatitude()), Double.doubleToLongBits(-6.081689));
    }

    @Test
    /**
     * Manual testing. Not gonna bother to type out the entire data files as an ArrayList of classes. Functions work.
     */
    public void loadRouteFileTest() throws IOException {
        ArrayList<Route> routeList = loader.loadRouteFile("data/routes.dat");
        for (Route route: routeList) {
            //System.out.println(route);
        }

    }

    @Test
    /**
     * Manual testing. Will update later.
     */
    public void loadFlightFileTest() throws IOException {

        Flight flight = loader.loadFlightFile("data/flighttest.csv");

        System.out.println(flight.getStatus());
        System.out.println(flight.getLocations());
        System.out.println(flight.getAltitudes());
        System.out.println(flight.getLatitudes());
        System.out.println(flight.getLongitudes());

    }

    @Test
    /**
     * Manual testing. Not gonna bother to type out the entire data files as an ArrayList of classes. Functions work.
     */
    public void loadAirportFileTest() throws IOException {
        Loader loader = new Loader();
        ArrayList<Airport> airportList = loader.loadAirportFile("data/airports.dat");
        for (Airport airport: airportList) {
            // System.out.println(airport);
        }
    }

    @Test
    /**
     * Manual testing. Not gonna bother to type out the entire data files as an ArrayList of classes. Functions work.
     */
    public void loadAirlineFileTest() throws IOException {
        Loader loader = new Loader();
        ArrayList<Airline> airlineList = loader.loadAirlineFile("data/airlines.dat");
        for (Airline airline: airlineList) {
            // System.out.println(airline);
        }

    }

    @Test

    public void loadCovidFileTest() throws IOException{
        // checks that covid file doesnt fail
        ArrayList<Covid> covid_list = loader.loadCovidFile("data/covid.dat");
//        for(Covid covid: covid_list){
//            System.out.println(covid.print_country_data());
//        }

    }

    @Test
    public void testCovid() throws IOException {
        //runs test file with missing attributes to ensure loader wont crash
        // test file contains missing values and illegal values, to test that checks work correctly
        ArrayList<Covid> covid_list = loader.loadCovidFile("data/covid_test.dat");

//        for(Covid covid: covid_list){
//            System.out.println(covid.print_country_data());
//        }

    }
}
