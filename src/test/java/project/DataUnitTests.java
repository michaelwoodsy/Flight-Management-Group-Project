package project;

import org.junit.Test;
import project.model.Airline;
import project.model.Airport;
import project.model.Route;

import static org.junit.Assert.assertEquals;

public class DataUnitTests {

    @Test
    public void routeUpdate() {
        Route testRoute = new Route("Air NZ", 500, "Christchurch International Airport", 5011, "Wellington International Airport", 5012, 0, "AX54", false);
        testRoute.update("Jetstar", 500, "Christchurch International Airport", 5011, "Wellington International Airport", 5012, 0, "AX54", false);
        assertEquals("Jetstar", testRoute.getAirline());
    }

    @Test
    public void airlineUpdate() {
        Airline testAirline = new Airline(500, "Air NZ", true, "New Zealand", "AirNZ", "BD45X", "678234A", "34345B");
        testAirline.update(502, "Quantas", true, "Australia", "QNTS", "BD45X", "678234A", "34345B");
        assertEquals("Quantas", testAirline.getName());
        assertEquals(502, testAirline.getId());
        assertEquals("Australia", testAirline.getCountry());
    }

    @Test
    public void airportUpdate() {
        Airport testAirport = new Airport(101, 9001, "Christchurch International Airport", "Christchurch", "New Zealand", "BX45", "C9845", 40.0, 40.0, 40, 5, "Y", "NZST", "Default", "Openflights", 46);
        assertEquals("Christchurch International Airport", testAirport.getName());
        assertEquals(9001, testAirport.getRisk());

        testAirport.update(101, 500, "Christchurch Domestic Airport", "Christchurch", "New Zealand", "BX45", "C9845", 40.1, 40.1, 40, 5, "Y", "NZST", "Y", "Openflights", 46);
        assertEquals("Christchurch Domestic Airport", testAirport.getName());
        assertEquals(500, testAirport.getRisk());
    }
}
