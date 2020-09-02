package project.controller;

import com.sun.glass.ui.CommonDialogs;
import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import project.model.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.*;

import static javafx.collections.FXCollections.observableArrayList;

/**
 * Class for working in while experimenting with GUI features
 */
public class GUIController implements Initializable {

    @FXML
    private ListView airportList;
    @FXML
    private ListView airlineList;
    @FXML
    private ListView routeList;
    @FXML
    private ListView airlineDetailList;
    @FXML
    private ListView airportDetailList;
    @FXML
    private ListView routeDetailList;
    @FXML
    private TextField airportFilterCriteria;
    @FXML
    private TextField airlineFilterCriteria;
    @FXML
    private TextField airportSearchCriteria;
    @FXML
    private TextField airlineSearchCriteria;
    @FXML
    private TextField routeFilterCriteria;
    @FXML
    private ChoiceBox airlineSearchBy;
    @FXML
    private ChoiceBox routeSearchBy;
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
    private ChoiceBox airlineFilterBy;
    @FXML
    private ChoiceBox airportFilterBy;
    @FXML
    private ChoiceBox routeFilterBy;
    @FXML
    private ChoiceBox airportSearchBy;
    @FXML
    private CheckBox hobbyCheckBox;
    @FXML
    private CheckBox airlineActiveBox;
    @FXML
    private CheckBox airlineInactiveBox;
    @FXML
    private CheckBox routeDirectBox;
    @FXML
    private CheckBox routeIndirectBox;


    private Record record = Database.generateRecord();
    private boolean optedIn = false;
    private Loader loader = new Loader();
    private List<Airline> defaultAirlineList = new ArrayList<Airline>();
    private List<Route> defaultRouteList = new ArrayList<Route>();

    /**
     * Stuff to do on setup
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        ObservableList<String> filterAirlines = observableArrayList("Countries");
        ObservableList<String> searchAirlines = observableArrayList("Name", "Alias", "Callsign", "IATA", "ICAO");

        airlineFilterBy.setItems(filterAirlines);
        airlineSearchBy.setItems(searchAirlines);

        ObservableList<String> filterAirports = observableArrayList("Countries");
        ObservableList<String> searchAirports = observableArrayList("Name", "City", "IATA", "ICAO", "Timezone", "Total # Routes");

        airportFilterBy.setItems(filterAirports);
        airportSearchBy.setItems(searchAirports);

        ObservableList<String> filterRoutes = observableArrayList("Source Airport", "Destination Airport", "Equipment");
        ObservableList<String> searchRoutes = observableArrayList("Airline", "Total # Stops", "Source ID", "Destination ID");

        routeFilterBy.setItems(filterRoutes);
        routeSearchBy.setItems(searchRoutes);

        routeSearchBy.getSelectionModel().selectFirst();
        routeFilterBy.getSelectionModel().selectFirst();
        airportSearchBy.getSelectionModel().selectFirst();
        airportFilterBy.getSelectionModel().selectFirst();
        airlineSearchBy.getSelectionModel().selectFirst();
        airlineFilterBy.getSelectionModel().selectFirst();

    }

    @FXML
    /**
     * Retrieves user input and searches for airports that are located within that country.
     * Displays only those airports in the airport data viewer.
     * If input is blank, display an error pop-up.
     */
    public void filterAirports() {
        String country = airportFilterCriteria.getText();
        if (country.isBlank()) {
            System.err.println("No country entered");
        }
        ArrayList<Airport> filteredAirports = record.filterAirports(country);
        airportList.setItems(observableArrayList(filteredAirports));
    }

    @FXML
    /**
     * Retrieves user input and searches for airlines that are located within that country.
     * Displays only those airlines in the airlines data viewer.
     * If input is blank, display an error pop-up.
     */
    public void filterAirlines() {
        airlineActiveBox.setSelected(true);
        airlineInactiveBox.setSelected(true);

        String country = airlineFilterCriteria.getText();
        if (country.isBlank()) {
            System.err.println("No country entered");
        }
        ArrayList<Airline> filteredAirlines = record.filterAirlinesCountry(country);
        airlineList.setItems(observableArrayList(filteredAirlines));
        defaultAirlineList = airlineList.getItems();
    }

    @FXML
    public void leastRoutesButton(ActionEvent event) throws IOException {
        List<Airport> currentData = airportList.getItems();
        List<Airport> rankedAirports = record.rankAirports(false, currentData);
        airportList.setItems(observableArrayList(rankedAirports));
    }

    @FXML
    public void mostRoutesButton(ActionEvent event) throws IOException {
        List<Airport> currentData = airportList.getItems();
        List<Airport> rankedAirports = record.rankAirports(true, currentData);
        airportList.setItems(observableArrayList(rankedAirports));
    }

