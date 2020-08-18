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

                // TBC
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

        int id = Integer.parseInt(airlineData[0]);
        String name = airlineData[1];
        String alias = airlineData[2];
        String callSign = airlineData[5];
        String country = airlineData[6];
        String activeString = airlineData[7];

        // Error handling
        String iata;
        try {
            iata = airlineData[3];
        } catch (Exception e) {
            iata = "";
        }

        String icao;
        try {
            icao = airlineData[4];
        } catch (Exception e) {
            icao = "";
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

        int risk = 0; //Placeholder until we decide how we're doing the covid stuff

        int id = Integer.parseInt(airportData[0]);

        String name = airportData[1];
        String city = airportData[2];
        String country = airportData[3];

        String iata;
        try {
            iata = airportData[4];
        } catch (Exception e) {
            iata = "";
        }

        String icao;
        try {
            icao = airportData[5];
        } catch (Exception e) {
            icao = "";
        }

        double latitude = Double.parseDouble(airportData[6]);
        double longitude = Double.parseDouble(airportData[7]);
        int altitude = Integer.parseInt(airportData[8]);
        double timezone = Double.parseDouble(airportData[9]);
        String dst = airportData[10];
        String timezoneString = airportData[11];

        String type;
        try {
            type = airportData[12];
        } catch (Exception e) {
            type = "";
        }

        String source;
        try {
            source = airportData[13];
        } catch (Exception e) {
            source = "";
        }

        int numRoutes = 0; //Placeholder

        Airport newAirport = new Airport(id, risk, name, city, country, iata, icao, latitude, longitude, altitude, timezone, dst, timezoneString, type, source, numRoutes);
        return newAirport;
    }

    /**
     * Returns a route class from a line read in routes.dat.
     */
    public Route loadRoute(String[] routeData) {

        String airline = routeData[0];
        String sourceAirport = routeData[2];
        String destAirport = routeData[4];
        String codeshareString = routeData[6];
        int numStops = Integer.parseInt(routeData[7]);

        // Error handling
        int id;
        try {
            id = Integer.parseInt(routeData[1]);
        } catch (Exception e) {
            id = -1;
        }

        int sourceID;
        try {
            sourceID = Integer.parseInt(routeData[3]);
        } catch (Exception e) {
            sourceID = -1;
        }

        int destID;
        try {
            destID = Integer.parseInt(routeData[5]);
        } catch (Exception e) {
            destID = -1;
        }

        boolean codeshare;
        if (codeshareString == "Y") {
            codeshare = true;
        } else {
            codeshare = false;
        }

        String equipment;
        try {
            equipment = routeData[8];
        } catch (Exception e) {
            equipment = "";
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
