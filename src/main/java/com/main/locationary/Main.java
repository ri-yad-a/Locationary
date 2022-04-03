package com.main.locationary;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * CPSC 233 W22 Demo 3
 * Tutorial: T10
 * Date: April 01, 2022
 * Gaurav Ashar, Riyad Abdullayev
 */

public class Main extends Application {

    static AnchorPane root;
    static List<GridPane> grid = new ArrayList<GridPane>();

    private static int idx = 0;

    @Override
    public void start(Stage stage) throws IOException {
        root = FXMLLoader.load(getClass().getResource("home-view.fxml"));

        grid.add(FXMLLoader.load(getClass().getResource("bucketList-view.fxml")));
        grid.add(FXMLLoader.load(getClass().getResource("visited-view.fxml")));

        root.getChildren().add(grid.get(0));

        Scene scene = new Scene(root, 320, 240);
        stage.setTitle("Locationary - Demo 3 Release!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}