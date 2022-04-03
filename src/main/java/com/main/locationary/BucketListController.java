package com.main.locationary;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.ArrayList;
import java.util.List;

public class BucketListController {

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
    private ListView<Location> locationsView;

    @FXML
    private Label statusLabel;

    @FXML
    private TextArea locationDisplay;

    @FXML
    void initialize() {
        // add monster weapon types
        scopeChoiceBox.getItems().add("Citywide");
        scopeChoiceBox.getItems().add("Domestic");
        scopeChoiceBox.getItems().add("International");
        citywideButton.setSelected(true);
        domesticButton.setSelected(true);
        internationalButton.setSelected(true);

    }

    @FXML
    void addLocationButtonClicked(ActionEvent event) {

        // get the user inputted location name
        String locationName = locationNameTextField.getText();
        // declare a location object that will be added to bucket list
        if (!locationName.equals("")) {
            boolean canCreate = true;
            String poiInput =  POITextField.getText();
            Location.Scope scope = null;

            // check the scope choice of the user form scopeChoiceBox and set the visitedLocation object
            // with the correct scope and location name
            if (scopeChoiceBox.getValue() == null) {
                statusLabel.setText("Please choose a location scope.");
                canCreate = false;
            } else if (scopeChoiceBox.getValue().equals("Citywide")) {
                scope = Location.Scope.CITYWIDE;
            } else if (scopeChoiceBox.getValue().equals("Domestic")) {
                scope = Location.Scope.DOMESTIC;
            } else if (scopeChoiceBox.getValue().equals("International")) {
                scope = Location.Scope.INTERNATIONAL;
            }

            Location bucketListLocation = null;
            if (canCreate) {
                bucketListLocation = new Location(scope, locationName);
                // add location to bucketList
                HomeController.bucketList.addLocation(bucketListLocation);
            }

            // if POI text field is not empty then add POI
            if (!poiInput.equals("") && bucketListLocation != null) {
                POI poi = new POI(poiInput);
                // add poi to the location
                bucketListLocation.addPOI(poi);
            }

            // update bucketList display
            updateLocationsView();
        } else {
            statusLabel.setText("Please enter a location name.");
        }



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
        for (Location l: HomeController.bucketList.getLocations()) {
            if (l.getScope() == Location.Scope.CITYWIDE && c) {
                locationsView.getItems().add(l);
            }
        }
        for (Location l: HomeController.bucketList.getLocations()) {
            if (l.getScope() == Location.Scope.DOMESTIC && d) {
                locationsView.getItems().add(l);
            }
        }
        for (Location l: HomeController.bucketList.getLocations()) {
            if (l.getScope() == Location.Scope.INTERNATIONAL && i) {
                locationsView.getItems().add(l);
            }
        }
    }

    @FXML
    void selectLocationAction() {
        Location l = locationsView.getSelectionModel().getSelectedItem();
        if (l != null) {
            statusLabel.setText("Showing attributes of location " + l.getName());
            locationDisplay.setText(l.toVerboseString());
        } else {
            statusLabel.setText("Please select a location from the list.");
        }
    }





}
