package project.controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import project.model.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;
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
    private WebView webView;

    @FXML
    private WebView mapView;

    @FXML
    private TextField airportID;
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
    private TextField airlineID;
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
    private TextField routeAirlineID;
    @FXML
    private TextField routeSource;
    @FXML
    private TextField routeSourceID;
    @FXML
    private TextField routeDest;
    @FXML
    private TextField routeDestID;
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
    private Button modifyAirportWindowButton;

    @FXML
    private Button modifyAirlineWindowButton;

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

    WebEngine mapEngine;

    /**
     * Stuff to do on setup
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        recordList = new ArrayList<Record>();
        currentRecord = Database.generateRecord();
        currentRecord.setName("Record 1");
        recordList.add(currentRecord);

        recordSelectAirport.setItems(observableArrayList(currentRecord.getName()));
        recordSelectAirport.getSelectionModel().selectFirst();
        recordSelectAirline.setItems(observableArrayList(currentRecord.getName()));
        recordSelectAirline.getSelectionModel().selectFirst();
        recordSelectRoute.setItems(observableArrayList(currentRecord.getName()));
        recordSelectRoute.getSelectionModel().selectFirst();

        recordDropdown.setItems(observableArrayList(currentRecord.getName(), "New Record"));
        recordDropdown.getSelectionModel().selectFirst();

        WebEngine mapEngine = mapView.getEngine();
        mapEngine.load(getClass().getResource("/map.html").toExternalForm());



        WebEngine engine = webView.getEngine();
        engine.load("https://openflights.org/data.html");

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
    }

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

        currentRecord = recordList.get(index);
    }

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

        currentRecord = recordList.get(index);
    }

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

        currentRecord = recordList.get(index);
    }

    @FXML
    /**
     * Retrieves user input and searches for airports that match the provided criteria in the selected attribute.
     * Displays only those airports in the airport data viewer.
     * If input is blank, display an error pop-up.
     */
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



    @FXML
    public void leastRoutesButton(ActionEvent event) throws IOException {
        List<Airport> currentData = airportList.getItems();
        List<Airport> rankedAirports = currentRecord.rankAirports(false, currentData);
        airportList.setItems(observableArrayList(rankedAirports));
    }

    @FXML
    public void mostRoutesButton(ActionEvent event) throws IOException {
        List<Airport> currentData = airportList.getItems();
        List<Airport> rankedAirports = currentRecord.rankAirports(true, currentData);
        airportList.setItems(observableArrayList(rankedAirports));
    }

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
            ArrayList<String> recordNames = new ArrayList<String>();
            for (Record records: recordList) {
                recordNames.add(records.getName());
            }
            recordNames.add("New Record");
            recordDropdown.setItems(observableArrayList(recordNames));

            recordSelectAirline.getSelectionModel().select(recordList.size() - 1);
            recordSelectAirport.getSelectionModel().select(recordList.size() - 1);
            recordSelectRoute.getSelectionModel().select(recordList.size() - 1);
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
        recordDropdown.getSelectionModel().select(index);

        currentRecord = recordList.get(index);
    }

    @FXML
    /**
     * Can't handle errors yet, and doesn't have the option to append data to new record yet
     */
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
                    DialogBoxes.fileFormatInfo(airportCheck, true, "airport");
                    if (!airportCheck) {return;}

                    addFileHelper();

                    ArrayList<Airport> newAirportList = airportLoad.loadAirportFile(file.getAbsolutePath());
                    currentRecord.addAirports(newAirportList);
                    hideAllTables();
                    if (Airport.getNumMissingCovid() > 0) {
                        DialogBoxes.missingCovidInfoBox();
                    }
                } else if (selectFile.getSelectedToggle() == airlineRadioButton) {
                    boolean airlineCheck = airlineLoad.loadAirlineErrorCheck(file.getAbsolutePath());
                    DialogBoxes.fileFormatInfo(airlineCheck, true, "airline");
                    if (!airlineCheck) {return;}

                    addFileHelper();

                    ArrayList<Airline> newAirlineList = airlineLoad.loadAirlineFile(file.getAbsolutePath());
                    currentRecord.addAirlines(newAirlineList);
                    hideAllTables();
                } else if (selectFile.getSelectedToggle() == routeRadioButton) {
                    boolean routeCheck = routeLoad.loadRouteErrorCheck(file.getAbsolutePath());
                    DialogBoxes.fileFormatInfo(routeCheck, true, "route");
                    if (!routeCheck) {return;}

                    addFileHelper();

                    ArrayList<Route> newRouteList = routeLoad.loadRouteFile(file.getAbsolutePath());
                    currentRecord.addRoutes(newRouteList);
                    hideAllTables();
                } else if (selectFile.getSelectedToggle() == flightRadioButton) {
                    boolean flightCheck = flightLoad.loadFlightErrorCheck(file.getAbsolutePath());
                    DialogBoxes.fileFormatInfo(flightCheck, true, "flight");
                    if (!flightCheck) {return;}

                    addFileHelper();

                    Flight newFlight = flightLoad.loadFlightFile(file.getAbsolutePath());
                    currentRecord.addFlights(newFlight);
                    Parent root = FXMLLoader.load(getClass().getResource("../Flight_Screen.fxml"));
                    Stage stage = new Stage();
                    stage.setScene(new Scene(root, 750, 500));
                    stage.show();
                }
            }
        }
    }

    @FXML
    /**
     * Can't handle errors yet, and doesn't have the option to append data to new record yet.
     * Also doesn't have confirmation on when files are successfully loaded.
     */
    public void addAirportButton() {
        addFileHelper();

        ArrayList<String> errors = new ArrayList<String>();

        int id = 0;
        try {
            id = Integer.parseInt(airportID.getText().trim());
            if (id <= currentRecord.getAirportList().size()) {
                errors.add("Invalid ID (Must be greater than " + currentRecord.getAirportList().size() + ")");
            }
        } catch (Exception e) {
            errors.add("Invalid ID");
        }

        String name = airportName.getText().trim();
        if (name.equals("") || !name.matches("^[a-zA-Z]*$")) {
            errors.add("Invalid Airport Name");
        }

        String city = airportCity.getText().trim();
        if (city.equals("") || !city.matches("^[a-zA-Z]*$")) {
            errors.add("Invalid City Name");
        }

        String country = airportCountry.getText().trim();
        if (country.equals("") || !country.matches("^[a-zA-Z]*$")) {
            errors.add("Invalid Country Name");
        }

        String iata = airportIATA.getText().trim();
        if (iata.equals("") || !iata.matches("[a-zA-Z0-9]*")) {
            errors.add("Invalid IATA Code");
        }

        String icao = airportICAO.getText().trim();
        if (iata.equals("") || !iata.matches("[a-zA-Z0-9]*")) {
            errors.add("Invalid ICAO Code");
        }

        double latitude = 0;
        try {
            latitude = Double.parseDouble(airportLatitude.getText().trim());
            if (latitude < 0 || latitude > 360) {
                errors.add("Invalid Latitude (Must be lesser than 360)");
            }
        } catch (Exception e) {
            errors.add("Invalid Latitude");
        }

        double longitude = 0;
        try {
            longitude = Double.parseDouble(airportLongitude.getText().trim());
            if (longitude < 0 || longitude > 360) {
                errors.add("Invalid Longitude (Must be lesser than 360)");
            }
        } catch (Exception e) {
            errors.add("Invalid Longitude");
        }

        int altitude = 0;
        try {
            altitude = Integer.parseInt(airportAltitude.getText().trim());
        } catch (Exception e) {
            errors.add("Invalid Altitude");
        }

        double timezone = 0;
        try {
            timezone = Double.parseDouble(airportTimezone.getText().trim());
            if (timezone < 0 || timezone > 25) {
                errors.add("Invalid Timezone Number(Must be less than 25)");
            }
        } catch (Exception e) {
            errors.add("Invalid Timezone Number");
        }

        String dst = airportDST.getText().trim();
        if (dst.equals("")) {
            errors.add("Invalid DST");
        }

        String timezoneString = airportTimezoneString.getText().trim();
        if (dst.equals("")) {
            errors.add("Invalid Timezone Name");
        }

        String type = airportType.getText().trim();
        if (type.equals("")) {
            type = null;
        }

        String source = airportSource.getText().trim();
        if (source.equals("")) {
            source = null;
        }

        int numRoutesSource = 0;
        int numRoutesDest = 0;
        int risk = 0;

        if (errors.size() == 0) {
            Airport newAirport = new Airport(id, risk, name, city, country, iata, icao, latitude, longitude, altitude, timezone, dst, timezoneString, type, source, numRoutesSource, numRoutesDest);
            ArrayList<Airport> newAirportList = new ArrayList<Airport>();
            newAirportList.add(newAirport);
            currentRecord.addAirports(newAirportList);
        } else {
            DialogBoxes.newDataError(errors);
        }
        hideAllTables();
    }

    @FXML
    /**
     * Can't handle errors yet, and doesn't have the option to append data to new record yet.
     * Also doesn't have confirmation on when files are successfully loaded.
     */
    public void addAirlineButton(ActionEvent event) throws IOException {
        addFileHelper();
        ArrayList<String> errors = new ArrayList<>();

        int id = 0;
        try {
            id = Integer.parseInt(airlineID.getText().trim());
            if (id <= currentRecord.getAirlineList().size()) {
                errors.add("Invalid ID (Must be greater than " + currentRecord.getAirlineList().size() + ")");
            }
        } catch (Exception e) {
            errors.add("Invalid ID");
        }

        String name = airlineName.getText().trim();
        if (name.equals("")) {
            errors.add("Invalid Airline Name");
        }

        boolean active = false;
        if (airlineActive.isSelected()) {
            active = true;
        }

        String country = airlineCountry.getText().trim();
        if (country.equals("") || !country.matches("^[a-zA-Z]*$")) {
            errors.add("Invalid Country Name");
        }

        String alias = airlineAlias.getText().trim();
        if (name.equals("")) {
            alias = null;
        }

        String callSign = airlineCallsign.getText().trim();
        if (name.equals("")) {
            callSign = null;
        }

        String iata = airlineIATA.getText().trim();
        if (iata.equals("") || !iata.matches("[a-zA-Z0-9]*")) {
            errors.add("Invalid IATA Code");
        }

        String icao = airlineICAO.getText().trim();
        if (icao.equals("") || !icao.matches("[a-zA-Z0-9]*")) {
            errors.add("Invalid ICAO Code");
        }

        if (errors.size() == 0) {
            Airline newAirline = new Airline(id, name, active, country, alias, callSign, iata, icao);
            ArrayList<Airline> newAirlineList = new ArrayList<Airline>();
            newAirlineList.add(newAirline);
            currentRecord.addAirlines(newAirlineList);
        } else {
            DialogBoxes.newDataError(errors);
        }

        hideAllTables();
    }

    @FXML
    /**
     * Can't handle errors yet, and doesn't have the option to append data to new record yet.
     * Also doesn't have confirmation on when files are successfully loaded.
     */
    public void addRouteButton(ActionEvent event) throws IOException {
        addFileHelper();
        ArrayList<String> errors = new ArrayList<>();


        String airline = routeAirline.getText().trim();
        if (airline.equals("")) {
            errors.add("Invalid Airline Name");
        }

        int id = 0;
        try {
            id = Integer.parseInt(routeAirlineID.getText().trim());
            if  (!(id >= 0 && id <= currentRecord.getAirlineList().size())) {
                errors.add("Invalid Airline ID (Must be within 0 and " + currentRecord.getAirlineList().size() + ")");
            }
        } catch (Exception e) {
            errors.add("Invalid Airline ID");
        }

        String sourceAirport = routeSource.getText().trim();
        if (sourceAirport.equals("")) {
            errors.add("Invalid Source Airport Name");
        }

        int sourceID = 0;
        try {
            sourceID = Integer.parseInt(routeSourceID.getText().trim());
            if  (!(sourceID >= 0 && sourceID <= currentRecord.getAirportList().size())) {
                errors.add("Invalid Source Airport ID (Must be within 0 and " + currentRecord.getAirportList().size() + ")");
            }
        } catch (Exception e) {
            errors.add("Invalid Source Airport ID");
        }

        String destAirport = routeDest.getText().trim();
        if (destAirport.equals("")) {
            errors.add("Invalid Destination Airport Name");
        }

        int destID = 0;
        try {
            destID = Integer.parseInt(routeDestID.getText().trim());
            if  (!(destID >= 0 && destID <= currentRecord.getAirportList().size())) {
                errors.add("Invalid Destination Airport ID (Must be within 0 and " + currentRecord.getAirportList().size() + ")");
            }
        } catch (Exception e) {
            errors.add("Invalid Destination Airport ID");
        }

        int numStops = 0;
        try {
            numStops = Integer.parseInt(routeStops.getText().trim());
        } catch (Exception e) {
            errors.add("Invalid Number of Stops");
        }

        String equipment = routeEquipment.getText().trim();
        if (equipment.equals("")) {
            errors.add("Invalid Equipment Name");
        }

        boolean codeshare = false;
        if (routeCodeShare.isSelected()) {
            codeshare = true;
        }

        if (errors.size() == 0) {
            Route newRoute = new Route(airline, id, sourceAirport, sourceID, destAirport, destID, numStops, equipment, codeshare);
            ArrayList<Route> newRouteList = new ArrayList<Route>();
            newRouteList.add(newRoute);
            currentRecord.addRoutes(newRouteList);
        } else {
            DialogBoxes.newDataError(errors);
        }

        hideAllTables();
    }

    /**
     * Makes the airport data viewer display every airport in the current record.
     */
    public void displayAllAirports() {
        airportList.setItems(observableArrayList(currentRecord.getAirportList()));
        recordSelectAirport.getSelectionModel().select(currentRecord.getName());

    }

    public void hideAllTables() {
        routeList.setItems(observableArrayList());
        airlineList.setItems(observableArrayList());
        airportList.setItems(observableArrayList());
        airportDetailList.setItems(observableArrayList());
        airlineDetailList.setItems(observableArrayList());
        routeDetailList.setItems(observableArrayList());
    }

    public void displayAllAirlines() {
        airlineList.setItems(observableArrayList(currentRecord.getAirlineList()));
        recordSelectAirline.getSelectionModel().select(currentRecord.getName());
        airlineActiveBox.setSelected(true);
        airlineInactiveBox.setSelected(true);
        defaultAirlineList = airlineList.getItems();
    }


    public void displayAllRoutes() {
        routeList.setItems(observableArrayList(currentRecord.getRouteList()));
        recordSelectRoute.getSelectionModel().select(currentRecord.getName());
        routeDirectBox.setSelected(true);
        routeIndirectBox.setSelected(true);
        defaultRouteList = routeList.getItems();
    }

    @FXML
    public void setOptedIn() {
        if (extraInfoBox.isSelected()) {
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

            String name = String.format("Name: %s", airport.getName());
            String location = String.format("Location: %s, %s", airport.getCity(), airport.getCountry());
            String risk = String.format("COVID risk level: %s (%.2f%%)", airport.getRiskString(), airport.getRisk());

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

            airportDetailList.setItems(observableArrayList(name, location, risk, numRoutes, timezone, distanceString));
            modifyAirportWindowButton.setVisible(true);

            if (optedIn) {

                String lat;
                if (airport.getLatitude() == 360) {
                    lat = ("Latitude: Unknown");
                } else {
                    lat = ("Latitude: " + airport.getLatitude() + " decimal degrees");
                }

                String lon;
                if (airport.getLongitude() == 360) {
                    lon = ("Longitude: Unknown");
                } else {
                    lon = ("Longitude: " + airport.getLongitude() + " decimal degrees");
                }

                String alt;
                if (airport.getAltitude() == -1) {
                    alt = ("Altitude: Unknown");
                } else {
                    alt = ("Altitude: " + airport.getAltitude() + " feet");
                }

                String iata = String.format("IATA code: %s", airport.getIata());
                String icao = String.format("ICAO code: %s", airport.getIcao());

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


    @FXML
    /**
     * Displays additional information about a selected route in the detail panel.
     * If the user has opted in, displays further additional information
     */
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
     *
     * Modifying Airlines is working but not the best way to code
     * Using Panes rather than windows
     *
     */

    @FXML
    public void deleteAirlineButton(ActionEvent event) throws IOException {
        Airline airline = (Airline) airlineList.getSelectionModel().getSelectedItem();
        currentRecord.removeAirlines(airline);
        modifyAirlinePane.setVisible(false);
        modifyAirlineWindowButton.setVisible(false);
        airlineSplitPane.setVisible(true);
        airlineList.setVisible(true);
        displayAllAirlines();
        additionalAirlineInfo();
    }

    @FXML
    public void modifyAirlineButton(ActionEvent event) throws IOException {
        ArrayList<String> errors = new ArrayList<>();
        Airline airline = (Airline) airlineList.getSelectionModel().getSelectedItem();

        int id = airline.getId();

        String name;
        try {
            name = airlineNameMod.getText().trim();
            if (name.equals("")) {
                errors.add("Invalid Airline Name");
            }
        } catch (Exception e) {
            name = null;
        }

        boolean active = false;
        if (airlineActiveMod.isSelected()) {
            active = true;
        }

        String country;
        try {
            country = airlineCountryMod.getText().trim();
            if (country.equals("")) {
                errors.add("Invalid Country Name");
            }
        } catch (Exception e) {
            country = null;
        }

        String alias;
        try {
            alias = airlineAliasMod.getText().trim();
        } catch (Exception e) {
            alias = null;
        }

        String callSign;
        try {
            callSign = airlineCallsignMod.getText().trim();
        } catch (Exception e) {
            callSign = null;
        }

        String iata;
        try {
            iata = airlineIATAMod.getText().trim();
            if (iata.equals("") || !iata.matches("[a-zA-Z0-9]*")) {
                iata = null;
            }
        } catch (Exception e) {
            iata = null;
        }

        String icao;
        try {
            icao = airlineICAOMod.getText().trim();
            if (icao.equals("") || !icao.matches("[a-zA-Z0-9]*")) {
                icao = null;
            }
        } catch (Exception e) {
            icao = null;
        }

        if (errors.size() == 0) {
            Airline newAirline = new Airline(id, name, active, country, alias, callSign, iata, icao);
            currentRecord.modifyAirline(airline, newAirline);
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

    @FXML
    public void modifyAirlineWindowButton(ActionEvent event) throws IOException {
        Airline airline = (Airline) airlineList.getSelectionModel().getSelectedItem();
        airlineSplitPane.setVisible(false);
        airlineList.setVisible(false);
        modifyAirlineLabel.setText("Modify Airline (" + airline.getId() + ")");
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
     * work in progress
     * These open a new window but isn't working the way I want it to :(
     *
     */
    @FXML
    public void modifyRouteWindowButton(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../Modify_Route_Screen.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(root, 750, 500));
        stage.show();
    }

    @FXML
    public void modifyAirportWindowButton(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../Modify_Airport_Screen.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(root, 750, 500));
        stage.show();
    }
}
