module com.example.ikitchen {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    exports com.iKitchen;
    opens com.iKitchen to javafx.fxml;
    opens com.iKitchen.view to javafx.fxml;
    exports com.iKitchen.view;
}