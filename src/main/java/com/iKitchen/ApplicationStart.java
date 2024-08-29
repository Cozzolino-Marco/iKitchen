package com.iKitchen;

import com.iKitchen.model.domain.ApplicazioneStage;
import com.iKitchen.model.utility.ScreenSize;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class ApplicationStart extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        ApplicazioneStage.setStage(stage);
        FXMLLoader fxmlLoader = new FXMLLoader(ApplicationStart.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), ScreenSize.WIDTH_GUI1, ScreenSize.HEIGHT_GUI1);
        stage.setTitle("iKitchen");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}