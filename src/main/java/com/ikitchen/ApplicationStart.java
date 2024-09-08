package com.ikitchen;

import com.ikitchen.model.domain.ApplicazioneStage;
import com.ikitchen.model.utility.ScreenSize;
import javafx.application.Application;
import javafx.application.HostServices;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class ApplicationStart extends Application {

    private static HostServices hostServices;

    @Override
    public void start(Stage stage) throws IOException {

        // Assegna correttamente il servizio HostServices alla variabile statica
        ApplicationStart.hostServices = getHostServices();

        // Caricamento scena di default iniziale
        ApplicazioneStage.setStage(stage);
        FXMLLoader fxmlLoader = new FXMLLoader(ApplicationStart.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), ScreenSize.getSceneWidth(), ScreenSize.getSceneHeight());
        stage.setTitle("iKitchen");
        stage.setScene(scene);
        stage.show();
    }

    // Ritorno l'istanza utile al servizio link web della ricetta
    public static HostServices getHostServicesInstance() {
        return hostServices;
    }

    // MAIN
    public static void main(String[] args) {
        launch();
    }
}