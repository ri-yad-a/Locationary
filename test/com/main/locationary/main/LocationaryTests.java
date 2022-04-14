package com.main.locationary.main;

import com.main.locationary.info.*;
import com.main.locationary.util.FileHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * CPSC 233 W22 Demo 3
 * Tutorial: T10
 * Date: March 23, 2022
 * Gaurav Ashar, Riyad Abdullayev
 * This is a testing class for multiple classes
 */

class LocationaryTests {

    public static BucketList bl;
    public static Visited visited;

    @BeforeEach
    void setUp() {
        // create locations and add POIs where necessary
        bl = new BucketList();
        visited = new Visited();
        bl.addLocation(new Location(Location.Scope.CITYWIDE, "Calgary"));
        Location new1 = new Location(Location.Scope.DOMESTIC, "Vancouver");
        new1.addPOI(new POI("Beach"));
        bl.addLocation(new1);
        Location new2 = new Location(Location.Scope.INTERNATIONAL, "Paris");
        new2.addPOI(new POI("Eiffel Tower"));
        new2.addPOI(new POI("Louvre"));
        bl.addLocation(new2);

        Location new3 = new Location(Location.Scope.INTERNATIONAL, "Dubai");
        new3.addPOI(new POI("Burj Khalifa"));
        new3.setRating(1.2);
        visited.addLocation(new3);
        Location new4 = new Location(Location.Scope.DOMESTIC, "Toronto");
        new4.setRating(3.4);
        new4.addPOI(new POI("CN Tower"));
        new4.addPOI(new POI("Art Gallery"));
        visited.addLocation(new4);
        visited.addLocation(new Location(Location.Scope.CITYWIDE, "Edmonton"));
    }

    @AfterEach
    void tearDown() {
    }

    // test the hasLocation() method when the BucketList object has the location
    @Test
    public void testHasLocationBucketListTrue() {
        // create the expected boolean of true
        boolean expected = true;
        // create a location that already exists in the bucket list
        Location newLocation = new Location(Location.Scope.DOMESTIC, "Vancouver");
        // create a boolean that will store true if the bucket list has the previously created location and false if it doesn't
        boolean actual = bl.hasLocation("Vancouver");
        // assert that the two booleans are equal
        assertEquals(expected, actual, "The hasLocation() method does not return true when the bucket list has the desired location");
    }

    // test the hasLocation() method when the BucketList object does not have the location
    @Test
    public void testHasLocationBucketListFalse() {
        // create the expected boolean of false
        boolean expected = false;
        // create a location that does not already exist in the bucket list
        Location newLocation = new Location(Location.Scope.INTERNATIONAL, "Munich");
        // create a boolean that will store true if the bucket list has the previously created location and false if it doesn't
        boolean actual = bl.hasLocation("Munich");
        // assert that the two booleans are equal
        assertEquals(expected, actual, "The hasLocation() method does not return false when the bucket list does" +
                " not have the desired location");
    }

    @Test
    public void testHasLocationVisitedTrue() {
        // create the expected boolean of true
        boolean expected = true;
        // create a location that already exists in the bucket list
        Location newLocation = new Location(Location.Scope.INTERNATIONAL, "Dubai");
        // create a boolean that will store true if the bucket list has the previously created location and false if it doesn't
        boolean actual = visited.hasLocation("Dubai");
        // assert that the two booleans are equal
        assertEquals(expected, actual, "The hasLocation() method does not return true when the visited has the desired location");
    }

    // test the hasLocation() method when the BucketList object does not have the location
    @Test
    public void testHasLocationVisitedFalse() {
        // create the expected boolean of false
        boolean expected = false;
        // create a location that does not already exist in the bucket list
        Location newLocation = new Location(Location.Scope.INTERNATIONAL, "Munich");
        // create a boolean that will store true if the bucket list has the previously created location and false if it doesn't
        boolean actual = visited.hasLocation("Munich");
        // assert that the two booleans are equal
        assertEquals(expected, actual, "The hasLocation() method does not return false when the visited does" +
                " not have the desired location");
    }

    // test equals()
    @Test
    public void testBLEquals() {
        // init test object and add locations in same manner
        BucketList test = new BucketList();
        test.addLocation(new Location(Location.Scope.CITYWIDE, "Calgary"));
        Location new1 = new Location(Location.Scope.DOMESTIC, "Vancouver");
        new1.addPOI(new POI("Beach"));
        test.addLocation(new1);
        Location new2 = new Location(Location.Scope.INTERNATIONAL, "Paris");
        new2.addPOI(new POI("Eiffel Tower"));
        new2.addPOI(new POI("Louvre"));
        test.addLocation(new2);
        // assert equals
        assertEquals(bl, test, "Two equal Visited objects are incorrectly designated as unequal");

    }

