module com.main.locationary {
    requires javafx.controls;
    requires javafx.fxml;


    exports com.main.locationary.main;
    opens com.main.locationary.main to javafx.fxml;
    exports com.main.locationary.info;
    opens com.main.locationary.info to javafx.fxml;
    exports com.main.locationary.util;
    opens com.main.locationary.util to javafx.fxml;
}