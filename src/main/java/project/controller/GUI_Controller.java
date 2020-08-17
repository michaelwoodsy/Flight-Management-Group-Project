package project.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;

/**
 * Class for working in while experimenting with GUI features
 */
public class GUI_Controller {

    @FXML
    private ListView airportList;

    /**
     * Configure the airportList
     */
    public void testMethod() {
        //Not multiselect by default
        airportList.getItems().addAll("test", "Test2", "test23");
        //Operation to enable multiselect
        airportList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }
}
