package com.main.locationary.info;

import java.util.ArrayList;

/**
 * CPSC 233 W22 Final submission
 * Tutorial: T10
 * Date: April 15, 2022
 * Gaurav Ashar, Riyad Abdullayev
 * This class sets the framework for information handlers such as BucketList and Visited objects.
 * */

public class Journal {

    private ArrayList<Location> locations;

    public Journal() {
        this.locations = new ArrayList<>();
    }

    /**
     * This method is responsible for adding a Location object
     * @param location is the Location object that will be added
     */
    public void addLocation(Location location) {
        this.locations.add(location);
    }

    /**
     * This method is responsible for getting a Locaiton at a specific index
     * @param index is the specified index we want to get the location from
     * @return the Location object that is at the desired index
     */
    public Location getLocation(int index) {
        return this.locations.get(index);
    }

    /**
     * This method is responsible for returning all locations
     * @return an ArrayList of all locations
     */
    public ArrayList<Location> getLocations() {
        return this.locations;
    }

    /**
     * This method is responsible for checking if a Journal item has a specific location
     * @param name is the location we are checking for
     * @return true if the Journal item has the location and false if the location item does not have it
     */
    public boolean hasLocation(String name) {
        // loop through location objects
        for (Location location: this.locations) {
            // if there is a location with the same name as the argument
            if (location.getName().equals(name)) {
                // return true
                return true;
            }
        }
        // otherwise return false
        return false;
    }

    /**
     * This method is responsible for getting a specified Location from the Journal item
     * @param name is the name of the location we wish to retrieve
     * @return the location object that has the name of the parameter 'name'
     */
    public Location getLocation(String name) {
        // loop through location objects
        for (Location location: this.locations) {
            // if the current location has the same name as the argument
            if (location.getName().equals(name)) {
                // return the location object
                return location;
            }
        }
        return null;
    }

}
