package com.iKitchen;

import com.iKitchen.model.domain.ApplicazioneStage;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import static com.iKitchen.model.domain.ScreenSize.HEIGHT_GUI1;
import static com.iKitchen.model.domain.ScreenSize.WIDTH_GUI1;

public class ApplicationStart extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        ApplicazioneStage.setStage(stage);
        FXMLLoader fxmlLoader = new FXMLLoader(ApplicationStart.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), WIDTH_GUI1, HEIGHT_GUI1);
        stage.setTitle("iKitchen");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}