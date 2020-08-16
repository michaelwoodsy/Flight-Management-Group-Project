package project;

import org.junit.Test;
import project.model.Airport;

import static org.junit.Assert.assertEquals;

public class AirportTest {
    @Test
    public void distanceTest() {
        Airport testAirport1 = new Airport(1, 7, "Auckland Airport", "Auckland", "New Zealand", "AKL", "NZAA", 37.0082, 174.7850, 7, 12, "dunno", "NZST", "Airport", "Source", 0);
        Airport testAirport2 = new Airport(2, 2, "Christchurch International Airport", "Christchurch", "New Zealand", "CHC", "NZCH", 43.4876, 172.5374, 37, 12, "dunno", "NZST", "Airport", "Source", 0);
        double dist = testAirport1.distance(testAirport2);
        assertEquals(dist, 745.692, 1);
    }
}
