package com.main.locationary.main;

import com.main.locationary.info.*;
import com.main.locationary.util.FileHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

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
    private CheckBox citywideCheckBox;

    @FXML
    private CheckBox domesticCheckBox;

    @FXML
    private CheckBox internationalCheckBox;

    @FXML
    private Label statusLabel;

    @FXML
    private Label ratingNumberLabel;

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


    /**
     * Set the initial status of all the necessary JavaFx components
     */
    @FXML
    void initialize() {
        // add monster weapon types
        scopeChoiceBox.getItems().add("Citywide");
        scopeChoiceBox.getItems().add("Domestic");
        scopeChoiceBox.getItems().add("International");
        // set the default status of the checkboxes to all being selected
        citywideCheckBox.setSelected(true);
        domesticCheckBox.setSelected(true);
        internationalCheckBox.setSelected(true);
        // initially disable all the poi input fields
        newPOIButton.setDisable(true);
        newPOILabel.setDisable(true);
        newPOITextField.setDisable(true);
        ratingSlider.setDisable(true);
        ratingLabel.setDisable(true);
        addRatingButton.setDisable(true);
        updateView();

    }

    /**
     * Add a Location to visited action
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

            Location visitedLocation = null;
            if (canCreate) {
                visitedLocation = new Location(scope, locationName);
                // add location to visited
                if (!HomeController.visited.hasLocation(visitedLocation.getName()) && !HomeController.bucketList.hasLocation(visitedLocation.getName())) {
                    HomeController.visited.addLocation(visitedLocation);
                    statusLabel.setText("Location " + visitedLocation.getName() + " added to Visited journal.");
                    // clear input fields after performing action
                    locationNameTextField.clear();
                    scopeChoiceBox.setValue(null);
                    POITextField.clear();
                } else {
                    statusLabel.setText("Location " + visitedLocation.getName() + " already exists!");
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

    /**
     * GO back to home screen action
     */
    @FXML
    void locationaryButtonClicked() {
        Main.switchScreen("home-view.fxml");
    }


    /**
     * update the visited locations ListView
     */
    @FXML
    void updateView() {
        // clear the list view
        visitedListView.getItems().clear();

        // get booleans for the status of all the checkboxes
        boolean c = citywideCheckBox.isSelected();
        boolean d = domesticCheckBox.isSelected();
        boolean i = internationalCheckBox.isSelected();

        // first loop through the locations of visited and check if they are citywide, then add them to the visited listview if citywide button is clicked
        for (Location location: HomeController.visited.getLocations()) {
            if (c && location.getScope() == Location.Scope.CITYWIDE) {
                visitedListView.getItems().add(location);
            }
        }
        // do the same for domestic locations
        for (Location location: HomeController.visited.getLocations()) {
            if (d && location.getScope() == Location.Scope.DOMESTIC) {
                visitedListView.getItems().add(location);
            }
        }
        // and finally do the same for international locations
        for (Location location: HomeController.visited.getLocations()) {
            if (i && location.getScope() == Location.Scope.INTERNATIONAL) {
                visitedListView.getItems().add(location);
            }
        }

    }

    /**
     * update the ListVIew that displays POIs of a location
     */
    @FXML
    void updatePOIDisplay() {

        // get the location that is selected in the ListView
        Location location = visitedListView.getSelectionModel().getSelectedItem();
        // clear the pois view
        displayLocationPOIS.getItems().clear();
        // if the location isn't null
        if (location != null) {
            // display all the pois of the selcted locations
            statusLabel.setText("Showing POIs of location " + location.getName());
            for (POI poi: location.getPOIs()) {
                displayLocationPOIS.getItems().add(poi);
            }
            newPOIButton.setDisable(false);
            newPOITextField.setDisable(false);
        } else {
            // if location is null then disable poi input fields and ask user to select a location
            newPOIButton.setDisable(true);
            newPOITextField.setDisable(true);
            statusLabel.setText("Please select a location from the list.");
        }


    }

    /**
     * Add a new POI to a location action
     */
    @FXML
    void newPOIButtonClicked() {

        // get the name of the poi from the appropriate text field
        String poi = newPOITextField.getText();
        // if the field is blank
        if (poi.isBlank()) {
            // ask user to input a name
            statusLabel.setText("Please enter a name for a new POI!");
        } else {
            // if not empty
            // get selected location
            Location location = visitedListView.getSelectionModel().getSelectedItem();
            // add poi to the location and update poi display and status label
            if (!location.hasPOI(poi)) {
                location.addPOI(new POI(poi));
                updatePOIDisplay();
                statusLabel.setText("POI " + poi + " added!");
            } else {
                statusLabel.setText("POI " + poi + " already exists in " + location.getName());
            }
        }

        // clear input fields after performing action
        newPOITextField.clear();
    }

    /**
     * When the display of POIS is clicked
     */
    @FXML
    void poiDisplayClicked() {

        // enable the poi input fields
        ratingSlider.setDisable(false);
        ratingLabel.setDisable(false);
        addRatingButton.setDisable(false);


    }

    /**
     * Adding a rating to a POI action
     */
    @FXML
    void addRatingButtonClicked() {

        // get selected location
        Location selectedLocation = visitedListView.getSelectionModel().getSelectedItem();
        // get rating from slider input
        double rating = ratingSlider.getValue();
        String roundedRatingStr = String.format("%.2g%n", rating);
        double roundedRating = Double.parseDouble(roundedRatingStr);

        try {
            // get selected poi and set its rating to the rating input
            POI selectedPOI = displayLocationPOIS.getSelectionModel().getSelectedItem();
            selectedPOI.setRating(roundedRating);
            selectedLocation.setRating(0);
            // update poi display
            updatePOIDisplay();
        } catch (NumberFormatException e) {
            statusLabel.setText("Please enter a value between 1 and 5 for location rating");
        } catch (NullPointerException e) {
            // if null pointer exception is caught, ask user to select a poi to add a rating to
            statusLabel.setText("Please select a POI to add a rating to");
        }



    }

    /**
     * Update rating number when changing the slider
     */
    @FXML
    void updateRatingNumberLabel() {
        // get the rating from the slider input
        double rating = ratingSlider.getValue();
        String roundedRatingStr = String.format("%.2g%n", rating);
        // set the ratingNumberLabel text to the rating so that user can see exactly which number they are inputting
        ratingNumberLabel.setText("" + roundedRatingStr);

    }


    /**
     * Unselect a location action
     */
    @FXML
    void unselectLocationAction() {
        // clear ListView selection
        visitedListView.getSelectionModel().clearSelection();
        // clear poi display
        displayLocationPOIS.getItems().clear();
        // disable poi input fields
        newPOIButton.setDisable(true);
        newPOITextField.setDisable(true);
    }

    /**
     * Load data from file action
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
                updateView();
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
     * Save to default file action
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
     * Save data to a new file action
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
     * Quits program
     */
    @FXML
    void quitAction() {
        System.exit(0);
    }


    /**
     * Alert Instructions on how to use Visited
     */
    @FXML
    void instructionsClicked() {
        // Create and display the appropriate alert for instructions
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("How to use Locationary");
        alert.setHeaderText("How to use your visited journal");
        alert.setContentText("The 'Location Name', 'Scope', and 'POI' fields are used to add locations to your visited locations.\n" +
                "Once you have added locations to your visited journal, you can choose to display any arrangement " +
                "of your Citywide, Domestic and International locations.\n" +
                "You can also click on any of the locations to view the POIS" +
                " (Points of interest) that you have added to that location. " +
                "From there you are able to add new POIS to the location or add ratings to the existing POIS");
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.show();
    }

    /**
     * View information
     */
    @FXML
    void aboutAction() {
        // Create and display the appropriate alert for information about the app
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About Locationary");
        alert.setHeaderText("Locationary");
        alert.setContentText("Locationary is a traveller's journal that keeps track of places one wants to travel to, and places that travellers have already been. " +
                "Authors:\nGaurav Ashar (gaurav.ashar@ucalgary.ca)\nRiyad Abdullayev (riyad.abdullayev@ucalgary.ca)");
        alert.show();
    }




}
