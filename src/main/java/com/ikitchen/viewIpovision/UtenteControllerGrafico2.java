package com.ikitchen.viewIpovision;

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
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
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

public class UtenteControllerGrafico2 {

    @FXML
    private Label labelTitle;

    @FXML
    private TabPane tabPane;

    @FXML
    private GridPane gridContainerValidi;

    @FXML
    private GridPane gridContainerNonValidi;

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

        // Inizializza il titolo caps lock con il messaggio interattivo
        setLabelTitle("DISPENSA DI " + Credentials.getNome().toUpperCase());

        // Invoca il metodo per caricare i prodotti della dispensa a griglia
        if (gridContainerValidi != null) {
            caricaIngredienti();
        }

        // Invoca il metodo per configurare lo stile personalizzato dei tab
        configuraCustomTab();
    }

    // Dallo username, interagisce con controller e DAO per ottenere la lista di ingredienti dal DB
    private void caricaIngredienti() throws DAOException, SQLException {

        // Inizializza il controller applicativo per ottenere gli ingredienti
        OttieniIngredientiControllerApplicativo ingredientiController = new OttieniIngredientiControllerApplicativo();

        // Definisci il numero di colonne che vuoi usare per il GridPane
        int numColumns = 4;

        // Gestisci gli ingredienti validi
        BeanIngredienti validi = ingredientiController.mostraIngredientiValidi();
        if (validi.getListIngredienti().isEmpty()) {
            Label emptyMessage = new Label("La lista è vuota!");
            emptyMessage.setFont(new Font("System Bold", 20));
            emptyMessage.setStyle("-fx-text-fill: grey;");
            gridContainerValidi.add(emptyMessage, 0, 0); // Aggiunge il messaggio alla prima posizione della griglia
        } else {
            int row = 0;
            int col = 0;
            for (BeanIngrediente ingrediente : validi.getListIngredienti()) {
                BorderPane element = createIngredientElement(ingrediente);

                // Imposta la larghezza minima per ciascun elemento
                element.setMinWidth(270);

                // Aggiunge l'elemento alla griglia nella posizione calcolata
                gridContainerValidi.add(element, col, row);

                // Avanza di colonna; se raggiungi l'ultima colonna, passa alla riga successiva
                col++;
                if (col >= numColumns) {
                    col = 0;
                    row++;
                }
            }
        }

        // Gestisci gli ingredienti non validi
        BeanIngredienti nonValidi = ingredientiController.mostraIngredientiNonValidi();
        if (nonValidi.getListIngredienti().isEmpty()) {
            Label emptyMessage = new Label("La lista è vuota!");
            emptyMessage.setFont(new Font("System Bold", 20));
            emptyMessage.setStyle("-fx-text-fill: grey;");
            gridContainerNonValidi.add(emptyMessage, 0, 0); // Aggiunge il messaggio alla prima posizione della griglia
        } else {
            int row = 0;
            int col = 0;
            for (BeanIngrediente ingrediente : nonValidi.getListIngredienti()) {
                BorderPane element = createIngredientElement(ingrediente);

                // Imposta la larghezza minima per ciascun elemento
                element.setMinWidth(270);

                // Aggiunge l'elemento alla griglia nella posizione calcolata
                gridContainerNonValidi.add(element, col, row);

                // Avanza di colonna; se raggiungi l'ultima colonna, passa alla riga successiva
                col++;
                if (col >= numColumns) {
                    col = 0;
                    row++;
                }
            }
        }
    }

    // Metodo che crea l'elemento grafico per ogni ingrediente
    private BorderPane createIngredientElement(BeanIngrediente ingrediente) {
        BorderPane element = new BorderPane();

        // Creazione dell'immagine dell'ingrediente
        HBox imgBox;
        ImageView imageView = new ImageView();
        imageView.setFitHeight(80);
        imageView.setFitWidth(75);

        // Imposta l'immagine
        try {
            if (ingrediente.getImmagine() != null) {
                InputStream inputStream = ingrediente.getImmagine().getBinaryStream();
                if (inputStream != null) {
                    Image image = new Image(inputStream, 100, 100, true, true);
                    imageView.setImage(image);
                } else {
                    Image defaultImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/default_image.png")), 100, 100, true, true);
                    imageView.setImage(defaultImage);
                }
            } else {
                Image defaultImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/default_image.png")), 100, 100, true, true);
                imageView.setImage(defaultImage);
            }
        } catch (SQLException e) {
            Image defaultImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/default_image.png")), 100, 100, true, true);
            imageView.setImage(defaultImage);
        }

        // Creazione di un Rectangle per angoli arrotondati delle immagini
        Rectangle clip = new Rectangle(75, 80);
        clip.setArcWidth(30);
        clip.setArcHeight(30);
        imageView.setClip(clip);
        imgBox = new HBox(imageView);

        // Creazione del titolo dell'ingrediente
        Label titleLabel = new Label(ingrediente.getNome().toUpperCase());
        titleLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

        // Quantità
        String tipoIngrediente = ingrediente.getTipo().equals("cibo") ? "g" : "l";
        Label quantitaLabel = new Label("QUANTITÀ: " + ingrediente.getQuantita() + " " + tipoIngrediente);
        quantitaLabel.setStyle("-fx-font-size: 12px;");

        // Data di scadenza
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.ITALIAN);
        String formattedDate = ingrediente.getScadenza() != null ? sdf.format(ingrediente.getScadenza()) : "";
        Label scadenzaLabel = new Label("SCADENZA: " + formattedDate);
        scadenzaLabel.setStyle("-fx-font-size: 12px;");

        // Settaggi del box verticale
        VBox detailsBox = new VBox(titleLabel, quantitaLabel, scadenzaLabel);
        detailsBox.setSpacing(5);
        detailsBox.setAlignment(Pos.CENTER_LEFT);

        // Settaggi del box orizzontale
        HBox mainContent = new HBox(imgBox, detailsBox);
        mainContent.setSpacing(10);
        mainContent.setAlignment(Pos.CENTER_LEFT);
        mainContent.setPadding(new Insets(5));

        // Impostazione dell'elemento grafico
        element.setCenter(mainContent);
        element.setPadding(new Insets(10));
        element.setStyle("-fx-border-color: gray; -fx-border-width: 1; -fx-background-color: white; -fx-border-radius: 10; -fx-background-radius: 10;");

        return element;
    }

    // Visualizzazione della pagina dell'utente
    public void homePageUtente() throws IOException, DAOException, SQLException {
        FXMLLoader fxmlLoader;
        Stage stage = ApplicazioneStage.getStage();
        Scene scene;

        String fxmlFile = "/com/IpovisionGUI/utentiView2.fxml";
        fxmlLoader = new FXMLLoader();
        Parent rootNode = fxmlLoader.load(getClass().getResourceAsStream(fxmlFile));

        // Forzo la chiamata al controller stesso per rinizializzare la pagina
        UtenteControllerGrafico2 controller = fxmlLoader.getController();
        controller.initialize();

        scene = new Scene(rootNode, ScreenSize.getSceneWidth(), ScreenSize.getSceneHeight());

        stage.setTitle("iKitchen");
        stage.setScene(scene);
        stage.setResizable(true);
        stage.show();
    }

    // Visualizzazione della pagina delle possibili categorie di ricette
    public void categorieView() throws IOException {
        FXMLLoader fxmlLoader;
        Stage stage = ApplicazioneStage.getStage();
        Scene scene;

        String fxmlFile = "/com/IpovisionGUI/categorieView2.fxml";
        fxmlLoader = new FXMLLoader();
        Parent rootNode = fxmlLoader.load(getClass().getResourceAsStream(fxmlFile));

        OttieniRicettaControllerGrafico2 controller = fxmlLoader.getController();
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
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/IpovisionGUI/login2.fxml"));
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
        tabPane.setStyle("-fx-background-color: black;");
        tabPane.setTabMinWidth(621);

        // Stile base per i tab
        String baseStyle = "-fx-background-color: #a69a9a;"
                + "-fx-text-fill: white;"
                + "-fx-border-radius: 10px;"
                + "-fx-background-radius: 10px;";

        // Stile per il tab selezionato
        String selectedStyle = "-fx-background-color: #4d4949;"
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