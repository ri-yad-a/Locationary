package com.main.locationary;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

public class VisitedController {

    Visited visited = new Visited();

    @FXML
    private CheckBox citywideCheckBox;

    @FXML
    private CheckBox domesticCheckBox;

    @FXML
    private CheckBox internationalCheckBox;

    @FXML
    private TextField locationNameTextField;

    @FXML
    private TextField reviewTextField;

    @FXML
    private TextField POITextField;

    @FXML
    private TextField ratingTextField;

    @FXML
    private ChoiceBox<String> scopeChoiceBox;

    @FXML
    void initialize() {
        // add monster weapon types
        scopeChoiceBox.getItems().add("Citywide");
        scopeChoiceBox.getItems().add("Domestic");
        scopeChoiceBox.getItems().add("International");

    }

    @FXML
    void addLocationButtonClicked(ActionEvent event) {

        // get the user inputted location name
        String locationName = locationNameTextField.getText();
        // declare a location object that will be added to visited
        Location visitedLocation = new Location(Location.Scope.INTERNATIONAL, locationName);
        String poiInput =  POITextField.getText();

        // check the scope choice of the user form scopeChoiceBox and set the visitedLocation object
        // with the correct scope and location name
        if (scopeChoiceBox.getValue() == "Citywide") {
            visitedLocation.setScope(Location.Scope.CITYWIDE);
        } else if (scopeChoiceBox.getValue() == "Domestic") {
            visitedLocation.setScope(Location.Scope.DOMESTIC);
        } else if (scopeChoiceBox.getValue() == "International") {
            visitedLocation.setScope(Location.Scope.INTERNATIONAL);
        }

        // if POI text field is not empty then add POI
        if (!poiInput.equals("")) {
            POI poi = new POI(poiInput);
            // add poi to the location
            visitedLocation.addPOI(poi);
        }

        // add location to visited
        visited.addLocation(visitedLocation);

        // update visited display
    }

    @FXML
    void locationaryButtonClicked(ActionEvent event) {
        Main.switchScreen("home-view.fxml");
    }

}
