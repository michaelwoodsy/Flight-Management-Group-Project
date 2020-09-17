package project.model;

import java.util.ArrayList;
import java.util.Objects;

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

    /**
     * First code displayed in locations array (source ICAO airport code).
     */
    private String source;

    /**
     * Last code displayed in locations array (destination ICAO airport code).
     */
    private String dest;

    /**
     * Constructor class for a flight
     */
    public Flight(ArrayList<Double> latitudes, ArrayList<Double> longitudes, ArrayList<Integer> altitudes, ArrayList<String> locations, ArrayList<String> status, int risk) {
        this.latitudes = latitudes;
        this.longitudes = longitudes;
        this.altitudes = altitudes;
        this.locations = locations;
        this.status = status;
        this.risk = risk;
        this.source = locations.get(0);
        this.dest = locations.get(locations.size() - 1);
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
        this.source = locations.get(0);
        this.dest = locations.get(locations.size() - 1);
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

    public String getSource() {
        return source;
    }

    public String getDest() {
        return dest;
    }

    /**
     * Returns the flight "name" for use in the GUI flight choicebox.
     * Includes hashcode to differentiate flights which take different routes to the same destination.
     * @return flight "name" string, acts more as a descriptor, based on the flight's source and destination codes + hashcode.
     */
    public String flightName() {
        return this.source + " to " + this.dest + " (Code: "+ this.hashCode() + ")";
    }

    /**
     * Returns a list of simplified flight details, for use in flight ListView in GUI.
     * Rounds latitude and longitude to 5 decimal places (accurate to about 1m) for viewing convenience.
     * @return ArrayList of string descriptors for each point in the aircraft's flight (at each index)
     */
    public ArrayList<String> getStrings() {
        int index = 0;
        ArrayList<String> data = new ArrayList<String>();
        while (index < latitudes.size()) {
            String strLat = String.format("%.5f", latitudes.get(index));
            String strLon = String.format("%.5f", longitudes.get(index));
            data.add("At point " + (index + 1) + " in its flight, aircraft has: Altitude " + altitudes.get(index) + " ft, Latitude " + strLat + "°, Longitude " + strLon + "°");
            index += 1;
        }
        return data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Flight flight = (Flight) o;
        return Objects.equals(getLatitudes(), flight.getLatitudes()) &&
                Objects.equals(getLongitudes(), flight.getLongitudes()) &&
                Objects.equals(getAltitudes(), flight.getAltitudes()) &&
                Objects.equals(getLocations(), flight.getLocations()) &&
                Objects.equals(getStatus(), flight.getStatus());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLatitudes(), getLongitudes(), getAltitudes(), getLocations(), getStatus());
    }
}
