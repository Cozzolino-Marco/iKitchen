package com.iKitchen.model.utility;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.text.TextAlignment;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

public class Popup {

    public static void mostraPopup(String titolo, String messaggio, String tipo) {

        // Crea un nuovo Stage per il popup
        Stage popupStage = new Stage();

        // Blocca l'interazione con altre finestre
        popupStage.initModality(Modality.APPLICATION_MODAL);

        // Setting del titolo e creazione del contenuto del popup
        popupStage.setTitle(titolo);
        VBox popupContent = new VBox();
        popupContent.setSpacing(15);
        popupContent.setPadding(new Insets(20));
        popupContent.setAlignment(Pos.CENTER);
        popupContent.setStyle("-fx-background-color: #ffffff; -fx-border-radius: 10; -fx-background-radius: 10; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 10, 0, 0, 5);");

        // Aggiunta dell'icona in base al tipo di popup
        ImageView icon = new ImageView();
        if (tipo.equalsIgnoreCase("success")) {
            icon.setImage(new Image(Objects.requireNonNull(Popup.class.getResourceAsStream("/success_icon.png"))));
            icon.setFitHeight(50);
            icon.setFitWidth(50);
        } else if (tipo.equalsIgnoreCase("error")) {
            icon.setImage(new Image(Objects.requireNonNull(Popup.class.getResourceAsStream("/error_icon.png"))));
            icon.setFitHeight(50);
            icon.setFitWidth(50);
        } else if (tipo.equalsIgnoreCase("warning")) {
            icon.setImage(new Image(Objects.requireNonNull(Popup.class.getResourceAsStream("/warning_icon.png"))));
            icon.setFitHeight(50);
            icon.setFitWidth(50);
        } else if (tipo.equalsIgnoreCase("construction")) {
            icon.setImage(new Image(Objects.requireNonNull(Popup.class.getResourceAsStream("/construction_icon.jpg"))));
            icon.setFitHeight(160);
            icon.setFitWidth(160);
        }

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

    public static boolean mostraPopupConferma(String titolo, String messaggio) {
        AtomicBoolean confermato = new AtomicBoolean(false);

        // Crea un nuovo Stage per il popup
        Stage popupStage = new Stage();

        // Blocca l'interazione con altre finestre
        popupStage.initModality(Modality.APPLICATION_MODAL);

        // Imposta il titolo del popup
        popupStage.setTitle(titolo);

        // Crea il layout del popup
        VBox popupContent = new VBox();
        popupContent.setSpacing(15);
        popupContent.setPadding(new Insets(20));
        popupContent.setAlignment(Pos.CENTER);
        popupContent.setStyle("-fx-background-color: #ffffff; -fx-border-radius: 10; -fx-background-radius: 10; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 10, 0, 0, 5);");

        // Aggiunge un'icona in base al tipo di popup
        ImageView icon = new ImageView();
        icon.setImage(new Image(Objects.requireNonNull(Popup.class.getResourceAsStream("/warning_icon.png"))));
        icon.setFitHeight(50);
        icon.setFitWidth(50);

        // Genera il messaggio
        Label messageLabel = new Label(messaggio);
        messageLabel.setWrapText(true);
        messageLabel.setTextAlignment(TextAlignment.CENTER);
        messageLabel.setStyle("-fx-text-fill: #001818; -fx-font-size: 14px; -fx-padding: 10px; -fx-alignment: center;");

        // Gestione bottone di cancellazione
        Button cancelButton = new Button("Annulla");
        cancelButton.setStyle("-fx-background-color: #A50000; -fx-text-fill: #fff; -fx-font-size: 12px; -fx-pref-width: 80px; -fx-padding: 5px 10px; -fx-background-radius: 5px;");
        cancelButton.setOnMouseEntered(event -> cancelButton.setStyle("-fx-background-color: #c20000; -fx-pref-width: 80px; -fx-text-fill: #fff; -fx-font-size: 12px; -fx-padding: 5px 10px; -fx-background-radius: 5px;"));
        cancelButton.setOnMouseExited(event -> cancelButton.setStyle("-fx-background-color: #A50000; -fx-pref-width: 80px; -fx-text-fill: #fff; -fx-font-size: 12px; -fx-padding: 5px 10px; -fx-background-radius: 5px;"));
        cancelButton.setOnAction(event -> popupStage.close());

        // Gestion bottone di conferma
        Button confirmButton = new Button("Conferma");
        confirmButton.setStyle("-fx-background-color: #00A5A5; -fx-text-fill: #fff; -fx-font-size: 12px; -fx-pref-width: 80px; -fx-padding: 5px 10px; -fx-background-radius: 5px;");
        confirmButton.setOnMouseEntered(event -> confirmButton.setStyle("-fx-background-color: #00c2c2; -fx-pref-width: 80px; -fx-text-fill: #fff; -fx-font-size: 12px; -fx-padding: 5px 10px; -fx-background-radius: 5px;"));
        confirmButton.setOnMouseExited(event -> confirmButton.setStyle("-fx-background-color: #00A5A5; -fx-pref-width: 80px; -fx-text-fill: #fff; -fx-font-size: 12px; -fx-padding: 5px 10px; -fx-background-radius: 5px;"));
        confirmButton.setOnAction(event -> {
            confermato.set(true);
            popupStage.close();
        });

        // Box orizzontale per ospitare i due bottoni
        HBox buttons = new HBox(10, cancelButton, confirmButton);
        buttons.setAlignment(Pos.CENTER);

        // Aggiunge gli elementi al layout
        popupContent.getChildren().addAll(icon, messageLabel, buttons);

        // Imposta il layout come scena del popup
        Scene popupScene = new Scene(popupContent, 300, 300);
        popupStage.setScene(popupScene);

        // Mostra il popup e attende la risposta
        popupStage.showAndWait();

        return confermato.get();
    }
}