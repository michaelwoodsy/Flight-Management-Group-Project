package project.model;

public class routeLoader {
    /**
     * Returns a route class from a line read in routes.dat.
     * Invalid ints will be displayed as -1, invalid strings displayed as null
     */
    public Route loadRoute(String[] routeData) {

        String airline;
        try {
            airline = routeData[0].replaceAll("\"", "").replace("\\\\", "");
            if (airline.equals("\\N") || airline.equals("")) {
                airline = null;
            }
        } catch (Exception e) {
            airline = null;
        }

        int id;
        try {
            id = Integer.parseInt(routeData[1]);
        } catch (Exception e) {
            id = -1;
        }

        String sourceAirport;
        try {
            sourceAirport = routeData[2].replaceAll("\"", "").replace("\\\\", "");
            if (sourceAirport.equals("\\N") || sourceAirport.equals("") || !sourceAirport.matches("[a-zA-Z0-9]*")) {
                sourceAirport = null;
            }
        } catch (Exception e) {
            sourceAirport = null;
        }

        int sourceID;
        try {
            sourceID = Integer.parseInt(routeData[3]);
        } catch (Exception e) {
            sourceID = -1;
        }

        String destAirport;
        try {
            destAirport = routeData[4].replaceAll("\"", "").replace("\\\\", "");
            if (destAirport.equals("\\N") || destAirport.equals("") || !destAirport.matches("[a-zA-Z0-9]*")) {
                destAirport = null;
            }
        } catch (Exception e) {
            destAirport = null;
        }

        int destID;
        try {
            destID = Integer.parseInt(routeData[5]);
        } catch (Exception e) {
            destID = -1;
        }

        String codeshareString;
        try {
            codeshareString = routeData[6].replaceAll("\"", "").replace("\\\\", "");
            if (codeshareString.equals("\\N") || codeshareString.equals("")) {
                codeshareString = "N";
            }
        } catch (Exception e) {
            codeshareString = "N";
        }

        int numStops;
        try {
            numStops = Integer.parseInt(routeData[7]);
        } catch (Exception e) {
            numStops = -1;
        }

        String equipment;
        try {
            equipment = routeData[8].replaceAll("\"", "").replace("\\\\", "");
            if (equipment.equals("\\N") || equipment.equals("")) {
                equipment = null;
            }
        } catch (Exception e) {
            equipment = null;
        }

        boolean codeshare;
        codeshare = codeshareString.equals("Y");

        return new Route(airline, id, sourceAirport, sourceID, destAirport, destID, numStops, equipment, codeshare);

    }
}
