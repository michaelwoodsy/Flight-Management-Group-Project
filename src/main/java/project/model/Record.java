package project.model;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.*;


public class Record {
    private ArrayList<Flight> flightList;
    private ArrayList<Route> routeList;
    private ArrayList<Airport> airportList;
    private ArrayList<Airline> airlineList;
    private static Hashtable<String, Covid> covidDict = setCovid();

    public Record(ArrayList<Flight> flightList, ArrayList<Route> routeList, ArrayList<Airport> airportList, ArrayList<Airline> airlineList) {
        this.flightList = flightList;
        this.routeList = routeList;
        this.airportList = airportList;
        this.airlineList = airlineList;
    }

    /**
     * Filters airports by country entered. Returns a new list of airports meeting this criteria.
     */
    public ArrayList<Airport> filterAirports(String keyWord) {
        ArrayList<Airport> filteredAirports = new ArrayList<Airport>();

        for (Airport airport: airportList) {
            if (airport.getCountry().toUpperCase().contains(keyWord.toUpperCase())) {
                filteredAirports.add(airport);
            }
        }
        return filteredAirports;
    }

    /**
     * Filters airlines by whether they're active or not. Returns a new list of airlines meeting this criteria.
     */
    public ArrayList<Airline> filterAirlines(Boolean active, List<Airline> airlineList) {
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
    public ArrayList<Airline> filterAirlinesCountry(String keyWord) {
        ArrayList<Airline> filteredAirlines = new ArrayList<Airline>();

        for (Airline airline: airlineList) {
            if (airline.getCountry().toUpperCase().contains(keyWord.toUpperCase())) {
                filteredAirlines.add(airline);
            }
        }
        return filteredAirlines;
    }

    /**
     * Filters routes by source airport. Returns a new list of routes meeting this criteria.
     * Needs changing from sourceAirport to country. Perhaps countries can be mapped to source airports?
     */
    public ArrayList<Route> filterRoutesDeparture(String keyWord) {
        ArrayList<Route> filteredRoutes = new ArrayList<Route>();

        for (Route route: routeList) {
            if (route.getSourceAirport().toUpperCase().contains(keyWord.toUpperCase())) {
                filteredRoutes.add(route);
            }
        }
        return filteredRoutes;
    }

    /**
     * Filters routes by destination airport. Returns a new list of routes meeting this criteria.
     * Needs changing from destAirport to country. Perhaps countries can be mapped to destination airports?
     */
    public ArrayList<Route> filterRoutesDestination(String keyWord) {
        ArrayList<Route> filteredRoutes = new ArrayList<Route>();

        for (Route route: routeList) {
            if (route.getDestAirport().toUpperCase().contains(keyWord.toUpperCase())) {
                filteredRoutes.add(route);
            }
        }
        return filteredRoutes;
    }

    /**
     * Filters routes by whether they have stops or not. Returns a new list of routes meeting this criteria.
     */
    public ArrayList<Route> filterRoutesStops(boolean direct, List<Route> routeList) {
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
    public ArrayList<Route> filterRoutesEquipment(String keyWord) {
        ArrayList<Route> filteredRoutes = new ArrayList<Route>();

        for (Route route: routeList) {
            if (route.getEquipment() == null) {
                continue;
            } else if (route.getEquipment().toUpperCase().contains(keyWord.toUpperCase())) {
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
     * Finds airports where the value of the airport's 'attribute' matches 'keyword'
     * @param keyword The value of the airport's attributes that we are searching for
     * @param attribute The attribute of the airports to be compared
     * @return A list of airports which have a value of attribute that matches the keyword
     */
    public List<Airport> searchAirports(String keyword, String attribute) {
        List<Airport> searchResult = new ArrayList<Airport>();

        for (Airport airport: airportList) {
            boolean airportMatches = false;
            if (attribute == "name") {
                airportMatches = airport.getName().toUpperCase().contains(keyword.toUpperCase());
            } else if (attribute == "city") {
                airportMatches = airport.getCity().toUpperCase().contains(keyword.toUpperCase());
            } else if (attribute == "total # routes") {
                airportMatches = (Integer.toString(airport.getTotalRoutes()) == keyword);
            } else if (attribute == "iata") {
                airportMatches = airport.getIata() == keyword;
            } else if (attribute == "icao") {
                airportMatches = airport.getIcao() == keyword;
            } else if (attribute == "timezone") {
                airportMatches = airport.getTimezoneString() == keyword;
            }

            if (airportMatches) {
                searchResult.add(airport);
            }
        }
        return searchResult;
    }

    /**
     * Finds airlines where the value of the airline's 'attribute' matches 'keyword'
     * @param keyword The value of the airline's attributes that we are searching for
     * @param attribute The attribute of the airlines to be compared
     * @return A list of airlines which have a value of 'attribute' that matches the keyword
     */
    public List<Airline> searchAirlines(String keyword, String attribute) {
       List<Airline> searchResult = new ArrayList<Airline>();
       for (Airline airline: airlineList) {
           boolean airlineMatches = false;
           if (attribute == "name") {
               airlineMatches = airline.getName().toUpperCase().contains(keyword.toUpperCase());
           } else if (attribute == "alias") {
               airlineMatches = airline.getAlias().toUpperCase().contains(keyword.toUpperCase());
           } else if (attribute == "iata") {
               airlineMatches = airline.getIata() == keyword;
           } else if (attribute == "icao") {
               airlineMatches = airline.getIcao() == keyword;
           } else if (attribute == "callsign") {
               airlineMatches = airline.getCallSign() == keyword;
           }

           if (airlineMatches) {
               searchResult.add(airline);
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
     * Searches the current COVID list for a country, and returns the the COVID object that is of that country
     * @param country The country who's COVID stats we are looking for
     * @return The COVID object that contains the stats of the requested country
     */
    public static Covid searchCovid(String country) throws NoSuchFieldException {
        if (country == null) {
            return new Covid(null, null, 0, 0, 0 );
        }
        if (covidDict.get(country) != null) {
            return covidDict.get(country);
        } else {
            throw new NoSuchFieldException("No such country exists in our records");
        }
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
    public static Hashtable<String, Covid> setCovid() {
        Hashtable<String, Covid> covidDict = null;

        try {
            covidDict = Loader.loadCovidFile("./data/covid.dat");
        } catch (IOException e) {
            System.err.println("Could not load file");
        }
        return covidDict;
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
