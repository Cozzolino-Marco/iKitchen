package com.ikitchen.view_standard;

import static com.ikitchen.ApplicationStart.getHostServicesInstance;
import com.ikitchen.controller.OttieniRicettaControllerApplicativo;
import com.ikitchen.exception.DAOException;
import com.ikitchen.model.bean.*;
import com.ikitchen.model.domain.ApplicazioneStage;
import com.ikitchen.model.domain.Ingrediente;
import com.ikitchen.model.utility.Popup;
import com.ikitchen.model.utility.ScreenSize;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.net.URI;
import java.net.URISyntaxException;

public class OttieniRicettaControllerGrafico {

    // Elementi grafici
    @FXML
    private Label categoriaLabelTitle;

    @FXML
    private ComboBox provenienzaComboBox;

    @FXML
    private ComboBox filtraggioComboBox;

    @FXML
    private ComboBox storageComboBox;

    @FXML
    private VBox elementContainer;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private VBox categoriesContainer;

    // Dichiarazioni costanti
    private static final String APP_NAME = "iKitchen";
    private static final String WARNING_MESSAGE_TITLE = "Attenzione";
    private static final String WARNING_POPUP_TYPE = "warning";
    private static final String ERROR_MESSAGE_TITLE = "Errore";
    private static final String ERROR_POPUP_TYPE = "error";
    private static final String DEFAULT_IMAGE = "/default_image.png";

    // Altre dichiarazioni variabili
    private OttieniRicettaControllerApplicativo ricette = null;
    private int validIngredientCount = 0;
    private String categoriaScelta;
    private String provenienzaScelta;
    private String filtraggioScelta;
    private String storageScelto;

    // Configurazione iniziale degli elementi grafici
    public void initialize(String categoriaScelta, String provenienzaScelta, String filtraggioScelta, String storageScelto) {
        if (elementContainer != null) {
            try {
                caricaRicette(categoriaScelta, provenienzaScelta, filtraggioScelta, storageScelto);
            } catch (DAOException | SQLException | IOException e) {
                Popup.mostraPopup(ERROR_MESSAGE_TITLE, "Si è verificato un errore durante durante il caricamento delle ricette.", ERROR_POPUP_TYPE);
            }
        }
        if (categoriesContainer != null) {
            loadCategories();
        }
    }

    // Setta il titolo della categoria della barra di navigazione superiore
    public void setCategoriaLabelTitle(String categoria) {
        categoriaLabelTitle.setText(categoria);
    }

    // Setter delle tre informazioni di filtraggio delle ricette
    public void setCategoria(String categoria) {
        this.categoriaScelta = categoria;
    }
    public void setProvenienza(String provenienza) {
        this.provenienzaScelta = provenienza;
    }
    public void setFiltraggio(String filtraggio) {
        this.filtraggioScelta = filtraggio;
    }
    public void setStorage(String storage) {
        this.storageScelto = storage;
    }

    // Metodo per mostrare la pagina della scelta delle categorie
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

