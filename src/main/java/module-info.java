module com.example.ikitchen {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.jsoup;

    exports com.iKitchen;
    opens com.iKitchen to javafx.fxml;
    opens com.iKitchen.view to javafx.fxml;
    exports com.iKitchen.view;
    exports com.iKitchen.model.domain;
    opens com.iKitchen.model.domain to javafx.fxml;
    exports com.iKitchen.model.utility;
    opens com.iKitchen.model.utility to javafx.fxml;
}