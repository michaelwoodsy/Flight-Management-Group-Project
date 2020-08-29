package project.model;

import javafx.collections.ObservableList;

import java.io.*;
import java.util.*;


public class Record {
    private ArrayList<Flight> flightList = new ArrayList<Flight>();
    private ArrayList<Route> routeList = new ArrayList<Route>();
    private ArrayList<Airport> airportList = new ArrayList<Airport>();
    private ArrayList<Airline> airlineList = new ArrayList<Airline>();
    private ArrayList<Covid> covidList = new ArrayList<Covid>();

    public Record(ArrayList<Flight> flightList, ArrayList<Route> routeList, ArrayList<Airport> airportList, ArrayList<Airline> airlineList, ArrayList<Covid> covidList) {
        this.flightList = flightList;
        this.routeList = routeList;
        this.airportList = airportList;
        this.airlineList = airlineList;
        this.covidList = covidList;
    }

    /**
     * Filters airports by country entered. Returns a new list of airports meeting this criteria.
     */
    public ArrayList<Airport> filterAirports(String country) {
        ArrayList<Airport> filteredAirports = new ArrayList<Airport>();
        for (Airport airport: airportList) {
            if (airport.getCountry().toLowerCase().equals(country.toLowerCase())) {
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
    public List<Airport> rankAirports(boolean reverse, List<Airport> airports) {
        List<Airport> rankedAirports = airports;
        if (reverse) {
            rankedAirports.sort(Comparator.comparing(Airport::getTotalRoutes).reversed());
        } else {
            rankedAirports.sort(Comparator.comparing(Airport::getTotalRoutes));
        }
        return rankedAirports;
    }

    /**
     * Search by Keyword. Might need changing...
     * To search airports, input a key word(string)
     * Searches through list of Airports and checks whether it contains the key word
     * e.g. keyWord = "land", results = "Zealand", "orLANDdo"...
     *
     * @param keyWord The key word(string) to be searched
     * @return searchResult, a list of Airport containing key word in Airport name
     */
    public ArrayList<Airport> searchAirports(String keyWord) {
        ArrayList<Airport> searchResult = new ArrayList<Airport>();

        for (Airport airport: airportList) {
            if (airport.getName().toUpperCase().contains(keyWord.toUpperCase())) {
                searchResult.add(airport);
            }
        }
        return searchResult;
    }

    /**
     * Search by airline name. Might need changing.
     */
    public ArrayList<Route> searchRoutes(String keyWord) {
        ArrayList<Route> searchResult = new ArrayList<Route>();

        for (Route route: routeList) {
            if (route.getAirline().toUpperCase().contains(keyWord.toUpperCase())) {
                searchResult.add(route);
            }
        }
        return searchResult;
    }

    /**
     * Requires special implementation in GUI.
     * To search flights, check either the destination or source checkbox (can't be both).
     * Then enter the name of the source/destination airport. If this is on record, will identify the
     * ICAO code from that airport, and search the flightlist for flights matching this source/destination
     * ICAO. If none can be found, returns empty list. Print exception when this occurs.
     */
    public ArrayList<Flight> searchFlights(boolean source, String keyWord) {

        ArrayList<Flight> searchResult = new ArrayList<Flight>();

        for (Airport airport: airportList) {
            if (airport.getName().toUpperCase().contains(keyWord.toUpperCase())) {
                String icao = airport.getIcao();
                for (Flight flight : flightList) {
                    if (source) { // Searching source
                        if (flight.getSource().equals(icao)) {
                            searchResult.add(flight);
                        }
                    } else {  // Searching destination
                        if (flight.getDest().equals(icao)) {
                            searchResult.add(flight);
                        }
                    }
                }
            }
        }
        return searchResult;
    }

    /**
     * Iterates through the list of airports and list of routes and adds 1 to the numRoutesSource attribute
     * of each airport based on how many routes begin at that airport.
     */
    public void setNumRoutesSource() {
        for (Airport airport : airportList) {
            String icao = airport.getIcao();
            String iata = airport.getIata();
            for (Route route : routeList) {
                if (route.getSourceAirport().equals(icao) || route.getSourceAirport().equals(iata)) {
                    airport.setNumRoutesSource(airport.getNumRoutesSource() + 1);
                }
            }
        }
    }

    /**
     * Iterates through the list of airports and list of routes and adds 1 to the numRoutesDest attribute
     * of each airport based on how many routes end at that airport.
     */
    public void setNumRoutesDest() {
        for (Airport airport : airportList) {
            String icao = airport.getIcao();
            String iata = airport.getIata();
            for (Route route : routeList) {
                if (route.getDestAirport().equals(icao) || route.getDestAirport().equals(iata)) {
                    airport.setNumRoutesDest(airport.getNumRoutesDest() + 1);
                }
            }
        }
    }

    /**
     * This will be tricky. return statement and parameters aren't concrete.
     */
    public void addRecord(InputStream inFile, boolean newRecord) {

    }

    /**
     * Could be same as above. Depends on GUI implementation.
     */
    public void deleteData(int dataType, int index) {

    }

    public void addRoutes(ArrayList<Route> newRouteList) {
        this.routeList.addAll(newRouteList);
        Set<Route> uniqueElements = new HashSet<>(this.routeList);
        this.routeList.clear();
        this.routeList.addAll(uniqueElements);
        this.routeList.sort(Comparator.comparing(Route::getId));
    }

    public void addAirports(ArrayList<Airport> newAirportList) {
        this.airportList.addAll(newAirportList);
        Set<Airport> uniqueElements = new HashSet<>(this.airportList);
        this.airportList.clear();
        this.airportList.addAll(uniqueElements);
        this.airportList.sort(Comparator.comparing(Airport::getId));
    }

    public void addAirlines(ArrayList<Airline> newAirlineList) {
        this.airlineList.addAll(newAirlineList);
        Set<Airline> uniqueElements = new HashSet<>(this.airlineList);
        this.airlineList.clear();
        this.airlineList.addAll(uniqueElements);
        this.airlineList.sort(Comparator.comparing(Airline::getId));
    }

    public void addFlights(Flight newFlight) {
        this.flightList.add(newFlight);
        Set<Flight> uniqueElements = new HashSet<>(this.flightList);
        this.flightList.clear();
        this.flightList.addAll(uniqueElements);
    }

    /**
     * Not sure if we'll even need this function. Incomplete.
     */
    public void addCovid(ArrayList<Covid> newCovidList) {
        this.covidList.addAll(newCovidList);
    }

    public ArrayList<Flight> getFlightList() {
        return flightList;
    }

    public ArrayList<Route> getRouteList() {
        return routeList;
    }

    public ArrayList<Airport> getAirportList() {
        return airportList;
    }

    public ArrayList<Airline> getAirlineList() {
        return airlineList;
    }
}
