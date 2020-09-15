package project.controller;

import javafx.scene.control.Alert;
import javafx.scene.layout.Region;
import project.model.Airport;
import project.model.Covid;

import java.util.ArrayList;

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

    public static void newDataError(ArrayList<String> errors) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        String errorString = "";
        for (int i = 0; i < errors.size(); i++) {
            errorString += errors.get(i) + '\n';
        }
        alert.setContentText(errorString);
        alert.setHeaderText(null);
        alert.setTitle("ERRORS WITH DATA");
        alert.showAndWait();
    }

    public static void noRoutes() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        String bodyText = "The airport you have selected does not have any flights either in or out of it.\nHave you considered adding some?";
        alert.setContentText(bodyText);
        alert.setHeaderText(null);
        alert.setTitle("AIRPORT MISSING ROUTES");
        alert.showAndWait();
    }

    /**
     * A dialog box that displays on GUI bootup, to inform users that our calculations are approximate only.
     */
    public static void welcomeBox() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        String headerText = "Welcome to PlaneSonar25! Thank you for using our app.";
        String bodyText = "Our app handles some sensitive information surrounding COVID infection rates, and the risks that" +
                " a country's airport poses to your health. As such, we see it as our duty to notify you that these risks are calculated " +
                "using the WHO's COVID case data for each country, and that these risks are purely approximations, and should not " +
                "be used to fully inform your chances of infection in a country. Our risk matrix for countries is as follows:\n" +
                "Extremely Low: Less than 0.1% of the population have cases\nLow: Less than 0.5% of the population have cases\n " +
                "Medium: Less than 1% of the population have cases\nHigh: Less than 5% of the population have cases\n" +
                "Extreme: More than 5% of the population have cases\nRemember to social distance when you can, and stay safe.";
        alert.setTitle("WELCOME");
        alert.setHeaderText(headerText);
        alert.setContentText(bodyText);
        alert.showAndWait();
    }
}
