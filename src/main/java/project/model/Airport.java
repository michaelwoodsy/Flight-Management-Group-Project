package project.model;

import java.util.ArrayList;

public class Airport {
    private int id;
    private int risk;
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
    private String type;
    private String source;
    private int numRoutesSource;
    private int numRoutesDest;
    private int totalRoutes;

    public Airport(int id, int risk, String name, String city, String country, String iata, String icao, double latitude, double longitude, int altitude, double timezone, String dst, String timezoneString, String type, String source, int numRoutesSource, int numRoutesDest) {
        this.id = id;
        this.risk = risk;
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
        this.type = type;
        this.source = source;
        this.numRoutesSource = numRoutesSource;
        this.numRoutesDest = numRoutesDest;
        this.totalRoutes = this.numRoutesDest + this.numRoutesSource;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRisk() {
        return risk;
    }

    public void setRisk(int risk) {
        this.risk = risk;
    }

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

    public void setIata(String iata) {
        this.iata = iata;
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

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getAltitude() {
        return altitude;
    }

    public void setAltitude(int altitude) {
        this.altitude = altitude;
    }

    public double getTimezone() {
        return timezone;
    }

    public void setTimezone(double timezone) {
        this.timezone = timezone;
    }

    public String getDst() {
        return dst;
    }

    public void setDst(String dst) {
        this.dst = dst;
    }

    public String getTimezoneString() {
        return timezoneString;
    }

    public void setTimezoneString(String timezoneString) {
        this.timezoneString = timezoneString;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public int getNumRoutesSource() {
        return numRoutesSource;
    }

    public void setNumRoutesSource(int numRoutesSource) {
        this.numRoutesSource = numRoutesSource;
        setTotalRoutes();
    }

    public int getNumRoutesDest() {
        return numRoutesDest;
    }

    public void setNumRoutesDest(int numRoutesDest) {
        this.numRoutesDest = numRoutesDest;
        setTotalRoutes();
    }

    public int getTotalRoutes() {
        return totalRoutes;
    }

    public void setTotalRoutes() {
        this.totalRoutes = this.numRoutesDest + this.numRoutesSource;
    }

    /**
     * Placeholder until we've decided what the format should be for airports.
     */
    @Override
    public String toString() {
        return "Airport{" +
                "id=" + id +
                ", risk=" + risk +
                ", name='" + name + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", iata='" + iata + '\'' +
                ", icao='" + icao + '\'' +
                ", latitude='" + String.format("%.4f", latitude) + '\'' +
                ", longitude='" + String.format("%.4f", longitude) + '\'' +
                ", altitude=" + altitude + '\'' +
                ", timezone=" + timezone + '\'' +
                ", dst='" + dst + '\'' +
                ", timezoneString='" + timezoneString + '\'' +
                ", type='" + type + '\'' +
                ", source='" + source + '\'' +
                ", numRoutesSource=" + numRoutesSource +
                ", numRoutesDest=" + numRoutesDest +
                ", totalRoutes=" + totalRoutes +
                '}';
    }

    public void update(int id, int risk, String name, String city, String country, String iata, String icao, double latitude, double longitude, int altitude, int timezone, String dst, String timezoneString, String type, String source, int numRoutesSource, int numRoutesDest) {

        if (id != this.id) {
            setId(id);
        }
        if (risk != this.risk) {
            setRisk(risk);
        }
        if (!name.equals(this.name)) {
            setName(name);
        }
        if (!city.equals(this.city)) {
            setCity(city);
        }
        if (!country.equals(this.country)) {
            setCountry(country);
        }
        if (!iata.equals(this.iata)) {
            setIata(iata);
        }
        if (!icao.equals(this.icao)) {
            setIcao(icao);
        }
        if (latitude != this.latitude) {
            setLatitude(latitude);
        }
        if (longitude != this.longitude) {
            setLongitude(longitude);
        }
        if (altitude != this.altitude) {
            setAltitude(altitude);
        }
        if (timezone != this.timezone) {
            setTimezone(timezone);
        }
        if (!dst.equals(this.dst)) {
            setDst(dst);
        }
        if (!timezoneString.equals(this.timezoneString)) {
            setTimezoneString(timezoneString);
        }
        if (!type.equals(this.type)) {
            setType(type);
        }
        if (!source.equals(this.source)) {
            setSource(source);
        }
        if (numRoutesSource != this.numRoutesSource) {
            setNumRoutesSource(numRoutesSource);
        }
        if (numRoutesDest != this.numRoutesDest) {
            setNumRoutesDest(numRoutesDest);
        }
    }

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
     * Checks two airports for duplicates
     */
    public boolean equals(Airport otherAirport) {
        return ((this.id == otherAirport.getId())
                && (this.name.equals(otherAirport.getName()))
                && (this.city.equals(otherAirport.getCity()))
                && (this.country.equals(otherAirport.getCountry()))
                && (this.dst.equals(otherAirport.getDst()))
                && (this.timezoneString.equals(otherAirport.getTimezoneString()))
                && (this.iata.equals(otherAirport.getIata()))
                && (this.icao.equals(otherAirport.getIcao()))
                && (this.type.equals(otherAirport.getType()))
                && (this.source.equals(otherAirport.getSource()))
                && (this.latitude == otherAirport.getLatitude())
                && (this.longitude == otherAirport.getLongitude())
                && (this.altitude == otherAirport.getAltitude())
                && (this.timezone == otherAirport.getTimezone()));
    }

}
