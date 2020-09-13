package project.model;

public class AirportLoader {
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
}
