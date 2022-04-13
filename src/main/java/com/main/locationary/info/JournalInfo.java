package com.main.locationary.info;

/**
 * CPSC 233 W22 Demo 3
 * Tutorial: T10
 * Date: April 01, 2022
 * Gaurav Ashar, Riyad Abdullayev
 * This interface contains the framework methods necessary for the lower-level data handler objects,
 * such as Location and POI
 */

public interface JournalInfo {

    /**
     * Returns a String representation of the name of the object
     * @return a String with the object's name
     */
    String getName();

    /**
     * Sets a String representation of the name of the object
     * @param name the name to set the location/poi to
     */
    void setName(String name);

    /**
     * Sets rating of location/poi
     * @param r the rating to set it to
     */
    void setRating(double r);

    /**
     * Returns the rating of an object
     * @return the rating
     */
    double getRating();

    /**
     * Sets the ranking of the object according to ratings
     * @param r the ranking to set to
     */
    void setRanking(int r);

    /**
     * Returns an object's ranking
     * @return the ranking
     */
    int getRanking();

    /**
     * Returns a String for saving to file
     * @return a String representation of the object for the file
     */
    String getFileString();

}
