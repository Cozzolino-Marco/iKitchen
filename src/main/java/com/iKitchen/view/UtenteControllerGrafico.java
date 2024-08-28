package com.iKitchen.view;

import com.iKitchen.controller.OttieniIngredientiControllerApplicativo;
import com.iKitchen.exception.DAOException;
import com.iKitchen.model.bean.BeanIngrediente;
import com.iKitchen.model.bean.BeanIngredienti;
import com.iKitchen.model.bean.CredentialsBean;
import com.iKitchen.model.domain.ApplicazioneStage;
import com.iKitchen.model.domain.Credentials;
import com.iKitchen.model.domain.ScreenSize;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.control.Label;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

public class UtenteControllerGrafico {

    @FXML
    private VBox elementContainerValidi; // Contenitore per gli ingredienti validi

    @FXML
    private VBox elementContainerNonValidi; // Contenitore per gli ingredienti non validi

    // Variabili
    private OttieniIngredientiControllerApplicativo ingredienti = null;
    public VBox mainContainer;
    public ScrollPane scrollPaneValidi;
    public ScrollPane scrollPaneNonValidi;

    // Metodo per caricare e mostrare gli ingredienti
    public void initialize() throws DAOException, SQLException {
        if (elementContainerValidi != null) {
            caricaIngredienti(Credentials.getUsername());
        }
    }

    // Dallo username, interagisce con controller e DAO per ottenere la lista di ingredienti dal DB
    private void caricaIngredienti(String username) throws DAOException, SQLException {

        // Crea un bean con le informazioni selezionate
        CredentialsBean infoPerListaIngredienti = new CredentialsBean(username);

        // Inizializza il controller applicativo per ottenere gli ingredienti
        ingredienti = new OttieniIngredientiControllerApplicativo();

        // Ottieni la lista degli ingredienti validi dal controller applicativo
        BeanIngredienti validi = ingredienti.mostraIngredientiValidi(infoPerListaIngredienti);
        for (BeanIngrediente ingrediente : validi.getListIngredienti()) {
            BorderPane element = createIngredientElement(ingrediente);
            elementContainerValidi.getChildren().add(element);
        }

        // Ottieni la lista degli ingredienti non validi dal controller applicativo
        BeanIngredienti nonValidi = ingredienti.mostraIngredientiNonValidi(infoPerListaIngredienti);
        for (BeanIngrediente ingrediente : nonValidi.getListIngredienti()) {
            BorderPane element = createIngredientElement(ingrediente);
            elementContainerNonValidi.getChildren().add(element);
        }
    }

    // Metodo che crea l'elemento grafico per ogni ingrediente
    private BorderPane createIngredientElement(BeanIngrediente ingrediente) {
        BorderPane element = new BorderPane();

        // Creazione dell'immagine dell'ingrediente
        HBox imgBox;
        if (ingrediente.getImmagine() != null) {
            try {
                InputStream inputStream = ingrediente.getImmagine().getBinaryStream();
                if (inputStream != null) {
                    Image image = new Image(inputStream, 100, 100, true, true);
                    ImageView imageView = new ImageView(image);
                    imageView.setFitHeight(70);
                    imageView.setFitWidth(65);

                    // Creazione di un Rectangle per angoli arrotondati delle immagini
                    Rectangle clip = new Rectangle(65, 70);
                    clip.setArcWidth(15);
                    clip.setArcHeight(15);
                    imageView.setClip(clip);

                    imgBox = new HBox(imageView);
                } else {
                    imgBox = new HBox(new Text("Immagine non presente"));
                }
            } catch (SQLException e) {
                imgBox = new HBox(new Text("Immagine non presente"));
            }
        } else {
            imgBox = new HBox(new Text("Immagine non presente"));
        }

        // Creazione del titolo dell'ingrediente
        Label titleLabel = new Label(ingrediente.getNome());
        titleLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

        // Dettagli dell'ingrediente (quantità e scadenza)
        Label quantitaLabel = new Label("Quantità: " + ingrediente.getQuantita());
        quantitaLabel.setStyle("-fx-font-size: 12px;");
        Label scadenzaLabel = new Label("Scadenza: " + ingrediente.getScadenza());
        scadenzaLabel.setStyle("-fx-font-size: 12px;");

        // VBox per contenere i dettagli dell'ingrediente
        VBox detailsBox = new VBox(titleLabel, quantitaLabel, scadenzaLabel);
        detailsBox.setSpacing(5);
        detailsBox.setAlignment(Pos.CENTER_LEFT);

        // Creazione della struttura principale
        HBox mainContent = new HBox(imgBox, detailsBox);
        mainContent.setSpacing(10);
        mainContent.setAlignment(Pos.CENTER_LEFT);

        // Impostazione dell'elemento grafico
        element.setCenter(mainContent);
        element.setPadding(new Insets(10));
        element.setStyle("-fx-border-color: gray; -fx-border-width: 1; -fx-background-color: white; -fx-border-radius: 10; -fx-background-radius: 10;");

        // Gestore di eventi per il click sull'elemento
        element.setOnMouseClicked(event -> {});

        return element;
    }

    public void homePageUtente() throws IOException {
        FXMLLoader fxmlLoader;
        Stage stage = ApplicazioneStage.getStage();
        Scene scene;

        String fxmlFile = "/com/iKitchen/utentiView.fxml";
        fxmlLoader = new FXMLLoader();
        Parent rootNode = fxmlLoader.load(getClass().getResourceAsStream(fxmlFile));
        scene = new Scene(rootNode, ScreenSize.WIDTH_GUI1, ScreenSize.HEIGHT_GUI1);

        stage.setTitle("iKitchen");
        stage.setScene(scene);
        stage.show();
    }

    public void categorieView() throws IOException {
        FXMLLoader fxmlLoader;
        Stage stage = ApplicazioneStage.getStage();
        Scene scene;

        String fxmlFile = "/com/iKitchen/categorieView.fxml";
        fxmlLoader = new FXMLLoader();
        Parent rootNode = fxmlLoader.load(getClass().getResourceAsStream(fxmlFile));
        scene = new Scene(rootNode, ScreenSize.WIDTH_GUI1, ScreenSize.HEIGHT_GUI1);

        stage.setTitle("iKitchen");
        stage.setScene(scene);
        stage.show();
    }

    public void preferitiView() throws IOException {
        FXMLLoader fxmlLoader;
        Stage stage = ApplicazioneStage.getStage();
        Scene scene;

        String fxmlFile = "/com/iKitchen/preferitiView.fxml";
        fxmlLoader = new FXMLLoader();
        Parent rootNode = fxmlLoader.load(getClass().getResourceAsStream(fxmlFile));
        scene = new Scene(rootNode, ScreenSize.WIDTH_GUI1, ScreenSize.HEIGHT_GUI1);

        stage.setTitle("iKitchen");
        stage.setScene(scene);
        stage.show();
    }

    public void aggiungiProdotto() throws IOException {
        FXMLLoader fxmlLoader;
        Stage stage = ApplicazioneStage.getStage();
        Scene scene;

        String fxmlFile = "/com/iKitchen/aggiungiProdottoView.fxml";
        fxmlLoader = new FXMLLoader();
        Parent rootNode = fxmlLoader.load(getClass().getResourceAsStream(fxmlFile));
        scene = new Scene(rootNode, ScreenSize.WIDTH_GUI1, ScreenSize.HEIGHT_GUI1);

        stage.setTitle("iKitchen");
        stage.setScene(scene);
        stage.show();
    }
}