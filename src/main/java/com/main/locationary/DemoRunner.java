package com.main.locationary;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * CPSC 233 W22 Demo 2
 * Tutorial: T10
 * Date: March 23, 2022
 * Gaurav Ashar, Riyad Abdullayev
 * This class outputs information to the user using System.out and
 * takes input from the user via System.in. The program represents the
 * second version of a location tracker.
 */

public class DemoRunner {

    public static void main(String[] args) {
        BucketList bucketList = new BucketList();
        Visited visited = new Visited();
        File f = null;
        try {
            if (args.length == 1) {
                f = new File(args[0]);
                if (f.exists()) {
                    FileHandler.setFile(f);
                    Journal[] j1 = FileHandler.getFromFile();
                    bucketList = (BucketList) j1[0];
                    visited = (Visited) j1[1];
                }
            } else if (new File("data.csv").exists() && new File("data.csv").canRead()) {
                f = new File("data.csv");
            } else {
                if (new File("data.csv").createNewFile()) {
                    f = new File("data.csv");
                }
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
        FileHandler.setFile(f);

        // Create a scanner object to take in user input through System.in
        Scanner sc = new Scanner(System.in);
        // Create a boolean variable that will help in looping through menu screens
        boolean keepRunning = true;
        // Initialize an integer variable that will indicate which step/stage the user is in the menu options
        int step = 0;
        int firstChoice = -1;
        Location secondSelection = null;
        boolean ranFromStart = false;
        // loop while the boolean variable "keepRunning" is true
        // this loop will continue to loop through the menu options allowing the user to go back and forth between screens and
        // exit the program as they wish
        while(keepRunning) {
            // if the step variable is set to -3, the program will exit by changing the keepRunning variable false which will exit the loop
            if(step == -3) {
                System.out.println("Program is now exiting...");
                // set running variable to false, ends program
                keepRunning = false;
            // step variable = -2 is part of adding locations to the user's locations
            } else if (step == -2) {
                // based on a user's first choice, chose type of location
                String defScope;
                if (firstChoice == 1) {
                    defScope = "bucket list";
                } else {
                    defScope = "journal";
                }
                System.out.println("Operation complete. \nWould you like to \n1) add another location to your " + defScope + ",or\n2) " +
                        "add a point of interest to your " + defScope + ", or\n3) go back?");
                String resp = sc.nextLine();
                // next if/else is for choosing the next step in the program
                if (resp.equals("1")) {
                    // continue with adding
                    step = 1;
                } else if (resp.equals("2")) {
                    // add POI
                    step = 3;
                } else if (resp.equals("3")) {
                    // Main menu
                    step = 0;
                } else if (resp.equalsIgnoreCase("exit")) {
                    // exit
                    step = -3;
                } else {
                    // invalid input
                    step = -1;
                }
            // step = -1 for invalid input
            } else if (step == -1) {
                System.out.println("Invalid input recognized, going back to main menu.\n\n\n");
                step = 0;
            // step = 0 is the main menu, upon start
            } else if (step == 0) {
                firstChoice = runWelcomeMessage(sc);
                if (firstChoice == -1) {
                    // invalid input
                    step = -1;
                } else if (firstChoice == -2) {
                    // exit
                    step = -3;
                } else if (firstChoice == 1 || firstChoice == 2) {
                    // continue with adding locations
                    step = 1;
                } else {
                    //add POI or output menu
                    ranFromStart = true;
                    step = firstChoice;
                }
            // step = 1 is adding locations to the user's locations
            } else if (step == 1) {
                secondSelection = runLocationScopeMessage(sc, firstChoice);
                if (secondSelection == null) {
                    // invalid input
                    step = -1;
                } else if (secondSelection.getName() == null) {
                    // exit
                    step = -3;
                } else {
                    // check if location already exists, if it does, go to add POI
                    if((firstChoice == 1 && bucketList.hasLocation(secondSelection.getName()))
                            || (firstChoice == 2 && visited.hasLocation(secondSelection.getName()))) {
                        ranFromStart = false;
                        step = 3;
                    } else {
                        // does not, continue to add location
                        step = 2;
                    }
                }
            // step = 2 for finally adding the locations
            } else if (step == 2) {
                if (firstChoice == 1) {
                    // adds to bucket list
                    bucketList.addLocation(secondSelection);
                } else {
                    // adds to visited locations
                    visited.addLocation(secondSelection);
                }
                // go to continuing adding locations, ask for another or exit
                step = -2;
            // step = 3 for adding POIs to already initialized locations
            } else if (step == 3) {
                boolean b = false;
                if(firstChoice == 1) {
                    b = true;
                }
                // runs get POI message
                int outcome = runSetPOIMessage(ranFromStart, secondSelection, bucketList, b, visited, sc);
                if(outcome == 0) {
                    // operation was a success, ask for further instruction
                    if (!b) {
                        System.out.println("Operation successful. Would you like to: \n1) Add another POI, \n2) Go back to main menu, or \n3) Add a rating?");
                    } else {
                        System.out.println("Operation successful. Would you like to: \n1) Add another POI or \n2) Go back to main menu?");
                    }
                    String choice = sc.nextLine();
                    // if choice is 1 it works by redoing step 3
                    if(choice.equals("2")) {
                        // main menu
                        step = 0;
                    } else if (choice.equals("3")) {
                        // rating
                        step = 5;
                    } else if (!choice.equals("1")) {
                        // invalid input
                        step = -1;
                    }
                } else if(outcome == -1) {
                    // exit
                    step = -3;
                } else if(outcome == -2) {
                    // invalid input
                    step = -1;
                }
        // OUTPUT CODE BEGINS
            // step = 4 is the output menu, accessed from the main menu
            } else if(step == 4) {
                System.out.println("\nWelcome to the output menu. Would you like to: \n1) View your traveller bucket list locations, " +
                        "\n2) View your traveller journal,\n3) View all of the POIs in your bucket list, \n4) View all of the POIs" +
                        " in your journal, \n5) View all of the POIs for a specific bucket listed location, \n6) View all " +
                        "of the POIs for a specific visited location?,\n7) View all of your inputted locations, \n8) Get your top 3 locations" +
                        " in your visited journal, or \n9) " +
                        "Go back to the main menu?");
                String choice = sc.nextLine();
                // next if/else is for outputting desired info
                if(choice.equals("1")) {
                    // output all bucket list locations
                    outputBucketList(bucketList);
                } else if (choice.equals("2")) {
                    // output all visited locations
                    outputVisited(visited);
                } else if(choice.equals("3")) {
                    // output all POIs or for a specific scope
                    System.out.println("Would you like to output:\n1) Your citywide POIs, \n2) Your domestic POIs, \n3) Your" +
                            " international POIs, or \n4) All of your bucket listed POIs?");
                    choice = sc.nextLine();
                    if(choice.equals("1") || choice.equals("2") || choice.equals("3") || choice.equals("4")) {
                       outputBucketListPOIs(Integer.parseInt(choice) - 1, bucketList);
                    } else if (choice.equals("exit")) {
                        step = -3;
                    } else {
                        step = -1;
                    }
                } else if (choice.equals("4")) {
                    // output all POIs or for a specific scope
                    System.out.println("Would you like to output:\n1) Your citywide POIs, \n2) Your domestic POIs, \n3) Your" +
                            " international POIs, or \n4) All of your visited POIs?");
                    choice = sc.nextLine();
                    if(choice.equals("1") || choice.equals("2") || choice.equals("3") || choice.equals("4")) {
                        outputVisitedPOIs(Integer.parseInt(choice) - 1, visited);
                    } else if (choice.equals("exit")) {
                        step = -3;
                    } else {
                        step = -1;
                    }
                } else if (choice.equals("5")) {
                    // finds POIs based on location name
                    System.out.println("Please enter the name of the bucket listed location you would to see the POIs of. " +
                            "(\"exit\" will not work here)");
                    choice = sc.nextLine();
                    outputBucketListPOI(choice, bucketList);
                } else if (choice.equals("6")) {
                    // finds POIs based on location name
                    System.out.println("Please enter the name of the visited location you would to see the POIs of. " +
                            "(\"exit\" will not work here)");
                    choice = sc.nextLine();
                    outputVisitedPOI(choice, visited);
                } else if (choice.equals("7")) {
                    // output all locations
                   outputGeneral(bucketList, visited);
                } else if (choice.equals("8")) {
                    // gets top three
                    Location[] top = visited.getTopThree();
                    if (top[0] != null) {
                        String toPrint = "";
                        for (Location l: top) {
                            toPrint += "\n" + l;
                        }
                        System.out.println("Your most highly rated locations are: " + toPrint);
                    } else {
                        System.out.println("No locations added to your visited journal yet.");
                    }
                } else if (choice.equals("9")) {
                    step = 0;
                } else if (choice.equalsIgnoreCase("exit")) {
                    // exit
                    step = -3;
                } else {
                    // invalid input
                    step = -1;
                }
            } else if (step == 5) {
                int outcome1 = addRatingMessage(visited, sc);
                for (Location location: visited.getLocations()) {
                    location.setRating(0.0);
                }
                sc = new Scanner(System.in);
                step = outcome1;
            } else if (step == 6) {
                System.out.println("Saving to file...");
                FileHandler.writeToFile(f.getPath(), bucketList, visited);
                System.out.println("Data saved! Going back to main menu...");
                step = 0;
            } else if (step == 7) {
                System.out.println("Reading from file...");
                Journal[] j = FileHandler.getFromFile();
                bucketList = (BucketList) j[0];
                visited = (Visited) j[1];
                System.out.println("Data retrieved! Going back to main menu...");
                step = 0;
            }
        }
        // close the Scanner object
        sc.close();
    }

    /**
     *
     * This method will be responsible for displaying the welcome message to the user and handling the first choice the user must make.
     * @param sc is a scanner object that will be used for user input through System.in
     * @return an integer that represents the users first choice
     */
    public static int runWelcomeMessage(Scanner sc) {
        // Welcome the user to the app
        System.out.println("\nWelcome to Locationary!");
        // Ask the user to choose among the options of adding to their bucket list or traveller's journal (visited locations), adding a point of interest
        // to a previously inputted location or viewing the output menu
        System.out.println("Would your like to:\n1) Begin adding destinations to your traveller bucket list, or" +
                "\n2) Add previously visited destinations to your traveller journal, or\n3) Add a point of interest to a " +
                "previously inputted location, \n4) View the output menu, \n5) Add a rating, \n6) Save to file, or \n7) Load from file?\n(Please enter your answer " +
                "as a numeric value, either 1, 2, 3, or 4, or type \"exit\" at any time in the program to exit).");
        // take the users keyboard input by using Scanner.nextLine()
        String choice = sc.nextLine();
        // handle their choice and return the integer value corresponding to it
        if (choice.equals("1") || choice.equals("2") || choice.equals("3") || choice.equals("4") || choice.equals("5") || choice.equals("6") || choice.equals("7")) {
            return Integer.parseInt(choice);
            // if they type "exit" the method will return -2 and the program termination will be handled by the main method
        } else if (choice.equalsIgnoreCase("exit")) {
            return -2;
        }
        // default return value is -1
        return -1;
    }

    /**
     * This method will be responsible for handling the scope of the users desired location input
     * @param sc is a scanner object that will be used for the user's keyboard input through System.in
     * @param desiredOption represents the users choice to either add to their bucket list or their traveller's journal
     * @return a String array with the first value being their choice of scope and the second value in the array being the name of the location
     * they wish to add
     */
    public static Location runLocationScopeMessage(Scanner sc, int desiredOption) {
        // Create a new Location object with no parameters
        Location retLoc = new Location();
        // This String variable will track the users initial scope choice
        String rawChoice = "";
        // this String variable will hold the desired location name
        String locationName = "";

        // if the desired option argument is 1, they wish to add to their bucket list
        if (desiredOption == 1) {
            // print message confirming they are adding to bucket list and prompt for scope choice between
            // citywide, domestic or international
            System.out.println("You have chosen to add destinations to your traveller bucket list.\nPlease specify " +
                    "the destination type (citywide, domestic, or international) by either typing the whole type or " +
                    "the corresponding number (1, 2, or 3) to the option.");
            // set the rawChoice variable to their desired scope using Scanner.nextLine()
            rawChoice = sc.nextLine();
            // if the desired option is not 1 then they have chosen to add to their travellers journal
        } else {
            // print message confirming they are adding to journal and prompt for scope choice between
            // citywide, domestic or international
            System.out.println("You have chosen to add destinations to your traveller journal.\nPlease specify " +
                    "the destination type (citywide, domestic, or international) by either typing the whole type or " +
                    "the corresponding number (1, 2, or 3) to the option.");
            // set the rawChoice variable to their desired scope using Scanner.nextLine()
            rawChoice = sc.nextLine();
        }

        // if else statements that handle each citywide, domestic and international case by setting the location objects
        // location to the appropriate Scope value and printing a confirmation message for user clarification.
        // These statements also handle the prompt by the user to exit the program
        if (rawChoice.equalsIgnoreCase("citywide") || rawChoice.contains("1")) {
            retLoc.setScope(Location.Scope.CITYWIDE);
            System.out.println("You have chosen to add a destination or point of interest to a previously " +
                    "inputted destination within your city.");
        } else if (rawChoice.equalsIgnoreCase("domestic") || rawChoice.contains("2")) {
            retLoc.setScope(Location.Scope.DOMESTIC);
            System.out.println("You have chosen to add a destination or point of interest to a previously " +
                    "inputted destination within your country.");
        } else if (rawChoice.equalsIgnoreCase("international") || rawChoice.contains("3")){
            retLoc.setScope(Location.Scope.INTERNATIONAL);
            System.out.println("You have chosen to add an international destination or point of interest to a " +
                    "previously inputted destination.");
        } else if(rawChoice.equalsIgnoreCase("exit")) {
            return new Location();
        } else {
            return null;
        }

        // Asks the user for input on the location they would like to add or if they want to add to a POI (point of interest)
        System.out.println("Please input the name of the location you would like to add or add a POI to.");
        // set the locationName variable to the users keyboard input using Scanner.nextLine()
        locationName = sc.nextLine();
        // handle the exit prompt
        if(locationName.equalsIgnoreCase("exit")) {
            return new Location();
        }

        // set the name of the location object to the locationName variable
        retLoc.setName(locationName);
        // return the Location object that contains the appropriate scope and location name
        return retLoc;
    }

    /**
     * This method is responsible for handling the message that will be printed when adding POI's (points of interest)
     * @param ranStart boolean will indicate whether the method was run from the start or into the later stages of the program
     * @param location the desired location object that the POI will be added to
     * @param bucketList bucketList object that location may be in
     * @param visited visited object that the location may be in
     * @param sc scanner object that will be used to get keyboard input through System.in
     * @return an int that represents the outcome of the operation
     */

    public static int runSetPOIMessage(boolean ranStart, Location location, BucketList bucketList, boolean bl, Visited visited, Scanner sc) {
        String type = "";
        String poi = "";
        if(ranStart) {
            location = new Location();
            // ask user if they would like to add a POI to their bucket list locations or visited locations
            System.out.println("Would you like to add a point of interest to your \n1) bucket listed locations or your " +
                    "\n2) previously visited locations?");
            // set the choice variable to the users input through Scanner.nextLine()
            String choice = sc.nextLine();
            String wordScope = "";
            // if users choice is 1, set the type to bucket list
            if(choice.equals("1")) {
                type = "bucket list";
                bl = true;
                // if the users choice is 2, set the type to journal
            } else if (choice.equals("2")) {
                type = "journal";
                bl = false;
                // else there is an invalid input. Print message and return to main menu
            } else if (choice.equalsIgnoreCase("exit")) {
                return -1;
            } else {
                return -2;
            }
            // Ask user if they would like to add their POI to a citywide, domestic or international location in either their
            // bucket list or journal (depending on the type variable)
            System.out.println("Would you like to add a POI to your \n1) citywide, \n2) domestic, or \n3) international " +
                    "locations in your " + type + "?");
            choice = sc.nextLine();
            // next set of if/else statements are to determine the scope. The variable wordScope is set to the appropriate
            // string and the scope of the location object is changed according to the scope choice
            if(choice.equals("1")) {
                wordScope = "citywide";
                location.setScope(Location.Scope.CITYWIDE);
            } else if(choice.equals("2")) {
                wordScope = "domestic";
                location.setScope(Location.Scope.DOMESTIC);
            } else if(choice.equals("3")) {
                wordScope = "international";
                location.setScope(Location.Scope.INTERNATIONAL);
            } else if(choice.equalsIgnoreCase("exit")) {
                return -1;
            } else {
                return -2;
            }
            System.out.println("Please type the name of the " + wordScope + " location you would like to add to. (\"exit\" will not work here)");
            // get the location name they would like to add to with the Scanner
            String locationName = sc.nextLine();
            // set the name of the location object to the name retrieved from the user
            location.setName(locationName);
            // checks if location exists in either types with their choice
            if (bl && !bucketList.hasLocation(locationName)) {
                return 1;
            } else if (!bl && !visited.hasLocation(locationName)) {
                return 1;
            }
        }
        // gets POI name
        System.out.println("Please type the name of the POI you would like to add to " + location.getName() + ". (\"exit\" will not work here)");
        poi = sc.nextLine();

        // add the poi to the Location object
        if (bl) {
            bucketList.getLocation(location.getName()).addPOI(new POI(poi));
        } else {
            visited.getLocation(location.getName()).addPOI(new POI(poi));
        }


        // returns 0 for successful operation, 1 for redo, -1 for exit, -2 for invalid input
        return 0;
    }

    public static int addRatingMessage(Visited visited, Scanner sc) {

        System.out.println("Which location contains the POI you would like to add a rating to?");
        String name = sc.nextLine();
        if (!visited.hasLocation(name)) {
            System.out.println("This location does not exist in your Journal. Going back to main menu.");
            return 0;
        } else if (name.equals("exit")) {
            return -3;
        }
        Location location = visited.getLocation(name);
        System.out.println("Which POI would you like to add a rating to?");
        name = sc.nextLine();
        if (location.getPOI(name) == null) {
            System.out.println("This POI does not exist, going back to main menu.");
            return 0;
        } else if (name.equals("exit")) {
            return -3;
        }
        System.out.printf("What is your rating for %s? (Enter your answer as a decimal number from 0 to 5, \"exit\" will not work here)\n", name);
        while (!sc.hasNextDouble()) {
            sc.next();
            System.out.println("Please enter in the form of a decimal number.");
        }
        double rating = sc.nextDouble();
        if (rating < 0 || rating > 5) {
            return -1;
        }
        location.getPOI(name).setRating(rating);
        System.out.println("Operation successful, going back to main menu now.");
        return 0;
    }


  /**
     * This method is responsible for a general output of all the useful information collected from the user
     */
    public static void outputGeneral(BucketList bucketList, Visited visited) {
        // Print out both the bucketList and visited objects
        // printing with System.out.println will use the objects toString() methods and print them accordingly
        System.out.println(bucketList);
        System.out.println(visited);
        /*int sum = bucketListLocations[citywide].size() + bucketListLocations[domestic].size() + bucketListLocations[international].size() +
                visitedLocations[citywide].size() + visitedLocations[domestic].size() + visitedLocations[international].size();
        System.out.println("Total number of inputted locations: " + sum + ".");*/
    }

    /**
     * This method is responsible for printing the users bucket list
     */
    public static void outputBucketList(BucketList bucketList) {
        // Print out both the bucketList object
        // printing with System.out.println will use the objects toString() methods and print accordingly
        System.out.println(bucketList);
       /* int sum = bucketListLocations[citywide].size() + bucketListLocations[domestic].size() + bucketListLocations[international].size();
        System.out.println("Total number of bucket listed locations: " + sum + ".");*/
    }

    /**
     * This method is responsible for printing the users travellers journal (visited locations)
     */
    public static void outputVisited(Visited visited) {
        // Print out both the visited object
        // printing with System.out.println will use the objects toString() methods and print accordingly
        System.out.println(visited);
       /* int sum = visitedLocations[citywide].size() + visitedLocations[domestic].size() + visitedLocations[international].size();
        System.out.println("Total number of visited locations: " + sum + ".");*/
    }

    /**
     * This method is responsible for printing the users points of interest in the various layers of their bucket list
     * @param choice will determine whether they want to print citywide, domestic or international POI's
     * @param bucketList will be the bucketList object from which we will get POI's to output
     */
    public static void outputBucketListPOIs(int choice, BucketList bucketList) {

        // create and empty print String
        String printStr = "";
        // get the arraylist of location object from the bucketList object
        ArrayList<Location> bucketListLocations = bucketList.getLocations();

        // the following collection of if statements will check what choice the user made when outputting POIS
        // to either output only citywide, domestic, or international pois, or output them all when choice is 3
        if (choice == 0 || choice == 3) {
            // loop through the ArrayList of locations
            for (Location location: bucketListLocations) {
                // check what the scope of the location is. If it matches to the scope choice from the user
                // then add it to the print str
                if (location.getScope() == Location.Scope.CITYWIDE) {
                    printStr += location.getPOIs();
                }
            }
            // if the print strin gis not empty
            if (!printStr.equals("")) {
                // print out the scopes POIS
                System.out.println("Citywide POIS:" + printStr);
            } else {
                // print out that there are no POIS for this scope
                System.out.println("[Warning] No POIs.");
            }
        }
        // similar methodology used as in the above conditional
        if (choice == 1 || choice == 3) {
            for (Location location: bucketListLocations) {
                if (location.getScope() == Location.Scope.DOMESTIC) {
                    printStr += location.getPOIs();
                }
            }
            if (!printStr.equals("")) {
                System.out.println("Domestic POIS:" + printStr);
            } else {
                System.out.println("[Warning] No POIs.");
            }
        }

        // similar methodology used as in the above conditionals
        if (choice == 1 || choice == 3) {
            for (Location location: bucketListLocations) {
                if (location.getScope() == Location.Scope.INTERNATIONAL) {
                    printStr += location.getPOIs();
                }
            }
            if (!printStr.equals("")) {
                System.out.println("International POIS:" + printStr);
            } else {
                System.out.println("[Warning] No POIs.");
            }
        }

    }

    /**
     * This method is responsible for the output of the POI's for a specific location on the users bucket list
     * @param choice: a String representing the desired location to return POIs from
     * @param bucketList is the BucketList object from which we will get the POIs to output
     */
    public static void outputBucketListPOI(String choice, BucketList bucketList) {

        // Create an empty print string
        String printStr = "";
        // access the ArrayList of locations inside the bucketList object
        ArrayList<Location> bucketListLocations = bucketList.getLocations();

        // loop through the location ArrayList
        for (Location location: bucketListLocations) {
            // if the name of the location equals the users choice of location
            if (location.getName().equals(choice)) {
                // add the pois of this location to the print string
                printStr += location.getPOIs();
            }
        }

        // print out the accumulated print string appropriately
        System.out.println("All POIs for " + choice + ": " + printStr);

    }

    /**
     * This method is responsible for printing the users points of interest in the various layers of their travellers journal
     * @param choice will determine whether they want to print citywide, domestic or international POI's
     * @param visited will be the Visited object from which we will get POI's to output
     */
    public static void outputVisitedPOIs(int choice, Visited visited) {

        // create and empty print String
        String printStr = "";
        // get the arraylist of location object from the bucketList object
        ArrayList<Location> visitedLocations = visited.getLocations();

        // the following collection of if statements will check what choice the user made when outputting POIS
        // to either output only citywide, domestic, or international pois, or output them all when choice is 3
        if (choice == 0 || choice == 3) {
            // loop through the ArrayList of locations
            for (Location location: visitedLocations) {
                // check what the scope of the location is. If it matches to the scope choice from the user
                // then add it to the print str
                if (location.getScope() == Location.Scope.CITYWIDE) {
                    printStr += location.getPOIs();
                }
            }
            // if the print strin gis not empty
            if (!printStr.equals("")) {
                // print out the scopes POIS
                System.out.println("Citywide POIS:" + printStr);
            } else {
                // print out that there are no POIS for this scope
                System.out.println("[Warning] No POIs.");
            }
        }
        // similar methodology used as in the above conditional
        if (choice == 1 || choice == 3) {
            for (Location location: visitedLocations) {
                if (location.getScope() == Location.Scope.DOMESTIC) {
                    printStr += location.getPOIs();
                }
            }
            if (!printStr.equals("")) {
                System.out.println("Domestic POIS:" + printStr);
            } else {
                System.out.println("[Warning] No POIs.");
            }
        }

        // similar methodology used as in the above conditionals
        if (choice == 1 || choice == 3) {
            for (Location location: visitedLocations) {
                if (location.getScope() == Location.Scope.INTERNATIONAL) {
                    printStr += location.getPOIs();
                }
            }
            if (!printStr.equals("")) {
                System.out.println("International POIS:" + printStr);
            } else {
                System.out.println("[Warning] No POIs.");
            }
        }

    }

    /**
     * This method is responsible for the output of the POI's for a specific location on the users visited locaitons
     * @param choice: a String representing the desired location to return POIs from
     * @param visited is the Visited object from which we will get the POIs to output
     */
    public static void outputVisitedPOI(String choice, Visited visited) {
        // Create an empty print string
        String printStr = "";
        // access the ArrayList of locations inside the bucketList object
        ArrayList<Location> visitedLocations = visited.getLocations();

        // loop through the location ArrayList
        for (Location location: visitedLocations) {
            // if the name of the location equals the users choice of location
            if (location.getName().equals(choice)) {
                // add the pois of this location to the print string
                printStr += location.getPOIs();
            }
        }

        // print out the accumulated print string appropriately
        System.out.println("All POIs for " + choice + ": " + printStr);
    }

}