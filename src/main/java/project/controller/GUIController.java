package project.controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import project.model.*;

import java.util.ArrayList;

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


    private ArrayList<String> existingAirportCodes = new ArrayList<String>();

    /**
     * Returns the current objects within the airportList ListView
     * @return airportList.getItems() - an Observable List
     */
    public ObservableList getCurrentAirports() {
        return airportList.getItems();
    }

    public ArrayList<String> getExistingAirportCodes() {
        return existingAirportCodes;
    }

    /**
     * Adds new files to the airportList
     */
    public void addAirports(ArrayList<Airport> airports) {
        //Add each new airport to the file
        //Check the existing airports and add their unique codes to a file
        ObservableList<Airport> addedAirports = airportList.getItems();

        for (Airport airport: airports) {
            if (!existingAirportCodes.contains(airport.getIata())) {
                addedAirports.add(airport);
                existingAirportCodes.add(airport.getIata());
            }
        }
    }
}
