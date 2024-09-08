package com.ikitchen.view_standard;

import com.ikitchen.controller.OttieniIngredientiControllerApplicativo;
import com.ikitchen.exception.DAOException;
import com.ikitchen.model.bean.BeanIngrediente;
import com.ikitchen.model.bean.BeanIngredienti;
import com.ikitchen.model.dao.ConnectionFactory;
import com.ikitchen.model.domain.ApplicazioneStage;
import com.ikitchen.model.utility.Credentials;
import com.ikitchen.model.utility.Popup;
import com.ikitchen.model.utility.ScreenSize;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Objects;

public class UtenteControllerGrafico {

    @FXML
    private Label labelTitle;

    @FXML
    private VBox elementContainerValidi;

    @FXML
    private VBox elementContainerNonValidi;

    @FXML
    private TabPane tabPane;

    @FXML
    public VBox mainContainer;

    @FXML
    public ScrollPane scrollPaneValidi;

    @FXML
    public ScrollPane scrollPaneNonValidi;

    // Setta il titolo della categoria della barra di navigazione superiore
    public void setLabelTitle(String title) {
        labelTitle.setText(title);
    }

    // Metodo per inizializzare e configurare gli stili dei componenti
    public void initialize() throws DAOException, SQLException {

        // Inizializza il titolo con il messaggio interattivo
        setLabelTitle("Dispensa di " + Credentials.getNome());

        // Invoca il metodo per caricare i prodotti della dispensa ad elenco
        if (elementContainerValidi != null) {
            caricaIngredienti();
        }

        // Invoca il metodo per configurare lo stile personalizzato dei tab
        configuraCustomTab();
    }

