package com.main.locationary.main;

import com.main.locationary.info.BucketList;
import com.main.locationary.info.Journal;
import com.main.locationary.info.Visited;
import com.main.locationary.util.FileHandler;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

/**
 * CPSC 233 W22 Final submission
 * Tutorial: T10
 * Date: April 15, 2022
 * Gaurav Ashar, Riyad Abdullayev
 * The runner class, contains main method which launches Java FX program
 */

public class Main extends Application {

    private static Stage mainStage;

    @Override
    public void start(Stage stage) throws IOException {
        // set the main stage for the switch scene method
        mainStage = stage;
        // load the main page
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("home-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 700, 500);
        // set controls and show stage
        mainStage.setTitle("Locationary - Final release");
        mainStage.setScene(scene);
        mainStage.show();
    }

    public static void main(String[] args) {
        // command line argument handle
        File f = null;
        // if the length is 1 only
        if (args.length == 1) {
            // search for file
            f = new File(args[0]);
            if (f.exists()) {
                // if exists, get data from it
                FileHandler.setFile(f);
                Journal[] j1 = FileHandler.getFromFile();
                HomeController.bucketList = (BucketList) j1[0];
                HomeController.visited = (Visited) j1[1];
            }
        }
        FileHandler.setFile(f);
        // launch java fx components
        launch();
    }

    /**
     * Loads an FXML file to the stage
     * @param filename an FXML file
     */
    public static void switchScreen(String filename) {
        try {
            // check file
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(filename));
            Scene scene = new Scene(fxmlLoader.load(), 700, 500);
            // set it as the scene of the main stage
            mainStage.setScene(scene);
        } catch (IOException ignored) {
        }

    }
}