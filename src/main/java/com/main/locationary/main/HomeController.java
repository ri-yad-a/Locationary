package com.main.locationary.main;

import com.main.locationary.info.BucketList;
import com.main.locationary.info.Journal;
import com.main.locationary.info.Location;
import com.main.locationary.info.Visited;
import com.main.locationary.util.FileHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;

public class HomeController {

    // container objects used in BucketList and Visited Controllers
    public static BucketList bucketList = new BucketList();
    public static Visited visited = new Visited();

    @FXML
    private ListView<Location> bucketListView;

    @FXML
    private ListView<Location> visitedView;

    @FXML
    private Label statusLabel;

    @FXML
    private Button attributesButton;

    @FXML
    void initialize() {
        attributesButton.setDisable(true);
        updateViews();
    }

    /**
     * Show instructions
     */
    @FXML
    void instructionAction() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("How to use Locationary");
        alert.setHeaderText("How to navigate the Homepage");
        alert.setContentText("Use the File menu option to load, save, and save data as a .csv file, as well as to quit the program.\n" +
                "Use the Help menu option to view information about the program and instructions.\nFive locations from your Bucket List and Visited journal show up on this" +
                " screen when you add the.\nClick \"View Attributes\" to see the information specific to a certian location.\nFinally, use the other buttons to navigate to" +
                " other features of the program.");
        alert.show();
    }

    void updateViews() {
        bucketListView.getItems().clear();
        visitedView.getItems().clear();

        // if bucketlist has less than or equal to 5 locations
        // then output all those locations to the bucket list view
        ArrayList<Location> bucketListLocations = bucketList.getLocations();
        int blSize = bucketListLocations.size();
        if (blSize <= 5) {
            for (Location location: bucketListLocations) {
                bucketListView.getItems().add(location);
            }
            // if the bucketlist has more, output the last 5
        } else {
            int startIdx = blSize - 5;
            for (int i = startIdx; i < blSize; i++) {
                bucketListView.getItems().add(bucketListLocations.get(i));
            }
        }

        // if visited has less than or equal to 5 locations
        // then output all those locations to the visited view
        ArrayList<Location> visitedLocations = visited.getLocations();
        int vSize = visitedLocations.size();
        if (vSize <= 5) {
            for (Location location: visitedLocations) {
                visitedView.getItems().add(location);
            }
        } else {
            // output the 5 most highly rated locations
            Location[] top = visited.getTopFive();
            for (int i = 0; i < top.length; i++){
                visitedView.getItems().add(top[i]);
            }
        }



    }

    /**
     * Go to Bucket List view
     */
    @FXML
    void viewEditBucketListButtonClicked() {
        Main.switchScreen("bucketList-view.fxml");
    }


    /**
     * Go to Visited View
     * @param event
     */
    @FXML
    void viewEditVisitedButtonClicked(ActionEvent event) {
        Main.switchScreen("visited-view.fxml");

    }

    /**
     * Load a file to program
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
                bucketList = (BucketList) j[0];
                visited = (Visited) j[1];
                statusLabel.setTextFill(Color.BLACK);
                statusLabel.setText("Your BucketList and Visited journal have been loaded from " + fileLoad.getName());
                updateViews();
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
     * Save data to default file
     */
    @FXML
    void saveAction() {
        // write to default file
        boolean b = FileHandler.writeToFile("data.csv", bucketList, visited);
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
     * Save data to specified file
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
            boolean b = FileHandler.writeToFile(fileSave.getPath(), bucketList, visited);
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

    /**
     * Select a location from the Bucket List view
     */
    @FXML
    void selectBucketListLocationAction() {
        // clear selection from other
        visitedView.getSelectionModel().clearSelection();
        // get selected item
        Location l = bucketListView.getSelectionModel().getSelectedItem();
        // check if null
        if (l != null) {
            attributesButton.setDisable(false);
        } else {
            attributesButton.setDisable(true);
            statusLabel.setText("Please choose a location from the list!");
        }
    }

    /**
     * Select a location from the Visited view
     */
    @FXML
    void selectVisitedLocationAction() {
        // clear selection from other
        bucketListView.getSelectionModel().clearSelection();
        // get selected item
        Location l = visitedView.getSelectionModel().getSelectedItem();
        // check if null
        if (l != null) {
            attributesButton.setDisable(false);
        } else {
            statusLabel.setText("Please choose a location from the list!");
        }
    }

    /**
     * View attributes of selected location
     */
    @FXML
    void viewAttributesAction() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        Location l = bucketListView.getSelectionModel().getSelectedItem();
        if (l == null) {
            l = visitedView.getSelectionModel().getSelectedItem();
        }
        alert.setTitle("Information about a location");
        alert.setHeaderText(l.getName());
        alert.setContentText(l.toVerboseString());
        alert.show();
    }



}