        stage.setTitle(APP_NAME);
        stage.setScene(scene);
        stage.show();
    }

    @FXML // Metodo per aggiornare dinamicamente i bottoni delle categorie
    public void loadCategories() {
        categoriesContainer.getChildren().clear();

        // Creare una LinkedHashMap per mantenere l'ordine di inserimento
        Map<String, String> categorieConImmagini = new LinkedHashMap<>();
        categorieConImmagini.put("Colazione", "colazione.png");
        categorieConImmagini.put("Pasto veloce", "pasto_veloce.png");
        categorieConImmagini.put("Bevande", "bevande.png");
        categorieConImmagini.put("Primi piatti", "primi_piatti.png");
        categorieConImmagini.put("Secondi piatti", "secondi_piatti.png");
        categorieConImmagini.put("Contorni", "contorni.png");
        categorieConImmagini.put("Dolci", "dolci.png");

        for (Map.Entry<String, String> entry : categorieConImmagini.entrySet()) {
            String categoria = entry.getKey();
            String immaginePath = entry.getValue();

            // Creazione del bottone
            Button categoriaButton = new Button();
            categoriaButton.setPrefWidth(250);
            categoriaButton.setMaxWidth(250);

            // Creazione dell'HBox per contenere testo e immagine
            HBox buttonContent = new HBox();
            buttonContent.setAlignment(Pos.CENTER_LEFT);
            buttonContent.setSpacing(10);
            HBox.setHgrow(buttonContent, Priority.ALWAYS);

            // Creazione e aggiunta del testo al bottone
            Label textLabel = new Label(categoria);
            textLabel.setStyle("-fx-font-size: 16; -fx-text-fill: white;");

            // Creazione e aggiunta dell'immagine al bottone
            ImageView categoriaImageView = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/" + immaginePath))));

            // Impostazione della dimensione fissa per l'immagine
            final double fixedSize = 30;
            categoriaImageView.setFitHeight(fixedSize);
            categoriaImageView.setFitWidth(fixedSize);
            categoriaImageView.setPreserveRatio(false);

            // Creare una maschera quadrata con angoli smussati per rendere l'immagine stondato
            Rectangle clip = new Rectangle(30, 30);
            clip.setArcWidth(10);
            clip.setArcHeight(10);
            categoriaImageView.setClip(clip);

            // Aggiunta di spazio tra il testo e l'immagine per allineare l'immagine a destra
            Region spacer = new Region();
            HBox.setHgrow(spacer, Priority.ALWAYS);

            // Aggiunta di testo, spazio e immagine all'HBox
            buttonContent.getChildren().addAll(textLabel, spacer, categoriaImageView);

            // Aggiunta dell'HBox al bottone
            categoriaButton.setGraphic(buttonContent);

            // Event handler del bottone
            categoriaButton.setOnAction(event -> {
                setCategoria(categoria);
                try {
                    filtriView();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            // Stile del bottone con angoli stondati
            categoriaButton.setStyle("-fx-background-color: #0b5959; -fx-background-radius: 10;");

            // Aggiunta del bottone al contenitore con margini per centratura
            VBox.setMargin(categoriaButton, new Insets(7, 0, 7, 0));
            categoriesContainer.getChildren().add(categoriaButton);
        }

        // Impostazione di padding del VBox per distanziare il primo e ultimo bottone dalle barre di navigazione
        categoriesContainer.setPadding(new Insets(20, 0, 20, 0));
        categoriesContainer.setAlignment(Pos.CENTER);
    }

    @FXML // Metodo per mostrare la pagina dei filtri
    public void filtriView() throws IOException {

        // Mostra un popup di errore se la categoria non è stata selezionata
        if (categoriaScelta == null) {
            Popup.mostraPopup(WARNING_MESSAGE_TITLE, "Prima di andare avanti, seleziona per favore la categoria della ricetta!", WARNING_POPUP_TYPE);
        } else {
            // Se una categoria è stata selezionata, procedi con il caricamento della nuova scena
            FXMLLoader fxmlLoader = new FXMLLoader();
            Stage stage = ApplicazioneStage.getStage();
            Scene scene;

            String fxmlFile = "/com/StandardGUI/filtriView.fxml";
            Parent rootNode = fxmlLoader.load(getClass().getResourceAsStream(fxmlFile));

            OttieniRicettaControllerGrafico controller = fxmlLoader.getController();
            controller.setCategoria(categoriaScelta); // Passa la categoria selezionata
            controller.initialize("", "", "", "");

            scene = new Scene(rootNode, ScreenSize.getSceneWidth(), ScreenSize.getSceneHeight());

            stage.setTitle(APP_NAME);
            stage.setScene(scene);
            stage.show();
        }
    }

    // Dai parametri, interagisce con controller e DAO per ottenere la lista di ricette dal DB
    private void caricaRicette(String categoria, String provenienza, String filtraggio, String storage) throws DAOException, SQLException, IOException {

        // Crea un bean con le informazioni selezionate
        BeanRicette infoPerListaRicette = new BeanRicette(categoria, provenienza, filtraggio, storage);

        // Inizializza il controller applicativo
        ricette = new OttieniRicettaControllerApplicativo();

        // Ottieni la lista delle ricette dal controller applicativo
        BeanRicette listaRicette = ricette.mostraRicette(infoPerListaRicette);

        // Trova il BorderPane principale (root) della view
        BorderPane root = (BorderPane) scrollPane.getParent().getParent();

        // Se la lista è vuota, mostra il messaggio relativo
        if (listaRicette.getListRicette().isEmpty()) {
            Label emptyMessage = new Label("La lista è vuota!");
            emptyMessage.setFont(new Font("System Bold", 20));
            emptyMessage.setStyle("-fx-text-fill: grey;");
            root.setCenter(emptyMessage);
        } else {
            // Ciclo che itera la creazione di un elemento ricetta per la lista ricette
            for (BeanRicetta beanRicetta : listaRicette.getListRicette()) {
                BorderPane element = createRicettaElement(beanRicetta);
                elementContainer.getChildren().add(element);
            }
        }
    }

    @FXML // Metodo che mostra le ricette caricate a livello grafico
    protected void mostraRicette() throws IOException {

        // Mostra un avviso se anche uno dei campi non è stato selezionato
        if (provenienzaComboBox.getValue() == null || filtraggioComboBox.getValue() == null || storageComboBox.getValue() == null) {
            Popup.mostraPopup(WARNING_MESSAGE_TITLE, "Si prega di selezionare tutte le opzioni prima di procedere!", WARNING_POPUP_TYPE);

        } else if ("Dal web".equals(provenienzaComboBox.getValue()) &&
                ("Filtrate in base alla dispensa".equals(filtraggioComboBox.getValue()) ||
                        "Solo dal database".equals(storageComboBox.getValue()) ||
                        "Solo dal file system".equals(storageComboBox.getValue()) ||
                        "Da entrambi".equals(storageComboBox.getValue()))) {

            // Mostra un popup con un messaggio specifico
            Popup.mostraPopup(WARNING_MESSAGE_TITLE,
                    "Con la provenienza 'Dal web', non è possibile selezionare 'Filtrate in base alla dispensa' come filtraggio " +
                            "e non sono ammesse le selezioni di: 'Solo dal database', 'Solo dal file system', 'Da entrambi' come storage.",
                    WARNING_POPUP_TYPE);

        } else if ("Da chef".equals(provenienzaComboBox.getValue()) && "Nessuno".equals(storageComboBox.getValue())) {

            // Mostra un popup con un messaggio specifico
            Popup.mostraPopup(WARNING_MESSAGE_TITLE, "Con la provenienza 'Da chef', non è possibile selezionare 'Nessuno' come storage.", WARNING_POPUP_TYPE);

        } else {

            // Salvataggio della categoria scelta
            String categoriaSelezionata = this.categoriaScelta;

            // Caricamento della scena
            FXMLLoader fxmlLoader = new FXMLLoader();
            Stage stage = ApplicazioneStage.getStage();
            Scene scene;
            String fxmlFile = "/com/StandardGUI/elencoRicetteView.fxml";
            Parent rootNode = fxmlLoader.load(getClass().getResourceAsStream(fxmlFile));

            // Settaggio dei dati
            OttieniRicettaControllerGrafico controller = fxmlLoader.getController();
            controller.setCategoriaLabelTitle(categoriaSelezionata);
            controller.setCategoria(categoriaSelezionata);
            controller.setProvenienza(provenienzaComboBox.getValue().toString());
            controller.setFiltraggio(filtraggioComboBox.getValue().toString());
            controller.setStorage(storageComboBox.getValue().toString());

            // Chiamata al metodo per inizializzare
            controller.initialize(categoriaSelezionata, provenienzaComboBox.getValue().toString(), filtraggioComboBox.getValue().toString(), storageComboBox.getValue().toString());

            // Ultimi settaggi della scena ed avvio
            scene = new Scene(rootNode, ScreenSize.getSceneWidth(), ScreenSize.getSceneHeight());
            stage.setTitle(APP_NAME);
            stage.setScene(scene);
            stage.show();
        }
    }

    // Gestione grafica di un elemento ricetta
    private BorderPane createRicettaElement(BeanRicetta ricettaBean) {
        BorderPane element = new BorderPane();

        // Gestione dell'immagine della ricetta
        HBox imgBox;
        ImageView imageView;
        Image image;
        try {
            if (ricettaBean.getImmagine() != null && ricettaBean.getImmagine().getBinaryStream() != null) {
                InputStream inputStream = ricettaBean.getImmagine().getBinaryStream();
                image = new Image(inputStream, 100, 100, true, true); // Imposta dimensioni fisse e preserva il rapporto
            } else {
                image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(DEFAULT_IMAGE)));
            }
        } catch (SQLException e) {
            image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(DEFAULT_IMAGE)));
        }

        // Settaggi grafici per l'immagine
        imageView = new ImageView(image);
        imageView.setFitHeight(70);
        imageView.setFitWidth(65);
        Rectangle clip = new Rectangle(65, 70);
        clip.setArcWidth(15);
        clip.setArcHeight(15);
        imageView.setClip(clip);
        imgBox = new HBox(imageView);
        element.setLeft(imgBox);

        // Creazione del titolo della ricetta
        Label titleLabel = new Label(ricettaBean.getTitolo());
        titleLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 15px;");

        // Creazione dell'icona del cuoco e del nome del cuoco
        ImageView cuocoIcon = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/cuoco_icon.jpg"))));
        cuocoIcon.setFitHeight(14);
        cuocoIcon.setFitWidth(14);
        Label cuocoLabel = new Label(ricettaBean.getCuoco());
        cuocoLabel.setStyle("-fx-font-size: 10px;");
        HBox cuocoBox = new HBox(cuocoIcon, cuocoLabel);
        cuocoBox.setSpacing(5);

        // Creazione dell'icona per le calorie
        ImageView calorieIcon = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/calorie_icon.png"))));
        calorieIcon.setFitHeight(14);
        calorieIcon.setFitWidth(14);
        Label calorieLabel;
        if (ricettaBean.getCalorie() == 0) {
            calorieLabel = new Label("TBA Kcal");
        } else {
            calorieLabel = new Label(ricettaBean.getCalorie() + " Kcal");
        }
        calorieLabel.setStyle("-fx-font-size: 12px;");

        // Creazione di uno spazio vuoto (Region) tra le calorie e la durata
        Region spacer = new Region();
        spacer.setMinWidth(10);

        // Creazione dell'icona per durata della preparazione
        ImageView durataIcon = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/duration_icon.png"))));
        durataIcon.setFitHeight(14);
        durataIcon.setFitWidth(14);
        Label durataLabel = new Label(ricettaBean.getDurataPreparazione() + " min");
        durataLabel.setStyle("-fx-font-size: 12.1px;");

        // Parte bottom per info calorie e durata
        HBox infoBox = new HBox(calorieIcon, calorieLabel, spacer, durataIcon, durataLabel);
        infoBox.setSpacing(5);

        // VBox che contiene le informazioni del cuoco e calorie-durata
        VBox detailsBox = new VBox(cuocoBox, infoBox);
        detailsBox.setSpacing(5);

        // Creazione della struttura principale
        VBox titleAndDetailsBox = new VBox(titleLabel, detailsBox);
        titleAndDetailsBox.setSpacing(5);
        titleAndDetailsBox.setAlignment(Pos.CENTER_LEFT);

        // Creazione dell'icona del like interattiva
        ImageView likeIcon = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/like_icon_black.jpg"))));
        likeIcon.setFitHeight(24);
        likeIcon.setFitWidth(24);
        final boolean[] isLiked = {false}; // Stato del like

        // Impostare il comportamento interattivo del like
        likeIcon.setOnMouseClicked(event -> {
            if (isLiked[0]) {
                likeIcon.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/like_icon_black.jpg"))));
                isLiked[0] = false;
            } else {
                likeIcon.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/like_icon_red.png"))));
                isLiked[0] = true;
            }
        });

        // Impostazione della posizione dell'icona del like nell'angolo in alto a destra
        StackPane likePane = new StackPane(likeIcon);
        StackPane.setAlignment(likeIcon, Pos.TOP_RIGHT);

        // Creazione della struttura principale
        HBox mainContent = new HBox(imgBox, titleAndDetailsBox);
        mainContent.setSpacing(10);
        mainContent.setAlignment(Pos.CENTER_LEFT);

        // Impostazione dell'elemento grafico
        element.setCenter(mainContent);
        element.setRight(likePane);
        element.setPadding(new Insets(10));
        element.setStyle("-fx-border-color: gray; -fx-border-width: 1; -fx-background-color: white; -fx-border-radius: 10; -fx-background-radius: 10;");

        // Gestore di eventi per il click sull'elemento in base alla provenienza
        if (provenienzaScelta.equals("Da chef")) {
            element.setOnMouseClicked(event -> mostraDettagliRicetta(ricettaBean));
        } else {
            element.setOnMouseClicked(event -> {
                try {
                    // Ottieni il link della ricetta
                    String url = ricettaBean.getLinkRicetta();

                    // Verifica che il sistema supporti la navigazione su web
                    if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                        // Apri il link nel browser predefinito
                        Desktop.getDesktop().browse(new URI(url));
                    } else {
                        // Gestisci il caso in cui il sistema non supporti l'apertura di URL
                        Popup.mostraPopup(ERROR_MESSAGE_TITLE, "Navigazione web non supportata su questo sistema.", ERROR_POPUP_TYPE);
                    }
                } catch (IOException | URISyntaxException e) {
                    Popup.mostraPopup(ERROR_MESSAGE_TITLE, "Si è verificato un errore durante l'uso del link della ricetta.", ERROR_POPUP_TYPE);
                }
            });
        }
        return element;
    }

    // Metodo per mostrare la pagina dei dettagli della ricetta scelta
    private void mostraDettagliRicetta(BeanRicetta ricettaBean) {

        // Crea un nuovo Stage per il popup
        Stage popupStage = new Stage();
        popupStage.setTitle("Dettagli Ricetta");

        // Crea il contenuto del popup
        VBox popupContent = new VBox();
        popupContent.setSpacing(10);
        popupContent.setPadding(new Insets(20));
        popupContent.setStyle("-fx-background-color: #FFFFFF;");
        popupContent.setAlignment(Pos.CENTER);

        try {
            // Ottieni i dettagli completi della ricetta chiamando il controller applicativo
            BeanRicetta dettagliRicetta = ricette.ottieniDettagliRicetta(ricettaBean);

            // Creazione grafica di titolo, dettagli, immagine ricetta, cuoco, likes e descrizione
            VBox popupInitialContent = createPopupInitialContent(dettagliRicetta);

            // Creazione grafica della sezione dedicata agli ingredienti
            VBox popupIngredientiContent = createPopupIngredientiContent(dettagliRicetta);

            // Creazione grafica delle sezioni dedicate ai passaggi, link del video e bottone di conferma
            VBox popupOtherContent = createPopupFinalContent(dettagliRicetta);

            // Aggiungi tutti i contenuti creati al layout principale
            popupContent.getChildren().addAll(popupInitialContent, popupIngredientiContent, popupOtherContent);

            // Inserisci il contenuto nel ScrollPane
            ScrollPane localScrollPane = new ScrollPane(popupContent);
            localScrollPane.setFitToWidth(true);

            // Imposta la visualizzazione all'inizio
            localScrollPane.setVvalue(0);

            // Imposta il layout come scena del popup
            Scene popupScene = new Scene(localScrollPane, 310, 550);
            popupStage.setScene(popupScene);

            // Esecuzione codice passato per forzare lo scroll all'inizio dopo il rendering della GUI
            popupStage.show();
            Platform.runLater(() -> localScrollPane.setVvalue(0));

            // Mostra il popup
            popupStage.show();

        } catch (DAOException | SQLException e) {
            Popup.mostraPopup(ERROR_MESSAGE_TITLE, "Errore nel caricamento dei dettagli della ricetta.", ERROR_POPUP_TYPE);
        }
    }

    // Crea e restituisce il contenuto iniziale del popup per il metodo "mostraDettagliRicetta"
    private VBox createPopupInitialContent(BeanRicetta dettagliRicetta) throws SQLException {

        // Crea VBox per il titolo e dettagli
        VBox popupInitialContent = new VBox();
        popupInitialContent.setAlignment(Pos.CENTER);
        popupInitialContent.setSpacing(5);

        // Titolo della ricetta
        Label titolo = new Label(dettagliRicetta.getTitolo());
        titolo.setStyle("-fx-font-weight: bold; -fx-font-size: 22px;");
        titolo.setAlignment(Pos.CENTER);

        // Dettagli sopra l'immagine (categoria, durata, calorie)
        Label dettagli = new Label(dettagliRicetta.getCategoria() + " • " + dettagliRicetta.getDurataPreparazione() + " min • " + dettagliRicetta.getCalorie() + " Kcal");
        dettagli.setStyle("-fx-font-size: 15px; -fx-text-fill: #666666;");
        dettagli.setAlignment(Pos.CENTER);

        // Gestione grafica dell'immagine della ricetta
        ImageView immagineRicetta;
        if (dettagliRicetta.getImmagine() != null && dettagliRicetta.getImmagine().getBinaryStream() != null) {
            immagineRicetta = new ImageView(new Image(dettagliRicetta.getImmagine().getBinaryStream()));
            immagineRicetta.setFitWidth(250);
            immagineRicetta.setFitHeight(150);
        } else {
            immagineRicetta = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream(DEFAULT_IMAGE))));
            immagineRicetta.setFitWidth(140);
            immagineRicetta.setFitHeight(140);
        }
        immagineRicetta.setPreserveRatio(true);
        Rectangle clip = new Rectangle(immagineRicetta.getFitWidth(), immagineRicetta.getFitHeight());
        clip.setArcWidth(30);
        clip.setArcHeight(30);
        immagineRicetta.setClip(clip);

        // StackPane per cuoco e likes
        StackPane cuocoLikesPane = new StackPane();

        // Informazioni del cuoco
        ImageView cuocoIcon = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/cuoco_icon.jpg"))));
        cuocoIcon.setFitHeight(15);
        cuocoIcon.setFitWidth(15);
        Label cuoco = new Label(dettagliRicetta.getCuoco());
        cuoco.setStyle("-fx-text-fill: black; -fx-font-size: 15px;");
        HBox cuocoBox = new HBox(cuocoIcon, cuoco);
        cuocoBox.setAlignment(Pos.CENTER_LEFT);
        cuocoBox.setSpacing(5);

        // Informazioni dei likes
        Label likes = new Label("❤ " + dettagliRicetta.getLikes() + " Likes");
        likes.setStyle("-fx-font-size: 15px;");
        StackPane.setAlignment(likes, Pos.CENTER_RIGHT);

        // Aggiunta degli elementi al StackPane
        cuocoLikesPane.getChildren().addAll(cuocoBox, likes);

        // Descrizione della ricetta
        Label descrizione = new Label(dettagliRicetta.getDescrizione());
        descrizione.setStyle("-fx-font-size: 13px; -fx-text-fill: #666666; -fx-text-alignment: justify;");
        descrizione.setWrapText(true);

        // Aggiungi gli elementi iniziali al VBox
        popupInitialContent.getChildren().addAll(titolo, dettagli, immagineRicetta, cuocoLikesPane, descrizione);

        // Restituisci il contenuto delle informazioni iniziali
        return popupInitialContent;
    }

    /* Crea e gestisce solo la sezione dedicata agli ingredienti per il metodo "mostraDettagliRicetta"
    private VBox createPopupIngredientiContent(BeanRicetta dettagliRicetta) throws SQLException, DAOException {

        // Crea VBox per le altre informazioni
        VBox popupIngredientiContent = new VBox();
        popupIngredientiContent.setAlignment(Pos.CENTER_LEFT);
        popupIngredientiContent.setSpacing(10);

        // Recupero dal controller applicativo la lista di ingredienti validi per la ricetta scelta
        BeanIngredienti beanIngredienti = ricette.verificaQuantita(dettagliRicetta);

        // Gestione lista ingredienti
        Label ingredientiLabel = new Label("Ingredienti");
        ingredientiLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
        ingredientiLabel.setAlignment(Pos.CENTER_LEFT);
        VBox.setMargin(ingredientiLabel, new Insets(10, 0, 0, 0));
        GridPane ingredientiGrid = new GridPane();
        ingredientiGrid.setHgap(10);
        ingredientiGrid.setVgap(5);
        int column = 0;
        int row = 0;
        validIngredientCount = 0;
        int quantitaDisponibile = 0;

        // Controlla se l'ingrediente della ricetta è nella lista degli ingredienti validi
        for (Ingrediente ingrediente : dettagliRicetta.getIngredienti().getListaIngredienti()) {
            processIngredienteValido(dettagliRicetta, beanIngredienti, ingrediente, quantitaDisponibile, ingredientiGrid, row, column);
        }

        // Aggiungi le altre informazioni al VBox
        popupIngredientiContent.getChildren().addAll(ingredientiLabel, ingredientiGrid);

        // Restituisci le informazioni aggiuntive
        return popupIngredientiContent;
    }

    // Metodo interno
    private void processIngredienteValido(BeanRicetta dettagliRicetta, BeanIngredienti beanIngredienti, Ingrediente ingrediente, int quantitaDisponibile, GridPane ingredientiGrid, int row, int column) {

        // Dichiarazioni iniziali
        String tipoIngrediente = null;
        boolean ingredienteValido = false;
        Date currentDate = new Date();

        // Per ogni ingrediente valido
        for (BeanIngrediente beanIngrediente : beanIngredienti.getListIngredienti()) {
            if (beanIngrediente.getNome().equals(ingrediente.getNome()) && beanIngrediente.getQuantita() >= ingrediente.getQuantita() && beanIngrediente.getScadenza().after(currentDate)) {
                ingredienteValido = true;
                validIngredientCount++;
                break;
            } else {
                quantitaDisponibile = beanIngrediente.getQuantita();
            }
        }

        // Recupera il tipo di ingrediente
        if (ingrediente.getTipo().equals("cibo")) {
            tipoIngrediente = "g";
        } else if (ingrediente.getTipo().equals("drink")) {
            tipoIngrediente = "l";
        }

        // Crea la label per l'ingrediente
        Label ingredienteLabel = new Label(ingrediente.getNome() + " (" + ingrediente.getQuantita() + " " + tipoIngrediente + ")");
        ingredienteLabel.setStyle("-fx-font-size: 11.9px;");

        // Aggiungi un'icona di successo o di errore accanto all'ingrediente
        ImageView iconView;
        if (ingredienteValido) {
            iconView = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/success_icon.png"))));
        } else {
            iconView = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/error_icon.png"))));

            // Rendi l'icona cliccabile
            int finalQuantitaDisponibile = quantitaDisponibile;
            String finalTipoIngrediente = tipoIngrediente;
            iconView.setOnMouseClicked(event -> mostraAlertIngredienteNonValido(ingrediente, finalQuantitaDisponibile, finalTipoIngrediente));

            // Cambia il cursore quando si passa sopra l'icona per indicare che è cliccabile
            iconView.setCursor(Cursor.HAND);
        }

        // Imposta la dimensione dell'icona success/error
        iconView.setFitWidth(14);
        iconView.setFitHeight(14);

        // Aggiungi l'icona e la label all'ingrediente nella griglia
        HBox ingredienteBox = new HBox(5, iconView, ingredienteLabel);
        ingredientiGrid.add(ingredienteBox, column, row);

        // Gestione cambio colonne della griglia
        int dimListaIngredienti = dettagliRicetta.getIngredienti().getListaIngredienti().size();
        row++;
        if (dimListaIngredienti % 2 == 0) {
            // Se la lista degli ingredienti ha una dimensione pari
            if (row >= dimListaIngredienti / 2) {
                row = 0;
                column = 1;
            }
        } else {
            // Se la lista degli ingredienti ha una dimensione dispari
            if (row > dimListaIngredienti / 2) {
                row = 0;
                column = 1;
            }
        }
    }
    */

    private VBox createPopupIngredientiContent(BeanRicetta dettagliRicetta) throws SQLException, DAOException {

        // Crea VBox per le altre informazioni
        VBox popupIngredientiContent = new VBox();
        popupIngredientiContent.setAlignment(Pos.CENTER_LEFT);
        popupIngredientiContent.setSpacing(10);

        // Recupero dal controller applicativo la lista di ingredienti validi per la ricetta scelta
        BeanIngredienti beanIngredienti = ricette.verificaQuantita(dettagliRicetta);

        // Label ingredienti
        Label ingredientiLabel = new Label("Ingredienti");
        ingredientiLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
        ingredientiLabel.setAlignment(Pos.CENTER_LEFT);
        VBox.setMargin(ingredientiLabel, new Insets(10, 0, 0, 0));

        // Variabili di gestione della griglia degli ingredienti
        GridPane ingredientiGrid = new GridPane();
        ingredientiGrid.setHgap(10);
        ingredientiGrid.setVgap(5);
        String tipoIngrediente = null;
        int column = 0;
        int row = 0;

        // Iterazione su ogni oggetto ingrediente presente nella lista di ingredienti della ricetta
        for (Ingrediente ingrediente : dettagliRicetta.getIngredienti().getListaIngredienti()) {

            BeanIngrediente beanIngrediente = beanIngredienti.getListIngredienti().stream() // Conversione in stream che è una sequenza di elementi su cui si possono applicare operazioni come filtrare o trovare elementi
                    .filter(bi -> bi.getNome().equals(ingrediente.getNome()))               // Applicazione filtro che elimina tutti gli ingredienti con nome diverso da quello corrente
                    .findFirst().orElse(null);                                        // Trova il primo elemento che soddifa la condizione, altrimenti restituisci null

            // Recupera il tipo di ingrediente
            if (ingrediente.getTipo().equals("cibo")) {
                tipoIngrediente = "g";
            } else if (ingrediente.getTipo().equals("drink")) {
                tipoIngrediente = "l";
            }

            // Crea la label per l'ingrediente
            Label ingredienteLabel = new Label(ingrediente.getNome() + " (" + ingrediente.getQuantita() + " " + tipoIngrediente + ")");
            ingredienteLabel.setStyle("-fx-font-size: 11.9px;");

            // Aggiungi un'icona di successo o di errore accanto all'ingrediente
            ImageView iconView;
            if (beanIngrediente != null && beanIngrediente.getValidita()) {
                iconView = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/success_icon.png"))));
            } else {
                iconView = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/error_icon.png"))));
            }
            iconView.setFitWidth(14);
            iconView.setFitHeight(14);

            // Aggiungi l'icona e la label all'ingrediente nella griglia
            HBox ingredienteBox = new HBox(5, iconView, ingredienteLabel);
            ingredientiGrid.add(ingredienteBox, column, row);

            // Gestione cambio colonne della griglia
            int dimListaIngredienti = dettagliRicetta.getIngredienti().getListaIngredienti().size();
            row++;
            if (dimListaIngredienti % 2 == 0) {
                // Se la lista degli ingredienti ha una dimensione pari
                if (row >= dimListaIngredienti / 2) {
                    row = 0;
                    column = 1;
                }
            } else {
                // Se la lista degli ingredienti ha una dimensione dispari
                if (row > dimListaIngredienti / 2) {
                    row = 0;
                    column = 1;
                }
            }
        }

        // Aggiungi le altre informazioni al VBox
        popupIngredientiContent.getChildren().addAll(ingredientiLabel, ingredientiGrid);

        // Restituisci le informazioni aggiuntive
        return popupIngredientiContent;
    }

    // Crea e restituisce il contenuto aggiuntivo del popup per il metodo "mostraDettagliRicetta"
    private VBox createPopupFinalContent(BeanRicetta dettagliRicetta) throws DAOException {

        // Crea VBox per le altre informazioni
        VBox popupFinalContent = new VBox();
        popupFinalContent.setAlignment(Pos.CENTER_LEFT);
        popupFinalContent.setSpacing(10);

        // Passaggi della ricetta
        Label passaggiLabel = new Label("Passaggi");
        passaggiLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 13.9px;");
        VBox.setMargin(passaggiLabel, new Insets(10, 0, 0, 0));
        VBox passaggiBox = new VBox(10);
        String passaggiTesto = dettagliRicetta.getPassaggi();
        if (passaggiTesto == null || passaggiTesto.isEmpty()) {
            Label passaggiMessage = new Label("Non sono stati scritti i passaggi");
            passaggiBox.getChildren().add(passaggiMessage);
        } else {
            Label passaggi = new Label(dettagliRicetta.getPassaggi());
            passaggiBox.getChildren().add(passaggi);
        }

        // Video URL
        Label videoLabel = new Label("Video Tutorial");
        videoLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 13.8px;");
        VBox.setMargin(videoLabel, new Insets(10, 0, 0, 0));
        VBox linkVideoBox = new VBox(10);
        String linkVideo = dettagliRicetta.getVideoUrl();
        if (linkVideo == null || linkVideo.isEmpty()) {
            Label linkVideoMessage = new Label("Non è presente il link per il video");
            linkVideoBox.getChildren().add(linkVideoMessage);
        } else {
            Hyperlink videoLink = new Hyperlink(dettagliRicetta.getVideoUrl());
            videoLink.setStyle("-fx-font-size: 14px;");
            videoLink.setOnAction(e -> getHostServicesInstance().showDocument(dettagliRicetta.getVideoUrl()));
            linkVideoBox.getChildren().add(videoLink);
        }
        VBox.setMargin(linkVideoBox, new Insets(0, 0, 20, 0));

        // Pulsante per confermare l'uso della ricetta
        Button confirmButton = new Button("Usa ricetta");

        // Controlla se tutti gli ingredienti sono validi e se la lista non è vuota per abilitare o disabilitare il pulsante
        if (validIngredientCount == dettagliRicetta.getIngredienti().getListaIngredienti().size() && !dettagliRicetta.getIngredienti().getListaIngredienti().isEmpty()) {
            confirmButton.setDisable(false);  // Abilita il pulsante se tutti gli ingredienti sono validi
            confirmButton.setStyle("-fx-background-color: #0b5959; -fx-background-radius: 10; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 5px 10px;");
            confirmButton.setCursor(Cursor.HAND); // Cambia il cursore a "mano" se abilitato
        } else {
            confirmButton.setDisable(true);   // Disabilita il pulsante se non tutti gli ingredienti sono validi
            confirmButton.setStyle("-fx-background-color: #9e0606; -fx-background-radius: 10; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 5px 10px;");
            confirmButton.setCursor(Cursor.DEFAULT); // Cursore standard se disabilitato
        }

        // Invocazione del metodo per scalare le quantità degli ingredienti al click del bottone "usa ricetta"
        EventHandler<ActionEvent> confirmHandler = (confirmEvent) -> {
            try {
                ricette.usaRicetta(dettagliRicetta);
                Popup.mostraPopup("Successo", "La ricetta è stata usata con successo!", "success");
            } catch (DAOException | SQLException ex) {
                Popup.mostraPopup(ERROR_MESSAGE_TITLE, "Si è verificato un errore durante l'uso della ricetta.", ERROR_POPUP_TYPE);
            }
        };
        confirmButton.setOnAction(confirmHandler);

        // Allineamento bottone "usa ricetta"
        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().add(confirmButton);

        // Aggiungi le altre informazioni al VBox
        popupFinalContent.getChildren().addAll(passaggiLabel, passaggiBox, videoLabel, linkVideoBox, buttonBox);

        // Restituisci le informazioni aggiuntive
        return popupFinalContent;
    }

    // Gestione alert della motivazione della non validità dell'ingrediente
    private void mostraAlertIngredienteNonValido(Ingrediente ingrediente, int quantitaDisponibile, String tipoIngrediente) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Ingrediente non valido");
        alert.setHeaderText(null);

        String messaggio;
        if (ingrediente.getQuantita() <= quantitaDisponibile) {
            messaggio = "L'ingrediente \"" + ingrediente.getNome() + "\" non è valido perché:\n" +
                    "Quantità insufficiente: Richiesto " + ingrediente.getQuantita() + " " + tipoIngrediente + ", disponibile " + quantitaDisponibile + " " + tipoIngrediente + ".\n";
        } else {
            messaggio = "L'ingrediente \"" + ingrediente.getNome() + "\" non è valido perché è scaduto.";
        }

        alert.setContentText(messaggio);
        alert.showAndWait();
    }

    // Metodo per mostrare la homepage utente
    public void homePageUtente() throws IOException {
        FXMLLoader fxmlLoader;
        Stage stage = ApplicazioneStage.getStage();
        Scene scene;

        String fxmlFile = "/com/StandardGUI/utentiView.fxml";
        fxmlLoader = new FXMLLoader();
        Parent rootNode = fxmlLoader.load(getClass().getResourceAsStream(fxmlFile));
        scene = new Scene(rootNode, ScreenSize.getSceneWidth(), ScreenSize.getSceneHeight());

        stage.setTitle(APP_NAME);
        stage.setScene(scene);
        stage.show();
    }

    // Metodo per mostrare la pagina dei preferiti
    public void preferitiView() {
        Popup.mostraPopup("In costruzione", "Sezione non ancora implementata!", "construction");
    }
}