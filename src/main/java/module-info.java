module com.example.ikitchen {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.jsoup;
    requires java.sql.rowset;
    requires java.desktop;

    exports com.ikitchen;
    opens com.ikitchen to javafx.fxml;
    opens com.ikitchen.view to javafx.fxml;
    exports com.ikitchen.view;
    opens com.ikitchen.viewIpovision to javafx.fxml;
    exports com.ikitchen.model.domain;
    opens com.ikitchen.model.domain to javafx.fxml;
    exports com.ikitchen.model.utility;
    opens com.ikitchen.model.utility to javafx.fxml;
}