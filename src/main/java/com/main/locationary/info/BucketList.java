package com.main.locationary.info;

import java.util.ArrayList;

/**
 * CPSC 233 W22 Final submission
 * Tutorial: T10
 * Date: April 15, 2022
 * Gaurav Ashar, Riyad Abdullayev
 * This class handles Location objects that are present in the user's Bucket List.
 */

public class BucketList extends Journal {

    private ArrayList<Location> locations;

    public BucketList() {
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

        // add all locations
        retStr += "Bucket List Locations:\n";
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
        retStr.append("Bucket List Locations:\n");
        for (Location location: this.locations) {
            retStr.append(location.getFileString());
        }
        return retStr.toString();
    }

    /**
     * Overriden equals method from the Object class
     * @param o the object to check if equal to this
     * @return a boolean value, true if they are equal, false, if not
     */
    @Override
    public boolean equals(Object o) {
        // check if locations are the same
        BucketList bl = (BucketList) o;
        return this.locations.equals(bl.getLocations());
    }

    public void removeLocation(Location l) {
        locations.remove(l);
    }
}
