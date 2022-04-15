package com.main.locationary.util;

import com.main.locationary.info.Journal;
import com.main.locationary.info.Location;
import com.main.locationary.info.POI;
import com.main.locationary.info.Visited;
import com.main.locationary.info.BucketList;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * CPSC 233 W22 Final submission
 * Tutorial: T10
 * Date: April 15, 2022
 * Gaurav Ashar, Riyad Abdullayev
 * This standalone class deals with loading from and saving to file, and does not represent any
 * sort of object
 */

public class FileHandler {

    // class variable for the file to save to/load from
    private static File file;

    /**
     * Sets the file for the class
     * @param f the file to set to
     */
    public static void setFile(File f) {
        file = f;
    }

    /**
     * Writes to set file.
     * @param filename the file to add to
     * @param bl the Bucket List object
     * @param visited the Visited object
     */
    public static boolean writeToFile(String filename, BucketList bl, Visited visited) {
        // get file strings
        String blString = bl.getFileString();
        String visitedString = visited.getFileString();
        try {
            // write to file
            file = new File(filename);
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
            bufferedWriter.write(blString + "\n");
            bufferedWriter.write(visitedString);
            // immediate writing
            bufferedWriter.flush();
            bufferedWriter.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * Read from file.
     * @return a Journal array, index 0 is the bucket list and 1 is the visited journal
     */
    public static Journal[] getFromFile() {
        // init array
        Journal[] journals = new Journal[2];
        try {
            // read from file
            Scanner sc = new Scanner(file);
            BucketList newBL = new BucketList();
            Visited newVisit = new Visited();
            boolean bl = true;
            Location location = new Location();
            while(sc.hasNext()) {
                POI newPOI = new POI(null);
                // get next line
                String line = sc.nextLine();
                // split by comma
                String[] lineSplit = line.split(",");
                // set bl to true or false, determining whether the location should be added to bl or not
                if(lineSplit.length == 1 && lineSplit[0].contains("Bucket")) {
                    bl = true;
                } else if (lineSplit.length == 1 && lineSplit[0].contains("Visited")) {
                    bl = false;
                } else {
                    // check amount of tabs
                    if (lineSplit[0].contains("\t\t")) {
                        // init poi and place in location
                        newPOI.setName(lineSplit[1]);
                        newPOI.setRanking(Integer.parseInt(lineSplit[2]));
                        newPOI.setRating(Double.parseDouble(lineSplit[3]));
                        location.addPOI(newPOI);
                    } else if (lineSplit[0].contains("\t")) {
                        // init location, get info to add to it
                        location = new Location();
                        location.setName(lineSplit[1]);
                        location.setRanking(Integer.parseInt(lineSplit[2]));
                        location.setRating(Double.parseDouble(lineSplit[3]));
                        // find scope and set it
                        if (bl && lineSplit[0].contains("Citywide")) {
                            location.setScope(Location.Scope.CITYWIDE);
                            newBL.addLocation(location);
                        } else if (bl && lineSplit[0].contains("Domestic")) {
                            location.setScope(Location.Scope.DOMESTIC);
                            newBL.addLocation(location);
                        } else if(bl && lineSplit[0].contains("International")) {
                            location.setScope(Location.Scope.INTERNATIONAL);
                            newBL.addLocation(location);
                        } else if(!bl && lineSplit[0].contains("Citywide")) {
                            location.setScope(Location.Scope.CITYWIDE);
                            newVisit.addLocation(location);
                        } else if(!bl && lineSplit[0].contains("Domestic")) {
                            location.setScope(Location.Scope.DOMESTIC);
                            newVisit.addLocation(location);
                        } else if(!bl && lineSplit[0].contains("International")) {
                            location.setScope(Location.Scope.INTERNATIONAL);
                            newVisit.addLocation(location);
                        } else {
                            // incorrect formatting
                            return null;
                        }
                    }
                }
            }
            // add objects to array
            journals[0] = newBL;
            journals[1] = newVisit;
            sc.close();
        } catch (IOException | NumberFormatException | ArrayIndexOutOfBoundsException e) {
            journals = null;
        }
        // return array
        return journals;
    }


}
