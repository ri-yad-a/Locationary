package com.main.locationary.info;

/**
 * CPSC 233 W22 Demo 3
 * Tutorial: T10
 * Date: April 01, 2022
 * Gaurav Ashar, Riyad Abdullayev
 * This class contains information about Points of Interests that the user
 * adds to their Locations
 */

public class POI implements Comparable, JournalInfo {

    private String name;
    private int ranking;
    private double rating;

    public POI(String n) {
        this.name = n;
        this.ranking = -1;
        this.rating = -1.0;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setRating(double r) {
        this.rating = r;
    }

    @Override
    public double getRating() {
        return this.rating;
    }

    @Override
    public int getRanking() {
        return this.ranking;
    }

    /**
     * Returns a String for saving to file
     * @return a String representation of the object for the file
     */
    @Override
    public String getFileString() {
        return String.format("\t\tPOI,%s,%d,%f\n", this.name, this.ranking, this.rating);
    }

    @Override
    public void setRanking(int i) {
        this.ranking = i;
    }

    /**
     * Overriden toString method from Object class
     * @return a String representation of this object
     */
    @Override
    public String toString() {
        // add all necessary attributes
        String retStr = "";
        if (this.rating != -1) {
            retStr += "" + this.name + "(" + this.rating + ")";
        } else {
            retStr += "" + this.name;
        }
        return retStr;
    }

    /**
     * Overriden compareTo method from Comparable interface
     * @param o the object to compare to, casted to POI
     * @return an integer representing this object's comparison to the other
     */
    @Override
    public int compareTo(Object o) {
        // sort by rating
        POI other = (POI) o;
        if (this.rating < other.rating) {
            return -1;
        } else if (this.rating > other.rating) {
            return 1;
        }
        return 0;
    }

    /**
     * Overriden equals method from the Object class
     * @param o the object to check if equal to this
     * @return a boolean value, true if they are equal, false, if not
     */
    @Override
    public boolean equals(Object o) {
        // check instance variables are equal
        POI p = (POI) o;
        if (p != null) {
            return this.name.equals(p.getName()) && this.rating == p.getRating() && this.ranking == p.getRanking();
        }
        return false;
    }
}
