package project.controller;

import javafx.scene.control.Alert;
import javafx.scene.layout.Region;
import project.model.Airport;
import project.model.Covid;

public class DialogBoxes {

    public static void missingCovidInfoBox() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        int numMissing = Airport.getNumMissingCovid();
        alert.setTitle("MISSING COUNTRIES");
        alert.setHeaderText(String.format("%d airports are in countries that do not have information available on COVID cases." +
                " Airports in these countries will show a risk of 0 when selected:", numMissing));
        String missingCountries = Covid.missingCountries.get(0);
        for (int i = 1; i < Covid.missingCountries.size(); i++) {
            missingCountries += ", " + Covid.missingCountries.get(i);
        }
        alert.setContentText(missingCountries);
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);

        alert.showAndWait();
    }


    public static void fileFormatInfo(boolean check, boolean displayGoodLoad, String dataType) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("FILE STATUS");
        alert.setHeaderText(null);
        String body;
        if (check && displayGoodLoad) {
            body = "File loaded successfully";
            alert.setAlertType(Alert.AlertType.INFORMATION);
        } else if (check) {
            return;
        } else {
            body = String.format("Data laid out incorrectly, or is not %s data", dataType);
        }
        alert.setContentText(body);
        alert.showAndWait();
    }

    public static void blankTextField(boolean multipleBoxes) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("BLANK TEXT FIELD");
        alert.setHeaderText(null);
        String body;
        if (multipleBoxes) {
            body = "Please provide a value for each of the text boxes.";
        } else {
            body = "Please provide some search criteria.";
        }
        alert.setContentText(body);

        alert.showAndWait();
    }

    public static void noneReturned(String dataType) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        String title = String.format("NO MATCHING %s", dataType.toUpperCase());
        String body = String.format("Sorry, but there are no %s that match your search criteria.", dataType.toLowerCase());
        alert.setTitle(title);
        alert.setContentText(body);
        alert.setHeaderText(null);

        alert.showAndWait();
    }

}
