package project.model;

import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Comparator;

public class Record {
    private ArrayList<Flight> flightList = new ArrayList<Flight>();
    private ArrayList<Route> routeList = new ArrayList<Route>();
    private ArrayList<Airport> airportList = new ArrayList<Airport>();
    private ArrayList<Airline> airlineList = new ArrayList<Airline>();

    public Record(ArrayList<Flight> flightList, ArrayList<Route> routeList, ArrayList<Airport> airportList, ArrayList<Airline> airlineList) {
        this.flightList = flightList;
        this.routeList = routeList;
        this.airportList = airportList;
        this.airlineList = airlineList;
    }

    /**
     * Filters airports by country entered. Returns a new list of airports meeting this criteria.
     */
    public ArrayList<Airport> filterAirports(String country) {
        ArrayList<Airport> filteredAirports = new ArrayList<Airport>();
        for (Airport airport: airportList) {
            if (airport.getCountry().equals(country)) {
                filteredAirports.add(airport);
            }
        }
        return filteredAirports;
    }

    /**
     * Filters airlines by whether they're active or not. Returns a new list of airlines meeting this criteria.
     */
    public ArrayList<Airline> filterAirlines(Boolean active) {
        ArrayList<Airline> filteredAirlines = new ArrayList<Airline>();
        for (Airline airline: airlineList) {
            if (airline.isActive() == active) {
                filteredAirlines.add(airline);
            }
        }
        return filteredAirlines;
    }

    /**
     * Filters airlines by country entered. Returns a new list of airlines meeting this criteria.
     */
    public ArrayList<Airline> filterAirlinesCountry(String country) {
        ArrayList<Airline> filteredAirlines = new ArrayList<Airline>();
        for (Airline airline: airlineList) {
            if (airline.getCountry().equals(country)) {
                filteredAirlines.add(airline);
            }
        }
        return filteredAirlines;
    }

    /**
     * Filters routes by source airport. Returns a new list of routes meeting this criteria.
     * Needs changing from sourceAirport to country. Perhaps countries can be mapped to source airports?
     */
    public ArrayList<Route> filterRoutesDeparture(String sourceAirport){
        ArrayList<Route> filteredRoutes = new ArrayList<Route>();
        for (Route route: routeList) {
            if (route.getSourceAirport().equals(sourceAirport)) {
                filteredRoutes.add(route);
            }
        }
        return filteredRoutes;
    }

    /**
     * Filters routes by destination airport. Returns a new list of routes meeting this criteria.
     * Needs changing from destAirport to country. Perhaps countries can be mapped to destination airports?
     */
    public ArrayList<Route> filterRoutesDestination(String destAirport) {
        ArrayList<Route> filteredRoutes = new ArrayList<Route>();
        for (Route route: routeList) {
            if (route.getDestAirport().equals(destAirport)) {
                filteredRoutes.add(route);
            }
        }
        return filteredRoutes;
    }

    /**
     * Filters routes by whether they have stops or not. Returns a new list of routes meeting this criteria.
     */
    public ArrayList<Route> filterRoutesStops(boolean direct) {
        ArrayList<Route> filteredRoutes = new ArrayList<Route>();
        if (direct) {
            for (Route route: routeList) {
                if (route.getNumStops() == 0) {
                    filteredRoutes.add(route);
                }
            }
        } else {
            for (Route route: routeList) {
                if (route.getNumStops() != 0) {
                    filteredRoutes.add(route);
                }
            }
        }
        return filteredRoutes;
    }

    /**
     * Filters routes by their flight equipment. Returns a new list of routes meeting this criteria.
     */
    public ArrayList<Route> filterRoutesEquipment(String equipment) {
        ArrayList<Route> filteredRoutes = new ArrayList<Route>();
        for (Route route: routeList) {
            if (route.getEquipment().equals(equipment)) {
                filteredRoutes.add(route);
            }
        }
        return filteredRoutes;
    }

    /**
     * Ranks airports based on the number of routes they have.
     * Can be either ascending or descending order depending on the reverse parameter.
     * Returns new, sorted list of airports (doesn't override original airportList).
     */
    public ArrayList<Airport> rankAirports(boolean reverse) {
        ArrayList<Airport> rankedAirports = airportList;
        if (reverse) {
            rankedAirports.sort(Comparator.comparing(Airport::getNumRoutes).reversed());
        } else {
            rankedAirports.sort(Comparator.comparing(Airport::getNumRoutes));
        }
        return rankedAirports;
    }

    /**
     * Placeholders.
     * What are we basing the search criteria on? Airport? Country? Same applies to function below.
     */
    public ArrayList<Airport> searchAirports(String search) {

        return new ArrayList<Airport>();
    }
    public ArrayList<Flight> searchFlights(String search) {

        return new ArrayList<Flight>();
    }

    /**
     * This will be tricky. return statement and parameters aren't concrete.
     */
    public void addRecord(InputStream inFile, boolean newRecord){

    }

    public void addData(int dataType) {

    }

    public void deleteData(int dataType, int index) {

    }

}
