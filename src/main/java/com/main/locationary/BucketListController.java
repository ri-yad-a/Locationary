package com.main.locationary;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.List;

public class BucketListController {

    BucketList bucketList = new BucketList();

    @FXML
    private TextField POITextField;

    @FXML
    private RadioButton citywideButton;

    @FXML
    private RadioButton domesticButton;

    @FXML
    private RadioButton internationalButton;

    @FXML
    private TextField locationNameTextField;

    @FXML
    private ChoiceBox<String> scopeChoiceBox;

    @FXML
    private ListView<String> locationsView;

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
        updateLocationsView();

    }

    @FXML
    void completedButtonClicked(ActionEvent event) {

    }

    @FXML
    void locationaryButtonClicked(ActionEvent event) {
        Main.switchScreen("home-view.fxml");
    }

    @FXML
    void updateLocationsView() {
        boolean c = citywideButton.isSelected();
        boolean d = domesticButton.isSelected();
        boolean i = internationalButton.isSelected();
        locationsView.getItems().clear();
        for (Location l: bucketList.getLocations()) {
            if (l.getScope() == Location.Scope.CITYWIDE && c) {
                locationsView.getItems().add("[C] " + l);
            }
        }
        for (Location l: bucketList.getLocations()) {
            if (l.getScope() == Location.Scope.DOMESTIC && d) {
                locationsView.getItems().add("[D] " + l);
            }
        }
        for (Location l: bucketList.getLocations()) {
            if (l.getScope() == Location.Scope.INTERNATIONAL && i) {
                locationsView.getItems().add("[I] " + l);
            }
        }
    }





}
