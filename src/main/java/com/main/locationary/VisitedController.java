package com.main.locationary;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;

import java.text.NumberFormat;

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
    private TextArea reviewTextArea;

    @FXML
    private TextField POITextField;

    @FXML
    private TextField ratingTextField;

    @FXML
    private ListView visitedListView;

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
    void addLocationButtonClicked(ActionEvent event) {

        // get the user inputted location name
        String locationName = locationNameTextField.getText();
        String scopeChoice = scopeChoiceBox.getValue();
        String poiInput = POITextField.getText();

        if (!locationName.equals("")) {
            // declare a location object that will be added to visited
            Location visitedLocation = new Location(Location.Scope.INTERNATIONAL, locationName);

            // if POI text field is not empty then add POI
            if (!poiInput.equals("")) {
                POI poi = new POI(poiInput);
                try {
                    String rating = ratingTextField.getText();
                    if (!rating.equals("")) {
                        poi.setRating(Integer.parseInt(rating));
                    }
                    // add poi to the location
                    visitedLocation.addPOI(poi);

                } catch (NumberFormatException e) {
                    statusLabel.setTextFill(Color.rgb(255, 0, 0));
                    statusLabel.setText("Please enter a value between 1-5 for POI rating");
                }

            }


            if (scopeChoice != null) {
                // check the scope choice of the user form scopeChoiceBox and set the visitedLocation object
                // with the correct scope and location name
                if (scopeChoice == "Citywide") {
                    visitedLocation.setScope(Location.Scope.CITYWIDE);
                } else if (scopeChoice == "Domestic") {
                    visitedLocation.setScope(Location.Scope.DOMESTIC);
                } else if (scopeChoice == "International") {
                    visitedLocation.setScope(Location.Scope.INTERNATIONAL);
                }

                // add location to visited
                visited.addLocation(visitedLocation);
                // else when location name input is empty

            } else {
                statusLabel.setTextFill(Color.rgb(255, 0, 0));
                statusLabel.setText("Please choose a location scope");
            }
        } else {
            statusLabel.setTextFill(Color.rgb(255, 0, 0));
            statusLabel.setText("Please enter a location name");
        }

        // update visited display
        updateView();
    }

    @FXML
    void locationaryButtonClicked(ActionEvent event) {
        Main.switchScreen("home-view.fxml");
    }

    @FXML
    void citywideChecked(ActionEvent event) {
        updateView();
    }

    @FXML
    void domesticChecked(ActionEvent event) {
        updateView();
    }

    @FXML
    void internationalChecked(ActionEvent event) {
        updateView();
    }


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

    void viewInfo() {

    }





}
