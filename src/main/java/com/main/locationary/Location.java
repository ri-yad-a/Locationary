package com.main.locationary;

import java.util.ArrayList;

/**
 * CPSC 233 W22 Demo 2
 * Tutorial: T10
 * Date: March 23, 2022
 * Gaurav Ashar, Riyad Abdullayev
 * This class represents Location objects, which are locations that the user has declared
 * to keep in their Bucket List or Visited Journal
 */

public class Location implements Comparable, JournalInfo {

    // instance variables
    private Scope scope;
    private String name;
    private int ranking;
    private double rating;
    private ArrayList<POI> pois;

    public enum Scope {
        CITYWIDE, DOMESTIC, INTERNATIONAL;

        // returns a String representation of the enum
        public String toString() {
            if (this == CITYWIDE) {
                return "Citywide";
            } else if (this == DOMESTIC) {
                return "Domestic";
            } else {
                return "International";
            }
        }
    }

    public Location(Scope s, String n) {
        this.scope = s;
        this.name = n;
        this.ranking = -1;
        this.rating = -1;
        this.pois = new ArrayList<>();
    }

    public Location() {
        this.ranking = -1;
        this.rating = -1;
        this.pois = new ArrayList<>();
    }

    public Scope getScope() {
        return this.scope;
    }

    public void setScope(Scope scope) {
        this.scope = scope;
    }

    public void addPOI(POI poi) {
         this.pois.add(poi);
    }

    public POI getPOI(String name) {
        for (POI poi: this.pois) {
            if (poi.getName().equals(name)) {
                return poi;
            }
        }
        return null;
    }

    public ArrayList<POI> getPOIs() {
        return this.pois;
    }

    public boolean hasPOI(String name) {
        for (POI poi: pois) {
            if (poi.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }


    @Override
    public String getName() {
        return this.name;
    }

    /**
     * @param name the name to set the location/poi to
     */
    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setRating(double r) {
        if (r == 0.00) {
            double sum = 0;
            int count = 0;
            for (POI poi : this.pois) {
                if (poi.getRating() != -1.000) {
                    sum += poi.getRating();
                    count++;
                }
            }
            if (count != 0) {
                this.rating = sum / count;
            }
        } else {
            this.rating = r;
        }

    }

    @Override
    public double getRating() {
        return this.rating;
    }

    /**
     * @param r the ranking to set to
     */
    @Override
    public void setRanking(int r) {
        this.ranking = r;
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
        StringBuilder poiString = new StringBuilder();
        int count = 0;
        for (POI poi: this.pois) {
            poiString.append(poi.getFileString());
            if (count != this.pois.size() - 1) {
                poiString.append("\n");
            }
        }
        return String.format("\t%s Location,%s,%d,%f\n%s", this.scope.toString(), this.name, this.ranking, this.rating, poiString);
    }

    /**
     * Overriden toString method from Object class
     * @return a String representation of this object
     */
    @Override
    public String toString() {
        // add POIs, rating, etc
        String retStr = "";
        if (this.rating != -1) {
            retStr += this.name + "(" + this.rating + ") : \t" + "POIS: " + pois;
        } else {
            retStr += this.name + ": \t" + "POIS: " + pois;
        }
        return retStr;
    }

    /**
     * Overriden compareTo method from Comparable interface
     * @param o the object to compare to, casted to Location
     * @return an integer representing this object's comparison to the other
     */
    @Override
    public int compareTo(Object o) {
        // sort by rating
        Location other = (Location) o;
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
        // compare by instance variables
        Location l = (Location) o;
        return this.pois.equals(l.getPOIs()) && this.name.equals(l.getName())
                && this.rating == l.getRating() && this.ranking == l.getRanking();
    }

}
