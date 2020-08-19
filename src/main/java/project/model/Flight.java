package project.model;

import java.util.ArrayList;

public class Flight {

    /**
     * ArrayList containing the latitudes at certain points during the flight
     */
    private ArrayList<Double> latitudes;
    /**
     * ArrayList containing the longitudes at certain points during the flight
     */
    private ArrayList<Double> longitudes;
    /**
     * ArrayList containing the altitudes at certain points during the flight
     */
    private ArrayList<Integer> altitudes;
    /**
     * ArrayList containing the nearest locations at certain points during the flight
     */
    private ArrayList<String> locations;
    /**
     * A string detailing the status of the flight at any point
     */
    private ArrayList<String> status;
    /**
     * An integer displaying the percentage COVID risk that the flight holds
     */
    private int risk;

    public Flight(ArrayList<Double> latitudes, ArrayList<Double> longitudes, ArrayList<Integer> altitudes, ArrayList<String> locations, ArrayList<String> status, int risk) {
        this.latitudes = latitudes;
        this.longitudes = longitudes;
        this.altitudes = altitudes;
        this.locations = locations;
        this.status = status;
        this.risk = risk;
    }

    public void setAltitudes(ArrayList<Integer> altitudes) {
        this.altitudes = altitudes;
    }

    public void setLatitudes(ArrayList<Double> latitudes) {
        this.latitudes = latitudes;
    }

    public void setLongitudes(ArrayList<Double> longitudes) {
        this.longitudes = longitudes;
    }

    public void setLocations(ArrayList<String> locations) {
        this.locations = locations;
    }

    public void setRisk(int risk) {
        this.risk = risk;
    }

    public void setStatus(ArrayList<String> status) {
        this.status = status;
    }

    public ArrayList<Double> getLatitudes() {
        return latitudes;
    }

    public ArrayList<Double> getLongitudes() {
        return longitudes;
    }

    public ArrayList<Integer> getAltitudes() {
        return altitudes;
    }

    public ArrayList<String> getLocations() {
        return locations;
    }

    public ArrayList<String> getStatus() {
        return status;
    }

    public int getRisk() {
        return risk;
    }

    /**
     * Placeholder until we've decided what the format should be for flights.
     */
    public String print(int pointInFlight) {
        if (pointInFlight > altitudes.size()) {
            return ("Provided int not within bounds");
        } else {
            int altitude = altitudes.get(pointInFlight);
            double lat = latitudes.get(pointInFlight);
            double lon = longitudes.get(pointInFlight);
            return (String.format("At point %d in the flight, the plane had an altitude of %f, and was located at coordinates %f, %f", altitude, lat, lon));
        }
    }

    /**
     * Placeholder function for update
     */
    public void update() {
        risk = 0;
    }

}
