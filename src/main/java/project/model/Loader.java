package project.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Loader {

    /**
     * Returns a list of airlines by reading a comma-separated data file.
     */
    public ArrayList<Airline> loadAirlineFile(String path) throws IOException {

        ArrayList<Airline> airlineList = new ArrayList<Airline>();

        BufferedReader dataReader = new BufferedReader(new FileReader(path));

        boolean breaker = false;
        while (!breaker) {
            String row = dataReader.readLine();
            if (row == null) {
                breaker = true;
            } else {
                String[] data = row.split(",");
                airlineList.add(loadAirline(data));
            }
        }
        dataReader.close();
        return airlineList;
    }

    public Flight loadFlightFile(String path) throws IOException {

        ArrayList<String> statuses = new ArrayList<String>();
        ArrayList<String> locations = new ArrayList<String>();
        ArrayList<Integer> altitudes = new ArrayList<Integer>();
        ArrayList<Double> latitudes = new ArrayList<Double>();
        ArrayList<Double> longitudes = new ArrayList<Double>();
        int risk = 0; // Placeholder

        BufferedReader dataReader = new BufferedReader(new FileReader(path));

        boolean breaker = false;
        while (!breaker) {
            String row = dataReader.readLine();
            if (row == null) {
                breaker = true;
            } else {
                String[] data = row.split(",");

                String status;
                try {
                    status = data[0];
                } catch (Exception e) {
                    status = null;
                }

                String location;
                try {
                    location = data[1];
                } catch (Exception e) {
                    location = null;
                }

                int altitude;
                try {
                    altitude = Integer.parseInt(data[2]);
                } catch (Exception e) {
                    altitude = -1;
                }

                double latitude;
                try {
                    latitude = Double.parseDouble(data[3]);
                } catch (Exception e) {
                    latitude = 360; // Error case, latitude can't be 360.
                }

                double longitude;
                try {
                    longitude = Double.parseDouble(data[3]);
                } catch (Exception e) {
                    longitude = 360; // Error case, longitude can't be 360.
                }

                statuses.add(status);
                locations.add(location);
                altitudes.add(altitude);
                latitudes.add(latitude);
                longitudes.add(longitude);
            }
        }
        dataReader.close();

        Flight flight = new Flight(latitudes, longitudes, altitudes, locations, statuses, risk);
        return flight;
    }

    /**
     * Returns a list of airports by reading a comma-separated data file.
     */
    public ArrayList<Airport> loadAirportFile(String path) throws IOException {

        ArrayList<Airport> airportList = new ArrayList<Airport>();

        BufferedReader dataReader = new BufferedReader(new FileReader(path));

        boolean breaker = false;
        while (!breaker) {
            String row = dataReader.readLine();
            if (row == null) {
                breaker = true;
            } else {
                String[] data = row.split(",");

                // Lazy error handling - deleting the specific pieces of data that cause errors. Needs changing.
                int errorCheck = Integer.parseInt(data[0]);
                if (errorCheck == 5674 || errorCheck == 5675 || errorCheck == 5562 || errorCheck == 5881) {
                    continue;
                }
                airportList.add(loadAirport(data));
            }
        }
        dataReader.close();
        return airportList;
    }

    /**
     * Returns a list of routes by reading a comma-separated data file.
     */
    public ArrayList<Route> loadRouteFile(String path) throws IOException {

        ArrayList<Route> routeList = new ArrayList<Route>();

        BufferedReader dataReader = new BufferedReader(new FileReader(path));

        boolean breaker = false;
        while (!breaker) {
            String row = dataReader.readLine();
            if (row == null) {
                breaker = true;
            } else {
                String[] data = row.split(",");
                routeList.add(loadRoute(data));
            }
        }
        dataReader.close();
        return routeList;
    }

    /**
     * Returns an airline class from a line read in airlines.dat.
     */
    public Airline loadAirline(String[] airlineData) {

        int id;
        try {
            id = Integer.parseInt(airlineData[0]);
        } catch (Exception e) {
            id = -1;
        }

        String name;
        try {
            name = airlineData[1];
        } catch (Exception e) {
            name = null;
        }

        String alias;
        try {
            alias = airlineData[2];
        } catch (Exception e) {
            alias = null;
        }

        String iata;
        try {
            iata = airlineData[3];
        } catch (Exception e) {
            iata = null;
        }

        String icao;
        try {
            icao = airlineData[4];
        } catch (Exception e) {
            icao = null;
        }

        String callSign;
        try {
            callSign = airlineData[5];
        } catch (Exception e) {
            callSign = null;
        }

        String country;
        try {
            country = airlineData[6];
        } catch (Exception e) {
            country = null;
        }

        String activeString;
        try {
            activeString = airlineData[7];
        } catch (Exception e) {
            activeString = "N";
        }

        boolean active;
        if (activeString == "Y") {
            active = true;
        } else {
            active = false;
        }

        Airline newAirline = new Airline(id, name, active, country, alias, callSign, iata, icao);
        return newAirline;

    }

    /**
     * Returns an airport class from a line read in airports.dat.
     */
    public Airport loadAirport(String[] airportData) {

        int id;
        try {
            id = Integer.parseInt(airportData[0]);
        } catch (Exception e) {
            id = -1;
        }

        String name;
        try {
            name = airportData[1];
        } catch (Exception e) {
            name = null;
        }

        String city;
        try {
            city = airportData[2];
        } catch (Exception e) {
            city = null;
        }

        String country;
        try {
            country = airportData[3];
        } catch (Exception e) {
            country = null;
        }

        String iata;
        try {
            iata = airportData[4];
        } catch (Exception e) {
            iata = null;
        }

        String icao;
        try {
            icao = airportData[5];
        } catch (Exception e) {
            icao = null;
        }

        double latitude;
        try {
            latitude = Double.parseDouble(airportData[6]);
        } catch (Exception e) {
            latitude = 360; // Latitudes can't be this big, used for error catching when calculating distances.
        }

        double longitude;
        try {
            longitude = Double.parseDouble(airportData[7]);
        } catch (Exception e) {
            longitude = 360; // Longitudes can't be this big, used for error catching when calculating distances.
        }

        int altitude;
        try {
            altitude = Integer.parseInt(airportData[8]);
        } catch (Exception e) {
            altitude = -1;
        }

        double timezone;
        try {
            timezone = Double.parseDouble(airportData[9]);
        } catch (Exception e) {
            timezone = 25; // Timezones can't be this far ahead, used for error catching.
        }

        String dst;
        try {
            dst = airportData[10];
        } catch (Exception e) {
            dst = null;
        }

        String timezoneString;
        try {
            timezoneString = airportData[11];
        } catch (Exception e) {
            timezoneString = null;
        }

        String type;
        try {
            type = airportData[12];
        } catch (Exception e) {
            type = null;
        }

        String source;
        try {
            source = airportData[13];
        } catch (Exception e) {
            source = null;
        }

        int numRoutes = 0; // Placeholder, is altered through another function in Record (needs list routes to work).
        int risk = 0; // Placeholder until we decide how we're doing the covid stuff.

        Airport newAirport = new Airport(id, risk, name, city, country, iata, icao, latitude, longitude, altitude, timezone, dst, timezoneString, type, source, numRoutes);
        return newAirport;
    }

    /**
     * Returns a route class from a line read in routes.dat.
     */
    public Route loadRoute(String[] routeData) {

        String airline;
        try {
            airline = routeData[0];
        } catch (Exception e) {
            airline = null;
        }

        int id;
        try {
            id = Integer.parseInt(routeData[1]);
        } catch (Exception e) {
            id = -1;
        }

        String sourceAirport;
        try {
            sourceAirport = routeData[2];
        } catch (Exception e) {
            sourceAirport = null;
        }

        int sourceID;
        try {
            sourceID = Integer.parseInt(routeData[3]);
        } catch (Exception e) {
            sourceID = -1;
        }

        String destAirport;
        try {
            destAirport = routeData[4];
        } catch (Exception e) {
            destAirport = null;
        }

        int destID;
        try {
            destID = Integer.parseInt(routeData[5]);
        } catch (Exception e) {
            destID = -1;
        }

        String codeshareString;
        try {
            codeshareString = routeData[6];
        } catch (Exception e) {
            codeshareString = "N";
        }

        int numStops;
        try {
            numStops = Integer.parseInt(routeData[7]);
        } catch (Exception e) {
            numStops = -1;
        }

        String equipment;
        try {
            equipment = routeData[8];
        } catch (Exception e) {
            equipment = "";
        }

        boolean codeshare;
        if (codeshareString == "Y") {
            codeshare = true;
        } else {
            codeshare = false;
        }

        Route newRoute = new Route(airline, id, sourceAirport, sourceID, destAirport, destID, numStops, equipment, codeshare);
        return newRoute;

    }
    public ArrayList<Covid> loadCovidFile(String path) throws IOException {

        ArrayList<Covid> covidList = new ArrayList<Covid>();


        BufferedReader dataReader = new BufferedReader(new FileReader(path));

        boolean breaker = false;
        while (!breaker) {
            String row = dataReader.readLine();
            if (row == null) {
                breaker = true;
            } else {
                String[] data = row.split(",");
                covidList.add(loadCovid(data));
            }
        }
        dataReader.close();
        return covidList;
    }

    public Covid loadCovid(String[] covidData) {

        String Iso_code = covidData[0];
        String continent = covidData[1];
        String location = covidData[2];
        String date = covidData[3];
        int total_cases = Integer.parseInt(covidData[4]);
        int new_cases = Integer.parseInt(covidData[5]);
        int total_deaths = Integer.parseInt(covidData[6]);
        int new_deaths = Integer.parseInt(covidData[7]);
        float total_cases_per_million = Float.parseFloat(covidData[8]);
        float total_deaths_per_million = Float.parseFloat(covidData[9]);
        int population = Integer.parseInt(covidData[10]);
        float population_density = Float.parseFloat(covidData[11]);

        //need to add error handling

        Covid newCovid = new Covid(Iso_code, continent, location, date, total_cases, new_cases, total_deaths,
                new_deaths, total_cases_per_million, total_deaths_per_million, population, population_density);
        return newCovid;

    }
}
