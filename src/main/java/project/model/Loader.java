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
     * Returns a route class from a line read in routes.dat.
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
}
