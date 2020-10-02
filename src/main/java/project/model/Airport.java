package project.model;

import project.controller.Database;
import project.controller.RetrieveFromDatabase;

import java.io.Serializable;
import java.util.Objects;

/**
 * This is a Class implements an Airport.
 * You can calculate the distance between two Airports and
 * from the covid statistics will set the airport's risk accordingly
 */
public class Airport implements Serializable {
    private int id;
    private double risk;
    private String riskString;
    private String name;
    private String city;
    private String country;
    private String iata;
    private String icao;
    private double latitude;
    private double longitude;
    private int altitude;
    private double timezone;
    private String dst;
    private String timezoneString;
    private int numRoutesSource = 0;
    private int numRoutesDest = 0;
    private int totalRoutes = 0;
    private String recordName;
    private static int numMissingCovid;

    /**
     * @param id    An int that represents the Airline ID
     * @param name  A String that represents the Airline Name
     * @param city    A boolean that represents whether an Airline is active
     * @param country   A String that represents the country the Airline is in
     * @param iata  A String that represents a 2-letter code, if available
     * @param icao  A String that represents a 3-letter code, if available
     * @param latitude  A double that represents decimal degrees, where negative is South, positive is North
     * @param longitude A double that represents decimal degrees, where negative is West, positive is East
     * @param altitude An int that represents the plane's altitude in feet
     * @param timezone A double that represents the the hours offset from UTC
     * @param dst   A String that represents daylight savings time
     * @param timezoneString    A String that represents timezone in "tz" (Olson) format
     * @param numRoutesSource An int that represents number of routes that are from this airport
     * @param numRoutesDest An int that represents number of routes that go to this airport
     */
    public Airport(int id, String name, String city, String country, String iata, String icao, double latitude, double longitude, int altitude, double timezone, String dst, String timezoneString, int numRoutesSource, int numRoutesDest) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.country = country;
        this.iata = iata;
        this.icao = icao;
        this.latitude = latitude;
        this.longitude = longitude;
        this.altitude = altitude;
        this.timezone = timezone;
        this.dst = dst;
        this.timezoneString = timezoneString;
        this.determineCovidRisk();
        this.determineNumRoutes();
    }

    /**
     * This method calculates the distance between the current Airport and the destination Airport
     * using the longitude and latitude from both Airports.
     * @param   destAirport The destination Airport
     * @return  A double that represents the destination from the current Airport and the destination Airport
     */
    public double distance(Airport destAirport) {
        double lat1 = this.latitude;
        double lat2 = destAirport.latitude;
        double lon1 = this.longitude;
        double lon2 = destAirport.longitude;

        if (lat1 == 360 || lon1 == 360 || lat2 == 360 || lon2 == 360) {
            return -1; // Error case, can't have distance of -1. When this occurs, print error message in GUI.
        }

        double radius = 6371;
        double theta1 = lat1 * Math.PI / 180;
        double theta2 = lat2 * Math.PI / 180;
        double deltaTheta = (lat2 - lat1) * Math.PI / 180;
        double deltaLat = (lon2 - lon1) * Math.PI / 180;
        double a = Math.sin(deltaTheta / 2) * Math.sin(deltaTheta / 2) + Math.cos(theta1) * Math.cos(theta2) * Math.sin(deltaLat / 2) * Math.sin(deltaLat / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return radius * c;
    }

    /**
     * This method checks if the Airport is equal to another Airport object (o) ensuring it is an Airport object
     * @param   o An Airport object
     * @return  A boolean that represents whether o is equal to the current Airport
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Airport airport = (Airport) o;
        return getId() == airport.getId() &&
                Double.compare(airport.getLatitude(), getLatitude()) == 0 &&
                Double.compare(airport.getLongitude(), getLongitude()) == 0 &&
                getAltitude() == airport.getAltitude() &&
                Double.compare(airport.getTimezone(), getTimezone()) == 0 &&
                Objects.equals(getName(), airport.getName()) &&
                Objects.equals(getCity(), airport.getCity()) &&
                Objects.equals(getCountry(), airport.getCountry()) &&
                Objects.equals(getIata(), airport.getIata()) &&
                Objects.equals(getIcao(), airport.getIcao()) &&
                Objects.equals(getDst(), airport.getDst()) &&
                Objects.equals(getTimezoneString(), airport.getTimezoneString());
    }

    /**
     * Hashcode that complements equals method.
     * @return  returns an integer value, generated by a hashing algorithm.
     */
    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getCity(), getCountry(), getIata(), getIcao(), getLatitude(), getLongitude(), getAltitude(), getTimezone(), getDst(), getTimezoneString());
    }

    /**
     * Gets the covid statistics for the airport's country from the record class, and sets the airport's risk accordingly
     */
    public void determineCovidRisk() {
        try {
            Covid covidStats = Record.searchCovid(this.country);
            double riskDouble = covidStats.getRiskDouble();
            this.risk = riskDouble;
            this.riskString = covidStats.getRiskString();
        } catch (NoSuchFieldException e) {
            this.risk = 0;
            numMissingCovid += 1;
            if (!Covid.missingCountries.contains(this.country)) {
                Covid.missingCountries.add(this.country);
            }
        }
    }

    public void determineNumRoutes() {
        this.numRoutesSource = RetrieveFromDatabase.getNumRoutes(this, "sourceID");
        this.numRoutesDest = RetrieveFromDatabase.getNumRoutes(this, "destID");
        this.totalRoutes = this.numRoutesDest + this.numRoutesSource;
    }

    /**
     * This method returns a String of information about the Airport, and the city and country it's from.
     *
     * @return  A String of information about Airport, and the city and country it's from.
     */
    @Override
    public String toString() {
        String nameString;
        if (name == null) {
            nameString = "Unknown airport, ";
        } else {
            nameString = name + ", ";
        }

        String cityString;
        if (city == null) {
            cityString = "located in an unknown city, ";
        } else {
            cityString = "located in " + city + ", ";
        }

        String countryString;
        if (country == null) {
            countryString = "in an unknown country";
        } else {
            countryString = country;
        }

        return nameString + cityString + countryString;
    }

    public void setNumRoutesSource() {
        this.numRoutesSource = RetrieveFromDatabase.getNumRoutes(this, "sourceID");
        setTotalRoutes();
    }

    public int getNumRoutesDest() {
        return numRoutesDest;
    }

    public void setNumRoutesDest() {
        this.numRoutesDest = RetrieveFromDatabase.getNumRoutes(this, "destID");
        setTotalRoutes();
    }

    public int getTotalRoutes() {
        return totalRoutes;
    }

    public void setTotalRoutes() {
        this.totalRoutes = this.numRoutesDest + this.numRoutesSource;
    }

    public static int getNumMissingCovid() {
        return numMissingCovid;
    }

    public static void setNumMissingCovid() {
        numMissingCovid = 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getRisk() {
        return risk;
    }

    public String getRiskString() { return riskString; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getIata() {
        return iata;
    }

    public String getIcao() {
        return icao;
    }

    public void setIcao(String icao) {
        this.icao = icao;
    }

    public double getLatitude() {
        return latitude;
    }


    public double getLongitude() {
        return longitude;
    }


    public int getAltitude() {
        return altitude;
    }


    public double getTimezone() {
        return timezone;
    }

    public String getDst() {
        return dst;
    }

    public String getTimezoneString() {
        return timezoneString;
    }

    public void setRecordName(String recordName) { this.recordName = recordName; }

    public String getRecordName() { return recordName; }

    public int getNumRoutesSource() {
        return numRoutesSource;
    }

    /**
     * Create a string representation for the database
     */
    public String getDatabaseValues() {
        String values = String.format("%d,%d,?,?,?,'%s','%s','%s','%s',%f,%f,%f,'%s'",
                this.id, this.altitude, this.iata, this.icao,
                this.dst, this.timezoneString, this.latitude,
                this.longitude, this.timezone, this.recordName);
        return values;

    }


}