    @FXML
    public void filterActiveAirlines(ActionEvent event) throws IOException {

        if (airlineActiveBox.isSelected()) {
            List<Airline> filteredAirlines = record.filterAirlines(true, defaultAirlineList);
            if (airlineInactiveBox.isSelected()) {
                airlineList.setItems(observableArrayList(defaultAirlineList));
            } else {
                airlineList.setItems(observableArrayList(filteredAirlines));
            }
        } else {
            if (airlineInactiveBox.isSelected()) {
                List<Airline>filteredAirlines = record.filterAirlines(false, defaultAirlineList);
                airlineList.setItems(observableArrayList(filteredAirlines));
            } else {
                airlineList.setItems(observableArrayList(new ArrayList<Airline>()));
            }
        }
    }

    @FXML
    public void filterRouteStops(ActionEvent event) throws IOException {

        if (routeDirectBox.isSelected()) {
            List<Route> filteredRoutes = record.filterRoutesStops(true, defaultRouteList);
            if (routeIndirectBox.isSelected()) {
                routeList.setItems(observableArrayList(defaultRouteList));
            } else {
                routeList.setItems(observableArrayList(filteredRoutes));
            }
        } else {
            if (routeIndirectBox.isSelected()) {
                List<Route> filteredRoutes = record.filterRoutesStops(false, defaultRouteList);
                routeList.setItems(observableArrayList(filteredRoutes));
            } else {
                routeList.setItems(observableArrayList(new ArrayList<Route>()));
            }
        }
    }

