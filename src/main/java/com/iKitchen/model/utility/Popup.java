package com.iKitchen.model.utility;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.text.TextAlignment;

public class Popup {

    public static void mostraPopup(String titolo, String messaggio, String tipo) {
        // Crea un nuovo Stage per il popup
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL); // Blocca l'interazione con altre finestre
        popupStage.setTitle(titolo);

        // Crea il contenuto del popup
        VBox popupContent = new VBox();
        popupContent.setSpacing(15);
        popupContent.setPadding(new Insets(20));
        popupContent.setAlignment(Pos.CENTER);
        popupContent.setStyle("-fx-background-color: #ffffff; -fx-border-radius: 10; -fx-background-radius: 10; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 10, 0, 0, 5);");

        // Aggiunge l'icona
        ImageView icon = new ImageView();
        if (tipo.equalsIgnoreCase("success")) {
            icon.setImage(new Image(Popup.class.getResourceAsStream("/success_icon.png")));
        } else if (tipo.equalsIgnoreCase("error")) {
            icon.setImage(new Image(Popup.class.getResourceAsStream("/error_icon.png")));
        } else if (tipo.equalsIgnoreCase("warning")) {
            icon.setImage(new Image(Popup.class.getResourceAsStream("/warning_icon.png")));
        }
        icon.setFitHeight(50);
        icon.setFitWidth(50);

        // Generazione messaggio
        Label messageLabel = new Label(messaggio);
        messageLabel.setWrapText(true); // Permette al testo di andare a capo
        messageLabel.setTextAlignment(TextAlignment.CENTER); // Giustifica il testo al centro
        messageLabel.setStyle("-fx-text-fill: #001818; -fx-font-size: 14px; -fx-padding: 10px; -fx-alignment: center;");

        // Aggiunge un pulsante di chiusura
        Button closeButton = new Button("Chiudi");
        closeButton.setOnAction(event -> popupStage.close());
        closeButton.setStyle("-fx-background-color: #00A5A5; -fx-text-fill: #fff; -fx-font-size: 12px; -fx-padding: 5px 10px; -fx-background-radius: 5px;");
        closeButton.setOnMouseEntered(event -> closeButton.setStyle("-fx-background-color: #00c2c2; -fx-text-fill: #fff; -fx-font-size: 12px; -fx-padding: 5px 10px; -fx-background-radius: 5px;"));
        closeButton.setOnMouseExited(event -> closeButton.setStyle("-fx-background-color: #00A5A5; -fx-text-fill: #fff; -fx-font-size: 12px; -fx-padding: 5px 10px; -fx-background-radius: 5px;"));

        // Aggiunge gli elementi al layout
        popupContent.getChildren().addAll(icon, messageLabel, closeButton);

        // Imposta il layout come scena del popup
        Scene popupScene = new Scene(popupContent, 300, 300);
        popupStage.setScene(popupScene);

        // Mostra il popup
        popupStage.showAndWait();
    }
}