    @Test
    public void testBLNotEquals() {
        // init object, no locations added
        BucketList test = new BucketList();
        // assert they are unequal
        assertNotEquals(bl, test, "Two unequal Visited objects are incorrectly designated as equal");
    }

    // test the toString() method that will be responsible for turning a BucketList or visited object into a printable/readable String
    @Test
    public void testToStringBucketList() {
        // create the expected String
        String expected = "Bucket List Locations:\n" +
                "\t Citywide: \n" +
                "\t\t[C] Calgary\n" +
                "\t Domestic: \n" +
                "\t\t[D] Vancouver\n" +
                "\t International: \n" +
                "\t\t[I] Paris\n";
        // retrieve the actual string by using toString() on the bucket list object
        String actual = bl.toString();
        // assert that the two strings are equal
        assertEquals(expected, actual, "toString() does not return the appropriate String to represent a BucketList object");
    }

    @Test
    public void testToStringVisited() {
        // create the expected String
        String expected = "Visited Locations:\n" +
                "\t Citywide: \n" +
                "\t\t[C] Edmonton\n" +
                "\t Domestic: \n" +
                "\t\t[D] Toronto\n" +
                "\t International: \n" +
                "\t\t[I] Dubai\n";
        // retrieve the actual string by using toString() on the visited object
        String actual = visited.toString();
        // assert that the two strings are equal
        assertEquals(expected, actual, "toString() does not return the appropriate String to represent a Visited object");
    }

    @Test
    public void testToStringPOI() {
        // create the expected String
        String expected = "Burj Khalifa(4.5)";
        POI poi = new POI("Burj Khalifa");
        poi.setRating(4.5);
        // retrieve the actual string by using toString() on the visited object
        String actual = poi.toString();
        // assert that the two strings are equal
        assertEquals(expected, actual, "toString() does not return the appropriate String to represent a POI object");
    }

    @Test
    public void testGetFileStringJournal() {
        // created the expected String
        String expected = "Visited Locations:\n" +
                "\tInternational Location,Dubai,-1,1.200000\n" +
                "\t\tPOI,Burj Khalifa,-1,-1.000000\n" +
                "\tDomestic Location,Toronto,-1,3.400000\n" +
                "\t\tPOI,CN Tower,-1,-1.000000\n" +
                "\n" +
                "\t\tPOI,Art Gallery,-1,-1.000000\n" +
                "\n" +
                "\tCitywide Location,Edmonton,-1,-1.000000\n";
        // get the actual String by using the getFileString() method
        String actual = visited.getFileString();
        // assert the two string are equal
        assertEquals(expected, actual, "getFileString() does not return the appropriate String to represent a Visited object" +
                "in file format");
    }

    // test getTopFive
    @Test
    public void testGetTopFive() {

    }
    // No locations added
    @Test
    // test with no locations
    public void testGetTopFiveNone() {

    }

    // test equals()
    @Test
    public void testVisitedEquals() {
        // init test object and add locations in same manner
        Visited test = new Visited();
        Location new3 = new Location(Location.Scope.INTERNATIONAL, "Dubai");
        new3.addPOI(new POI("Burj Khalifa"));
        new3.setRating(1.2);
        test.addLocation(new3);
        Location new4 = new Location(Location.Scope.DOMESTIC, "Toronto");
        new4.setRating(3.4);
        new4.addPOI(new POI("CN Tower"));
        new4.addPOI(new POI("Art Gallery"));
        test.addLocation(new4);
        test.addLocation(new Location(Location.Scope.CITYWIDE, "Edmonton"));
        // assert equals
        assertEquals(visited, test, "Two equal Visited objects are incorrectly designated as unequal");

    }

    @Test
    public void testVisitedNotEquals() {
        // init object, no locations added
        Visited test = new Visited();
        // assert they are unequal
        assertNotEquals(visited, test, "Two unequal Visited objects are incorrectly designated as equal");
    }


