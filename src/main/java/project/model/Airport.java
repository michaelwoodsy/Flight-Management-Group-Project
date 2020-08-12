package project.model;

public class Airport {
    private int id;
    private int risk;
    private String name;
    private String country;
    private String iata;
    private String icao;
    private int altitude;
    private double timezone;
    private String dst;
    private String timezoneString;
    private String type;
    private String source;
    private int numRoutes;

    public Airport(int id, int risk, String name, String country, String iata, String icao, int altitude, double timezone, String dst, String timezoneString, String type, String source, int numRoutes) {
        setId(id);
        setRisk(risk);
        setName(name);
        setCountry(country);
        setIata(iata);
        setIcao(icao);
        setAltitude(altitude);
        setTimezone(timezone);
        setDst(dst);
        setTimezoneString(timezoneString);
        setType(type);
        setSource(source);
        setNumRoutes(numRoutes);
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

    public int getNumRoutes() {
        return numRoutes;
    }

    public void setNumRoutes(int numRoutes) {
        this.numRoutes = numRoutes;
    }

    /**
     * Placeholder until we've decided what the format should be for airports.
     */
    public String print() {
        return "Test";
    }

    public void update(int id, int risk, String name, String country, String iata, String icao, int altitude, double timezone, String dst, String timezoneString, String type, String source, int numRoutes) {

        if (id != this.id) {
            setId(id);
        }
        if (risk != this.risk) {
            setRisk(risk);
        }
        if (!name.equals(this.name)) {
            setName(name);
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
        if (numRoutes != this.numRoutes) {
            setNumRoutes(numRoutes);
        }
    }


}
