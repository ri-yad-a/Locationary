package com.main.locationary;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Scanner;

/**
 * CPSC 233 W22 Demo 3
 * Tutorial: T10
 * Date: April 01, 2022
 * Gaurav Ashar, Riyad Abdullayev
 */

public class Main extends Application {

    private Stage mainStage;

    @Override
    public void start(Stage stage) throws IOException {
        mainStage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("home-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 850, 650);
        mainStage.setTitle("Locationary - Demo 3 Release");
        mainStage.setScene(scene);
        mainStage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    /**
     * Loads an FXML file to the stage
     * @param filename an FXML file
     */
    public void switchScreen(String filename) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(filename));
            Scene scene = new Scene(fxmlLoader.load(), 850, 650);
            mainStage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}