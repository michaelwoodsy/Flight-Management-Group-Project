package project;

import org.junit.Test;
import project.model.Route;

import static org.junit.Assert.assertEquals;

public class DataUnitTests {

    @Test
    public void routeUpdate() {
        Route testRoute = new Route("Air NZ", 500, "Christchurch International Airport", 5011, "Wellington International Airport", 5012, 0, "AX54", false);
        testRoute.update("Jetstar", 500, "Christchurch International Airport", 5011, "Wellington International Airport", 5012, 0, "AX54", false);
        assertEquals("Jetstar", testRoute.getAirline());
    }
}
