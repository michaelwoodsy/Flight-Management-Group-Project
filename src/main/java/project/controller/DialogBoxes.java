package project.controller;

import javafx.scene.control.Alert;
import javafx.scene.layout.Region;
import project.model.Airport;
import project.model.Covid;

public class DialogBoxes {

    public static void missingCovidInfoBox() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        int numMissing = Airport.getNumMissingCovid();
        alert.setTitle("Missing Countries");
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


}
