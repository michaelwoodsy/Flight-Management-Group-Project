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
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import project.model.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.*;
import javafx.stage.Modality;

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
    private ChoiceBox recordDropdown;
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
    private Text fileText;
    @FXML
    private ChoiceBox recordSelectAirport;
    @FXML
    private ChoiceBox recordSelectAirline;
    @FXML
    private ChoiceBox recordSelectRoute;

    private ArrayList<Record> recordList;
    private Record currentRecord;
    private boolean optedIn = false;
    private Loader loader = new Loader();
    private List<Airline> defaultAirlineList = new ArrayList<>();
    private List<Route> defaultRouteList = new ArrayList<>();
    private Airport lastSelectedAirport = null;

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

        ObservableList<String> filterAirlines = observableArrayList("Countries");
        ObservableList<String> searchAirlines = observableArrayList("Name", "Alias", "Callsign", "IATA", "ICAO");

        // Commented out some code related to the original search button. Now needs to be integrated into the filter button.
        airlineFilterBy.setItems(filterAirlines);
        //airlineSearchBy.setItems(searchAirlines);

        ObservableList<String> filterAirports = observableArrayList("Countries");
        ObservableList<String> searchAirports = observableArrayList("Name", "City", "IATA", "ICAO", "Timezone", "Total # Routes");

        airportFilterBy.setItems(filterAirports);
        //airportSearchBy.setItems(searchAirports);

        ObservableList<String> filterRoutes = observableArrayList("Source Airport", "Destination Airport", "Equipment");
        ObservableList<String> searchRoutes = observableArrayList("Airline", "Total # Stops", "Source ID", "Destination ID");

        routeFilterBy.setItems(filterRoutes);
        //routeSearchBy.setItems(searchRoutes);

        //routeSearchBy.getSelectionModel().selectFirst();
        routeFilterBy.getSelectionModel().selectFirst();
        //airportSearchBy.getSelectionModel().selectFirst();
        airportFilterBy.getSelectionModel().selectFirst();
        //airlineSearchBy.getSelectionModel().selectFirst();
        airlineFilterBy.getSelectionModel().selectFirst();
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
     * Retrieves user input and searches for airports that are located within that country.
     * Displays only those airports in the airport data viewer.
     * If input is blank, display an error pop-up.
     */
    public void filterAirports() {

        String country = airportFilterCriteria.getText();
        if (country.isBlank()) {
            System.err.println("No country entered");
        }
        ArrayList<Airport> filteredAirports = currentRecord.filterAirports(country);
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
        ArrayList<Airline> filteredAirlines = currentRecord.filterAirlinesCountry(country);
        airlineList.setItems(observableArrayList(filteredAirlines));
        defaultAirlineList = airlineList.getItems();
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

    @FXML
    public void filterRoutes(ActionEvent event) throws IOException {

        routeDirectBox.setSelected(true);
        routeIndirectBox.setSelected(true);

        if (routeFilterBy.getValue().equals("Source Airport")) {
            String departure = routeFilterCriteria.getText();
            if (departure.isBlank()) {
                System.err.println("No source airport code entered");
            }
            ArrayList<Route> filteredRoutes = currentRecord.filterRoutesDeparture(departure);
            routeList.setItems(observableArrayList(filteredRoutes));
            defaultRouteList = routeList.getItems();
        } else if (routeFilterBy.getValue().equals("Destination Airport")) {
            String destination = routeFilterCriteria.getText();
            if (destination.isBlank()) {
                System.err.println("No destination airport code entered");
            }
            ArrayList<Route> filteredRoutes = currentRecord.filterRoutesDestination(destination);
            routeList.setItems(observableArrayList(filteredRoutes));
            defaultRouteList = routeList.getItems();
        } else if (routeFilterBy.getValue().equals("Equipment")) {
            String equipment = routeFilterCriteria.getText();
            if (equipment.isBlank()) {
                System.err.println("No equipment entered");
            }
            ArrayList<Route> filteredRoutes = currentRecord.filterRoutesEquipment(equipment);
            routeList.setItems(observableArrayList(filteredRoutes));
            defaultRouteList = routeList.getItems();
        }
    }

    public void addFileHelper() {

        if (recordDropdown.getValue() == "New Record") {
            Record record = new Record("Record " + (recordList.size() + 1));
            recordList.add(record);
            currentRecord = record;

            recordSelectAirport.getItems().add(currentRecord.getName());
            recordSelectAirline.getItems().add(currentRecord.getName());
            recordSelectRoute.getItems().add(currentRecord.getName());
            recordDropdown.setItems(recordSelectRoute.getItems());
            recordDropdown.getItems().add("New Record");

            recordSelectAirline.getSelectionModel().select(currentRecord);
            recordSelectAirport.getSelectionModel().select(currentRecord);
            recordSelectRoute.getSelectionModel().select(currentRecord);
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
     * Can't handle errors yet, and doesn't have the option to append data to new record yet.
     * Also doesn't have confirmation on when files are successfully loaded.
     */
    public void addFileButton(ActionEvent event) throws IOException {

        if (newTab.getSelectionModel().getSelectedIndex() == 3) {
            FileChooser loadFile = new FileChooser();
            loadFile.getExtensionFilters().add(new FileChooser.ExtensionFilter("DAT Files", "*.dat"));
            loadFile.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));

            File file = loadFile.showOpenDialog(null);


            if (file != null) {
                boolean goodFile = loader.errorHandler(file);
                if (!goodFile) {
                    fileText.setText("Incorrect file format");
                    fileText.setFill(Color.RED);
                    fileText.setVisible(true);
                    return;
                }
                if (selectFile.getSelectedToggle() == airportRadioButton) {
                    boolean airportCheck = loader.loadAirportErrorCheck(file.getAbsolutePath());
                    if (!airportCheck) {
                        fileText.setText("Incorrect file format");
                        fileText.setFill(Color.RED);
                        fileText.setVisible(true);
                        return;
                    }

                    addFileHelper();

                    fileText.setText("File loaded successfully");
                    fileText.setFill(Color.DARKGREEN);
                    fileText.setVisible(true);
                    ArrayList<Airport> newAirportList = loader.loadAirportFile(file.getAbsolutePath());
                    currentRecord.addAirports(newAirportList);
                    if (Airport.getNumMissingCovid() > 0) {
                        DialogBoxes.missingCovidInfoBox();
                    }
                } else if (selectFile.getSelectedToggle() == airlineRadioButton) {
                    boolean airlineCheck = loader.loadAirlineErrorCheck(file.getAbsolutePath());
                    if (!airlineCheck) {
                        fileText.setText("Incorrect file format");
                        fileText.setFill(Color.RED);
                        fileText.setVisible(true);
                        return;
                    }

                    addFileHelper();

                    fileText.setText("File loaded successfully");
                    fileText.setFill(Color.DARKGREEN);
                    fileText.setVisible(true);
                    ArrayList<Airline> newAirlineList = loader.loadAirlineFile(file.getAbsolutePath());
                    currentRecord.addAirlines(newAirlineList);
                } else if (selectFile.getSelectedToggle() == routeRadioButton) {
                    boolean routeCheck = loader.loadRouteErrorCheck(file.getAbsolutePath());
                    if (!routeCheck) {
                        fileText.setText("Incorrect file format");
                        fileText.setFill(Color.RED);
                        fileText.setVisible(true);
                        return;
                    }

                    addFileHelper();

                    fileText.setText("File loaded successfully");
                    fileText.setFill(Color.DARKGREEN);
                    fileText.setVisible(true);
                    ArrayList<Route> newRouteList = loader.loadRouteFile(file.getAbsolutePath());
                    currentRecord.addRoutes(newRouteList);
                } else if (selectFile.getSelectedToggle() == flightRadioButton) {
                    boolean flightCheck = loader.loadFlightErrorCheck(file.getAbsolutePath());
                    if (!flightCheck) {
                        fileText.setText("Incorrect file format");
                        fileText.setFill(Color.RED);
                        fileText.setVisible(true);
                        return;
                    }

                    addFileHelper();

                    fileText.setText("File loaded successfully");
                    fileText.setFill(Color.DARKGREEN);
                    fileText.setVisible(true);
                    Flight newFlight = loader.loadFlightFile(file.getAbsolutePath());
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
    public void addAirportButton(ActionEvent event) throws IOException {
        int id = Integer.parseInt(airportID.getText());
        String name = airportName.getText();
        String city = airportCity.getText();
        String country = airportCountry.getText();
        String iata = airportIATA.getText();
        String icao = airportICAO.getText();
        double latitude = Double.parseDouble(airportLatitude.getText());
        double longitude = Double.parseDouble(airportLongitude.getText());
        int altitude = Integer.parseInt(airportAltitude.getText());
        double timezone = Double.parseDouble(airportTimezone.getText());
        String dst = airportDST.getText();
        String timezoneString = airportTimezoneString.getText();
        String type = airportType.getText();
        String source = airportSource.getText();
        int numRoutesSource = 0;
        int numRoutesDest = 0;
        int risk = 0;

        Airport newAirport = new Airport(id, risk, name, city, country, iata, icao, latitude, longitude, altitude, timezone, dst, timezoneString, type, source, numRoutesSource, numRoutesDest);
        ArrayList<Airport> newAirportList = new ArrayList<Airport>();
        newAirportList.add(newAirport);
        currentRecord.addAirports(newAirportList);
    }

    @FXML
    /**
     * Can't handle errors yet, and doesn't have the option to append data to new record yet.
     * Also doesn't have confirmation on when files are successfully loaded.
     */
    public void addAirlineButton(ActionEvent event) throws IOException {
        int id = Integer.parseInt(airlineID.getText());
        String name = airlineName.getText();
        boolean active = false;
        if (airlineActive.isSelected()) {
            active = true;
        }
        String country = airlineCountry.getText();
        String alias = airlineAlias.getText();
        String callSign = airlineCallsign.getText();
        String iata = airlineIATA.getText();
        String icao = airlineICAO.getText();

        Airline newAirline = new Airline(id, name, active, country, alias, callSign, iata, icao);
        ArrayList<Airline> newAirlineList = new ArrayList<Airline>();
        newAirlineList.add(newAirline);
        currentRecord.addAirlines(newAirlineList);
    }

    @FXML
    /**
     * Can't handle errors yet, and doesn't have the option to append data to new record yet.
     * Also doesn't have confirmation on when files are successfully loaded.
     */
    public void addRouteButton(ActionEvent event) throws IOException {
        String airline = routeAirline.getText();
        int id = Integer.parseInt(routeAirlineID.getText());
        String sourceAirport = routeSource.getText();
        int sourceID = Integer.parseInt(routeSourceID.getText());
        String destAirport = routeDest.getText();
        int destID = Integer.parseInt(routeDestID.getText());
        int numStops = Integer.parseInt(routeStops.getText());
        String equipment = routeEquipment.getText();
        boolean codeshare = false;
        if (routeCodeShare.isSelected()) {
            codeshare = true;
        }

        Route newRoute = new Route(airline, id, sourceAirport, sourceID, destAirport, destID, numStops, equipment, codeshare);
        ArrayList<Route> newRouteList = new ArrayList<Route>();
        newRouteList.add(newRoute);
        currentRecord.addRoutes(newRouteList);
    }

    /**
     * Makes the airport data viewer display every airport in the current record.
     */
    public void displayAllAirports() {
        airportList.setItems(observableArrayList(currentRecord.getAirportList()));
    }

    public void displayAllAirlines() {
        airlineList.setItems(observableArrayList(currentRecord.getAirlineList()));
        airlineActiveBox.setSelected(true);
        airlineInactiveBox.setSelected(true);
        defaultAirlineList = airlineList.getItems();
    }


    public void displayAllRoutes() {
        routeList.setItems(observableArrayList(currentRecord.getRouteList()));
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

            String name;
            if (airport.getName() == null) {
                name = ("Name: Unknown");
            } else {
                name = ("Name: " + airport.getName());
            }

            String city;
            if (airport.getCity() == null) {
                if (airport.getCountry() == null) {
                    city = ("Location: Unknown, Unknown");
                } else {
                    city = ("Location: Unknown, " + airport.getCountry());
                }
            } else {
                if (airport.getCountry() == null) {
                    city = ("Location: " + airport.getCity() + ", Unknown");
                } else {
                    city = ("Location: " + airport.getCity()+ ", " + airport.getCountry());
                }
            }

            String risk = ("COVID risk level: " + airport.getRisk());

            String numRoutes;
            if (airport.getTotalRoutes() < 1) {
                numRoutes = ("Warning: No flight routes go through this airport");
            } else {
                numRoutes = ("A total of " + airport.getTotalRoutes() + " flight routes go through this airport");
            }

            String timezoneNum;
            if (airport.getTimezone() == 25) {
                timezoneNum = "Unknown";
            } else if (airport.getTimezone() >= 0) {
                timezoneNum = "+" + airport.getTimezone();
            } else {
                timezoneNum = "-" + airport.getTimezone();
            }

            String timezone;
            if (airport.getTimezoneString() == null) {
                timezone = ("Timezone: Unknown, " + timezoneNum + " hours");
            } else {
                timezone = ("Timezone: " + airport.getTimezoneString() + ", " + timezoneNum + " hours");
            }

            //Gets the last selected airport, and calculates the distance between that airport and the currently selected one
            String distanceString = "No previous airport selected";
            if (lastSelectedAirport != null) {
                double distanceValue = airport.distance(lastSelectedAirport);
                distanceString = String.format("Distance between this airport and last selected airport (%s) is %.2f km", lastSelectedAirport.getName(), distanceValue);
            }
            lastSelectedAirport = airport;

            airportDetailList.setItems(observableArrayList(name, city, risk, numRoutes, timezone, distanceString));

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

                String iata;
                if (airport.getIata() == null) {
                    iata = ("IATA code: Unknown");
                } else {
                    iata = ("IATA code: " + airport.getIata());
                }

                String icao;
                if (airport.getIcao() == null) {
                    icao = ("ICAO code: Unknown");
                } else {
                    icao = ("ICAO code: " + airport.getIcao());
                }

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

            String name;
            if (airline.getName() == null) {
                name = ("Name: Unknown");
            } else {
                name = ("Name: " + airline.getName());
            }

            String country;
            if (airline.getCountry() == null) {
                country = ("Country of Origin: Unknown");
            } else {
                country = ("Country of Origin: " + airline.getCountry());
            }

            String active;
            if (airline.isActive()) {
                active = ("This Airline is currently in operation");
            } else {
                active = ("This Airline is no longer in operation");
            }

            String alias;
            if (airline.getAlias() == null) {
                alias = ("This airline has no known aliases");
            } else {
                alias = ("Airline known as: " + airline.getAlias());
            }

            airlineDetailList.setItems(observableArrayList(name, country, active, alias));

            if (optedIn) {
                String iata;
                if (airline.getIata() == null) {
                    iata = ("IATA code: Unknown");
                } else {
                    iata = ("IATA code: " + airline.getIata());
                }

                String icao;
                if (airline.getIcao() == null) {
                    icao = ("ICAO code: Unknown");
                } else {
                    icao = ("ICAO code: " + airline.getIcao());
                }

                String callSign;
                if (airline.getCallSign() == null) {
                    callSign = ("Callsign: Unknown");
                } else {
                    callSign = ("Callsign: " + airline.getCallSign());
                }

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

            String airline;
            if (route.getAirline() == null) {
                airline = ("Airline Code: Unknown");
            } else {
                airline = ("Airline Code: " + route.getAirline());
            }

            String numStops;
            if (route.getNumStops() == -1) {
                numStops = ("Number of stops: Unknown");
            } else {
                numStops = ("Number of stops: " + route.getNumStops());
            }

            String sourceAirport;
            if (route.getSourceAirport() == null) {
                sourceAirport = ("Source Airport Code: Unknown");
            } else {
                sourceAirport = ("Source Airport Code: " + route.getSourceAirport());
            }

            String destAirport;
            if (route.getDestAirport() == null) {
                destAirport = ("Destination Airport Code: Unknown");
            } else {
                destAirport = ("Destination Airport Code: " + route.getDestAirport());
            }

            routeDetailList.setItems(observableArrayList(airline, numStops, sourceAirport, destAirport));

            if (optedIn) {

                String equipment;
                if (route.getEquipment() == null) {
                    equipment = ("Equipment: Unknown");
                } else {
                    equipment = ("Equipment: " + route.getEquipment());
                }

                String codeshare;
                if (route.isCodeshare()) {
                    codeshare = "This flight is a codeshare";
                } else {
                    codeshare = "This flight is not a codeshare";
                }

                routeDetailList.getItems().addAll(equipment, codeshare);
            }
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
            List<Airport> matchingAirports = currentRecord.searchAirports(searchCriteria, searchCategory);
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
            List<Airline> matchingAirlines = currentRecord.searchAirlines(searchCriteria, searchCategory);
            airlineList.setItems((ObservableList) matchingAirlines);
        }
    }

}
