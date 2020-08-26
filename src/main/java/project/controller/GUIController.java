package project.controller;

import javafx.beans.InvalidationListener;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import project.model.*;

import java.util.*;

import static javafx.collections.FXCollections.observableArrayList;

/**
 * Class for working in while experimenting with GUI features
 */
public class GUIController {

    @FXML
    private ListView airportList;
    @FXML
    private ListView airportDetailList;
    @FXML
    private TextField airportSearchCriteria;
    @FXML
    private ChoiceBox airportSortBy;
    @FXML
    private ChoiceBox airlineSortBy;
    @FXML
    private ChoiceBox airlineSearchBy;
    @FXML
    private ChoiceBox routeSearchBy;
    @FXML
    private ChoiceBox routeSortBy;

    private Record record = Database.generateRecord();
    private boolean optedIn = false;

    /**
     * Retrieves user input and searches for airports that are located within that country.
     * Displays only those airports in the airport data viewer.
     * If input is blank, display an error pop-up.
     */
    public void filterAirports() {
        String country = airportSearchCriteria.getText().toLowerCase();
        if (country == "") {
            System.err.println("No country entered");
        }
        ArrayList<Airport> filteredAirports = record.filterAirports(country);
        airportList.setItems(observableArrayList(filteredAirports));
    }

    /**
     * Makes the airport data viewer display every airport in the current record.
     */
    public void displayAllAirports() {
        airportList.setItems(observableArrayList(record.getAirportList()));
    }

    public void sortCurrentData() {}

    /**
     * Populate every choiceBox with the relevant choices for the user.
     */
    public void setUpChoices() {
        ObservableList<String> sortStrings = observableArrayList("Most Popular", "Least Popular");
        airportSortBy.setItems(sortStrings);
        routeSortBy.setItems(sortStrings);
        airlineSortBy.setItems(sortStrings);
        airportSortBy.setValue("Most Popular");
        airlineSortBy.setValue("Most Popular");
        routeSortBy.setValue("Most Popular");

        airlineSearchBy.setItems(observableArrayList("Country of Origin", "Active", "Inactive"));
        routeSearchBy.setItems(observableArrayList("Destination", "Departure Location", "Equipment"));
    }

    public void additionalAirportInfo() {
        ObservableList chosenAirport = airportList.getSelectionModel().getSelectedItems();
        if (chosenAirport.size() != 1) {
            Airport airport = (Airport) chosenAirport.get(0);
            String name = "Name: " + airport.getName();
            String city = String.format("Location: %s, %s", airport.getCity(), airport.getCountry());
            String risk = String.format("COVID risk: %d", airport.getRisk());
            String numRoutes = String.format("A total of %d flights go through this airport", airport.getTotalRoutes());
            String timezone = String.format("Timezone: %s", airport.getTimezoneString());
            airportDetailList.setItems(observableArrayList(name, city, risk, numRoutes));
            if (optedIn) {
                String lat = String.format("Latitude: %d", airport.getLatitude());
                String lon = String.format("Longitude: %d", airport.getLongitude());
                String iata = String.format("IATA code: %s", airport.getIata());
                String icao = String.format("ICAO code: %s", airport.getIcao());
                String alt = String.format("Airport's altitude: %s", airport.getAltitude());
                airportDetailList.getItems().addAll(lat, lon, iata, icao, alt);
            }
        }
    }


}
