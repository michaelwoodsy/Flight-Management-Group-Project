package project.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class that implements Airport Location.
 * This class is used for getting airport info for plotting on map in the JS file
 * and getting route info for plotting on the map in the JS file
 */
public class AirportLocations {
    private List<Position> routeLocations = new ArrayList<>();

    /**
     * @param points Position Object representing the location of an airport
     */
    public AirportLocations(Position ...points) {
        Collections.addAll(routeLocations, points);
    }

    /**
     * This is a method that determines the route location using each airport
     * @param currentRecord Record Object representing the record that is used currently for the map
     */
    public void addAirports(Record currentRecord) {
        //loops through all airports, creates a position and adds to a list
        for(Airport location:currentRecord.getAirportList()) {
            this.routeLocations.add(new Position(location.getLatitude(), location.getLongitude(), location.getName(), location.getIata()));
        }
    }

    /**
     * This method is used for putting used for putting each airport lat, lon, name,
     * iata into a javascript array format, returns a string with javascript array formatted array
     * @return String representing an array used for javascript
     */
    public String toJSONArray() {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        routeLocations.forEach(pos -> stringBuilder.append(
                String.format("{lat: %f, lng: %f, title: (\"%s (%s)\") }, ", pos.lat, pos.lng, pos.name, pos.iata)));
        stringBuilder.append("]");
        return stringBuilder.toString();
    }
}
