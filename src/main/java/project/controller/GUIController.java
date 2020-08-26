package project.controller;

import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
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
    private Button airportSearchButton;
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

    public void filterAirportsByCountry() {
        airportSortBy.setItems(observableArrayList("Most Popular", "Least Popular"));
        airportSortBy.setValue("Most Popular");
        String country = airportSearchCriteria.getText().toLowerCase();
        ArrayList<Airport> filteredAirports = record.filterAirports(country);
    }

    public void sortCurrentData() {}

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

}
