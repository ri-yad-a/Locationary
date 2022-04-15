# Locationary
## CPSC 233 W22 - Final Project 

## General Information
Locationary is an organized journal for traveller's to
document locations on their bucket list and keep track of 
locations that they have already travelled to. User's will
have the ability to 'complete' bucket list locations and
also add ratings to previously travelled destinations.

Authors: Riyad Abdullayev and Gaurav Ashar

Date: April 15, 2022

Tutorial T10



## Technologies
Java 16, Git, JavaFX, Scenebuilder 2.0



## How to run the program through command line
To run this program with the .class files that contain 
executable Java bytecode, navigate to the correct directory
that contain these files, navigate to where the 'locationary'
folder is located and run the following command:

`java --module-path "javafx-sdk lib folder directory"
--add-modules javafx.controls,javafx.fxml locationary.main.Main`

Where --module-path is the location of the
lib folder where you have downloaded your javafx sdk




To run this program using the .jar file for this project, 
navigate to the location of the .jar file and run the
following command:

`java --module-path "javafx-sdk lib folder directory"
--add-modules javafx.controls,javafx.fxml -jar locationary.jar`

To load in journal data from a file when running the program,
you can use the name of the desired file as a command line argument.
For example, if the file you wish to load was 'data.csv',
you can run the same commands as above with an additional argument like the following:

`java --module-path "javafx-sdk lib folder directory"
--add-modules javafx.controls,javafx.fxml locationary.main.Main data.csv`

or

`java --module-path "javafx-sdk lib folder directory"
--add-modules javafx.controls,javafx.fxml -jar locationary.jar data.csv`


## How to use the GUI
#### Home Screen
The home screen consists of two list views one for the 
users 5 most recent bucket list additions and the other
for the users top 5 visited locations based on their ratings.
You can select any location from these list views and click
on the 'View Attributes' button to see the details of the
location.

The two buttons 'View/Edit Bucket List' and 'View/Edit Visited' 
will take the user to different screens that are used
to add locations to their bucket list or visited locations

#### Bucket List Screen
The bucket list screen allows the users to add locations 
to their bucket list and add points of interest to each
location. Each location will have a name, a scope and
0 or more POI's. Everytime a location or POI is added,
the location or POI list view will be updated.


#### Visited Screen
The visited screen is responsible for adding location to 
the users visited locations. It is almost identical to the
bucket list screen apart from the added 'rating' data. The
user can choose to add ratings to their previously inputted
locations/POI's



## GUI Menu Bar Options
### File
* Load: gives the user the ability to load in journal data from a csv file
* Save As: gives the user the ability to save their current journal to a csv file
* Save: gives the user the ability to save to a default pre-exisitng file
* Quit: quits the application

### Help
* About: will display a window that provides information about the application
* Instructions: will provide instructions on how to use each screen correctly