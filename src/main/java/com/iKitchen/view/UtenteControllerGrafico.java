package com.iKitchen.view;

import com.iKitchen.controller.OttieniIngredientiControllerApplicativo;
import com.iKitchen.exception.DAOException;
import com.iKitchen.model.bean.BeanIngrediente;
import com.iKitchen.model.bean.BeanIngredienti;
import com.iKitchen.model.bean.CredentialsBean;
import com.iKitchen.model.domain.ApplicazioneStage;
import com.iKitchen.model.domain.Credentials;
import com.iKitchen.model.utility.ScreenSize;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.control.Label;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Objects;

public class UtenteControllerGrafico {

    @FXML
    private VBox elementContainerValidi; // Contenitore per gli ingredienti validi

    @FXML
    private VBox elementContainerNonValidi; // Contenitore per gli ingredienti non validi

    @FXML
    private TabPane tabPane;

    @FXML
    private Tab tabValidi;

    @FXML
    private Tab tabNonValidi;

    // Variabili
    private OttieniIngredientiControllerApplicativo ingredienti = null;
    public VBox mainContainer;
    public ScrollPane scrollPaneValidi;
    public ScrollPane scrollPaneNonValidi;

    // Metodo per inizializzare e configurare gli stili dei componenti
    public void initialize() throws DAOException, SQLException {
        if (elementContainerValidi != null) {
            caricaIngredienti(Credentials.getUsername());
        }

        // Centrare il TabPane orizzontalmente
        tabPane.setStyle("-fx-background-color: white;"); // Sfondo bianco per il TabPane
        tabPane.setTabMinWidth(130); // Larghezza minima dei tab
        tabPane.setTabMaxWidth(150); // Larghezza massima dei tab

        // Configurazione del TabPane
        tabPane.setStyle("-fx-background-color: white;"); // Sfondo bianco per il TabPane

        // Configurazione dei Tab
        configureTab(tabValidi); // Colori per i tab "Validi"
        configureTab(tabNonValidi); // Colori per i tab "Non validi"

        // Aggiungi un listener per cambiare lo stile quando un tab viene selezionato
        tabPane.getSelectionModel().selectedItemProperty().addListener((observable, oldTab, newTab) -> updateTabStyles());

        // Imposta lo stile iniziale
        updateTabStyles();
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
        ImageView imageView = new ImageView();
        imageView.setFitHeight(70);
        imageView.setFitWidth(65);

        // Imposta l'immagine
        try {
            if (ingrediente.getImmagine() != null) {
                InputStream inputStream = ingrediente.getImmagine().getBinaryStream();
                if (inputStream != null) {
                    Image image = new Image(inputStream, 100, 100, true, true); // Imposta dimensioni fisse e preserva il rapporto
                    imageView.setImage(image);
                } else {
                    // Carica immagine di default
                    Image defaultImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/default_image.png")), 100, 100, true, true);
                    imageView.setImage(defaultImage);
                }
            } else {
                // Carica immagine di default
                Image defaultImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/default_image.png")), 100, 100, true, true);
                imageView.setImage(defaultImage);
            }
        } catch (SQLException e) {
            // Carica immagine di default
            Image defaultImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/default_image.png")), 100, 100, true, true);
            imageView.setImage(defaultImage);
        }

        // Creazione di un Rectangle per angoli arrotondati delle immagini
        Rectangle clip = new Rectangle(65, 70);
        clip.setArcWidth(15);
        clip.setArcHeight(15);
        imageView.setClip(clip);

        // Impostazione dello stile dell'immagine
        imageView.setStyle("-fx-border-color: gray; -fx-border-width: 1; -fx-background-color: white; -fx-border-radius: 15;");

        // Predisposizione immagine
        imgBox = new HBox(imageView);

        // Creazione del titolo dell'ingrediente e icone
        Label titleLabel = new Label(ingrediente.getNome());
        titleLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

        // Icone matita e cestino
        ImageView pencilIcon = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/pencil_icon.png"))));
        pencilIcon.setFitHeight(20);
        pencilIcon.setFitWidth(20);
        ImageView trashIcon = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/trash_icon.png"))));
        trashIcon.setFitHeight(20);
        trashIcon.setFitWidth(20);

        // Creazione di un AnchorPane per gestire la posizione delle icone
        AnchorPane iconsAnchorPane = new AnchorPane();
        iconsAnchorPane.getChildren().addAll(pencilIcon, trashIcon);

        // Posizionamento delle icone all'interno dell'AnchorPane con uno spazio tra di esse
        AnchorPane.setTopAnchor(pencilIcon, 0.0);
        AnchorPane.setRightAnchor(pencilIcon, 25.0); // Spazio tra matita e cestino
        AnchorPane.setTopAnchor(trashIcon, 0.0);
        AnchorPane.setRightAnchor(trashIcon, 0.0); // Posizione del cestino vicino al bordo

        // StackPane per contenere le icone
        StackPane editingIcons = new StackPane(iconsAnchorPane);
        StackPane.setAlignment(editingIcons, Pos.TOP_RIGHT);

        // Combina titolo e icone in un unico HBox
        HBox titleAndIconsBox = new HBox(titleLabel, editingIcons);
        titleAndIconsBox.setSpacing(10); // Spazio tra il titolo e le icone
        titleAndIconsBox.setAlignment(Pos.CENTER_LEFT);

        // Dettagli dell'ingrediente (quantità)
        Label quantitaLabel = new Label("Quantità: " + ingrediente.getQuantita() + " g");
        quantitaLabel.setStyle("-fx-font-size: 12px;");

        // Formattazione della data di scadenza
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.ITALIAN);
        String formattedDate = "";
        if (ingrediente.getScadenza() != null) {
            formattedDate = sdf.format(ingrediente.getScadenza());
        }
        Label scadenzaLabel = new Label("Scadenza: " + formattedDate);
        scadenzaLabel.setStyle("-fx-font-size: 12px;");

