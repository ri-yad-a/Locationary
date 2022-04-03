package com.main.locationary;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;

import java.text.NumberFormat;

public class VisitedController {

    Visited visited = new Visited();

    @FXML
    private TextField locationNameTextField;

    @FXML
    private TextArea reviewTextArea;

    @FXML
    private TextField POITextField;

    @FXML
    private TextField ratingTextField;

    @FXML
    private ListView<Location> visitedListView;

    @FXML
    private ChoiceBox<String> scopeChoiceBox;

    @FXML
    private RadioButton citywideRadioButton;

    @FXML
    private RadioButton domesticRadioButton;

    @FXML
    private RadioButton internationalRadioButton;

    @FXML
    private Label statusLabel;

    @FXML
    private TextArea viewLocationAttributesTextArea;


    @FXML
    void initialize() {
        // add monster weapon types
        scopeChoiceBox.getItems().add("Citywide");
        scopeChoiceBox.getItems().add("Domestic");
        scopeChoiceBox.getItems().add("International");
        citywideRadioButton.setSelected(true);
        domesticRadioButton.setSelected(true);
        internationalRadioButton.setSelected(true);

    }

    @FXML
    void addLocationButtonClicked() {


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

            Location visitedLocation = null;
            if (canCreate) {
                visitedLocation = new Location(scope, locationName);
                // add location to visited
                boolean exists = false;
                for (Location location: visited.getLocations()) {
                    if (location.equals(location)) {
                        exists = true;
                        statusLabel.setText("Location already exists in visted");
                    }
                }
                if (!exists) {
                    visited.addLocation(visitedLocation);
                }
            }

            // if POI text field is not empty then add POI
            if (!poiInput.equals("") && visitedLocation != null) {
                POI poi = new POI(poiInput);
                /*String ratingStr = ratingTextField.getText();
                if (!ratingStr.equals("")) {
                    try {
                        double rating = Double.parseDouble(ratingStr);
                        poi.setRating(rating);
                    } catch (NumberFormatException e) {
                        statusLabel.setText("Please enter a value between 1 and 5 for location rating");
                    }
                }*/
                // add poi to the location



                visitedLocation.addPOI(poi);
            }

            // update bucketList display
            updateView();
        } else {
            statusLabel.setText("Please enter a location name.");
        }

    }

    @FXML
    void locationaryButtonClicked() {
        Main.switchScreen("home-view.fxml");
    }


    @FXML
    void updateView() {
        visitedListView.getItems().clear();

        boolean c = citywideRadioButton.isSelected();
        boolean d = domesticRadioButton.isSelected();
        boolean i = internationalRadioButton.isSelected();

        for (Location location: visited.getLocations()) {
            if (c && location.getScope() == Location.Scope.CITYWIDE) {
                    visitedListView.getItems().add(location);
            }
            if (d && location.getScope() == Location.Scope.DOMESTIC) {
                visitedListView.getItems().add(location);
            }
            if (i && location.getScope() == Location.Scope.INTERNATIONAL) {
                visitedListView.getItems().add(location);
            }
        }

    }

    @FXML
    void viewLocationInformationClicked() {

        Location location = visitedListView.getSelectionModel().getSelectedItem();
        if (location != null) {
            viewLocationAttributesTextArea.setText(location.toVerboseString());
        } else {
            statusLabel.setText("Please select a location from the list");
        }


    }



}
