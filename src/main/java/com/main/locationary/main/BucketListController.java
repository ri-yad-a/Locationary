package com.main.locationary.main;

import com.main.locationary.info.*;
import com.main.locationary.util.FileHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

/**
 * CPSC 233 W22 Final submission
 * Tutorial: T10
 * Date: April 15, 2022
 * Gaurav Ashar, Riyad Abdullayev
 * A controller class for the bucketList-view fxml scene
 */

public class BucketListController {

    // FXML components
    @FXML
    private TextField POITextField;

    @FXML
    private CheckBox citywideCheckBox;

    @FXML
    private CheckBox domesticCheckBox;

    @FXML
    private CheckBox internationalCheckBox;

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
        // add scope choices
        scopeChoiceBox.getItems().add("Citywide");
        scopeChoiceBox.getItems().add("Domestic");
        scopeChoiceBox.getItems().add("International");
        // set the filter check boxes to selected
        citywideCheckBox.setSelected(true);
        domesticCheckBox.setSelected(true);
        internationalCheckBox.setSelected(true);
        // disable the POI controls until a location is created and selected
        newPOIField.setDisable(true);
        addPOIButton.setDisable(true);
        completedButton.setDisable(true);
        // update the locations view so that changes from outside this scene will show
        updateLocationsView();
    }

    /**
     * Show instructions, control located in menu bar
     */
    @FXML
    void instructionAction() {
        // create Alert object
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        // set essential text
        alert.setTitle("How to use Locationary");
        alert.setHeaderText("How to use your Bucket List");
        alert.setContentText("Add locations to your Bucket List by specifying a location name, scope, and optionally a POI name and clicking the \"Add Location\"" +
                "button.\nYou can then view all your added locations in the pane.\nFilter these locations by scope using the radio buttons below.\nSelect a location to" +
                " see its POIs by name. Finally, add a POI by entering a name and clicking \"Add New POI.\"\nUse the Locationary button to go back to the Homepage.");
        // show alert
        alert.show();
    }

    /**
     * Add a location, invoked when user clicks the add location button
     */
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
            Location bucketListLocation = null;
            // creation will continue unless scope choice is null
            if (canCreate) {
                // initialize new location
                bucketListLocation = new Location(scope, locationName);
                // check if location exists
                if (!HomeController.bucketList.hasLocation(bucketListLocation.getName()) && !HomeController.visited.hasLocation(bucketListLocation.getName())) {
                    // add the location to bucket list
                    HomeController.bucketList.addLocation(bucketListLocation);
                    // set status text to success message
                    statusLabel.setText("Location " + bucketListLocation.getName() + " added to BucketList.");
                    // clear input fields after performing action
                    locationNameTextField.clear();
                    scopeChoiceBox.setValue(null);
                    POITextField.clear();
                } else {
                    // action failed, set to failure message
                    statusLabel.setText("Location " + bucketListLocation.getName() + " already exists!");
                }
            }

            // if POI text field is not empty then add POI
            if (!poiInput.isBlank() && bucketListLocation != null) {
                POI poi = new POI(poiInput);
                // add poi to the location
                bucketListLocation.addPOI(poi);
            }

            // update bucketList display
            updateLocationsView();
        } else {
            // location field is empty, set status label accordingly
            statusLabel.setText("Please enter a location name.");
        }
    }

    /**
     * Transfer a location to Visited journal when the completed button is clicked
     */
    @FXML
    void completedButtonClicked() {
        // get location
        Location l = locationsView.getSelectionModel().getSelectedItem();
        // call this method to disable POI controls and this button
        unselectLocationAction();
        // transfer it
        HomeController.bucketList.removeLocation(l);
        HomeController.visited.addLocation(l);
        // update view
        updateLocationsView();
        // set status text accordingly
        statusLabel.setText("Location " + l.getName() + " transferred to your Visited journal!");
    }

    /**
     * Go back to home page, when pressing Locationary button
     */
    @FXML
    void locationaryButtonClicked() {
        // call switch screen method from main
        Main.switchScreen("home-view.fxml");
    }

    /**
     * Update view automatically and when invoked by several methods
     */
    @FXML
    void updateLocationsView() {
        // check which check boxes are selected
        boolean c = citywideCheckBox.isSelected();
        boolean d = domesticCheckBox.isSelected();
        boolean i = internationalCheckBox.isSelected();
        // clear current view
        locationsView.getItems().clear();
        // separate loops to show locations in order, sorted by scope
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

    /**
     * Select a location from the List View, when list view is clicked
     */
    @FXML
    void selectLocationAction() {
        // get selected item
        Location l = locationsView.getSelectionModel().getSelectedItem();
        // null check, null means the list itself was clicked, not a location
        if (l != null) {
            // show POIs in POI list view
            statusLabel.setText("Showing POIs of location " + l.getName());
            // clear view
            locationPOIView.getItems().clear();
            for (POI poi: l.getPOIs()) {
                // add all POIs
                locationPOIView.getItems().add(poi);
            }
            // enable all POI components
            completedButton.setDisable(false);
            addPOIButton.setDisable(false);
            newPOIField.setDisable(false);
        } else {
            // disable all related components
            unselectLocationAction();
            // set status label accordingly
            statusLabel.setText("Please select a location from the list.");
        }
    }

    /**
     * Add POI to a selected location, when the add POI button is clicked
     */
    @FXML
    void addNewPOIAction() {
        // get name of POI
        String name = newPOIField.getText();
        if (name.isBlank()) {
            // checks for empty or just whitespace
            statusLabel.setText("Please enter a name for a new POI!");
        } else {
            // get selected location
            Location l = locationsView.getSelectionModel().getSelectedItem();
            // make sure location does not already have POI
            if (!l.hasPOI(name)) {
                // add POI
                l.addPOI(new POI(name));
                selectLocationAction();
                // set status text accordingly
                statusLabel.setText("POI " + name + " added!");
            } else {
                // failure message
                statusLabel.setText("POI " + name + " already exists in " + l.getName());
            }
        }

        //clear input fields after performing action
        newPOIField.clear();
    }

    /**
     * Unselect location from view, invoked by methods and unselect location button
     */
    @FXML
    void unselectLocationAction() {
        // clear selection and clear POI view
        locationsView.getSelectionModel().clearSelection();
        locationPOIView.getItems().clear();
        // disable POI components and completed button
        addPOIButton.setDisable(true);
        newPOIField.setDisable(true);
        completedButton.setDisable(true);
    }

    /**
     * Show POIs in view for a selected location
     */
    @FXML
    void viewPOIInfoAction() {
        // when a POI is selected
        POI poi = locationPOIView.getSelectionModel().getSelectedItem();
        if (poi != null) {
            statusLabel.setText("Selected the " + poi.getName() + " POI!");
        }

    }

    /**
     * Load data from file, menu option
     */
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

    /**
     * Save to default file, menu option
     */
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

    /**
     * Save to specified file, menu option
     */
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
     * Quits program, menu option
     */
    @FXML
    void quitAction() {
        System.exit(0);
    }

    /**
     * View information, menu option
     */
    @FXML
    void aboutAction() {
        // create alert
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        // set essential text
        alert.setTitle("About Locationary");
        alert.setHeaderText("Locationary");
        alert.setContentText("Locationary is a traveller's journal that keeps track of places one wants to travel to, and places that travellers have already been. " +
                "Authors:\nGaurav Ashar (gaurav.ashar@ucalgary.ca)\nRiyad Abdullayev (riyad.abdullayev@ucalgary.ca)");
        // show alert
        alert.show();
    }





}
