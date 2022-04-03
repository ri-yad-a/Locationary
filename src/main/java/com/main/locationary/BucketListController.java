package com.main.locationary;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

public class BucketListController {

    BucketList bucketList = new BucketList();

    @FXML
    private TextField POITextField;

    @FXML
    private CheckBox citywideCheckBox;

    @FXML
    private CheckBox domesticCheckBox;

    @FXML
    private TextField locationNameTextField;

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
        // declare a location object that will be added to bucketList
        Location bucketListLocation = null;

        // check the scope choice of the user form scopeChoiceBox and set the visitedLocation object
        // with the correct scope and location name
        if (scopeChoiceBox.getValue() == "Citywide") {
            bucketListLocation = new Location(Location.Scope.CITYWIDE, locationName);
        } else if (scopeChoiceBox.getValue() == "Domestic") {
            bucketListLocation = new Location(Location.Scope.DOMESTIC, locationName);
        } else if (scopeChoiceBox.getValue() == "International") {
            bucketListLocation = new Location(Location.Scope.INTERNATIONAL, locationName);
        }

        bucketList.addLocation(bucketListLocation);

        // update bucketList display

    }

    @FXML
    void completedButtonClicked(ActionEvent event) {

    }

}
