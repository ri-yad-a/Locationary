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
 * CPSC 233 W22 Demo 3
 * Tutorial: T10
 * Date: April 01, 2022
 * Gaurav Ashar, Riyad Abdullayev
 */

public class Main extends Application {

    private static Stage mainStage;

    @Override
    public void start(Stage stage) throws IOException {
        mainStage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("home-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 700, 500);
        mainStage.setTitle("Locationary - Demo 3 Release");
        mainStage.setScene(scene);
        mainStage.show();
    }

    public static void main(String[] args) {

        File f = null;
        if (args.length == 1) {
            f = new File(args[0]);
            if (f.exists()) {
                FileHandler.setFile(f);
                Journal[] j1 = FileHandler.getFromFile();
                HomeController.bucketList = (BucketList) j1[0];
                HomeController.visited = (Visited) j1[1];
            }
        }
        FileHandler.setFile(f);
        launch();
    }

    /**
     * Loads an FXML file to the stage
     * @param filename an FXML file
     */
    public static void switchScreen(String filename) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(filename));
            Scene scene = new Scene(fxmlLoader.load(), 700, 500);
            mainStage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}