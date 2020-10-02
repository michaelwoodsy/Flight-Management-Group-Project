package project.controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import project.model.*;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;
import java.util.List;

import org.json.simple.JSONArray;

import javax.naming.Binding;

import static javafx.collections.FXCollections.observableArrayList;

/**
 * Contains all functionality for the GUI screen.
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
    private TextField airportSearchCriteria;
    @FXML
    private TextField airlineSearchCriteria;
    @FXML
    private TextField routeSearchCriteria;
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
    private ChoiceBox recordDropdown;
    @FXML
    private ChoiceBox airportSearchBy;
    @FXML
    private CheckBox airlineActiveBox;
    @FXML
    private CheckBox airlineInactiveBox;
    @FXML
    private CheckBox routeDirectBox;
    @FXML
    private CheckBox routeIndirectBox;
    @FXML
    private CheckBox extraInfoBox;
    @FXML
    private WebView OpenFlightsWebView;
    @FXML
    private WebView CovidDataWebView;
    @FXML
    private WebView mapView;
    @FXML
    private TextField airportName;
    @FXML
    private TextField airportCity;
    @FXML
    private TextField airportCountry;
    @FXML
    private TextField airportIATA;
    @FXML
    private TextField airportICAO;
    @FXML
    private TextField airportType;
    @FXML
    private TextField airportTimezone;
    @FXML
    private TextField airportTimezoneString;
    @FXML
    private TextField airportDST;
    @FXML
    private TextField airportSource;
    @FXML
    private TextField airportLatitude;
    @FXML
    private TextField airportLongitude;
    @FXML
    private TextField airportAltitude;
    @FXML
    private TextField airlineName;
    @FXML
    private TextField airlineAlias;
    @FXML
    private TextField airlineCallsign;
    @FXML
    private TextField airlineIATA;
    @FXML
    private TextField airlineICAO;
    @FXML
    private TextField airlineCountry;
    @FXML
    private CheckBox airlineActive;
    @FXML
    private TextField routeAirline;
    @FXML
    private TextField routeSource;
    @FXML
    private TextField routeDest;
    @FXML
    private TextField routeEquipment;
    @FXML
    private TextField routeStops;
    @FXML
    private CheckBox routeCodeShare;
    @FXML
    private ChoiceBox recordSelectAirport;
    @FXML
    private ChoiceBox recordSelectAirline;
    @FXML
    private ChoiceBox recordSelectRoute;
    @FXML
    private Button modifyRouteWindowButton;
    @FXML
    private Pane modifyRoutePane;
    @FXML
    private SplitPane routeSplitPane;
    @FXML
    private TextField routeAirlineMod;
    @FXML
    private TextField routeAirlineIDMod;
    @FXML
    private TextField routeSourceMod;
    @FXML
    private TextField routeSourceIDMod;
    @FXML
    private TextField routeDestMod;
    @FXML
    private TextField routeDestIDMod;
    @FXML
    private TextField routeEquipmentMod;
    @FXML
    private TextField routeStopsMod;
    @FXML
    private CheckBox routeCodeShareMod;
    @FXML
    private Button modifyAirlineWindowButton;
    @FXML
    private Pane modifyAirlinePane;
    @FXML
    private Label modifyAirlineLabel;
    @FXML
    private TextField airlineNameMod;
    @FXML
    private TextField airlineAliasMod;
    @FXML
    private TextField airlineCallsignMod;
    @FXML
    private TextField airlineIATAMod;
    @FXML
    private TextField airlineICAOMod;
    @FXML
    private TextField airlineCountryMod;
    @FXML
    private CheckBox airlineActiveMod;
    @FXML
    private SplitPane airlineSplitPane;
    @FXML
    private SplitPane airportSplitPane;
    @FXML
    private Button modifyAirportWindowButton;
    @FXML
    private Pane modifyAirportPane;
    @FXML
    private Label modifyAirportLabel;
    @FXML
    private TextField airportNameMod;
    @FXML
    private TextField airportCityMod;
    @FXML
    private TextField airportCountryMod;
    @FXML
    private TextField airportIATAMod;
    @FXML
    private TextField airportICAOMod;
    @FXML
    private TextField airportTypeMod;
    @FXML
    private TextField airportTimezoneMod;
    @FXML
    private TextField airportTimezoneStringMod;
    @FXML
    private TextField airportDSTMod;
    @FXML
    private TextField airportSourceMod;
    @FXML
    private TextField airportLatitudeMod;
    @FXML
    private TextField airportLongitudeMod;
    @FXML
    private TextField airportAltitudeMod;
    @FXML
    private Text flightText;
    @FXML
    private ChoiceBox recordSelectFlight;
    @FXML
    private ChoiceBox flightDropDown;
    @FXML
    private ListView flightDetailList;
    @FXML
    private ListView flightList;
    @FXML
    private ComboBox helpDropdown;
    @FXML
    private TextArea helpTextArea;
    @FXML
    private ChoiceBox recordSelectMap;
    @FXML
    private ChoiceBox mapFilter;
    @FXML
    private TextField mapSearchBox;
    @FXML
    private TextField airportInfoBox;
    @FXML
    private ListView mapDetailList;

    private ArrayList<Record> recordList;
    private Record currentRecord;
    private boolean optedIn = false;

    private Loader loader = new Loader();
    private RouteLoader routeLoad = new RouteLoader();
    private AirportLoader airportLoad = new AirportLoader();
    private AirlineLoader airlineLoad = new AirlineLoader();
    private FlightLoader flightLoad = new FlightLoader();


    private List<Airline> defaultAirlineList = new ArrayList<>();
    private List<Route> defaultRouteList = new ArrayList<>();
    private Airport lastSelectedAirport = null;


    private WebEngine mapEngine;

    private DataChecker dataChecker = new DataChecker();


    //need to get this function to loop through airports adding position then RouteLocations


    private AirportLocations airports = new AirportLocations();
    private AirportLocations RoutesPlotLocations = new AirportLocations();

    /**
     * Sets up all the data array lists to be used along with the sources of
     * our data.
     *
     * The function then opens the welcoming dialog box to the user.
     *
     * @param url Location used to resolve relative paths for the root object, or null if the location is not known.
     * @param rb Resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //recordList = new ArrayList<Record>();
        recordList = RetrieveFromDatabase.generateRecord();
        if (recordList.size() > 0) {
            currentRecord = recordList.get(0);
        } else {
            currentRecord = new Record("Record 1");
            recordList.add(currentRecord);
        }

        ArrayList<String> recordNames = new ArrayList<String>();
        for (Record record: recordList) {
            System.out.println(record.getName());
            recordNames.add(record.getName());
        }

        recordSelectAirport.setItems(observableArrayList(recordNames));
        recordSelectAirport.getSelectionModel().selectFirst();
        recordSelectAirline.setItems(observableArrayList(recordNames));
        recordSelectAirline.getSelectionModel().selectFirst();
        recordSelectRoute.setItems(observableArrayList(recordNames));
        recordSelectRoute.getSelectionModel().selectFirst();
        recordSelectFlight.setItems(observableArrayList(recordNames));
        recordSelectFlight.getSelectionModel().selectFirst();
        recordSelectMap.setItems(observableArrayList(recordNames));
        recordSelectMap.getSelectionModel().selectFirst();

        recordNames.add("New Record");
        recordDropdown.setItems(observableArrayList(recordNames));
        recordDropdown.getSelectionModel().selectFirst();

        mapFilter.setItems(observableArrayList("Airport Code", "Equipment Code"));
        mapFilter.getSelectionModel().selectFirst();

        initMap();


        WebEngine OpenFlightsWebEngine = OpenFlightsWebView.getEngine();
        OpenFlightsWebEngine.load("https://openflights.org/data.html");

        WebEngine CovidWebEngine = CovidDataWebView.getEngine();
        CovidWebEngine.load("https://ourworldindata.org/coronavirus-source-data");

        ObservableList<String> airlineSearchOptions = observableArrayList("Country", "Name", "Alias", "Callsign", "IATA", "ICAO");
        airlineSearchBy.setItems(airlineSearchOptions);

        ObservableList<String> airportSearchOptions = observableArrayList("Country", "Name", "City", "IATA", "ICAO", "Timezone", "Total # Routes");
        airportSearchBy.setItems(airportSearchOptions);

        ObservableList<String> routeSearchOptions = observableArrayList("Source Airport", "Destination Airport", "Equipment", "Airline", "Total # Stops", "Source ID", "Destination ID");
        routeSearchBy.setItems(routeSearchOptions);

        routeSearchBy.getSelectionModel().selectFirst();
        airportSearchBy.getSelectionModel().selectFirst();
        airlineSearchBy.getSelectionModel().selectFirst();

        DialogBoxes.welcomeBox();



        helpDropdown.setItems(observableArrayList("Airport - ID", "Airport - Name", "Airport - City", "Airport - Country", "Airport - IATA Code", "Airport - ICAO Code", "Airport - Timezone", "Airport - Timezone Offset", "Airport - DST", "Airport - Latitude", "Airport - Longitude", "Airport - Altitude", "Airline - ID", "Airline - Name", "Airline - Alias", "Airline - Callsign", "Airline - Active", "Airline - IATA Code", "Airline - ICAO Code", "Airline - Country", "Route - Airline Code", "Route - Source Airport Code", "Route - Destination Airport Code", "Route - Equipment", "Route - Number of Stops", "Route - Codeshare"));
        helpDropdown.getSelectionModel().selectFirst();
        helpTextArea.setText("OpenFlights and PlaneSonar25's ID for the airport. Must be an integer.");


    }

    private void displayAirport(AirportLocations newAirportLocation) {
        String scriptToExecute = "displayAirport(" + newAirportLocation.toJSONArray() + ");";
        this.mapEngine.executeScript(scriptToExecute);
    }

    public void initMap(){
        this.mapEngine = mapView.getEngine();
        this.mapEngine.load(getClass().getResource("/map.html").toExternalForm());
    }

    @FXML
    public void airportLoop() {
        String scriptToExecute = "clearMap();";
        this.mapEngine.executeScript(scriptToExecute);

        airports = new AirportLocations();
        airports.addAirports(currentRecord);
        displayAirport(airports);
    }

    @FXML
    public void showMapRoutes() {
        String scriptToExecute = "clearMap();";
        this.mapEngine.executeScript(scriptToExecute);

        airports = new AirportLocations();
        airports.addAirports(currentRecord);
        plotRoute();
        displayAirport(airports);
    }

    public void plotRoute() {
        //loops through routes and gets lat and long of source and destination airports from database. Then draws line connecting them on map
        ArrayList<Route> filteredRoutes;

        if (mapFilter.getValue() == "Equipment Code") {
            filteredRoutes = currentRecord.searchRoutes(mapSearchBox.getText().toLowerCase(), "equipment");
        } else {
            filteredRoutes = currentRecord.searchRoutes(mapSearchBox.getText().toLowerCase(), "source airport");
            filteredRoutes.addAll(currentRecord.searchRoutes(mapSearchBox.getText().toLowerCase(), "destination airport"));
            Set<Route> noDuplicates = new HashSet<Route>(filteredRoutes);
            filteredRoutes.clear();
            filteredRoutes.addAll(noDuplicates);
        }

        for(Route routePlot: filteredRoutes) {
            ArrayList<Double> sourcePoints = RetrieveFromDatabase.getLatLong(routePlot, "sourceID");
            ArrayList<Double> destPoints = RetrieveFromDatabase.getLatLong(routePlot, "destID");
            try {
                String scriptToExecute = "drawRoute(" + "[{ lat: " + sourcePoints.get(0) + ", lng: " + sourcePoints.get(1) + " },{ lat: " + destPoints.get(0) + ", lng: " + destPoints.get(1) + " },]" + ");";
                this.mapEngine.executeScript(scriptToExecute);
            } catch (Exception IndexOutOfBoundsException){
                continue;
            }
        }
    }

    @FXML
    public void showEquipment() {
       // TODO;
    }

    @FXML
    public void airportMapInfo() {
        String searchCriteria = airportInfoBox.getText().toLowerCase();
        if (!searchCriteria.isBlank()) {
            List<Airport> matchingAirports = currentRecord.searchAirportsMap(searchCriteria.toLowerCase());
            if (matchingAirports.size() == 0 || searchCriteria.equals("null") || searchCriteria.equals("unknown")) {

                String entry_one = "No airport in the selected record";
                String entry_two =  "matches this IATA code.";
                String entry_three = "IATA codes for an airport can be";
                String entry_four = "found by hovering over the";
                String entry_five = " airport's icon on the map.";
                String entry_six = "Note entered IATA codes must";
                String entry_seven = "match exactly: Only one airport's";
                String entry_eight = "information can be displayed at once.";

                mapDetailList.setItems(observableArrayList(entry_one, entry_two, entry_three, entry_four, entry_five, entry_six, entry_seven, entry_eight));
                DialogBoxes.noneReturned("airports");
            } else {

                Airport airport = matchingAirports.get(0);

                String name = String.format("Name: %s", airport.getName());
                String location = String.format("Location: %s, %s", airport.getCity(), airport.getCountry());

                String risk;
                if (airport.getRiskString() == null) {
                    risk = "COVID risk level: Unknown (???%)";
                } else {
                    risk = String.format("COVID risk level: %s (%.2f%%)", airport.getRiskString(), airport.getRisk());
                }

                String iata = String.format("IATA code: %s", airport.getIata());
                String icao = String.format("ICAO code: %s", airport.getIcao());

                airport.determineNumRoutes();

                String numRoutesSource = ("A total of " + airport.getNumRoutesSource() + " flight routes begin at this airport");
                String numRoutesDest = ("A total of " + airport.getNumRoutesDest() + " flight routes are destined to this airport");

                String timezoneNum;
                if (airport.getTimezone() == 25) {
                    timezoneNum = "Unknown";
                } else {
                    timezoneNum = Double.toString(airport.getTimezone());
                    if (airport.getTimezone() > 0) {
                        timezoneNum = "+" + timezoneNum;
                    }
                }

                String timezone = String.format("Timezone: %s, %s hours", airport.getTimezoneString(), timezoneNum);

                mapDetailList.setItems(observableArrayList(name, location, risk, iata, icao, numRoutesSource, numRoutesDest, timezone));
                if (optedIn) {
                    String lat;
                    if (airport.getLatitude() == 360) {
                        lat = ("Latitude: Unknown");
                    } else {
                        lat = ("Latitude: " + airport.getLatitude() + "°");
                    }

                    String lon;
                    if (airport.getLongitude() == 360) {
                        lon = ("Longitude: Unknown");
                    } else {
                        lon = ("Longitude: " + airport.getLongitude() + "°");
                    }

                    String alt;
                    if (airport.getAltitude() == -1) {
                        alt = ("Altitude: Unknown");
                    } else {
                        alt = ("Altitude: " + airport.getAltitude() + " ft");
                    }

                    mapDetailList.getItems().addAll(lat, lon, alt);
                }
            }
        } else {
            String entry_one = "Please enter an IATA code";
            String entry_two =  "matching an airport in this record.";
            String entry_three = "IATA codes for an airport can be";
            String entry_four = "found by hovering over the";
            String entry_five = " airport's icon on the map.";
            String entry_six = "Note entered IATA codes must";
            String entry_seven = " match exactly: Only one airport's";
            String entry_eight = "information can be displayed at once.";

            mapDetailList.setItems(observableArrayList(entry_one, entry_two, entry_three, entry_four, entry_five, entry_six, entry_seven, entry_eight));

            DialogBoxes.blankTextField(false);
        }
    }

    /**
     * Finds the index of the selected route in the recordList and then selects
     * this given route.
     */
    @FXML
    public void mapSelect() {
        int index = 0;
        for (Record record: recordList) {
            if (recordSelectMap.getValue() == record.getName()) {
                index = recordList.indexOf(record);
                break;
            }
        }
        recordSelectAirport.getSelectionModel().select(index);
        recordSelectAirline.getSelectionModel().select(index);
        recordSelectFlight.getSelectionModel().select(index);
        recordSelectRoute.getSelectionModel().select(index);

        currentRecord = recordList.get(index);
        flightHelper();
    }

    /**
     * Finds the index of the selected route in the recordList and then selects
     * this given route.
     */
    @FXML
    public void routeSelect() {
        int index = 0;
        for (Record record: recordList) {
            if (recordSelectRoute.getValue() == record.getName()) {
                index = recordList.indexOf(record);
                break;
            }
        }
        recordSelectAirport.getSelectionModel().select(index);
        recordSelectAirline.getSelectionModel().select(index);
        recordSelectFlight.getSelectionModel().select(index);
        recordSelectMap.getSelectionModel().select(index);


        currentRecord = recordList.get(index);
        flightHelper();
    }

    /**
     * Finds the index of the selected airport in the recordList and then selects
     * this given airport.
     */
    @FXML
    public void airportSelect() {
        int index = 0;
        for (Record record: recordList) {
            if (recordSelectAirport.getValue() == record.getName()) {
                index = recordList.indexOf(record);
                break;
            }
        }
        recordSelectRoute.getSelectionModel().select(index);
        recordSelectAirline.getSelectionModel().select(index);
        recordSelectFlight.getSelectionModel().select(index);
        recordSelectMap.getSelectionModel().select(index);


        currentRecord = recordList.get(index);
        flightHelper();
    }

    /**
     * Finds the index of the selected airline in the recordList and then selects
     * this given airline.
     */
    @FXML
    public void airlineSelect() {
        int index = 0;
        for (Record record: recordList) {
            if (recordSelectAirline.getValue() == record.getName()) {
                index = recordList.indexOf(record);
                break;
            }
        }
        recordSelectAirport.getSelectionModel().select(index);
        recordSelectRoute.getSelectionModel().select(index);
        recordSelectFlight.getSelectionModel().select(index);
        recordSelectMap.getSelectionModel().select(index);


        currentRecord = recordList.get(index);
        flightHelper();
    }

    /**
     * Finds the index of the selected flight in the recordList and then selects
     * this given flight.
     */
    @FXML
    public void flightSelect() {
        int index = 0;
        for (Record record: recordList) {
            if (recordSelectFlight.getValue() == record.getName()) {
                index = recordList.indexOf(record);
                break;
            }
        }
        recordSelectAirport.getSelectionModel().select(index);
        recordSelectRoute.getSelectionModel().select(index);
        recordSelectAirline.getSelectionModel().select(index);
        recordSelectMap.getSelectionModel().select(index);


        currentRecord = recordList.get(index);
        flightHelper();
    }

    @FXML
    /**
     * Refreshes flight choicebox when changing records, as well as changing the flight display text accordingly.
     */
    public void flightHelper() {
        flightDropDown.setItems(observableArrayList());
        for (Flight flight: currentRecord.getFlightList()) {
            flightDropDown.getItems().add(flight.flightName());
        }
        flightDropDown.getSelectionModel().selectFirst();
        if (flightDropDown.getValue() == null) {
            flightText.setText("No flight selected");
        } else {
            flightText.setText("Currently selected flight: " + ((String) flightDropDown.getValue()).substring(0, 12));
        }
    }

    @FXML
    /**
     * Displays the status of a flight at all points in flightList.
     */
    public void showFlight(ActionEvent event) throws IOException {
        if (flightDropDown.getValue() == null) {
            flightText.setText("No flight selected");
            flightList.setItems(observableArrayList());
        } else {
            flightText.setText("Currently selected flight: " + ((String) flightDropDown.getValue()).substring(0, 12));
            for (Flight flight : currentRecord.getFlightList()) {
                if (flight.flightName().equals(flightDropDown.getValue())) {
                    flightList.setItems(observableArrayList(flight.getStrings()));
                    break;
                }
            }
        }
    }


    /**
     * Retrieves user input and searches for airports that match the provided criteria in the selected attribute.
     * Displays only those airports in the airport data viewer.
     *
     * If input is blank, display an error pop-up.
     */
    @FXML
    public void searchAirports() {
        String searchCategory = (String) airportSearchBy.getSelectionModel().getSelectedItem();
        String searchCriteria = airportSearchCriteria.getText().toLowerCase();
        if (!searchCriteria.isBlank()) {
            List<Airport> matchingAirports = currentRecord.searchAirports(searchCriteria.toLowerCase(), searchCategory.toLowerCase());
            if (matchingAirports.size() == 0) {
                DialogBoxes.noneReturned("airports");
            } else {
                airportList.setItems(observableArrayList(matchingAirports));
            }
        } else {
            DialogBoxes.blankTextField(false);
        }
    }

    /**
     * Retrieves user input and searches for airlines that match the provided criteria in the selected attribute.
     * Displays only those airlines in the airlines data viewer.
     *
     * If input is blank, display an error pop-up.
     */
    public void searchAirlines() {
        airlineActiveBox.setSelected(true);
        airlineInactiveBox.setSelected(true);

        String searchCategory = (String) airlineSearchBy.getSelectionModel().getSelectedItem();
        String searchCriteria = airlineSearchCriteria.getText().toLowerCase();
        if (!searchCriteria.isBlank()) {
            List<Airline> matchingAirlines = currentRecord.searchAirlines(searchCriteria.toLowerCase(), searchCategory.toLowerCase());
            if (matchingAirlines.size() == 0) {
                DialogBoxes.noneReturned("airlines");
            } else {
                airlineList.setItems(observableArrayList(matchingAirlines));
                defaultAirlineList = airlineList.getItems();
            }
        } else {
            DialogBoxes.blankTextField(false);
        }
    }

    /**
     * Retrieves user input, and searches for routes that match the provided criteria in the selected attribute.
     * Displays only those routes in the route data viewer.
     *
     * If input is blank, an error pop-up is displayed.
     */
    @FXML
    public void searchRoutes() {
        routeDirectBox.setSelected(true);
        routeIndirectBox.setSelected(true);

        String searchCategory = (String) routeSearchBy.getSelectionModel().getSelectedItem();
        String searchCriteria = routeSearchCriteria.getText().toLowerCase();
        if (!searchCriteria.isBlank()) {
            List<Route> matchingRoutes = currentRecord.searchRoutes(searchCriteria.toLowerCase(), searchCategory.toLowerCase());
            if (matchingRoutes.size() == 0) {
                DialogBoxes.noneReturned("routes");
            } else {
                routeList.setItems(observableArrayList(matchingRoutes));
                defaultRouteList = routeList.getItems();
            }
        } else {
            DialogBoxes.blankTextField(false);
        }
    }


    /**
     * Filters through the airport data and sorts them by the airports with
     * the least number of routes.
     *
     * @param event The user presses the leastRoutesButton in the airport tab.
     * @throws IOException Signals that an I/O exception of some sort has occurred.
     */
    @FXML
    public void leastRoutesButton(ActionEvent event) throws IOException {
        List<Airport> currentData = airportList.getItems();
        List<Airport> rankedAirports = currentRecord.rankAirports(false, currentData);
        airportList.setItems(observableArrayList(rankedAirports));
    }

    /**
     * Filters through the airport data and sorts them by the airports with
     * the highest number of routes.
     *
     * @param event The user presses the mostRoutesButton in the airport tab.
     * @throws IOException Signals that an I/O exception of some sort has occurred.
     */
    @FXML
    public void mostRoutesButton(ActionEvent event) throws IOException {
        List<Airport> currentData = airportList.getItems();
        List<Airport> rankedAirports = currentRecord.rankAirports(true, currentData);
        airportList.setItems(observableArrayList(rankedAirports));
    }

    /**
     * Filters the airlines shown in the list view based on whether they are active or not
     *
     * @param event User selects whether the airlines should filter active or inactive flights.
     * @throws IOException Signals that an I/O exception of some sort has occurred.
     */
    @FXML
    public void filterActiveAirlines(ActionEvent event) throws IOException {

        if (airlineActiveBox.isSelected()) {
            List<Airline> filteredAirlines = currentRecord.filterAirlines(true, defaultAirlineList);
            if (airlineInactiveBox.isSelected()) {
                airlineList.setItems(observableArrayList(defaultAirlineList));
            } else {
                airlineList.setItems(observableArrayList(filteredAirlines));
            }
        } else {
            if (airlineInactiveBox.isSelected()) {
                List<Airline>filteredAirlines = currentRecord.filterAirlines(false, defaultAirlineList);
                airlineList.setItems(observableArrayList(filteredAirlines));
            } else {
                airlineList.setItems(observableArrayList(new ArrayList<Airline>()));
            }
        }
    }

    /**
     * Filters the route list view based on whether the routes are a direct flight or have
     * multiple stops.
     *
     * @param event User selects whether the routes should filter direct routes or routes
     *              with multiple stops
     * @throws IOException Signals that an I/O exception of some sort has occurred.
     */
    @FXML
    public void filterRouteStops(ActionEvent event) throws IOException {

        if (routeDirectBox.isSelected()) {
            List<Route> filteredRoutes = currentRecord.filterRoutesStops(true, defaultRouteList);
            if (routeIndirectBox.isSelected()) {
                routeList.setItems(observableArrayList(defaultRouteList));
            } else {
                routeList.setItems(observableArrayList(filteredRoutes));
            }
        } else {
            if (routeIndirectBox.isSelected()) {
                List<Route> filteredRoutes = currentRecord.filterRoutesStops(false, defaultRouteList);
                routeList.setItems(observableArrayList(filteredRoutes));
            } else {
                routeList.setItems(observableArrayList(new ArrayList<Route>()));
            }
        }
    }

    /**
     * Helper function to addFileButton, used to handle updating of records.
     */
    public void addFileHelper() {

        if (recordDropdown.getValue() == "New Record") {
            Record record = new Record("Record " + (recordList.size() + 1));
            recordList.add(record);
            currentRecord = record;

            recordSelectAirport.getItems().add(currentRecord.getName());
            recordSelectAirline.getItems().add(currentRecord.getName());
            recordSelectRoute.getItems().add(currentRecord.getName());
            recordSelectFlight.getItems().add(currentRecord.getName());
            recordSelectMap.getItems().add(currentRecord.getName());
            ArrayList<String> recordNames = new ArrayList<String>();
            for (Record records: recordList) {
                recordNames.add(records.getName());
            }
            recordNames.add("New Record");
            recordDropdown.setItems(observableArrayList(recordNames));

            recordSelectAirline.getSelectionModel().select(recordList.size() - 1);
            recordSelectAirport.getSelectionModel().select(recordList.size() - 1);
            recordSelectRoute.getSelectionModel().select(recordList.size() - 1);
            recordSelectFlight.getSelectionModel().select(recordList.size() - 1);
            recordSelectMap.getSelectionModel().select(recordList.size() - 1);
            recordDropdown.getSelectionModel().select(recordList.size() - 1);

            return;
        }

        int index = 0;
        for (Record record: recordList) {
            if (recordDropdown.getValue() == record.getName()) {
                index = recordList.indexOf(record);
                break;
            }
        }

        recordSelectAirline.getSelectionModel().select(index);
        recordSelectAirport.getSelectionModel().select(index);
        recordSelectRoute.getSelectionModel().select(index);
        recordSelectFlight.getSelectionModel().select(index);
        recordSelectMap.getSelectionModel().select(index);
        recordDropdown.getSelectionModel().select(index);

        currentRecord = recordList.get(index);
    }

    /**
     * Based on the file type checked (Airport, Airline, Route, or Flight), the program attempts to load in
     * a data file of that type into the application.
     *
     * If the file is invalid, an appropriate error message is displayed.
     *
     * @param event The user has selected one of the four data types to load in and has loaded in a file
     *              from their file directory.
     * @throws IOException Signals that the file loaded in was either invalid or in the wrong format.
     */
    @FXML
    public void addFileButton(ActionEvent event) throws IOException {

        if (newTab.getSelectionModel().getSelectedIndex() == 3) {
            FileChooser loadFile = new FileChooser();
            loadFile.getExtensionFilters().add(new FileChooser.ExtensionFilter("DAT Files", "*.dat"));
            loadFile.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));

            File file = loadFile.showOpenDialog(null);

            if (file != null) {
                boolean goodFile = loader.errorHandler(file);
                DialogBoxes.fileFormatInfo(goodFile, false, null);
                if (!goodFile) {return;}
                if (selectFile.getSelectedToggle() == airportRadioButton) {
                    boolean airportCheck = airportLoad.loadAirportErrorCheck(file.getAbsolutePath());
                    DialogBoxes.fileFormatInfo(airportCheck, false, "airport");
                    if (!airportCheck) {return;}

                    addFileHelper();

                    ArrayList<Airport> newAirportList = airportLoad.loadAirportFile(file.getAbsolutePath(), currentRecord.getName());
                    currentRecord.addAirports(newAirportList);
                    boolean sync = DialogBoxes.confirmSync("Airport", newAirportList.size());
                    if (sync) {
                        for (Airport airport : newAirportList) {
                            ManipulateDatabase.addNewAirport(airport);
                        }
                    }
                    hideAllTables();
                    if (Airport.getNumMissingCovid() > 0) {
                        DialogBoxes.missingCovidInfoBox();
                    }
                    flightHelper();
                    DialogBoxes.fileFormatInfo(true, true, "airport");
                } else if (selectFile.getSelectedToggle() == airlineRadioButton) {
                    boolean airlineCheck = airlineLoad.loadAirlineErrorCheck(file.getAbsolutePath());
                    DialogBoxes.fileFormatInfo(airlineCheck, false, "airline");
                    if (!airlineCheck) {return;}

                    addFileHelper();

                    ArrayList<Airline> newAirlineList = airlineLoad.loadAirlineFile(file.getAbsolutePath(), currentRecord.getName());
                    currentRecord.addAirlines(newAirlineList);
                    boolean sync = DialogBoxes.confirmSync("Airline", newAirlineList.size());
                    if (sync) {
                        for (Airline airline : newAirlineList) {
                            ManipulateDatabase.addNewAirline(airline);
                        }
                    }
                    hideAllTables();
                    flightHelper();
                    DialogBoxes.fileFormatInfo(true, true, "airline");
                } else if (selectFile.getSelectedToggle() == routeRadioButton) {
                    boolean routeCheck = routeLoad.loadRouteErrorCheck(file.getAbsolutePath());
                    DialogBoxes.fileFormatInfo(routeCheck, false, "route");
                    if (!routeCheck) {return;}

                    addFileHelper();

                    ArrayList<Route> newRouteList = routeLoad.loadRouteFile(file.getAbsolutePath(), currentRecord.getName());
                    currentRecord.addRoutes(newRouteList);
                    boolean sync = DialogBoxes.confirmSync("Route", newRouteList.size());
                    if (sync) {
                        for (Route route : newRouteList) {
                            ManipulateDatabase.addNewRoute(route);
                        }
                    }
                    hideAllTables();
                    flightHelper();
                    DialogBoxes.fileFormatInfo(true, true, "route");
                } else if (selectFile.getSelectedToggle() == flightRadioButton) {
                    boolean flightCheck = flightLoad.loadFlightErrorCheck(file.getAbsolutePath());
                    DialogBoxes.fileFormatInfo(flightCheck, false, "flight");
                    if (!flightCheck) {return;}

                    addFileHelper();

                    Flight newFlight = flightLoad.loadFlightFile(file.getAbsolutePath());
                    currentRecord.addFlights(newFlight);
                    hideAllTables();
                    flightHelper();
                    DialogBoxes.fileFormatInfo(true, true, "flight");
                }
            }
        }
    }

    /**
     * The user enters the data for the new airport they wish to add and presses the 'add airport' button. If all
     * fields have valid inputs, the airport is added to the database.
     *
     * If some of the fields have invalid inputs, the user will be prompted with an appropriate error message.
     */
    @FXML
    public void addAirportButton() {
        addFileHelper();

        int id = currentRecord.getAirportList().size()+1;
        String name = airportName.getText().trim();
        String city = airportCity.getText().trim();
        String country = airportCountry.getText().trim();
        String iata = airportIATA.getText().trim();
        String icao = airportICAO.getText().trim();
        String latitude = airportLatitude.getText().trim();
        String longitude = airportLongitude.getText().trim();
        String altitude = airportAltitude.getText().trim();
        String timezone = airportTimezone.getText().trim();
        String dst = airportDST.getText().trim();
        String timezoneString = airportTimezoneString.getText().trim();

        ArrayList<String> errors = dataChecker.checkAirport(currentRecord, name, city, country, iata, icao, latitude, longitude, altitude, timezone, dst, timezoneString);

        int numRoutesSource = 0;
        int numRoutesDest = 0;

        if (errors.size() == 0) {
            Airport newAirport = new Airport(id, name, city, country, iata, icao, Double.parseDouble(latitude), Double.parseDouble(longitude), Integer.parseInt(altitude), Double.parseDouble(timezone), dst, timezoneString, numRoutesSource, numRoutesDest);
            ArrayList<Airport> newAirportList = new ArrayList<Airport>();
            newAirportList.add(newAirport);
            currentRecord.addAirports(newAirportList);
            DialogBoxes.addedAlert("Airport");
        } else {
            DialogBoxes.newDataError(errors);
        }
        hideAllTables();
    }

    /**
     * The user enters the data for the new airline they wish to add and presses the 'add airline' button. If all
     * fields have valid inputs, the airline is added to the database.
     *
     * If some of the fields have invalid inputs, the user will be prompted with an appropriate error message.
     *
     * @param event The user fills the inputs and presses the addAirlineButton
     * @throws IOException Signals that the airline loaded in was either invalid or in the wrong format.
     */
    @FXML
    public void addAirlineButton(ActionEvent event) throws IOException {
        addFileHelper();

        int id = currentRecord.getAirlineList().size()+1;
        String name = airlineName.getText().trim();
        boolean active = false;
        if (airlineActive.isSelected()) { active = true; }
        String country = airlineCountry.getText().trim();
        String iata = airlineIATA.getText().trim();
        String icao = airlineICAO.getText().trim();
        String alias = airlineAlias.getText().trim();
        String callSign = airlineCallsign.getText().trim();
        //As one IATA airline code can be assigned to multiple airlines, uniqueness does not need to be verified here
        ArrayList<String> errors = dataChecker.checkAirline(currentRecord, name, country, iata, icao);

        if (errors.size() == 0) {
            Airline newAirline = new Airline(id, name, active, country, alias, callSign, iata, icao);
            ArrayList<Airline> newAirlineList = new ArrayList<Airline>();
            newAirlineList.add(newAirline);
            currentRecord.addAirlines(newAirlineList);
            DialogBoxes.addedAlert("Airline");
            hideAllTables();
        } else {
            DialogBoxes.newDataError(errors);
        }
    }

    /**
     * The user enters the data for the new route they wish to add and presses the 'add route' button. If all
     * fields have valid inputs, the route is added to the database.
     *
     * If some of the fields have invalid inputs, the user will be prompted with an appropriate error message.
     *
     * @param event The user fills the inputs and presses the addRouteButton
     * @throws IOException Signals that the route loaded in was either invalid or in the wrong format.
     */
    @FXML
    public void addRouteButton(ActionEvent event) throws IOException {
        addFileHelper();

        String airline = routeAirline.getText().trim();
        String sourceAirport = routeSource.getText().trim();
        String destAirport = routeDest.getText().trim();
        String numStops = routeStops.getText().trim();
        String equipment = routeEquipment.getText().trim();
        boolean codeshare = false;
        if (routeCodeShare.isSelected()) { codeshare = true; }

        ArrayList<String> errors = dataChecker.checkRoutes(currentRecord, airline, sourceAirport, destAirport, numStops, equipment);

        if (errors.size() == 0) {
            Airline currAirline = currentRecord.searchAirlines(airline, "name").get(0);
            Airport currSource = currentRecord.searchAirports(sourceAirport, "name").get(0);
            Airport currDest = currentRecord.searchAirports(destAirport, "name").get(0);
            Route newRoute = new Route(currAirline.getId(), currAirline.getIata(), currAirline.getId(), currSource.getIata(), currSource.getId(), currDest.getIata(), currDest.getId(), Integer.parseInt(numStops), equipment, codeshare);
            ArrayList<Route> newRouteList = new ArrayList<Route>();
            newRouteList.add(newRoute);
            currentRecord.addRoutes(newRouteList);
            DialogBoxes.addedAlert("Route");
        } else {
            DialogBoxes.newDataError(errors);
        }
        hideAllTables();
    }

    /**
     * When the button is pressed, all the airport data currently loaded into the program is shown
     * in the main list view on the airport tab.
     */
    public void displayAllAirports() {
        airportList.setItems(observableArrayList(currentRecord.getAirportList()));
        recordSelectAirport.getSelectionModel().select(currentRecord.getName());

    }

    /**
     * Refreshes all the lists by temporarily hiding them. Both the regular lists
     * along with the lists displaying extra data are refreshed.
     */
    public void hideAllTables() {
        routeList.setItems(observableArrayList());
        airlineList.setItems(observableArrayList());
        airportList.setItems(observableArrayList());
        flightList.setItems(observableArrayList());
        airportDetailList.setItems(observableArrayList());
        airlineDetailList.setItems(observableArrayList());
        routeDetailList.setItems(observableArrayList());
        flightDetailList.setItems(observableArrayList());
    }

    /**
     * When the button is pressed, all the airline data currently loaded into the program is shown
     * in the main list view on the airline tab.
     */
    public void displayAllAirlines() {
        airlineList.setItems(observableArrayList(currentRecord.getAirlineList()));
        recordSelectAirline.getSelectionModel().select(currentRecord.getName());
        airlineActiveBox.setSelected(true);
        airlineInactiveBox.setSelected(true);
        defaultAirlineList = airlineList.getItems();
    }

    /**
     * When the button is pressed, all the route data currently loaded into the program is shown
     * in the main list view on the route tab.
     */
    public void displayAllRoutes() {
        routeList.setItems(observableArrayList(currentRecord.getRouteList()));
        recordSelectRoute.getSelectionModel().select(currentRecord.getName());
        routeDirectBox.setSelected(true);
        routeIndirectBox.setSelected(true);
        defaultRouteList = routeList.getItems();
    }

    /**
     * When the setOptedIn box is checked, the user is able to see additional route, airline
     * and airport information in their respective tabs.
     *
     * If not checked, the user will be unable to see this additional information/
     */
    @FXML
    public void setOptedIn() {
        if (extraInfoBox.isSelected()) {
            optedIn = true;
        } else {
            optedIn = false;
        }
    }

    /**
     * Displays additional information about a selected airport in the detail panel.
     * If the user has opted in to viewing further additional information, this
     * is also displayed.
     */
    public void additionalAirportInfo() {
        Airport airport = (Airport) airportList.getSelectionModel().getSelectedItem();

        if (airport != null) {

            String name = String.format("Name: %s", airport.getName());
            String location = String.format("Location: %s, %s", airport.getCity(), airport.getCountry());

            String risk;
            if (airport.getRiskString() == null) {
                risk = "COVID risk level: Unknown (???%)";
            } else {
                risk = String.format("COVID risk level: %s (%.2f%%)", airport.getRiskString(), airport.getRisk());
            }

            airport.determineNumRoutes();
            String numRoutes = ("A total of " + airport.getTotalRoutes() + " flight routes go through this airport");

            if (airport.getTotalRoutes() < 1) {
                DialogBoxes.noRoutes();
            }

            String timezoneNum;
            if (airport.getTimezone() == 25) {
                timezoneNum = "Unknown";
            } else {
                timezoneNum = Double.toString(airport.getTimezone());
                if (airport.getTimezone() > 0) {
                    timezoneNum = "+" + timezoneNum;
                }
            }

            String timezone = String.format("Timezone: %s, %s hours", airport.getTimezoneString(), timezoneNum);

            //Gets the last selected airport, and calculates the distance between that airport and the currently selected one
            String distanceString = "No previous airport selected";
            if (lastSelectedAirport != null) {
                double distanceValue = airport.distance(lastSelectedAirport);
                distanceString = String.format("Distance between this airport and last selected airport (%s) is %.2f km", lastSelectedAirport.getName(), distanceValue);
            }
            lastSelectedAirport = airport;

            String airlinesUsing = "Airlines using this airport: ";
            ArrayList<String> airlines = RetrieveFromDatabase.getAirlinesThroughAirport(airport);
            if (airlines.size() == 0) {
                airlinesUsing += "None";
            } else {
                airlinesUsing += airlines.get(0);
                for (int i=1; i < airlines.size(); i++) {
                    airlinesUsing += (", " + airlines.get(i));
                }
            }

            airportDetailList.setItems(observableArrayList(name, location, risk, numRoutes, timezone, distanceString, airlinesUsing));
            modifyAirportWindowButton.setVisible(true);

            if (optedIn) {

                String lat;
                if (airport.getLatitude() == 360) {
                    lat = ("Latitude: Unknown");
                } else {
                    lat = ("Latitude: " + airport.getLatitude() + "°");
                }

                String lon;
                if (airport.getLongitude() == 360) {
                    lon = ("Longitude: Unknown");
                } else {
                    lon = ("Longitude: " + airport.getLongitude() + "°");
                }

                String alt;
                if (airport.getAltitude() == -1) {
                    alt = ("Altitude: Unknown");
                } else {
                    alt = ("Altitude: " + airport.getAltitude() + " ft");
                }

                String iata = String.format("IATA code: %s", airport.getIata());
                String icao = String.format("ICAO code: %s", airport.getIcao());

                airportDetailList.getItems().addAll(icao, iata, lat, lon, alt);

            }
        }
    }


    /**
     * Displays additional information about a selected airline in the detail panel.
     * If the user has opted in to viewing further additional information, this
     * is also displayed.
     */
    public void additionalAirlineInfo() {
        Airline airline = (Airline) airlineList.getSelectionModel().getSelectedItem();

        if (airline != null) {

            String name = String.format("Name: %s", airline.getName());

            String country = String.format("Country of Origin: %s", airline.getCountry());

            String airlineIsActive = "";
            if (!airline.isActive()) {
                airlineIsActive = " not";
            }
            String active = String.format("This Airline is%s currently in operation", airlineIsActive);

            String alias = String.format("Known aliases for this airline: %s", airline.getAlias());

            airlineDetailList.setItems(observableArrayList(name, country, active, alias));
            modifyAirlineWindowButton.setVisible(true);

            if (optedIn) {
                String iata = String.format("IATA code: %s", airline.getIata());
                String icao = String.format("ICAO code: %s", airline.getIcao());
                String callSign = String.format("Callsign: %s", airline.getCallSign());

                airlineDetailList.getItems().addAll(iata, icao, callSign);
            }
        }
    }


    /**
     * Displays additional information about a selected route in the detail panel.
     * If the user has opted in to viewing further additional information, this
     * is also displayed.
     */
    @FXML
    public void additionalRouteInfo() {
        Route route = (Route) routeList.getSelectionModel().getSelectedItem();

        if (route != null) {

            String airline = String.format("Airline Code: %s", route.getAirline());

            String numStops;
            if (route.getNumStops() == -1) {
                numStops = ("Number of stops: Unknown");
            } else {
                numStops = ("Number of stops: " + route.getNumStops());
            }

            String sourceAirport = String.format("Source Airport Code: %s", route.getSourceAirport());
            String destAirport = String.format("Destination Airport Code: %s", route.getDestAirport());

            routeDetailList.setItems(observableArrayList(airline, numStops, sourceAirport, destAirport));
            modifyRouteWindowButton.setVisible(true);

            if (optedIn) {

                String equipment = String.format("Equipment: %s", route.getEquipment());
                String isACodeshare = "";
                if (!route.isCodeshare()) {
                    isACodeshare = " not";
                }
                String codeshare = String.format("This flight is%s a codeshare", isACodeshare);

                routeDetailList.getItems().addAll(equipment, codeshare);
            }
        }
    }

    /**
     * Displays additional information about a selected airline in the detail panel.
     * If the user has opted in to viewing further additional information, this
     * is also displayed.
     */
    public void additionalFlightInfo() {
        Flight currentFlight = null;
        for (Flight flight : currentRecord.getFlightList()) {
            if (flight.flightName().equals(flightDropDown.getValue())) {
                currentFlight = flight;
                break;
            }
        }
        int index = flightList.getSelectionModel().getSelectedIndex();
        if (currentFlight != null && index != -1) {

            String source = String.format("Source: %s", currentFlight.getSource());
            String dest = String.format("Destination: %s", currentFlight.getDest());
            String alt = String.format("Altitude: %s ft", currentFlight.getAltitudes().get(index));
            String lat = String.format("Latitude: %.5f°", currentFlight.getLatitudes().get(index));
            String lon = String.format("Longitude: %.5f°", currentFlight.getLongitudes().get(index));


            flightDetailList.setItems(observableArrayList(source, dest, alt, lat, lon));

            if (optedIn) {
                String status = String.format("Current status: %s", currentFlight.getStatus().get(index));
                String location = String.format("Current location: %s", currentFlight.getLocations().get(index));

                flightDetailList.getItems().addAll(status, location);
            }
        }
    }

    /**
     * Overlays a window over the airline tab in which the user can make their desired
     * changes to the selected airline.
     *
     * @param event The user selects an airline and then the modifyAirlineWindowButton.
     * @throws IOException Signals that an I/O exception of some sort has occurred.
     */
    @FXML
    public void modifyAirlineWindowButton(ActionEvent event) throws IOException {
        Airline airline = (Airline) airlineList.getSelectionModel().getSelectedItem();
        airlineSplitPane.setVisible(false);
        airlineList.setVisible(false);
        modifyAirlineLabel.setText("Modify Airline (ID: " + airline.getId() + ")");
        airlineNameMod.setText(airline.getName());
        airlineAliasMod.setText(airline.getAlias());
        airlineCallsignMod.setText(airline.getCallSign());
        airlineIATAMod.setText(airline.getIata());
        airlineICAOMod.setText(airline.getIcao());
        airlineCountryMod.setText(airline.getCountry());
        airlineActiveMod.setSelected(airline.isActive());
        modifyAirlinePane.setVisible(true);
    }

    /**
     * Permanently removes the selected airline from the airline database.
     *
     * @param event The user selects an airline and presses the deleteAirlineButton.
     * @throws IOException Signals that an I/O exception of some sort has occurred.
     */
    @FXML
    public void deleteAirlineButton(ActionEvent event) throws IOException {
        if (DialogBoxes.confirmationAlert("delete airline")) {
            Airline airline = (Airline) airlineList.getSelectionModel().getSelectedItem();
            currentRecord.removeAirlines(airline);
            ManipulateDatabase.removeData(airline.getId(), airline.getRecordName(), "airlines");
            modifyAirlinePane.setVisible(false);
            modifyAirlineWindowButton.setVisible(false);
            airlineSplitPane.setVisible(true);
            airlineList.setVisible(true);
            displayAllAirlines();
            additionalAirlineInfo();
        }
    }

    /**
     * Runs through the changes made to an airline and checks whether all inputs for the newly modified
     * airline are of the right type and format.
     *
     * If they are not, the program will notify the user about which input(s) are invalid and need to
     * be changed.
     *
     * @param event The user has filled in the modifyAirline section with the changes they would like to
     *              make and have pressed the modifyAirlineButton.
     * @throws IOException Signals that an I/O exception of some sort has occurred.
     */
    @FXML
    public void modifyAirlineButton(ActionEvent event) throws IOException {
        Airline airline = (Airline) airlineList.getSelectionModel().getSelectedItem();
        int airlineIndex = currentRecord.getAirlineList().indexOf(airline);
        currentRecord.removeAirlines(airline);

        int id = airline.getId();
        String name = airlineNameMod.getText().trim();
        boolean active = false;
        if (airlineActiveMod.isSelected()) { active = true; }
        String country = airlineCountryMod.getText().trim();
        String iata = airlineIATAMod.getText().trim();
        String icao = airlineICAOMod.getText().trim();
        String alias = airlineAliasMod.getText().trim();
        if (name.equals("")) { alias = "Unknown"; }
        String callSign = airlineCallsignMod.getText().trim();
        if (name.equals("")) { callSign = "Unknown"; }

        ArrayList<String> errors = dataChecker.checkAirline(currentRecord, name, country, iata, icao);

        if (errors.size() == 0) {
            Airline newAirline = new Airline(id, name, active, country, alias, callSign, iata, icao);
            newAirline.setRecordName(airline.getRecordName());
            currentRecord.modifyAirline(airlineIndex, newAirline);
            modifyAirlinePane.setVisible(false);
            modifyAirlineWindowButton.setVisible(false);
            airlineSplitPane.setVisible(true);
            airlineList.setVisible(true);
            displayAllAirlines();
            additionalAirlineInfo();
        } else {
            DialogBoxes.newDataError(errors);
        }

    }



    /**
     * Overlays a window over the route tab in which the user can make their desired
     * changes to the selected route.
     *
     * @param event The user selects an route and then the modifyRouteWindowButton.
     * @throws IOException Signals that an I/O exception of some sort has occurred.
     */
    @FXML
    public void modifyRouteWindowButton(ActionEvent event) throws IOException {
        Route route = (Route) routeList.getSelectionModel().getSelectedItem();
        routeSplitPane.setVisible(false);
        routeList.setVisible(false);
        routeAirlineMod.setText(route.getAirline());
        routeAirlineIDMod.setText(String.valueOf(route.getAirlineId()));
        routeSourceMod.setText(route.getSourceAirport());
        routeSourceIDMod.setText(String.valueOf(route.getSourceID()));
        routeDestMod.setText(route.getDestAirport());
        routeDestIDMod.setText(String.valueOf(route.getDestID()));
        routeEquipmentMod.setText(route.getEquipment());
        routeStopsMod.setText(String.valueOf(route.getNumStops()));
        routeCodeShareMod.setSelected(route.isCodeshare());
        modifyRoutePane.setVisible(true);
    }

    /**
     * Permanently removes the selected route from the route database.
     *
     * @param event The user selects an route and presses the deleteRouteButton.
     * @throws IOException Signals that an I/O exception of some sort has occurred.
     */
    @FXML
    public void deleteRouteButton(ActionEvent event) throws IOException {
        if (DialogBoxes.confirmationAlert("delete route")) {
            Route route = (Route) routeList.getSelectionModel().getSelectedItem();
            currentRecord.removeRoutes(route);
            ManipulateDatabase.removeData(route.getId(), route.getRecordName(), "routes");
            modifyRoutePane.setVisible(false);
            modifyRouteWindowButton.setVisible(false);
            routeSplitPane.setVisible(true);
            routeList.setVisible(true);
            displayAllRoutes();
            additionalRouteInfo();
        }
    }

    /**
     * Runs through the changes made to an route and checks whether all inputs for the newly modified
     * route are of the right type and format.
     *
     * If they are not, the program will notify the user about which input(s) are invalid and need to
     * be changed.
     *
     * @param event The user has filled in the modifyRoute section with the changes they would like to
     *              make and have pressed the modifyRouteButton.
     * @throws IOException Signals that an I/O exception of some sort has occurred.
     */
    @FXML
    public void modifyRouteButton(ActionEvent event) throws IOException {
        ArrayList<String> errors = new ArrayList<>();
        Route route = (Route) routeList.getSelectionModel().getSelectedItem();

        int id = route.getId();
        int airlineId = route.getAirlineId();

        String airline = routeAirlineMod.getText().trim();
        if (airline.equals("") || airline.length() != 2){
            errors.add("Invalid Airline IATA");
        }

        String sourceAirport = routeSourceMod.getText().trim();
        if (sourceAirport.equals("") || sourceAirport.length() != 3) {
            errors.add("Invalid Source Airport IATA");
        }

        int sourceID = 0;
        try {
            sourceID = Integer.parseInt(routeSourceIDMod.getText().trim());
        } catch (Exception e) {
            errors.add("Invalid Source Airport IATA");
        }

        String destAirport = routeDestMod.getText().trim();
        if (destAirport.equals("") || destAirport.length() != 3) {
            errors.add("Invalid Destination Airport IATA");
        }

        int destID = 0;
        try {
            destID = Integer.parseInt(routeDestIDMod.getText().trim());
        } catch (Exception e) {
            errors.add("Invalid Destination Airport ID");
        }

        int numStops = 0;
        try {
            numStops = Integer.parseInt(routeStopsMod.getText().trim());
        } catch (Exception e) {
            errors.add("Invalid Number of Stops");
        }

        String equipment = routeEquipmentMod.getText().trim();
        if (equipment.equals("")) {
            errors.add("Invalid Equipment Name");
        }

        boolean codeshare = false;
        if (routeCodeShareMod.isSelected()) {
            codeshare = true;
        }

        if (errors.size() == 0) {
            Route newRoute = new Route(id, airline, airlineId, sourceAirport, sourceID, destAirport, destID, numStops, equipment, codeshare);
            newRoute.setRecordName(route.getRecordName());
            System.out.println(newRoute.getRecordName());
            currentRecord.modifyRoute(route, newRoute);
            modifyRoutePane.setVisible(false);
            modifyRouteWindowButton.setVisible(false);
            routeSplitPane.setVisible(true);
            routeList.setVisible(true);
            displayAllRoutes();
        } else {
            DialogBoxes.newDataError(errors);
        }
    }

    /**
     * Overlays a airport over the route tab in which the user can make their desired
     * changes to the selected airport.
     *
     * @param event The user selects an route and then the modifyAirportWindowButton.
     * @throws IOException Signals that an I/O exception of some sort has occurred.
     */
    @FXML
    public void modifyAirportWindowButton(ActionEvent event) throws IOException {
        Airport airport = (Airport) airportList.getSelectionModel().getSelectedItem();
        airportSplitPane.setVisible(false);
        airportList.setVisible(false);
        modifyAirportLabel.setText("Modify Airport (ID: " + airport.getId() + ")");
        airportNameMod.setText(airport.getName());
        airportCityMod.setText(airport.getCity());
        airportCountryMod.setText(airport.getCountry());
        airportIATAMod.setText(airport.getIata());
        airportICAOMod.setText(airport.getIcao());
        airportTimezoneStringMod.setText(airport.getTimezoneString());
        airportTimezoneMod.setText(String.valueOf(airport.getTimezone()));
        airportDSTMod.setText(airport.getDst());
        airportLatitudeMod.setText(String.valueOf(airport.getLatitude()));
        airportLongitudeMod.setText(String.valueOf(airport.getLongitude()));
        airportAltitudeMod.setText(String.valueOf(airport.getAltitude()));
        modifyAirportPane.setVisible(true);
    }

    /**
     * Permanently removes the selected airport from the airport database.
     *
     * @param event The user selects an airport and presses the deleteAirportButton.
     * @throws IOException Signals that an I/O exception of some sort has occurred.
     */
    @FXML
    public void deleteAirportButton(ActionEvent event) throws IOException {
        if (DialogBoxes.confirmationAlert("delete airport")) {
            Airport airport = (Airport) airportList.getSelectionModel().getSelectedItem();
            currentRecord.removeAirports(airport);
            ManipulateDatabase.removeData(airport.getId(), airport.getRecordName(), "airports");
            modifyAirportPane.setVisible(false);
            modifyAirportWindowButton.setVisible(false);
            airportSplitPane.setVisible(true);
            airportList.setVisible(true);
            displayAllAirports();
            additionalAirportInfo();
        }
    }

    /**
     * Runs through the changes made to an airport and checks whether all inputs for the newly modified
     * airport are of the right type and format.
     *
     * If they are not, the program will notify the user about which input(s) are invalid and need to
     * be changed.
     *
     * @param event The user has filled in the modifyAirport section with the changes they would like to
     *              make and have pressed the modifyAirportButton.
     * @throws IOException Signals that an I/O exception of some sort has occurred.
     */
    @FXML
    public void modifyAirportButton(ActionEvent event) throws IOException {
        Airport airport = (Airport) airportList.getSelectionModel().getSelectedItem();
        int airportIndex = currentRecord.getAirportList().indexOf(airport);
        currentRecord.removeAirports(airport);

        int id = airport.getId();
        String name = airportNameMod.getText().trim();
        String city = airportCityMod.getText().trim();
        String country = airportCountryMod.getText().trim();
        String iata = airportIATAMod.getText().trim();
        String icao = airportICAOMod.getText().trim();
        String latitude = airportLatitudeMod.getText().trim();
        String longitude = airportLongitudeMod.getText().trim();
        String altitude = airportAltitudeMod.getText().trim();
        String timezone = airportTimezoneMod.getText().trim();
        String dst = airportDSTMod.getText().trim();
        String timezoneString = airportTimezoneStringMod.getText().trim();

        ArrayList<String> errors = dataChecker.checkAirport(currentRecord, name, city, country, iata, icao, latitude, longitude, altitude, timezone, dst, timezoneString);

        int numRoutesSource = airport.getNumRoutesSource();
        int numRoutesDest = airport.getNumRoutesDest();

        if (errors.size() == 0) {
            Airport newAirport = new Airport(id, name, city, country, iata, icao, Double.parseDouble(latitude), Double.parseDouble(longitude), Integer.parseInt(altitude), Double.parseDouble(timezone), dst, timezoneString, numRoutesSource, numRoutesDest);
            currentRecord.modifyAirport(airportIndex, newAirport);
            modifyAirportPane.setVisible(false);
            modifyAirportWindowButton.setVisible(false);
            airportSplitPane.setVisible(true);
            airportList.setVisible(true);
            displayAllAirlines();
            additionalAirlineInfo();
            displayAllAirports();
            additionalAirportInfo();
        } else {
            DialogBoxes.newDataError(errors);
        }
    }

    /**
     * Opens the user's default browser to the flight planner website upon clicking on the hyperlink in the
     * Help tab of the GUI.
     *
     * @param event The user has clicked on the hyperlink.
     * @throws IOException Signals that an I/O exception of some sort has occurred.
     */
    @FXML
    public void hyperLink (ActionEvent event) throws IOException {
        if(Desktop.isDesktopSupported())
        {
            try {
                Desktop.getDesktop().browse(new URI("https://flightplandatabase.com/planner"));
            } catch (IOException e1) {
                e1.printStackTrace();
            } catch (URISyntaxException e1) {
                e1.printStackTrace();
            }
        }
    }

    @FXML
    /**
     * Sets the information TextArea for data attributes on the Help tab of the GUI after a user selects
     * an attribute from the ChoiceBox and clicks on the Select button.
     *
     * @param event The user has clicked on the button and selected an item from the dropdown.
     * @throws IOException Signals that an I/O exception of some sort has occurred.
     */
    public void helpButton(ActionEvent event) throws IOException {
        if (helpDropdown.getValue() == "Airport - ID") {
            helpTextArea.setText("OpenFlights and PlaneSonar25's ID for the airport. Must be an integer.");
        } else if (helpDropdown.getValue() == "Airport - Name") {
            helpTextArea.setText("The name of the airport.");
        } else if (helpDropdown.getValue() == "Airport - City") {
            helpTextArea.setText("The main city served by the airport.");
        } else if (helpDropdown.getValue() == "Airport - Country") {
            helpTextArea.setText("The name of the country in which the airport is located.");
        } else if (helpDropdown.getValue() == "Airport - IATA Code") {
            helpTextArea.setText("Unique 3 letter airport code defined by the International Air Transport Association.");
        } else if (helpDropdown.getValue() == "Airport - ICAO Code") {
            helpTextArea.setText("Unique 4 letter airport code defined by the International Civil Aviation Organization.");
        } else if (helpDropdown.getValue() == "Airport - Timezone") {
            helpTextArea.setText("A textual representation of the timezone in which the airport is located. For example, Pacific/Auckland.");
        } else if (helpDropdown.getValue() == "Airport - Timezone Offset") {
            helpTextArea.setText("The amount of hours the airport's timezone is offset by. Can be positive or negative. For example, -12. Must be a number.");
        } else if (helpDropdown.getValue() == "Airport - DST") {
            helpTextArea.setText("The 1 letter daylight savings code used by OpenFlights.");
        } else if (helpDropdown.getValue() == "Airport - Latitude") {
            helpTextArea.setText("The latitude location of the airport in decimal degrees. Must be a number between -90 and 90.");
        } else if (helpDropdown.getValue() == "Airport - Longitude") {
            helpTextArea.setText("The longitude location of the airport in decimal degrees. Must be a number between -180 and 180.");
        } else if (helpDropdown.getValue() == "Airport - Altitude") {
            helpTextArea.setText("The altitude location of the airport in feet. Must be an integer.");
        } else if (helpDropdown.getValue() == "Airport - Altitude") {
            helpTextArea.setText("The altitude location of the airport in feet. Must be an integer.");
        } else if (helpDropdown.getValue() == "Airline - ID") {
            helpTextArea.setText("OpenFlights and PlaneSonar25's ID for the airline. Must be an integer.");
        } else if (helpDropdown.getValue() == "Airline - Name") {
            helpTextArea.setText("The name of the airline.");
        } else if (helpDropdown.getValue() == "Airline - Alias") {
            helpTextArea.setText("Common alternate name(s) of the airline.");
        } else if (helpDropdown.getValue() == "Airline - Callsign") {
            helpTextArea.setText("Callsign code that identifies the airline.");
        } else if (helpDropdown.getValue() == "Airline - Active") {
            helpTextArea.setText("Whether the airline is currently active or not.");
        } else if (helpDropdown.getValue() == "Airline - IATA Code") {
            helpTextArea.setText("Unique 2 letter airline code defined by the International Air Transport Association.");
        } else if (helpDropdown.getValue() == "Airline - ICAO Code") {
            helpTextArea.setText("Unique 3 letter airline code defined by the International Civil Aviation Organization.");
        } else if (helpDropdown.getValue() == "Airline - Country") {
            helpTextArea.setText("The name of the country of origin of the airline.");
        } else if (helpDropdown.getValue() == "Airline - Country") {
            helpTextArea.setText("The name of the country of origin of the airline.");
        } else if (helpDropdown.getValue() == "Route - Airline Code") {
            helpTextArea.setText("2 letter IATA or 3 Letter ICAO code of the airline operating the flight route.");
        } else if (helpDropdown.getValue() == "Route - Source Airport Code") {
            helpTextArea.setText("3 letter IATA or 4 Letter ICAO code of the source airport of the route.");
        } else if (helpDropdown.getValue() == "Route - Destination Airport Code") {
            helpTextArea.setText("3 letter IATA or 4 Letter ICAO code of the destination airport of the route.");
        } else if (helpDropdown.getValue() == "Route - Equipment") {
            helpTextArea.setText("3 letter code(s) of plane types used on the route.");
        } else if (helpDropdown.getValue() == "Route - Number of Stops") {
            helpTextArea.setText("The number of stops the flight route takes before reaching its destination. Commonly 0. Must be an integer.");
        } else if (helpDropdown.getValue() == "Route - Codeshare") {
            helpTextArea.setText("Indicates if the route is a codeshare or not (route operated by an airline or not).");
        }
    }
}
