package com.main.locationary;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class HomeController {

    @FXML
    void viewAttributesButtonClicked(ActionEvent event) {

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

