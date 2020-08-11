package project.model;

import java.util.ArrayList;

public class Flight extends Data {

    /**
     * ArrayList containing the latitudes at certain points during the flight
     */
    private ArrayList<Float> latitudes;
    /**
     * ArrayList containing the longitudes at certain points during the flight
     */
    private ArrayList<Float> longitudes;
    /**
     * ArrayList containing the altitudes at certain points during the flight
     */
    private ArrayList<Float> altitudes;
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

    public Flight(ArrayList<Float> latitudes, ArrayList<Float> longitudes, ArrayList<Float> altitudes, ArrayList<String> locations, String status, int risk) {}

    public void setAltitudes(ArrayList<Float> altitudes) {
        this.altitudes = altitudes;
    }

    public void setLatitudes(ArrayList<Float> latitudes) {
        this.latitudes = latitudes;
    }

    public void setLongitudes(ArrayList<Float> longitudes) {
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

    public ArrayList<Float> getLatitudes() {
        return latitudes;
    }

    public ArrayList<Float> getLongitudes() {
        return longitudes;
    }

    public ArrayList<Float> getAltitudes() {
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

    public String print(int pointInFlight) {
        if (pointInFlight > altitudes.size()) {
            return ("Provided int not within bounds");
        } else {
            float altitude = altitudes.get(pointInFlight);
            float lat = latitudes.get(pointInFlight);
            float lon = longitudes.get(pointInFlight);
            return (String.format("At point %d in the flight, the plane had an altitude of %f, and was located at coordinates %f, %f", altitude, lat, lon));
        }
    }

    public void update() {}

}
