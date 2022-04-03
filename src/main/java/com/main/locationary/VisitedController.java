package com.main.locationary;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

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
        if (visitedLocation.getScope() == Location.Scope.CITYWIDE) {
            visitedListView.getItems().add("[C] " +visitedLocation);
        } else if (visitedLocation.getScope() == Location.Scope.DOMESTIC) {
            visitedListView.getItems().add("[D] " +visitedLocation);
        } else if (visitedLocation.getScope() == Location.Scope.INTERNATIONAL) {
            visitedListView.getItems().add("[I] " +visitedLocation);
        }
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


    @FXML
    void updateView() {
        visitedListView.getItems().clear();

        for (Location location: visited.getLocations()) {
            if (citywideRadioButton.isSelected()) {
                if (location.getScope() == Location.Scope.CITYWIDE) {
                    visitedListView.getItems().add("[C] " + location);
                }
            }
            if (domesticRadioButton.isSelected()) {
                if (location.getScope() == Location.Scope.DOMESTIC) {
                    visitedListView.getItems().add("[D] " + location);
                }
            }
            if (internationalRadioButton.isSelected()) {
                if (location.getScope() == Location.Scope.INTERNATIONAL) {
                    visitedListView.getItems().add("[I] " + location);
                }
            }
        }



    }





}
