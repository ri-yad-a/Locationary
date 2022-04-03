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
        ArrayList<Location> bucketListLocations = new ArrayList<Location>();
        int size = bucketListLocations.size();
        if (size <= 5) {
            for (Location location: bucketListLocations) {
                bucketListView.getItems().add(location);
            }
            // if the bucketlist has more, output the last 5
        } else if (size > 5) {
            int startIdx = size - 5;
            for (int i = startIdx; i < size; i++) {
                bucketListView.getItems().add(bucketListLocations.get(i));
            }
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

