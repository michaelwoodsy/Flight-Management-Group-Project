package project.controller;

import project.model.Airline;
import project.model.Airport;
import project.model.Record;

import java.util.ArrayList;
import java.util.List;

public class DataChecker {

    /**
     *
     * @param currentRecord
     * @param name
     * @param city
     * @param country
     * @param iata
     * @param icao
     * @param latitude
     * @param longitude
     * @param altitude
     * @param timezone
     * @param dst
     * @param timezoneString
     * @return
     */
    public ArrayList<String> checkAirport(Record currentRecord, String name, String city, String country, String iata, String icao, String latitude, String longitude, String altitude, String timezone, String dst, String timezoneString) {
        ArrayList<String> errors = new ArrayList<String>();

        if (name.equals("")) { errors.add("Invalid Airport Name"); }

        if (city.equals("")) { errors.add("Invalid City Name"); }

        if (country.equals("")) { errors.add("Invalid Country Name"); }

        if (iata.equals("") || !iata.matches("[a-zA-Z0-9]*")) {
            errors.add("Invalid IATA Code");
        } else if (iata.length() != 3) {
            errors.add("Airport IATA codes must be 3 letters long");
        } else if (currentRecord.searchAirports(iata, "iata").size() != 0) {
            //Check if any airports in the current record have the same
            errors.add("New IATA Code matches an existing airport");
        }

        if (icao.equals("") || !icao.matches("[a-zA-Z0-9]*")) {
            errors.add("Invalid ICAO Code");
        } else if (icao.length() != 4) {
            errors.add("Airport ICAO codes must be 4 letters long");
        } else if (currentRecord.searchAirports(icao, "icao").size() != 0) {
            errors.add("New ICAO Code matches an existing airport");
        }

        double num_latitude = 0;
        try {
            num_latitude = Double.parseDouble(latitude);
            if (num_latitude < -90 || num_latitude > +90) { errors.add("Invalid Latitude (Must be between -90 and +90)"); }
        } catch (Exception e) {
            errors.add("Invalid Latitude");
        }

        double num_longitude = 0;
        try {
            num_longitude = Double.parseDouble(longitude);
            if (num_longitude < -180 || num_longitude > 180) { errors.add("Invalid Longitude (Must be between -180 and +180)"); }
        } catch (Exception e) {
            errors.add("Invalid Longitude");
        }

        int num_altitude = 0;
        try {
            num_altitude = Integer.parseInt(altitude);
        } catch (Exception e) {
            errors.add("Invalid Altitude");
        }

        double num_timezone = 0;
        try {
            num_timezone = Double.parseDouble(timezone);
            if (num_timezone < -5 || num_timezone > 25) { errors.add("Invalid Timezone Offset(Must be between -5 and +25)"); }
        } catch (Exception e) {
            errors.add("Invalid Timezone Offset");
        }

        if (dst.equals("")) { errors.add("Invalid DST"); }

        if (timezoneString.equals("")) { errors.add("Invalid Timezone Name"); }
        return errors;
    }

    /**
     *
     * @param currentRecord
     * @param name
     * @param country
     * @param iata
     * @param icao
     * @return
     */
    public ArrayList<String> checkAirline(Record currentRecord, String name, String country, String iata, String icao) {
        ArrayList<String> errors = new ArrayList<String>();

        if (name.equals("")) { errors.add("Invalid Airline Name"); }

        if (country.equals("")) { errors.add("Invalid Country Name"); }

        //As one IATA airline code can be assigned to multiple airlines, uniqueness does not need to be verified here
        if (iata.equals("") || !iata.matches("[a-zA-Z0-9]*")) {
            errors.add("Invalid IATA Code");
        } else if (iata.length() != 2 && !iata.equals("Unknown")) {
            errors.add("Airline IATA codes must be 2 letters long");
        }

        //Each ICAO code is unique to an airline, hence uniqueness needs to be verified here
        if (icao.equals("") || !icao.matches("[a-zA-Z0-9]*")) {
            errors.add("Invalid ICAO Code");
        } else if (icao.length() != 3) {
            errors.add("Airline ICAO codes must be 3 letters long");
        } else if (currentRecord.searchAirlines(icao.toLowerCase(), "icao").size() != 0) {
            errors.add("New ICAO code matches an existing airline");
        }

        return errors;
    }

    /**
     *
     * @param currentRecord
     * @param airline
     * @param sourceAirport
     * @param destAirport
     * @param numStops
     * @param equipment
     * @return
     */
    public ArrayList<String> checkRoutes(Record currentRecord, String airline, String sourceAirport, String destAirport, String numStops, String equipment) {
        ArrayList<String> errors = new ArrayList<String>();

        List<Airline> resultAirline = currentRecord.searchAirlines(airline, "name");
        if (airline.equals("")) {
            errors.add("Invalid Airline Name");
        } else if (resultAirline.size() < 1) {
            errors.add("Airline does not exist with that name");
        } else if (resultAirline.size() > 1) {
            errors.add("Please be more specific with the Airline Name");
        } else {
            airline = resultAirline.get(0).getIata();
            if (airline.equals("Unknown")) { errors.add("Airline has an invalid IATA code"); }
        }

        List<Airport> resultSource = currentRecord.searchAirports(sourceAirport, "name");
        if (sourceAirport.equals("")) {
            errors.add("Invalid Source Airport Name");
        } else if (resultSource.size() < 1) {
            errors.add("Source Airport does not exist with that name");
        } else if (resultSource.size() > 1) {
            errors.add("Please be more specific with the Source Airport name");
        } else {
            sourceAirport = resultSource.get(0).getIata();
            if (sourceAirport.equals("Unknown")) { errors.add("Source Airport has an invalid IATA code"); }
        }

        List<Airport> resultDest = currentRecord.searchAirports(destAirport, "name");
        if (destAirport.equals("")) {
            errors.add("Invalid Destination Airport Name");
        } else if (resultDest.size() < 1) {
            errors.add("Destination Airport does not exist with that name");
        } else if (resultDest.size() > 1) {
            errors.add("Please be more specific with the Destination name");
        } else {
            destAirport = resultDest.get(0).getIata();
            if (destAirport.equals("Unknown")) { errors.add("Destination Airport has an invalid IATA code"); }
        }

        int testNumStops = 0;
        try {
            testNumStops = Integer.parseInt(numStops);
        } catch (Exception e) {
            errors.add("Invalid Number of Stops");
        }

        if (equipment.equals("")) { errors.add("Invalid Equipment Name"); }

        return errors;
    }
}
