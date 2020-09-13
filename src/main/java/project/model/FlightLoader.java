package project.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class FlightLoader {
    public Flight loadFlightFile(String path) throws IOException {

        ArrayList<String> statuses = new ArrayList<String>();
        ArrayList<String> locations = new ArrayList<String>();
        ArrayList<Integer> altitudes = new ArrayList<Integer>();
        ArrayList<Double> latitudes = new ArrayList<Double>();
        ArrayList<Double> longitudes = new ArrayList<Double>();
        int risk = 0; // Placeholder

        BufferedReader dataReader = new BufferedReader(new FileReader(path));

        boolean breaker = false;
        while (!breaker) {
            String row = dataReader.readLine();
            if (row == null) {
                breaker = true;
            } else {
                String[] data = row.split(",");

                String status;
                try {
                    status = data[0];
                } catch (Exception e) {
                    status = null;
                }

                String location;
                try {
                    location = data[1];
                } catch (Exception e) {
                    location = null;
                }

                int altitude;
                try {
                    altitude = Integer.parseInt(data[2]);
                } catch (Exception e) {
                    altitude = -1;
                }

                double latitude;
                try {
                    latitude = Double.parseDouble(data[3]);
                } catch (Exception e) {
                    latitude = 360; // Error case, latitude can't be 360.
                }

                double longitude;
                try {
                    longitude = Double.parseDouble(data[3]);
                } catch (Exception e) {
                    longitude = 360; // Error case, longitude can't be 360.
                }

                statuses.add(status);
                locations.add(location);
                altitudes.add(altitude);
                latitudes.add(latitude);
                longitudes.add(longitude);
            }
        }
        dataReader.close();

        return new Flight(latitudes, longitudes, altitudes, locations, statuses, risk);
    }

    /**
     * Checks if loaded airport file is right format.
     */
    public boolean loadFlightErrorCheck(String path) throws IOException {

        BufferedReader dataReader = new BufferedReader(new FileReader(path));

        String row = dataReader.readLine();
        if (row == null) {
            return false;
        } else {
            String[] data = row.split(",");
            if (data.length == 5) {
                return true;
            }
        }

        dataReader.close();
        return false;
    }
}
