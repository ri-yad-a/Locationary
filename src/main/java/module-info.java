module com.main.locationary {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.main.locationary to javafx.fxml;
    exports com.main.locationary;
}