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
        Location visitedLocation = null;

        // check the scope choice of the user form scopeChoiceBox and set the visitedLocation object
        // with the correct scope and location name
        if (scopeChoiceBox.getValue() == "Citywide") {
            visitedLocation = new Location(Location.Scope.CITYWIDE, locationName);
        } else if (scopeChoiceBox.getValue() == "Domestic") {
            visitedLocation = new Location(Location.Scope.DOMESTIC, locationName);
        } else if (scopeChoiceBox.getValue() == "International") {
            visitedLocation = new Location(Location.Scope.INTERNATIONAL, locationName);
        }

        visited.addLocation(visitedLocation);

        // update visited display
    }

}
