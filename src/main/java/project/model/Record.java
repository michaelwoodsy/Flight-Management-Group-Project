package project.model;

import java.io.*;
import java.util.*;


public class Record {
    private ArrayList<Flight> flightList;
    private ArrayList<Route> routeList;
    private ArrayList<Airport> airportList;
    private ArrayList<Airline> airlineList;
    private static Hashtable<String, Covid> covidDict = setCovid();
    private String name;

    public Record(String name) {
        this.flightList = new ArrayList<Flight>();
        this.routeList = new ArrayList<Route>();
        this.airportList = new ArrayList<Airport>();
        this.airlineList = new ArrayList<Airline>();
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Record(ArrayList<Flight> flightList, ArrayList<Route> routeList, ArrayList<Airport> airportList, ArrayList<Airline> airlineList) {
        this.flightList = flightList;
        this.routeList = routeList;
        this.airportList = airportList;
        this.airlineList = airlineList;
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
            if (attribute.equals("country")) {
                airportMatches = airport.getCountry().toLowerCase().contains(keyword.toLowerCase());
            } else if (attribute.equals("name")) {
                airportMatches = airport.getName().toLowerCase().contains(keyword.toLowerCase());
            } else if (attribute.equals("city")) {
                airportMatches = airport.getCity().toLowerCase().contains(keyword.toLowerCase());
            } else if (attribute.equals("total # routes")) {
                airportMatches = (Integer.toString(airport.getTotalRoutes()).equals(keyword));
            } else if (attribute.equals("iata")) {
                System.out.println(airport.getIata());
                airportMatches = airport.getIata().toLowerCase().contains(keyword.toLowerCase());
            } else if (attribute.equals("icao")) {
                airportMatches = airport.getIcao().toLowerCase().contains(keyword.toLowerCase());
            } else if (attribute.equals("timezone")) {
                airportMatches = airport.getTimezoneString().equals(keyword);
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
           if (attribute.equals("name")) {
               airlineMatches = airline.getName().toLowerCase().contains(keyword.toUpperCase());
           } else if (attribute.equals("alias")) {
               airlineMatches = airline.getAlias().toLowerCase().contains(keyword.toUpperCase());
           } else if (attribute.equals("iata")) {
               airlineMatches = airline.getIata().toLowerCase().contains(keyword);
           } else if (attribute.equals("icao")) {
               airlineMatches = airline.getIcao().toLowerCase().contains(keyword);
           } else if (attribute.equals("callsign")) {
               airlineMatches = airline.getCallSign().toLowerCase().contains(keyword);
           } else if (attribute.equals("country")) {
               airlineMatches = airline.getCountry().toLowerCase().contains(keyword.toLowerCase());
           }

           if (airlineMatches) {
               searchResult.add(airline);
           }
       }
       return searchResult;
    }


    /**
     * Finds routes where the value of the route's 'attribute' matches 'keyword'
     * @param keyWord The value of the route's attributes that we are searching for
     * @param attribute The attribute of the routes to be compared
     * @return A list of routes which have a value of 'attribute' that matches the keyword
     */
    public ArrayList<Route> searchRoutes(String keyWord, String attribute) {
        ArrayList<Route> searchResult = new ArrayList<Route>();

        for (Route route: routeList) {
            boolean match = false;
            if (attribute.equals("airline")) {
                match = route.getAirline().toLowerCase().contains(keyWord.toLowerCase());
            } else if (attribute.equals("total # stops")) {
                match = Integer.toString(route.getNumStops()).equals(keyWord);
            } else if (attribute.equals("source id")) {
                match = Integer.toString(route.getSourceID()).equals(keyWord);
            } else if (attribute.equals("destination id")) {
                match = Integer.toString(route.getDestID()).equals(keyWord);
            } else if (attribute.equals("source airport")) {
                match = route.getSourceAirport().toLowerCase().contains(keyWord.toLowerCase());
            } else if (attribute.equals("destination airport")) {
                match = route.getDestAirport().toLowerCase().contains(keyWord.toLowerCase());
            } else if (attribute.equals("equipment")) {
                match = route.getEquipment().toLowerCase().contains(keyWord.toLowerCase());
            }

            if (match) {
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
        if (country == null || country.toLowerCase().equals("unknown country")) {
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

    public void modifyAirport(Airport oldAirport, Airport newAirport) {
        if (this.airportList.contains(oldAirport)) {
            this.airportList.set(airportList.indexOf(oldAirport), newAirport);
        }
    }

    public void modifyAirline(Airline oldAirline, Airline newAirline) {
        if (this.airlineList.contains(oldAirline)) {
            this.airlineList.set(airlineList.indexOf(oldAirline), newAirline);
        }
    }

    public void modifyRoute(Route oldRoute, Route newRoute) {
        if (this.routeList.contains(oldRoute)) {
            this.routeList.set(routeList.indexOf(oldRoute), newRoute);
        }
    }

    public void removeAirports(Airport airport) {
        if (this.airportList.contains(airport)) {
            this.airportList.remove(airport);
        }
    }

    public void removeAirlines(Airline airlines) {
        if (this.airlineList.contains(airlines)) {
            this.airlineList.remove(airlines);
        }
    }

    public void removeRoutes(Route route) {
        if (this.routeList.contains(route)) {
            this.routeList.remove(route);
        }
    }
    /**
     * Prompts the loading of a pre-installed covid file
     */
    public static Hashtable<String, Covid> setCovid() {
        Hashtable<String, Covid> covidDict = null;

        try {
            CovidLoader covidLoad = new CovidLoader();
            covidDict = covidLoad.loadCovidFile("./data/covid.dat");
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
