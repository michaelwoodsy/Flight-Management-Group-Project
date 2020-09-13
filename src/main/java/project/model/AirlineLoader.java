package project.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class AirlineLoader {
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
}
