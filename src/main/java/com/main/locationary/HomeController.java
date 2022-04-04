package com.main.locationary;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.util.ArrayList;

public class HomeController {

    public static BucketList bucketList = new BucketList();
    public static Visited visited = new Visited();

    @FXML
    private ListView<Location> bucketListView;

    @FXML
    private ListView<Location> visitedView;


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



}

