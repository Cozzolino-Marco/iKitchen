package com.iKitchen.view;

import com.iKitchen.model.domain.ApplicazioneStage;
import com.iKitchen.model.utility.ScreenSize;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class RegistratiGrafico {

    // Caricamento del file FXML della registrazione
    public void registratiView() throws IOException {
        FXMLLoader fxmlLoader;
        Stage stage = ApplicazioneStage.getStage();
        Scene scene;

        String fxmlFile = "/com/iKitchen/registratiView.fxml";
        fxmlLoader = new FXMLLoader();
        Parent rootNode = fxmlLoader.load(getClass().getResourceAsStream(fxmlFile));

        scene = new Scene(rootNode, ScreenSize.WIDTH_GUI1, ScreenSize.HEIGHT_GUI1);

        stage.setTitle("iKitchen");
        stage.setScene(scene);
        stage.show();
    }

    // Chiamata al controller del login
    public void loginView() throws IOException {
        LoginGrafico loginGrafico = new LoginGrafico();
        loginGrafico.loginView();
    }

    public void confermaRegistrazione() {
    }
}
