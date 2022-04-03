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
        // declare a location object that will be added to bucket list
        Location bucketListLocation = new Location(Location.Scope.INTERNATIONAL, locationName);
        String poiInput =  POITextField.getText();

        // check the scope choice of the user form scopeChoiceBox and set the visitedLocation object
        // with the correct scope and location name
        if (scopeChoiceBox.getValue() == "Citywide") {
            bucketListLocation.setScope(Location.Scope.CITYWIDE);
        } else if (scopeChoiceBox.getValue() == "Domestic") {
            bucketListLocation.setScope(Location.Scope.DOMESTIC);
        } else if (scopeChoiceBox.getValue() == "International") {
            bucketListLocation.setScope(Location.Scope.INTERNATIONAL);
        }

        // if POI text field is not empty then add POI
        if (!poiInput.equals("")) {
            POI poi = new POI(poiInput);
            // add poi to the location
            bucketListLocation.addPOI(poi);
        }

        // add location to bucketList
        bucketList.addLocation(bucketListLocation);

        // update bucketList display

    }

    @FXML
    void completedButtonClicked(ActionEvent event) {

    }

    @FXML
    void locationaryButtonClicked(ActionEvent event) {
        Main.switchScreen("home-view.fxml");
    }



}
