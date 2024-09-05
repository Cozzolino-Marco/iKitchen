package com.iKitchen.viewIpovision;

import com.iKitchen.controller.OttieniIngredientiControllerApplicativo;
import com.iKitchen.exception.DAOException;
import com.iKitchen.model.bean.BeanIngrediente;
import com.iKitchen.model.bean.BeanIngredienti;
import com.iKitchen.model.bean.CredentialsBean;
import com.iKitchen.model.domain.ApplicazioneStage;
import com.iKitchen.model.domain.Credentials;
import com.iKitchen.model.utility.Popup;
import com.iKitchen.model.utility.ScreenSize;
import com.iKitchen.view.LoginGrafico;
import com.iKitchen.view.OttieniRicettaControllerGrafico;
import com.iKitchen.view.UtenteControllerGrafico;
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
    private Tab tabValidi;

    @FXML
    private Tab tabNonValidi;

    @FXML
    private GridPane gridContainerValidi; // GridPane per ingredienti validi

    @FXML
    private GridPane gridContainerNonValidi; // GridPane per ingredienti non validi

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
        caricaIngredienti(Credentials.getUsername());

        // Inizializza il titolo con il messaggio interattivo
        setLabelTitle("DISPENSA DI " + Credentials.getNome());

        // Configurazione del TabPane
        tabPane.setStyle("-fx-background-color: white;");
        tabPane.setTabMinWidth(130);
        tabPane.setTabMaxWidth(150);

        // Configurazione dei Tab
        configureTab(tabValidi);
        configureTab(tabNonValidi);

        // Aggiungi un listener per cambiare lo stile quando un tab viene selezionato
        tabPane.getSelectionModel().selectedItemProperty().addListener((observable, oldTab, newTab) -> updateTabStyles());

        // Imposta lo stile iniziale
        updateTabStyles();
    }

    // Dallo username, interagisce con controller e DAO per ottenere la lista di ingredienti dal DB
    private void caricaIngredienti(String username) throws DAOException, SQLException {

        CredentialsBean infoPerListaIngredienti = new CredentialsBean(username);

        OttieniIngredientiControllerApplicativo ingredientiController = new OttieniIngredientiControllerApplicativo();

        // Definisci il numero di colonne che vuoi usare per il GridPane
        int numColumns = 4;

        // Gestisci gli ingredienti validi
        BeanIngredienti validi = ingredientiController.mostraIngredientiValidi(infoPerListaIngredienti);
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

                // Imposta la larghezza massima per ciascun elemento
                element.setMaxWidth(1200); // Aumenta questa dimensione se vuoi elementi più larghi

                // Aggiunge l'elemento alla griglia nella posizione calcolata
                gridContainerValidi.add(element, col, row);

                // Imposta larghezza minima al GridPane
                gridContainerValidi.setMinWidth(1200);
                gridContainerValidi.setMaxWidth(1200);

                // Avanza di colonna; se raggiungi l'ultima colonna, passa alla riga successiva
                col++;
                if (col >= numColumns) {
                    col = 0;
                    row++;
                }
            }
        }

        // Gestisci gli ingredienti non validi
        BeanIngredienti nonValidi = ingredientiController.mostraIngredientiNonValidi(infoPerListaIngredienti);
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

                // Imposta la larghezza massima per ciascun elemento
                element.setMaxWidth(1200); // Aumenta questa dimensione se vuoi elementi più larghi

                // Imposta larghezza minima al GridPane
                gridContainerNonValidi.setMinWidth(1200);
                gridContainerNonValidi.setMaxWidth(1200);

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
        clip.setArcWidth(20);
        clip.setArcHeight(20);
        imageView.setClip(clip);

        imgBox = new HBox(imageView);

        // Creazione del titolo dell'ingrediente
        Label titleLabel = new Label(ingrediente.getNome());
        titleLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

        // Quantità e data di scadenza
        String tipoIngrediente = ingrediente.getTipo().equals("cibo") ? "g" : "l";
        Label quantitaLabel = new Label("Quantità: " + ingrediente.getQuantita() + " " + tipoIngrediente);
        quantitaLabel.setStyle("-fx-font-size: 12px;");

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.ITALIAN);
        String formattedDate = ingrediente.getScadenza() != null ? sdf.format(ingrediente.getScadenza()) : "";
        Label scadenzaLabel = new Label("Scadenza: " + formattedDate);
        scadenzaLabel.setStyle("-fx-font-size: 12px;");

        VBox detailsBox = new VBox(titleLabel, quantitaLabel, scadenzaLabel);
        detailsBox.setSpacing(5);
        detailsBox.setAlignment(Pos.CENTER_LEFT);

        HBox mainContent = new HBox(imgBox, detailsBox);
        mainContent.setSpacing(10);
        mainContent.setAlignment(Pos.CENTER_LEFT);
        mainContent.setPadding(new Insets(5));

        element.setCenter(mainContent);
        element.setPadding(new Insets(10));
        element.setStyle("-fx-border-color: gray; -fx-border-width: 1; -fx-background-color: white; -fx-border-radius: 10; -fx-background-radius: 10;");

        return element;
    }

    public void homePageUtente() throws IOException, DAOException, SQLException {
        FXMLLoader fxmlLoader;
        Stage stage = ApplicazioneStage.getStage();
        Scene scene;

        String fxmlFile = "/com/iKitchen/utentiView.fxml";
        fxmlLoader = new FXMLLoader();
        Parent rootNode = fxmlLoader.load(getClass().getResourceAsStream(fxmlFile));

        // Forzo la chiamata al controller stesso per rinizializzare la pagina
        UtenteControllerGrafico controller = fxmlLoader.getController();
        controller.initialize();

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

        OttieniRicettaControllerGrafico controller = fxmlLoader.getController();
        controller.initialize("", "", "", "");

        scene = new Scene(rootNode, ScreenSize.WIDTH_GUI1, ScreenSize.HEIGHT_GUI1);

        stage.setTitle("iKitchen");
        stage.setScene(scene);
        stage.show();
    }

    public void preferitiView() {
        Popup.mostraPopup("In costruzione", "Sezione non ancora implementata!", "construction");
    }

    public void aggiungiProdotto() {
        Popup.mostraPopup("In costruzione", "Sezione non ancora implementata!", "construction");
    }

    // Chiamata al controller del login
    public void loginView() throws IOException {
        boolean confermato = Popup.mostraPopupConferma("Conferma Logout", "Sei sicuro di voler effettuare il logout?");
        if (confermato) {
            //Credentials.setRole(""); // TODO: Evitare eccezione DAO per il ruolo
            LoginGrafico loginGrafico = new LoginGrafico();
            loginGrafico.loginView();
        }
    }

    // Metodo per configurare lo stile base dei tab
    private void configureTab(Tab tab) {
        tab.setStyle("-fx-background-color: #00A5A5;"
                + "-fx-text-fill: white;"
                + "-fx-border-radius: 10px;"
                + "-fx-background-radius: 10px;");
    }

    // Metodo per aggiornare gli stili dei tab in base alla selezione
    private void updateTabStyles() {
        Tab selectedTab = tabPane.getSelectionModel().getSelectedItem();

        for (Tab tab : tabPane.getTabs()) {
            if (tab.equals(selectedTab)) {
                tab.setStyle("-fx-background-color: #8ee8e4;"
                        + "-fx-text-fill: white;"
                        + "-fx-border-radius: 10px;"
                        + "-fx-background-radius: 10px;");
            } else {
                tab.setStyle("-fx-background-color: #00A5A5;"
                        + "-fx-text-fill: white;"
                        + "-fx-border-radius: 10px;"
                        + "-fx-background-radius: 10px;");
            }
        }
    }
}