    // Dallo username, interagisce con controller e DAO per ottenere la lista di ingredienti dal DB
    private void caricaIngredienti() throws DAOException, SQLException {

        // Inizializza il controller applicativo per ottenere gli ingredienti
        OttieniIngredientiControllerApplicativo ingredienti = new OttieniIngredientiControllerApplicativo();

        // Gestisci la visualizzazione degli ingredienti validi
        BeanIngredienti validi = ingredienti.mostraIngredientiValidi();
        if (validi.getListIngredienti().isEmpty()) {
            Label emptyMessage = new Label("La lista è vuota!");
            emptyMessage.setFont(new Font("System Bold", 20));
            emptyMessage.setStyle("-fx-text-fill: grey;");
            elementContainerValidi.getChildren().add(emptyMessage);
        } else {
            // Aggiungi gli ingredienti validi al VBox
            for (BeanIngrediente ingrediente : validi.getListIngredienti()) {
                BorderPane element = createIngredientElement(ingrediente);
                elementContainerValidi.getChildren().add(element);
            }
        }

        // Gestisci la visualizzazione degli ingredienti non validi
        BeanIngredienti nonValidi = ingredienti.mostraIngredientiNonValidi();
        if (nonValidi.getListIngredienti().isEmpty()) {
            Label emptyMessage = new Label("La lista è vuota!");
            emptyMessage.setFont(new Font("System Bold", 20));
            emptyMessage.setStyle("-fx-text-fill: grey;");
        } else {
            // Aggiungi gli ingredienti non validi al VBox
            for (BeanIngrediente ingrediente : nonValidi.getListIngredienti()) {
                BorderPane element = createIngredientElement(ingrediente);
                elementContainerNonValidi.getChildren().add(element);
            }
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
            InputStream inputStream = (ingrediente.getImmagine() != null) ? ingrediente.getImmagine().getBinaryStream() : null;

            if (inputStream != null) {
                // Imposta immagine specificata
                Image image = new Image(inputStream, 100, 100, true, true); // Imposta dimensioni fisse e preserva il rapporto
                imageView.setImage(image);
            } else {
                // Forza il catch per caricare immagine di default
                throw new SQLException();
            }
        } catch (Exception e) {
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

        // Recupera il tipo di ingrediente
        String tipoIngrediente = null;
        if (ingrediente.getTipo().equals("cibo")) {
            tipoIngrediente = "g";
        } else if (ingrediente.getTipo().equals("drink")) {
            tipoIngrediente = "l";
        }

        // Dettagli dell'ingrediente (quantità)
        Label quantitaLabel = new Label("Quantità: " + ingrediente.getQuantita() + " " + tipoIngrediente);
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
        mainContent.setPadding(new Insets(5));

        // Impostazione dell'elemento grafico
        element.setCenter(mainContent);
        element.setRight(editingIcons);
        element.setPadding(new Insets(10));
        element.setStyle("-fx-border-color: gray; -fx-border-width: 1; -fx-background-color: white; -fx-border-radius: 10; -fx-background-radius: 10;");

        // Gestore di eventi per il click sull'elemento
        element.setOnMouseClicked(event -> {});

        return element;
    }

    // Visualizzazione della pagina dell'utente
    public void homePageUtente() throws IOException, DAOException, SQLException {
        FXMLLoader fxmlLoader;
        Stage stage = ApplicazioneStage.getStage();
        Scene scene;

        String fxmlFile = "/com/StandardGUI/utentiView.fxml";
        fxmlLoader = new FXMLLoader();
        Parent rootNode = fxmlLoader.load(getClass().getResourceAsStream(fxmlFile));

        // Forzo la chiamata al controller stesso per rinizializzare la pagina
        UtenteControllerGrafico controller = fxmlLoader.getController();
        controller.initialize();

        scene = new Scene(rootNode, ScreenSize.getSceneWidth(), ScreenSize.getSceneHeight());

        stage.setTitle("iKitchen");
        stage.setScene(scene);
        stage.show();
    }

    // Visualizzazione della pagina delle possibili categorie di ricette
    public void categorieView() throws IOException {
        FXMLLoader fxmlLoader;
        Stage stage = ApplicazioneStage.getStage();
        Scene scene;

        String fxmlFile = "/com/StandardGUI/categorieView.fxml";
        fxmlLoader = new FXMLLoader();
        Parent rootNode = fxmlLoader.load(getClass().getResourceAsStream(fxmlFile));

        OttieniRicettaControllerGrafico controller = fxmlLoader.getController();
        controller.initialize("", "", "", "");

        scene = new Scene(rootNode, ScreenSize.getSceneWidth(), ScreenSize.getSceneHeight());

        stage.setTitle("iKitchen");
        stage.setScene(scene);
        stage.show();
    }

    // Visualizzazione lista delle ricette preferite
    public void preferitiView() {
        Popup.mostraPopup("In costruzione", "Sezione non ancora implementata!", "construction");
    }

    // Metodo per aggiungere un ingrediente alla propria dispensa
    public void aggiungiProdotto() {
        Popup.mostraPopup("In costruzione", "Sezione non ancora implementata!", "construction");
    }

    // Metodo per effettuare il logout
    public void logout() throws IOException, SQLException {

        // Ottieni la scelta dell'utente al popup
        boolean confermato = Popup.mostraPopupConferma("Conferma Logout", "Sei sicuro di voler effettuare il logout?");

        if (confermato) {

            // Azzera le credenziali dell'utente
            Credentials.setUsername(null);
            Credentials.setPassword(null);
            Credentials.setRole(null);

            // Chiude la connessione
            ConnectionFactory.closeConnection();

            // Carica il file FXML per la vista del login
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/StandardGUI/login.fxml"));
            Parent root = fxmlLoader.load();

            // Ottieni lo stage attuale dalla classe ApplicazioneStage
            Stage stage = ApplicazioneStage.getStage();

            // Imposta la nuova scena con il layout caricato
            Scene scene = new Scene(root, ScreenSize.getSceneWidth(), ScreenSize.getSceneHeight());

            // Cambia la scena dello stage
            stage.setScene(scene);
            stage.show();
        }
    }

    // Metodo per la gestione grafica dei tab
    private void configuraCustomTab() {

        // Configurazione del colore e larghezza del TabPane
        tabPane.setStyle("-fx-background-color: white;");
        tabPane.setTabMinWidth(130);

        // Stile base per i tab
        String baseStyle = "-fx-background-color: #8ee8e4;"
                + "-fx-text-fill: white;"
                + "-fx-border-radius: 10px;"
                + "-fx-background-radius: 10px;";

        // Stile per il tab selezionato
        String selectedStyle = "-fx-background-color: #00A5A5;"
                + "-fx-text-fill: white;"
                + "-fx-border-radius: 10px;"
                + "-fx-background-radius: 10px;";

        // Configura tutti i tab con lo stile base
        for (Tab tab : tabPane.getTabs()) {
            tab.setStyle(baseStyle);
        }

        // Aggiungi un listener per cambiare lo stile quando un tab viene selezionato
        tabPane.getSelectionModel().selectedItemProperty().addListener((observable, oldTab, newTab) -> {
            for (Tab tab : tabPane.getTabs()) {
                if (tab.equals(newTab)) {
                    tab.setStyle(selectedStyle); // Applica lo stile al tab selezionato
                } else {
                    tab.setStyle(baseStyle); // Applica lo stile base agli altri
                }
            }
        });

        // Imposta lo stile iniziale per il tab attualmente selezionato
        Tab selectedTab = tabPane.getSelectionModel().getSelectedItem();
        if (selectedTab != null) {
            selectedTab.setStyle(selectedStyle);
        }
    }
}