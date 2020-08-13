package project;

import org.junit.Test;
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
            System.out.println(route);
        }

    }
}
