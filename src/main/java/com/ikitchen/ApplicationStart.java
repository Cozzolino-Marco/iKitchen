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

    // Memorizza l'istanza di HostServices che viene fornita dalla JVM quando l'applicazione JavaFX viene avviata
    private static HostServices hostServices;

    @Override
    public void start(Stage stage) throws IOException {

        // Assegna correttamente il servizio HostServices alla variabile statica
        setHostServicesInstance(getHostServices());

        // Caricamento scena di default iniziale
        ApplicazioneStage.setStage(stage);
        FXMLLoader fxmlLoader = new FXMLLoader(ApplicationStart.class.getResource("/com/StandardGUI/login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), ScreenSize.getSceneWidth(), ScreenSize.getSceneHeight());
        stage.setTitle("iKitchen");
        stage.setScene(scene);
        stage.show();
    }

    // Metodo pubblico e statico per ottenere l'istanza di HostServices utile al link del video della ricetta
    public static HostServices getHostServicesInstance() {
        return hostServices;
    }

    // Imposta l'istanza di HostServices qua che è l'unico punto in cui è garantito che l'istanza di HostServices sia pronta e disponibile
    public static void setHostServicesInstance(HostServices hostServices) {
        ApplicationStart.hostServices = hostServices;
    }

    // MAIN
    public static void main(String[] args) {
        launch();
    }
}