    @FXML
    public void filterRoutes(ActionEvent event) throws IOException {

        routeDirectBox.setSelected(true);
        routeIndirectBox.setSelected(true);

        if (routeFilterBy.getValue().equals("Source Airport")) {
            String departure = routeFilterCriteria.getText();
            if (departure.isBlank()) {
                System.err.println("No source airport code entered");
            }
            ArrayList<Route> filteredRoutes = record.filterRoutesDeparture(departure);
            routeList.setItems(observableArrayList(filteredRoutes));
            defaultRouteList = routeList.getItems();
        } else if (routeFilterBy.getValue().equals("Destination Airport")) {
            String destination = routeFilterCriteria.getText();
            if (destination.isBlank()) {
                System.err.println("No destination airport code entered");
            }
            ArrayList<Route> filteredRoutes = record.filterRoutesDestination(destination);
            routeList.setItems(observableArrayList(filteredRoutes));
            defaultRouteList = routeList.getItems();
        } else if (routeFilterBy.getValue().equals("Equipment")) {
            String equipment = routeFilterCriteria.getText();
            if (equipment.isBlank()) {
                System.err.println("No equipment entered");
            }
            ArrayList<Route> filteredRoutes = record.filterRoutesEquipment(equipment);
            routeList.setItems(observableArrayList(filteredRoutes));
            defaultRouteList = routeList.getItems();
        }
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
                    record.addAirports(newAirportList);;
                } else if (selectFile.getSelectedToggle() == airlineRadioButton) {
                    ArrayList<Airline> newAirlineList = loader.loadAirlineFile(file.getAbsolutePath());
                    record.addAirlines(newAirlineList);
                } else if (selectFile.getSelectedToggle() == routeRadioButton) {
                    ArrayList<Route> newRouteList = loader.loadRouteFile(file.getAbsolutePath());
                    record.addRoutes(newRouteList);
                } else if (selectFile.getSelectedToggle() == flightRadioButton) {
                    Flight newFlight = loader.loadFlightFile(file.getAbsolutePath());
                    record.addFlights(newFlight);
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

    public void displayAllAirlines() {
        airlineList.setItems(observableArrayList(record.getAirlineList()));
        airlineActiveBox.setSelected(true);
        airlineInactiveBox.setSelected(true);
        defaultAirlineList = airlineList.getItems();
    }


    public void displayAllRoutes() {
        routeList.setItems(observableArrayList(record.getRouteList()));
        routeDirectBox.setSelected(true);
        routeIndirectBox.setSelected(true);
        defaultRouteList = routeList.getItems();
    }

    @FXML
    public void setOptedIn() {
        if (hobbyCheckBox.isSelected()) {
            optedIn = true;
        } else {
            optedIn = false;
        }
    }

    /**
     * Extracts the selected airport from the current data in the data viewer, and displays its additional
     * data in the appropriate panel. Displays additional information if the user has opted in.
     */
    public void additionalAirportInfo() {
        Airport airport = (Airport) airportList.getSelectionModel().getSelectedItem();
        if (airport != null) {
            String name = ("Name: " + airport.getName());
            String city = String.format("Location: %s, %s", airport.getCity(), airport.getCountry());
            String risk = String.format("COVID risk level: %d", airport.getRisk());

            String numRoutes;
            if (airport.getTotalRoutes() < 1) {
                numRoutes = String.format("Warning: No flight routes go through this airport", airport.getTotalRoutes());
            } else {
                numRoutes = String.format("A total of %d flight routes go through this airport", airport.getTotalRoutes());
            }

            String timezoneNum = "" + airport.getTimezone();
            if (airport.getTimezone() >= 0) {
                timezoneNum = "+" + airport.getTimezone();
            }
            String timezone = String.format("Timezone: %s, ", airport.getTimezoneString()) + timezoneNum + " hours";

            airportDetailList.setItems(observableArrayList(name, city, risk, numRoutes, timezone));
            if (optedIn) {
                String lat = ("Latitude: " + airport.getLatitude() + " decimal degrees");
                String lon = ("Longitude: " + airport.getLongitude() + " decimal degrees");

                String iata;
                if (airport.getIata() == null || airport.getIata().equals("\\N") || airport.getIata().equals("")) {
                    iata = ("IATA code: Unknown");
                } else {
                    iata = String.format("IATA code: %s", airport.getIata());
                }

                String icao;
                if (airport.getIcao() == null || airport.getIcao().equals("\\N") || airport.getIcao().equals("")) {
                    icao = ("ICAO code: Unknown");
                } else {
                    icao = String.format("ICAO code: %s", airport.getIcao());
                }

                String alt = ("Altitude: " + airport.getAltitude() + " feet");
                airportDetailList.getItems().addAll(icao, iata, lat, lon, alt);
            }
        }
    }

    /**
     * Displays additional information about a selected airline in the detail panel.
     * If the user has opted in, displays further additional information
     */
    public void additionalAirlineInfo() {
        Airline airline = (Airline) airlineList.getSelectionModel().getSelectedItem();
        if (airline != null) {
            String activeStatus = "";
            if (!airline.isActive()) {
                activeStatus = " not";
            }
            String name = String.format("Name: %s", airline.getName());
            String country = String.format("Country of Origin: %s", airline.getCountry());
            String active = String.format("This Airline is currently%s in operation", activeStatus);

            String alias;
            if (airline.getAlias() == null || airline.getAlias().equals("")) {
                alias = String.format("This airline has no known aliases");
            } else {
                alias = String.format("Airline known as: %s", airline.getAlias());
            }
            airlineDetailList.setItems(observableArrayList(name, country, active, alias));
            if (optedIn) {
                String iata = String.format("IATA code: %s", airline.getIata());
                String icao = String.format("ICAO code: %s", airline.getIcao());
                String callsign = String.format("Callsign: %s", airline.getCallSign());
                airlineDetailList.getItems().addAll(iata, icao, callsign);
            }
        }
    }

    public void additionalRouteInfo() {
        Route route = (Route) routeList.getSelectionModel().getSelectedItem();

        String airline = ("Airline Code: " + route.getAirline());
        String numStops = ("Number of stops: " + route.getNumStops());
        routeDetailList.setItems(observableArrayList(airline, numStops));
        if (optedIn) {
            String sourceAirport = ("Source Airport Code: " + route.getSourceAirport());
            String destAirport = ("Destination Airport Code: " + route.getDestAirport());
            String equipment = ("Equipment: " + route.getEquipment());
            String codeshare;
            if (route.isCodeshare()) {
                codeshare = "This flight is a codeshare";
            } else {
                codeshare = "This flight is not a codeshare";
            }
            routeDetailList.getItems().addAll(sourceAirport, destAirport, equipment, codeshare);
        }

    }

    /**
     * Retrieves the user's inputs, and searches for airports that match the provided criteria.
     * Does nothing if the textfield is empty.
     */
    public void searchForAirport() {
        String searchCategory = (String) airportSearchBy.getSelectionModel().getSelectedItem();
        String searchCriteria = airportSearchCriteria.getText().toLowerCase();
        if (!searchCriteria.isBlank()) {
            List<Airport> matchingAirports = record.searchAirports(searchCriteria, searchCategory);
            airportList.setItems((ObservableList) matchingAirports);
        }
    }

    /**
     * Retrieves the user's inputs, and searches for airlines that match the provided criteria in the selected attribute.
     * Does nothing if the textfield is empty.
     */
    public void searchForAirline() {
        String searchCategory = (String) airlineSearchBy.getSelectionModel().getSelectedItem();
        String searchCriteria = airlineSearchCriteria.getText().toLowerCase();
        if (!searchCriteria.isBlank()) {
            List<Airline> matchingAirlines = record.searchAirlines(searchCriteria, searchCategory);
            airlineList.setItems((ObservableList) matchingAirlines);
        }
    }

}
