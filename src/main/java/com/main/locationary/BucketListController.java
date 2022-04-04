package com.main.locationary;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

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
    private ListView<POI> locationPOIView;

    @FXML
    private TextField newPOIField;

    @FXML
    private Button addPOIButton;

    @FXML
    private Button completedButton;

    @FXML
    void initialize() {
        // add monster weapon types
        scopeChoiceBox.getItems().add("Citywide");
        scopeChoiceBox.getItems().add("Domestic");
        scopeChoiceBox.getItems().add("International");
        citywideButton.setSelected(true);
        domesticButton.setSelected(true);
        internationalButton.setSelected(true);
        newPOIField.setDisable(true);
        addPOIButton.setDisable(true);
        completedButton.setDisable(true);
        updateLocationsView();
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
                // check if location exists
                if (!HomeController.bucketList.hasLocation(bucketListLocation.getName())) {
                    HomeController.bucketList.addLocation(bucketListLocation);
                } else {
                    statusLabel.setText("Location " + bucketListLocation.getName() + " already exists in your Bucket List!");
                }
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
    void completedButtonClicked() {
        Location l = locationsView.getSelectionModel().getSelectedItem();
        unselectLocationAction();
        HomeController.bucketList.removeLocation(l);
        HomeController.visited.addLocation(l);
        updateLocationsView();
        statusLabel.setText("Location " + l.getName() + " transferred to your Visited journal!");
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
            statusLabel.setText("Showing POIs of location " + l.getName());
            locationPOIView.getItems().clear();
            for (POI poi: l.getPOIs()) {
                locationPOIView.getItems().add(poi);
            }
            completedButton.setDisable(false);
            addPOIButton.setDisable(false);
            newPOIField.setDisable(false);
        } else {
            addPOIButton.setDisable(true);
            newPOIField.setDisable(true);
            completedButton.setDisable(true);
            statusLabel.setText("Please select a location from the list.");
        }
    }

    @FXML
    void addNewPOIAction() {
        String name = newPOIField.getText();
        if (name.isBlank()) {
            statusLabel.setText("Please enter a name for a new POI!");
        } else {
            Location l = locationsView.getSelectionModel().getSelectedItem();
            if (!l.hasPOI(name)) {
                l.addPOI(new POI(name));
                selectLocationAction();
                statusLabel.setText("POI " + name + " added!");
            } else {
                statusLabel.setText("POI " + name + " already exists in " + l.getName());
            }
        }
    }

    @FXML
    void unselectLocationAction() {
        locationsView.getSelectionModel().clearSelection();
        locationPOIView.getItems().clear();
        addPOIButton.setDisable(true);
        newPOIField.setDisable(true);
        completedButton.setDisable(true);
    }

    @FXML
    void viewPOIInfoAction() {
        POI poi = locationPOIView.getSelectionModel().getSelectedItem();
        if (poi != null) {
            statusLabel.setText("Selected the " + poi.getName() + " POI!");
        }

    }

    @FXML
    void loadFileAction() {
        // open file chooser object
        final FileChooser fileChooser = new FileChooser();
        // set initial directory
        fileChooser.setInitialDirectory(new File("."));
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("CSV", "*.csv"));
        // get file from file chooser in new window
        File fileLoad = fileChooser.showOpenDialog(new Stage());
        // if the file is not null, load world
        if (fileLoad != null) {
            FileHandler.setFile(fileLoad);
            Journal[] j = FileHandler.getFromFile();
            if (j != null) {
                HomeController.bucketList = (BucketList) j[0];
                HomeController.visited = (Visited) j[1];
                statusLabel.setTextFill(Color.BLACK);
                statusLabel.setText("Your BucketList and Visited journal have been loaded from " + fileLoad.getName());
                updateLocationsView();
            } else {
                // if null, error has occurred and file is not acceptable
                // set right status to fail message
                statusLabel.setTextFill(Color.RED);
                statusLabel.setText("File is not in the correct format!");
            }
        } else {
            // set right status to fail message
            statusLabel.setTextFill(Color.RED);
            statusLabel.setText("File not chosen.");
        }
    }

    @FXML
    void saveAction() {
        // write to default file
        boolean b = FileHandler.writeToFile("data.csv", HomeController.bucketList, HomeController.visited);
        if (b) {
            // set right status to success message
            statusLabel.setTextFill(Color.BLACK);
            statusLabel.setText("Data saved to data.csv");
        } else {
            // set right status to fail message
            statusLabel.setTextFill(Color.RED);
            statusLabel.setText("Unable to write to default file, data.csv!");
        }
    }

    @FXML
    void saveAsAction() {
        // setup file chooser
        final FileChooser fileChooser = new FileChooser();
        // set initial directory and filename
        fileChooser.setInitialDirectory(new File("."));
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("CSV", "*.csv"));
        fileChooser.setInitialFileName("data.csv");
        // get file from file chooser in a new window
        File fileSave = fileChooser.showSaveDialog(new Stage());
        // null occurs when user does not choose any file to save to
        if (fileSave != null) {
            // save to specifed file
            boolean b = FileHandler.writeToFile(fileSave.getPath(), HomeController.bucketList, HomeController.visited);
            if (b) {
                // set right status to success message
                statusLabel.setTextFill(Color.BLACK);
                statusLabel.setText("Data saved to " + fileSave.getName());
            } else {
                // set right status to fail message
                statusLabel.setTextFill(Color.RED);
                statusLabel.setText("Data could not be saved to specified file!");
            }

        } else {
            // set right status message to fail message
            statusLabel.setTextFill(Color.RED);
            statusLabel.setText("File not chosen!");
        }
    }

    /**
     * Quits program
     */
    @FXML
    void quitAction() {
        System.exit(0);
    }

    /**
     * View information
     */
    @FXML
    void aboutAction() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About Locationary");
        alert.setHeaderText("Locationary");
        alert.setContentText("Locationary is a traveller's journal that keeps track of places one wants to travel to, and places that travellers have already been. " +
                "Authors:\nGaurav Ashar (gaurav.ashar@ucalgary.ca)\nRiyad Abdullayev (riyad.abdullayev@ucalgary.ca)");
        alert.show();
    }





}
