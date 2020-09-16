package project.model;

import java.util.Objects;

public class Route {
    private String airline;
    private int id;
    private String sourceAirport;
    private int sourceID;
    private String destAirport;
    private int destID;
    private int numStops;
    private String equipment;
    private boolean codeshare;

    public Route(String airline, int id, String sourceAirport, int sourceID, String destAirport, int destID, int numStops, String equipment, boolean codeshare) {
            setAirline(airline);
            setId(id);
            setSourceAirport(sourceAirport);
            setSourceID(sourceID);
            setDestAirport(destAirport);
            setDestID(destID);
            setNumStops(numStops);
            setEquipment(equipment);
            setCodeshare(codeshare);

    }

    public String getAirline() {
        return airline;
    }

    public void setAirline(String airline) {
        this.airline = airline;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSourceAirport() {
        return sourceAirport;
    }

    public void setSourceAirport(String sourceAirport) {
        this.sourceAirport = sourceAirport;
    }

    public int getSourceID() {
        return sourceID;
    }

    public void setSourceID(int sourceID) {
        this.sourceID = sourceID;
    }

    public String getDestAirport() {
        return destAirport;
    }

    public void setDestAirport(String destAirport) {
        this.destAirport = destAirport;
    }

    public int getDestID() {
        return destID;
    }

    public void setDestID(int destID) {
        this.destID = destID;
    }

    public int getNumStops() {
        return numStops;
    }

    public void setNumStops(int numStops) {
        this.numStops = numStops;
    }

    public String getEquipment() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }

    public boolean isCodeshare() {
        return codeshare;
    }

    public void setCodeshare(boolean codeshare) {
        this.codeshare = codeshare;
    }

    /**
     * Placeholder until we've decided what the format should be for routes.
     */
    @Override
    public String toString() {
        String sourceString;
        if (sourceAirport == null) {
            sourceString = "an unknown source airport ";
        } else {
            sourceString = "source airport " + sourceAirport + " ";
        }

        String destString;
        if (destAirport == null) {
            destString = "to an unknown destination airport ";
        } else {
            destString = "to destination airport " + destAirport + " ";
        }

        String airlineString;
        if (airline == null) {
            airlineString = "through an unknown airline";
        } else {
            airlineString = "through airline " + airline;
        }

        return "Route from " + sourceString + destString + airlineString;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Route route = (Route) o;
        return getId() == route.getId() &&
                getSourceID() == route.getSourceID() &&
                getDestID() == route.getDestID() &&
                getNumStops() == route.getNumStops() &&
                isCodeshare() == route.isCodeshare() &&
                Objects.equals(getAirline(), route.getAirline()) &&
                Objects.equals(getSourceAirport(), route.getSourceAirport()) &&
                Objects.equals(getDestAirport(), route.getDestAirport()) &&
                Objects.equals(getEquipment(), route.getEquipment());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAirline(), getId(), getSourceAirport(), getSourceID(), getDestAirport(), getDestID(), getNumStops(), getEquipment(), isCodeshare());
    }
}
