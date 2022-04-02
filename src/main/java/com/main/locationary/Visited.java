package com.main.locationary;

import java.util.ArrayList;
import java.util.Collections;

/**
 * CPSC 233 W22 Demo 3
 * Tutorial: T10
 * Date: April 01, 2022
 * Gaurav Ashar, Riyad Abdullayev
 * This class handles Location objects that are present in the user's Visited Journal
 */

public class Visited extends Journal{

    private ArrayList<Location> locations;

    public Visited() {
        super();
        this.locations = super.getLocations();
    }

    /**
     * This method overrides the Object toString() method to return a string with desired formatting for Journal items
     * @return a String in the desired format for a Journal item
     */
    @Override
    public String toString() {
        // init Strings
        String retStr = "";
        String retCityStr = "";
        String retDomesticStr = "";
        String retInternationalStr = "";

        retStr += "Visited Locations:\n";
        // add all locations
        for (Location location : this.locations) {
            if (location.getScope() == Location.Scope.CITYWIDE) {
                retCityStr += "\t\t" + location + "\n";
            } else if (location.getScope() == Location.Scope.DOMESTIC) {
                retDomesticStr += "\t\t" + location + "\n";
            } else {
                retInternationalStr += "\t\t" + location + "\n";
            }

        }
        // add to Strings
        retStr += "\t Citywide: \n" + retCityStr;
        retStr += "\t Domestic: \n" + retDomesticStr;
        retStr += "\t International: \n" + retInternationalStr;
        // return
        return retStr;
    }

    /**
     * This method is responsible for creating a String in a convientent format
     * that will be used to read info about Journal items from files
     * @return a String in the desired format for reading Journal items from files
     */
    public String getFileString() {
        StringBuilder retStr = new StringBuilder();
        retStr.append("Visited Locations:\n");
        for (Location location: this.locations) {
            retStr.append(location.getFileString());
        }
        return retStr.toString();
    }

    public double getRating(Location location) {
        return location.getRating();
    }

    /**
     * Returns the top three locations, according to Rating
     * @return an array of Locations, size 3, with the top 3 locations
     */
    public Location[] getTopThree() {
        // create copy and sort, invokes compareTo
        ArrayList<Location> copy = new ArrayList<>();
        for (Location l: this.locations) {
            copy.add(l);
        }
        Collections.sort(copy, Collections.reverseOrder());
        // init return variable
        Location[] top = new Location[3];
        // add top 3
        for (int i = 0; i < 3; i++) {
            if (i < copy.size()) {
                top[i] = copy.get(i);
            }
        }
        // return
        return top;
    }

    /**
     * Overriden equals method from the Object class
     * @param o the object to check if equal to this
     * @return a boolean value, true if they are equal, false, if not
     */
    @Override
    public boolean equals(Object o) {
        // check if locations are the same
        Visited v = (Visited) o;
        return this.locations.equals(v.getLocations());
    }


}
