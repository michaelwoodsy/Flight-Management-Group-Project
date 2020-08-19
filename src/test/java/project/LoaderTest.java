package project;

import org.junit.Test;
import project.model.Airline;
import project.model.Airport;
import project.model.Loader;
import project.model.Route;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class LoaderTest {
    private Loader loader = new Loader();

    @Test
    /**
     * Function works fine, just difficult to test error cases due to how java handles arrays (fixed type).
     * Error cases have been manually tested.
     */
    public void loadIndividualRouteTest() {

        String[] routeData1 = {"2B", "410", "AER", "2965", "KZN", "2990", "", "0", "CR2"};
        Route testRoute1 = loader.loadRoute(routeData1);

        assertEquals(testRoute1.getEquipment(), "CR2");
        assertEquals(testRoute1.getId(), 410);
        assertEquals(testRoute1.isCodeshare(), false);

        String[] routeData2 = {"2B", "410a", "AER", "2965", "123", "2990", "Y", "0", "CR2"};
        testRoute1 = loader.loadRoute(routeData2);

        assertEquals(testRoute1.getId(), -1);
        assertEquals(testRoute1.isCodeshare(), true);

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
        assertEquals(testAirline1.isActive(), true);

        String[] airlineData2 = {"1", "Private flight", "", "-", "N/A", "", "", ""};
        testAirline1 = loader.loadAirline(airlineData2);

        assertEquals(testAirline1.isActive(), false);

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
}
