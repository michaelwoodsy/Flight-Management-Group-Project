package project.controller;

import com.sun.glass.ui.CommonDialogs;
import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import project.model.*;

import java.io.File;
import java.io.IOException;
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
    @FXML
    private TabPane newTab;
    @FXML
    private ToggleGroup selectFile;
    @FXML
    private RadioButton airportRadioButton;
    @FXML
    private RadioButton airlineRadioButton;
    @FXML
    private RadioButton routeRadioButton;
    @FXML
    private RadioButton flightRadioButton;
    @FXML
    private RadioButton covidRadioButton;

    private Record record = Database.generateRecord();
    private boolean optedIn = false;
    private Loader loader = new Loader();

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

    @FXML
    /**
     * Can't handle errors yet, and doesn't have the option to append data to new record yet.
     * Also doesn't have confirmation on when files are successfully loaded.
     */
    public void addButton(ActionEvent event) throws IOException {

        if (newTab.getSelectionModel().getSelectedIndex() == 3) {
            FileChooser loadFile = new FileChooser();
            loadFile.getExtensionFilters().add(new FileChooser.ExtensionFilter("DAT Files", "*.dat"));
            loadFile.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));

            File file = loadFile.showOpenDialog(null);


            if (file != null) {
                if (selectFile.getSelectedToggle() == airportRadioButton) {
                    ArrayList<Airport> newAirportList = loader.loadAirportFile(file.getAbsolutePath());
                    record.addAirports(newAirportList);
                } else if (selectFile.getSelectedToggle() == airlineRadioButton) {
                    ArrayList<Airline> newAirlineList = loader.loadAirlineFile(file.getAbsolutePath());
                    record.addAirlines(newAirlineList);
                } else if (selectFile.getSelectedToggle() == routeRadioButton) {
                    ArrayList<Route> newRouteList = loader.loadRouteFile(file.getAbsolutePath());
                    record.addRoutes(newRouteList);
                } else if (selectFile.getSelectedToggle() == flightRadioButton) {
                    Flight newFlight = loader.loadFlightFile(file.getAbsolutePath());
                    record.addFlights(newFlight);
                } else {
                    ArrayList<Covid> newCovidList = loader.loadCovidFile(file.getAbsolutePath());
                    record.addCovid(newCovidList);
                }
            }
        }

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
