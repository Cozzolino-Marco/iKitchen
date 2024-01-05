module com.example.ikitchen {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.ikitchen to javafx.fxml;
    exports com.example.ikitchen;
}