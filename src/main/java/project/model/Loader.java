package project.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;

public class Loader {

    /**
     * Ensures loaded file is correct file format.
     */
    public boolean errorHandler(File file) {
        String extension = "";

        int i = file.getAbsolutePath().lastIndexOf('.');
        int p = Math.max(file.getAbsolutePath().lastIndexOf('/'), file.getAbsolutePath().lastIndexOf('\\'));

        if (i > p) {
            extension = file.getAbsolutePath().substring(i+1);
        }
        if (!extension.equals("dat") && !extension.equals("csv")) {
            return false;
        } else {
            return true;
        }

    }

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
     * Checks if loaded airline file is right format.
     */
    public boolean loadAirlineErrorCheck(String path) throws IOException {

        BufferedReader dataReader = new BufferedReader(new FileReader(path));

        String row = dataReader.readLine();
        if (row == null) {
                return false;
        } else {
            String[] data = row.split(",");
            if (data.length == 8) {
                return true;
            }
        }

        dataReader.close();
        return false;
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

        return new Flight(latitudes, longitudes, altitudes, locations, statuses, risk);
    }

    /**
     * Checks if loaded airport file is right format.
     */
    public boolean loadFlightErrorCheck(String path) throws IOException {

        BufferedReader dataReader = new BufferedReader(new FileReader(path));

        String row = dataReader.readLine();
        if (row == null) {
            return false;
        } else {
            String[] data = row.split(",");
            if (data.length == 5) {
                return true;
            }
        }

        dataReader.close();
        return false;
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
                String[] data = row.split(",(?! )");
                airportList.add(loadAirport(data));
            }
        }
        dataReader.close();
        return airportList;
    }

    /**
     * Checks if loaded airport file is right format.
     */
    public boolean loadAirportErrorCheck(String path) throws IOException {

        BufferedReader dataReader = new BufferedReader(new FileReader(path));

        String row = dataReader.readLine();
        if (row == null) {
            return false;
        } else {
            String[] data = row.split(",");
            if (data.length >= 12  && data.length <= 14) {
                return true;
            }
        }

        dataReader.close();
        return false;
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
     * Checks if loaded route file is right format.
     */
    public boolean loadRouteErrorCheck(String path) throws IOException {

        BufferedReader dataReader = new BufferedReader(new FileReader(path));

        String row = dataReader.readLine();
        if (row == null) {
            return false;
        } else {
            String[] data = row.split(",");
            if (data.length == 9) {
                return true;
            }
        }

        dataReader.close();
        return false;
    }

    /**
     * Returns an airline class from a line read in airlines.dat.
     * Invalid ints will be displayed as -1, invalid strings displayed as null
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
            name = airlineData[1].replace("\"", "").replace("\\\\", "");
            if (name.equals("\\N") || name.equals("")) {
                name = null;
            }
        } catch (Exception e) {
            name = null;
        }

        String alias;
        try {
            alias = airlineData[2].replaceAll("\"", "").replace("\\\\", "");
            if (alias.equals("\\N") || alias.equals("")) {
                alias = null;
            }
        } catch (Exception e) {
            alias = null;
        }

        String iata;
        try {
            iata = airlineData[3].replace("\"", "").replace("\\\\", "");
            if (iata.equals("\\N") || iata.equals("") || !iata.matches("[a-zA-Z0-9]*")) {
                iata = null;
            }
        } catch (Exception e) {
            iata = null;
        }

        String icao;
        try {
            icao = airlineData[4].replace("\"", "").replace("\\\\", "");
            if (icao.equals("\\N") || icao.equals("") || !icao.matches("[a-zA-Z0-9]*")) {
                icao = null;
            }
        } catch (Exception e) {
            icao = null;
        }

        String callSign;
        try {
            callSign = airlineData[5].replace("\"", "").replace("\\\\", "");
            if (callSign.equals("\\N") || callSign.equals("")) {
                callSign = null;
            }
        } catch (Exception e) {
            callSign = null;
        }

        String country;
        try {
            country = airlineData[6].replace("\"", "").replace("\\\\", "");
            if (country.equals("\\N") || country.equals("")) {
                country = null;
            }
        } catch (Exception e) {
            country = null;
        }

        String activeString;
        try {
            activeString = airlineData[7].replace("\"", "").replace("\\\\", "");
        } catch (Exception e) {
            activeString = "N";
        }

        boolean active;
        active = activeString.equals("Y");

        return new Airline(id, name, active, country, alias, callSign, iata, icao);

    }

    /**
     * Returns an airport class from a line read in airports.dat.
     * (Most) nvalid ints will be displayed as -1, invalid strings displayed as null
     * Invalid latitudes and longitudes are displayed as 360. This is because these are impossible latitude/longitude values, and correspond to easy error handling.
     * Invalid timezones displayed as 25. Can't have a +25 hour timezone after all.
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
            name = airportData[1].replaceAll("\"", "").replace("\\\\", "");
            if (name.equals("\\N") || name.equals("")) {
                name = null;
            }
        } catch (Exception e) {
            name = null;
        }

        String city;
        try {
            city = airportData[2].replaceAll("\"", "").replace("\\\\", "");
            if (city.equals("\\N") || city.equals("")) {
                city = null;
            }
        } catch (Exception e) {
            city = null;
        }

        String country;
        try {
            country = airportData[3].replaceAll("\"", "").replace("\\\\", "");
            if (country.equals("\\N") || country.equals("")) {
                country = null;
            }
        } catch (Exception e) {
            country = null;
        }

        String iata;
        try {
            iata = airportData[4].replaceAll("\"", "").replace("\\\\", "");
            if (iata.equals("\\N") || iata.equals("") || !iata.matches("[a-zA-Z0-9]*")) {
                iata = null;
            }
        } catch (Exception e) {
            iata = null;
        }

        String icao;
        try {
            icao = airportData[5].replaceAll("\"", "").replace("\\\\", "");
            if (icao.equals("\\N") || icao.equals("") || !icao.matches("[a-zA-Z0-9]*")) {
                icao = null;
            }
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
            dst = airportData[10].replaceAll("\"", "").replace("\\\\", "");
            if (dst.equals("\\N") || dst.equals("")) {
                dst = null;
            }
        } catch (Exception e) {
            dst = null;
        }

        String timezoneString;
        try {
            timezoneString = airportData[11].replaceAll("\"", "").replace("\\\\", "");
            if (timezoneString.equals("\\N") || timezoneString.equals("")) {
                timezoneString = null;
            }
        } catch (Exception e) {
            timezoneString = null;
        }

        String type;
        try {
            type = airportData[12].replaceAll("\"", "").replace("\\\\", "");
            if (type.equals("\\N") || type.equals("")) {
                type = null;
            }
        } catch (Exception e) {
            type = null;
        }

        String source;
        try {
            source = airportData[13].replaceAll("\"", "").replace("\\\\", "");
            if (source.equals("\\N") || source.equals("")) {
                source = null;
            }
        } catch (Exception e) {
            source = null;
        }

        int numRoutesSource = 0; // Placeholder, is altered through another function in Record (needs list routes to work).
        int numRoutesDest = 0; // Same as above.
        int risk = 0; // Placeholder until we decide how we're doing the covid stuff.

        return new Airport(id, risk, name, city, country, iata, icao, latitude, longitude, altitude, timezone, dst, timezoneString, type, source, numRoutesSource, numRoutesDest);
    }

    /**
     * Returns a route class from a line read in routes.dat.
     * Invalid ints will be displayed as -1, invalid strings displayed as null
     */
    public Route loadRoute(String[] routeData) {

        String airline;
        try {
            airline = routeData[0].replaceAll("\"", "").replace("\\\\", "");
            if (airline.equals("\\N") || airline.equals("")) {
                airline = null;
            }
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
            sourceAirport = routeData[2].replaceAll("\"", "").replace("\\\\", "");
            if (sourceAirport.equals("\\N") || sourceAirport.equals("") || !sourceAirport.matches("[a-zA-Z0-9]*")) {
                sourceAirport = null;
            }
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
            destAirport = routeData[4].replaceAll("\"", "").replace("\\\\", "");
            if (destAirport.equals("\\N") || destAirport.equals("") || !destAirport.matches("[a-zA-Z0-9]*")) {
                destAirport = null;
            }
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
            codeshareString = routeData[6].replaceAll("\"", "").replace("\\\\", "");
            if (codeshareString.equals("\\N") || codeshareString.equals("")) {
                codeshareString = "N";
            }
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
            equipment = routeData[8].replaceAll("\"", "").replace("\\\\", "");
            if (equipment.equals("\\N") || equipment.equals("")) {
                equipment = null;
            }
        } catch (Exception e) {
            equipment = null;
        }

        boolean codeshare;
        codeshare = codeshareString.equals("Y");

        return new Route(airline, id, sourceAirport, sourceID, destAirport, destID, numStops, equipment, codeshare);

    }


    public static Hashtable<String, Covid> loadCovidFile(String path) throws IOException {

        Hashtable<String, Covid> covidDict = new Hashtable<>();
        Covid currentCovid;

        BufferedReader dataReader = new BufferedReader(new FileReader(path));

        boolean breaker = false;
        while (!breaker) {
            String row = dataReader.readLine();
            if (row == null) {
                breaker = true;
            } else {
                String[] data = row.split(",");
                currentCovid = loadCovid(data);
                covidDict.put(currentCovid.getCountry(), currentCovid);
            }
        }
        dataReader.close();
        return covidDict;
    }

    public static Covid loadCovid(String[] covidData) {
        // for numeric values, if they are not valid they are set to a default of 0.
        // for string values, if they contain illegal characters are set to null
        //e.g. if total_cases is missing or is not numeric, then it is set to 0
        //e.g. if continent is Asi3a (invalid), it is set to null
        String location;
        try {
            if (covidData[2].matches("[A-Za-z '-]+")){
                location = covidData[2];
            }
            else{
                location = "Unknown";
            }
        }catch (Exception e){
            location = "Unknown";
        }

        String date;
        try {
            //checks date doesn't contain invalid characters
            if (covidData[3].matches("[0-9/]+")){
                date = covidData[3];
            }
            else{
                date = null;
            }
        }catch (Exception e){
            date = null;
        }

        float total_cases_per_million;
        try {
            total_cases_per_million = Float.parseFloat(covidData[8]);
        }catch (Exception e){
            total_cases_per_million = 0;
        }

        float total_deaths_per_million;
        try {
            total_deaths_per_million = Float.parseFloat(covidData[9]);
        }catch (Exception e){
            total_deaths_per_million = 0;
        }

        int population;
        try {
            population = Integer.parseInt(covidData[10]);
        }catch (Exception e){
            population = 0;
        }

        Covid newCovid = new Covid(location, date, total_cases_per_million, total_deaths_per_million, population);
        return newCovid;

    }
}