    // test FileHandler methods
    // test writeToFile() in FileHandler class
    @Test
    public void testWriteToFile() throws FileNotFoundException {
        // write to a file
        FileHandler.writeToFile("test.csv", bl, visited);
        // create scanner object to get file string
        Scanner sc = new Scanner(new File("test.csv"));
        String fileString = "";
        // init expected string
        String expected = "Bucket List Locations:\n" +
                "\tCitywide Location,Calgary,-1,-1.000000\n" +
                "\tDomestic Location,Vancouver,-1,-1.000000\n" +
                "\t\tPOI,Beach,-1,-1.000000\n" +
                "\tInternational Location,Paris,-1,-1.000000\n" +
                "\t\tPOI,Eiffel Tower,-1,-1.000000\n" +
                "\n" +
                "\t\tPOI,Louvre,-1,-1.000000\n" +
                "\n" +
                "\n" +
                "Visited Locations:\n" +
                "\tInternational Location,Dubai,-1,1.200000\n" +
                "\t\tPOI,Burj Khalifa,-1,-1.000000\n" +
                "\tDomestic Location,Toronto,-1,3.400000\n" +
                "\t\tPOI,CN Tower,-1,-1.000000\n" +
                "\n" +
                "\t\tPOI,Art Gallery,-1,-1.000000\n" +
                "\n" +
                "\tCitywide Location,Edmonton,-1,-1.000000\n";
        // add to file string
        while (sc.hasNext()) {
            fileString += sc.nextLine() + "\n";
        }
        // assert they are equal
        assertEquals(expected, fileString, "writeToFile() in FileHandler does not write to file correctly");

    }

    // test getFromFile() method in FileHandler
    @Test
    public void testGetFromFileBL() {
        // test if the bucket list is correct from the file
        FileHandler.setFile(new File("test.csv"));
        Journal[] journals = FileHandler.getFromFile();
        BucketList actualBL = (BucketList) journals[0];
        assertEquals(bl, actualBL, "FileHandler does not return the correct BucketList from file");
    }

    @Test
    public void testGetFromFileVisited() {
        // test if the visited journal is correct from file
        FileHandler.setFile(new File("test.csv"));
        Journal[] journals = FileHandler.getFromFile();
        Visited actualVisited = (Visited) journals[1];
        assertEquals(visited, actualVisited, "FileHandler does not return the correct Visited journal from file");
    }

    @Test
    public void testHasPOITrue() {
        // create a new location and add pois
        Location testLocation = new Location(Location.Scope.INTERNATIONAL, "Dubai");
        testLocation.addPOI(new POI("Burj Khalifa"));
        testLocation.addPOI(new POI("Burj Al Arab"));
        testLocation.addPOI(new POI("Dubai Skydiving"));
        // created the expected boolean value
        boolean expected = true;
        // get the actual boolean value by using the hasPOI() method
        boolean actual = testLocation.hasPOI("Burj Al Arab");
        // assert that the two expected and actual booleans are equal
        assertEquals(expected, actual, "The hasPOI() method does not return true when the a location has the desired POI");
    }

    @Test
    public void testHasPOIFalse() {
        // create a new location and add pois
        Location testLocation = new Location(Location.Scope.INTERNATIONAL, "Dubai");
        testLocation.addPOI(new POI("Burj Khalifa"));
        testLocation.addPOI(new POI("Burj Al Arab"));
        // created the expected boolean value
        boolean expected = false;
        // get the actual boolean value by using the hasPOI() method
        boolean actual = testLocation.hasPOI("Dubai Skydiving");
        // assert that the two expected and actual booleans are equal
        assertEquals(expected, actual, "The hasPOI() method does not return false when the a location does not have the desired POI");
    }

    @Test
    public void testGetFileStringLocation() {
        String expected = "\tInternational Location,Dubai,-1,-1.000000\n" +
                "\t\tPOI,Burj Khalifa,-1,-1.000000\n";
        // create a new location and add pois
        Location testLocation = new Location(Location.Scope.INTERNATIONAL, "Dubai");
        testLocation.addPOI(new POI("Burj Khalifa"));
        String actual = testLocation.getFileString();
        assertEquals(expected, actual);

    }

    // test Location methods
    // toString()
    @Test
    // test location with no POIs
    public void testToStringLocationNoPOI() {
        // get actual
        String actual = bl.getLocation("Calgary").toString();
        // init expected
        String expected = "[C] Calgary";
        // assert they are equal
        assertEquals(expected, actual, "The toString() method in Location does not correctly represent a Location with no POIs");
    }

