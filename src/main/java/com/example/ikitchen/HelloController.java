package com.example.ikitchen;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HelloController {
    @FXML
    private Label welcomeText;

    //TODO ao questo provaaaaaa

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Prova!");
    }
}