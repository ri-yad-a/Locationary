package com.main.locationary;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class HomeController {

    public static BucketList bucketList = new BucketList();
    public static Visited visited = new Visited();

    @FXML
    private ListView<Location> bucketListView;

    @FXML
    private ListView<Location> visitedView;

    @FXML
    private Label statusLabel;


    @FXML
    void viewAttributesButtonClicked(ActionEvent event) {

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
        }



    }

    @FXML
    void viewEditBucketListButtonClicked(ActionEvent event) {
        Main.switchScreen("bucketList-view.fxml");
    }

    @FXML
    void viewEditVisitedButtonClicked(ActionEvent event) {
        Main.switchScreen("visited-view.fxml");

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
                bucketList = (BucketList) j[0];
                visited = (Visited) j[1];
                statusLabel.setTextFill(Color.BLACK);
                statusLabel.setText("Your BucketList and Visited journal have been loaded from " + fileLoad.getName());
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



}