    @Test
    // test location with one POI
    public void testToStringLocationOnePOI() {
        // get actual
        String actual = bl.getLocation("Vancouver").toString();
        // init expected
        String expected = "Vancouver: \tPOIS: [Beach]";
        // assert they are equal
        assertEquals(expected, actual, "The toString() method in Location does not correctly represent a Location with one POI");
    }

    @Test
    // test location with two POIs
    public void testToStringLocationTwoPOI() {
        // get actual
        String actual = bl.getLocation("Paris").toString();
        // init expected
        String expected = "Paris: \tPOIS: [Eiffel Tower, Louvre]";
        // assert they are equal
        assertEquals(expected, actual, "The toString() method in Location does not correctly represent a Location with two POIs");
    }

    // compareTo()
    @Test
    // test location with lesser rating
    public void testCompareSmallerLocation() {
        // get actual
        int actual = visited.getLocation("Edmonton").compareTo(visited.getLocation("Toronto"));
        // assert they are equal
        assertEquals(-1, actual, "compareTo() in Location does not designate a location with a lesser rating as smaller");
    }
    @Test
    // test location with same rating
    public void testCompareSameLocation() {
        // get actual
        int actual = visited.getLocation("Toronto").compareTo(visited.getLocation("Toronto"));
        // assert they are equal
        assertEquals(0, actual, "compareTo() in Location does not designate a location with a same rating as same");
    }
    @Test
    // test location with more rating
    public void testCompareLargerLocation() {
        // get actual
        int actual = visited.getLocation("Toronto").compareTo(visited.getLocation("Dubai"));
        // assert they are equal
        assertEquals(1, actual, "compareTo() in Location does not designate a location with a larger rating as larger");
    }

    // test equals()
    @Test
    public void testLocationEquals() {
        // init object, add same POI
        Location l = new Location(Location.Scope.DOMESTIC, "Vancouver");
        l.addPOI(new POI("Beach"));
        assertEquals(bl.getLocation("Vancouver"), l, "equals() in Location does not work correctly");
    }

    @Test
    public void testLocationNotEquals() {
        // init test location
        Location l = new Location(Location.Scope.INTERNATIONAL, "Dubai");
        assertNotEquals(visited.getLocation("Dubai"), l, "equals() in Location does not work correctly");
    }

    // test POI class
    // test equals()
    @Test
    public void testPOIEquals() {
        POI test = new POI("Beach");
        assertEquals(bl.getLocation("Vancouver").getPOI("Beach"), test, "equals() in POI does not work correctly");
    }

    @Test
    public void testPOINotEquals() {
        POI test = new POI("Burj Khalifa");
        assertNotEquals(bl.getLocation("Vancouver").getPOI("Beach"), test, "equals() in POI does not work correctly");
    }

    // test compareTo()
    @Test
    public void testSamePOI() {
        POI test1 = new POI("Beach");
        test1.setRating(4.5);
        POI test2 = new POI("Beach");
        test2.setRating(4.5);
        assertEquals(0, test1.compareTo(test2), "compareTo() in POI designates equal objects as unequal");
    }

    @Test
    public void testLargePOI() {
        POI test1 = new POI("Louvre");
        test1.setRating(4.5);
        POI test2 = new POI("Beach");
        test2.setRating(1.5);
        assertEquals(1, test1.compareTo(test2), "compareTo() in POI designates larger objects as not larger");
    }

    @Test
    public void testSmallPOI() {
        POI test1 = new POI("Skydiving");
        test1.setRating(1.5);
        POI test2 = new POI("Beach");
        test2.setRating(4.5);
        assertEquals(-1, test1.compareTo(test2), "compareTo() in POI designates lesser objects as not lesser");
    }

    @Test
    public void testSortPOI() {
        // create test, sort it
        ArrayList<POI> test = new ArrayList<>();
        test.add(new POI("Beach"));
        test.get(0).setRating(1.5);
        test.add(new POI("Skydiving"));
        test.get(1).setRating(3.5);
        test.add(new POI("Museum"));
        test.get(2).setRating(0.5);
        Collections.sort(test);
        // create expected, already sorted
        ArrayList<POI> expected = new ArrayList<>();
        expected.add(new POI("Museum"));
        expected.get(0).setRating(0.5);
        expected.add(new POI("Beach"));
        expected.get(1).setRating(1.5);
        expected.add(new POI("Skydiving"));
        expected.get(2).setRating(3.5);
        assertEquals(expected, test, "Sorting a POI ArrayList does not work correctly because of compareTo.");
    }

}