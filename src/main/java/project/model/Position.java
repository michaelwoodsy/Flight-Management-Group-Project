package project.model;

public class Position {
//  Used for getting airport info for plotting on map in the JS file
    public double lat;
    public double lng;
    public String name;
    public String iata;

    public Position(double lat, double lng, String name, String iata) {
        this.lat = lat;
        this.lng = lng;
        this.name = name;
        this.iata = iata;
    }
}