        // VBox per contenere i dettagli dell'ingrediente
        VBox detailsBox = new VBox(titleAndIconsBox, quantitaLabel, scadenzaLabel);
        detailsBox.setSpacing(5);
        detailsBox.setAlignment(Pos.CENTER_LEFT);

        // Creazione della struttura principale
        HBox mainContent = new HBox(imgBox, detailsBox);
        mainContent.setSpacing(10);
        mainContent.setAlignment(Pos.CENTER_LEFT);
        mainContent.setPadding(new Insets(5)); // Padding per distanziare l'elemento dal bordo del BorderPane

        // Impostazione dell'elemento grafico
        element.setCenter(mainContent);
        element.setRight(editingIcons);
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

    // Metodo per configurare lo stile base dei tab
    private void configureTab(Tab tab) {
        // Usa solo stili di base che sono applicabili direttamente
        tab.setStyle("-fx-background-color: " + "#00A5A5" + ";"
                + "-fx-text-fill: white;"
                + "-fx-border-radius: 10px;"
                + "-fx-background-radius: 10px;");
    }

    // Metodo per aggiornare gli stili dei tab in base alla selezione
    private void updateTabStyles() {
        Tab selectedTab = tabPane.getSelectionModel().getSelectedItem();

        // Aggiorna gli stili dei tab
        for (Tab tab : tabPane.getTabs()) {
            if (tab.equals(selectedTab)) {
                tab.setStyle("-fx-background-color: #8ee8e4;" // Colore per il tab selezionato
                        + "-fx-text-fill: white;"
                        + "-fx-border-radius: 10px;"
                        + "-fx-background-radius: 10px;");
            } else {
                tab.setStyle("-fx-background-color: #00A5A5;" // Colore per i tab non selezionati
                        + "-fx-text-fill: white;"
                        + "-fx-border-radius: 10px;"
                        + "-fx-background-radius: 10px;");
            }
        }
    }
}