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
    private ListView detailList;
    @FXML
    private TextField searchBar;
    @FXML
    private Button searchButton;
    @FXML
    private ChoiceBox sortChoice;

    private Record record = Database.generateRecord();

    public void filterAirportsByCountry() {
        sortChoice.setItems(observableArrayList("Most Popular", "Least Popular"));
        sortChoice.setValue("Most Popular");
        String country = searchBar.getText().toLowerCase();
        ArrayList<Airport> filteredAirports = record.filterAirports(country);
        String wayToSort = (String) sortChoice.getValue();
        if (wayToSort == "Least Popular") {
            airportList.setItems(observableArrayList(record.rankAirports(true)));

        } else {
            airportList.setItems(observableArrayList(record.rankAirports(false)));
        }

    }
}
