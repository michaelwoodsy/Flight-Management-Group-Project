package project;

import org.junit.Test;
import project.model.Airline;
import project.model.Airport;
import project.model.Loader;
import project.model.Route;

import java.io.IOException;
import java.util.ArrayList;

public class LoaderTest {

    @Test
    /**
     * Manual testing. Will update later.
     */
    public void loadRouteFileTest() throws IOException {
        Loader loader = new Loader();
        ArrayList<Route> routeList = loader.loadRouteFile("data/routes.dat");
        for (Route route: routeList) {
            //System.out.println(route);
        }

    }

    @Test
    /**
     * Manual testing. Will update later.
     */
    public void loadAirportFileTest() throws IOException {
        Loader loader = new Loader();
        ArrayList<Airport> airportList = loader.loadAirportFile("data/airports.dat");
        for (Airport airport: airportList) {
            System.out.println(airport);
        }
    }

    @Test
    /**
     * Manual testing. Will update later.
     */
    public void loadAirlineFileTest() throws IOException {
        Loader loader = new Loader();
        ArrayList<Airline> airlineList = loader.loadAirlineFile("data/airlines.dat");
        for (Airline airline: airlineList) {
            // System.out.println(airline);
        }

    }
}
