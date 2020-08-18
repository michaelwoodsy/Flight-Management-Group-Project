package project.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import project.model.*;

import java.util.ArrayList;

/**
 * Class for working in while experimenting with GUI features
 */
public class GUI_Controller {

    @FXML
    private ListView airportList;
    private ArrayList<String> existingAirportCodes = new ArrayList<String>();

    /**
     * Adds new files to the airportList
     */
    public void addAirports(ArrayList<Airport> airports) {
        //Add each new airport to the file
        //Check the existing airports and add their unique codes to a file
        ArrayList<Airport> addedAirports = (ArrayList<Airport>) airportList.getItems();

        for (Airport airport: airports) {
            if (!existingAirportCodes.contains(airport.getIata())) {
                addedAirports.add(airport);
                existingAirportCodes.add(airport.getIata());
            }
        }

        //Not multiselect by default
        airportList.getItems().addAll("test", "Test2", "test23");
        //Operation to enable multiselect
        airportList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }
}
