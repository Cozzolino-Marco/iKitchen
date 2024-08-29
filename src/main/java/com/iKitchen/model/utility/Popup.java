package com.iKitchen.model.utility;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Popup {

    public static void mostraPopup(String titolo, String messaggio) {
        // Crea un nuovo Stage per il popup
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL); // Blocca l'interazione con altre finestre
        popupStage.setTitle(titolo);

        // Crea il contenuto del popup
        VBox popupContent = new VBox();
        popupContent.setSpacing(10);
        popupContent.setPadding(new Insets(10));

        // Generazione messaggio
        Label messageLabel = new Label(messaggio);
        Button closeButton = new Button("Chiudi");
        closeButton.setOnAction(event -> popupStage.close());

        // Aggiungi gli elementi al layout
        popupContent.getChildren().addAll(messageLabel, closeButton);

        // Imposta il layout come scena del popup
        Scene popupScene = new Scene(popupContent, 300, 200);
        popupStage.setScene(popupScene);

        // Mostra il popup
        popupStage.showAndWait();
    }
}
