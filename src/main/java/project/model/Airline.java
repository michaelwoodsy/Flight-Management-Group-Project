package project.model;

import java.util.Objects;

public class Airline {
    private int id;
    private String name;
    private boolean active;
    private String country;
    private String alias;
    private String callSign;
    private String iata;
    private String icao;

    public Airline(int id, String name, boolean active, String country, String alias, String callSign, String iata, String icao) {
        setId(id);
        setName(name);
        setActive(active);
        setCountry(country);
        setAlias(alias);
        setCallSign(callSign);
        setIata(iata);
        setIcao(icao);
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public void setCallSign(String callSign) {
        this.callSign = callSign;
    }

    public void setIata(String iata) {
        this.iata = iata;
    }

    public void setIcao(String icao) {
        this.icao = icao;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isActive() {
        return active;
    }

    public String getCountry() {
        return country;
    }

    public String getAlias() {
        return alias;
    }

    public String getCallSign() {
        return callSign;
    }

    public String getIata() {
        return iata;
    }

    public String getIcao() {
        return icao;
    }

    /**
     * Placeholder until we've decided what the format should be for airlines.
     */
    @Override
    public String toString() {
        String nameString;
        if (name == null) {
            nameString = "Unknown airline, ";
        } else {
            nameString = name + ", ";
        }

        String countryString;
        if (country == null) {
            countryString = "originating from an unknown country";
        } else {
            countryString = "originating from " + country;
        }

        return nameString + countryString;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Airline airline = (Airline) o;
        return getId() == airline.getId() &&
                isActive() == airline.isActive() &&
                Objects.equals(getName(), airline.getName()) &&
                Objects.equals(getCountry(), airline.getCountry()) &&
                Objects.equals(getAlias(), airline.getAlias()) &&
                Objects.equals(getCallSign(), airline.getCallSign()) &&
                Objects.equals(getIata(), airline.getIata()) &&
                Objects.equals(getIcao(), airline.getIcao());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), isActive(), getCountry(), getAlias(), getCallSign(), getIata(), getIcao());
    }
}
