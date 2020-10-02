package project.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AirportLocations {
    private List<Position> routeLocations = new ArrayList<>();

    public AirportLocations(Position ...points) {
        Collections.addAll(routeLocations, points);
    }

    public void addAirports(Record currentRecord) {
        //loops through all airports, creates a position and adds to a list
        for(Airport location:currentRecord.getAirportList()) {
            this.routeLocations.add(new Position(location.getLatitude(), location.getLongitude(), location.getName(), location.getIata()));
        }
    }




    public String toJSONArray() {
        //Used for putting each airport lat, lon, name, iata into a javascript array format, returns a string with javascript array formatted array
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        routeLocations.forEach(pos -> stringBuilder.append(
                String.format("{lat: %f, lng: %f, title: (\"%s (%s)\") }, ", pos.lat, pos.lng, pos.name, pos.iata)));
        stringBuilder.append("]");
        return stringBuilder.toString();
    }
}
