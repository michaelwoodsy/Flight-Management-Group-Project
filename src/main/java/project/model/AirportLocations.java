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
        for(Airport location:currentRecord.getAirportList()) {
            this.routeLocations.add(new Position(location.getLatitude(), location.getLongitude()));
        }
    }



    public String toJSONArray() {
        System.out.println("json");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        routeLocations.forEach(pos -> stringBuilder.append(
                String.format("{lat: %f, lng: %f}, ", pos.lat, pos.lng)));
        stringBuilder.append("]");
        return stringBuilder.toString();
    }
}
