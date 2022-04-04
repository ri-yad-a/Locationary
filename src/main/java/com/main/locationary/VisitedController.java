package com.main.locationary;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;

import java.text.NumberFormat;

public class VisitedController {


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
    private ListView<POI> displayLocationPOIS;

    @FXML
    private Button newPOIButton;

    @FXML
    private Label newPOILabel;

    @FXML
    private TextField newPOITextField;

    @FXML
    private Label ratingLabel;

    @FXML
    private Slider ratingSlider;

    @FXML
    private Button addRatingButton;


    @FXML
    void initialize() {
        // add monster weapon types
        scopeChoiceBox.getItems().add("Citywide");
        scopeChoiceBox.getItems().add("Domestic");
        scopeChoiceBox.getItems().add("International");
        citywideRadioButton.setSelected(true);
        domesticRadioButton.setSelected(true);
        internationalRadioButton.setSelected(true);
        newPOIButton.setDisable(true);
        newPOILabel.setDisable(true);
        newPOITextField.setDisable(true);
        ratingSlider.setDisable(true);
        ratingLabel.setDisable(true);
        addRatingButton.setDisable(true);
        updateView();

    }

    @FXML
    void addLocationButtonClicked() {


        // get the user inputted location name
        String locationName = locationNameTextField.getText();
        // declare a location object that will be added to bucket list
        if (!locationName.isBlank()) {
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
                if (HomeController.visited.hasLocation(visitedLocation.getName())) {
                    statusLabel.setText("Location already exists in visited");
                } else {
                    HomeController.visited.addLocation(visitedLocation);
                }

            }

            // if POI text field is not empty then add POI
            if (!poiInput.isBlank() && visitedLocation != null) {
                POI poi = new POI(poiInput);
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

        for (Location location: HomeController.visited.getLocations()) {
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
        displayLocationPOIS.getItems().clear();
        if (location != null) {
            statusLabel.setText("Showing POIs of location " + location.getName());
            for (POI poi: location.getPOIs()) {
                displayLocationPOIS.getItems().add(poi);
            }
            newPOIButton.setDisable(false);
            newPOITextField.setDisable(false);
        } else {
            newPOIButton.setDisable(true);
            newPOITextField.setDisable(true);
            statusLabel.setText("Please select a location from the list.");
        }


    }

    @FXML
    void newPOIButtonClicked() {

        Location selectedLocation = visitedListView.getSelectionModel().getSelectedItem();
        String poi = newPOITextField.getText();

        for (Location location: HomeController.visited.getLocations()) {
            if (location.getName().equals(selectedLocation.getName())) {
                if (!poi.isBlank()) {
                    if (location.hasPOI(poi)) {
                        statusLabel.setText("POI already exists in this location");
                    } else {
                        location.addPOI(new POI(poi));
                        viewLocationInformationClicked();
                    }
                } else {
                    statusLabel.setText("Please enter a name for a new POI");
                }

            }
        }
    }

    @FXML
    void poiDisplayClicked() {

        POI selectedPOI = displayLocationPOIS.getSelectionModel().getSelectedItem();

        ratingSlider.setDisable(false);
        ratingLabel.setDisable(false);
        addRatingButton.setDisable(false);


    }

    @FXML
    void addRatingButtonClicked() {

        Location selectedLocation = visitedListView.getSelectionModel().getSelectedItem();
        double rating = ratingSlider.getValue();
        try {
            POI selectedPOI = displayLocationPOIS.getSelectionModel().getSelectedItem();
            selectedPOI.setRating(rating);
        } catch (NumberFormatException e) {
            statusLabel.setText("Please enter a value between 1 and 5 for location rating");
        }
    }


    @FXML
    void unselectLocationAction() {
        visitedListView.getSelectionModel().clearSelection();
        displayLocationPOIS.getItems().clear();
        newPOIButton.setDisable(true);
        newPOITextField.setDisable(true);
    }



}
