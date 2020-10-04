package project.model;

/**
 * This is a class that implements Position.
 * This class is used for getting airport info for plotting on map in the JS file
 */
public class Position {
//  Used for getting airport info for plotting on map in the JS file
    public double lat;
    public double lng;
    public String name;
    public String iata;

    /**
     * @param lat A double representing the latitude of an Airport
     * @param lng A double representing the longitude of an Airport
     * @param name String representing name of Airport
     * @param iata String representing IATA code of Airport
     */
    public Position(double lat, double lng, String name, String iata) {
        this.lat = lat;
        this.lng = lng;
        this.name = name;
        this.iata = iata;
    }
}
