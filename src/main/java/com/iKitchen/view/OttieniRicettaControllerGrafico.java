package com.iKitchen.view;

import com.iKitchen.controller.OttieniRicettaControllerApplicativo;
import com.iKitchen.exception.DAOException;
import com.iKitchen.model.bean.BeanRicetta;
import com.iKitchen.model.bean.BeanRicette;
import com.iKitchen.model.bean.CredentialsBean;
import com.iKitchen.model.domain.ApplicazioneStage;
import com.iKitchen.model.domain.Credentials;
import com.iKitchen.model.domain.Ingrediente;
import com.iKitchen.model.utility.Popup;
import com.iKitchen.model.utility.ScreenSize;
import javafx.event.EventHandler;
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
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import java.awt.Desktop;
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
    private Label titoloLabel;

    @FXML
    private VBox elementContainer;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private VBox categoriesContainer;

    // Variabili
    private OttieniRicettaControllerApplicativo ricette = null;
    private OttieniRicettaControllerApplicativo ricetta = null;
    private String categoriaScelta;
    private String provenienzaScelta;
    private String filtraggioScelta;

    // Configurazione iniziale degli elementi grafici
    public void initialize(String categoriaScelta, String provenienzaScelta, String filtraggioScelta) {
        if (elementContainer != null) {
            try {
                caricaRicette(categoriaScelta, provenienzaScelta, filtraggioScelta);
            } catch (DAOException | SQLException | IOException e) {
                throw new RuntimeException(e);
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

    // Dai parametri, interagisce con controller e DAO per ottenere la lista di ricette dal DB
    private void caricaRicette(String categoria, String provenienza, String filtraggio) throws DAOException, SQLException, IOException {

        // Crea un bean con le informazioni selezionate
        BeanRicette infoPerListaRicette = new BeanRicette(categoria, provenienza, filtraggio);

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
                BorderPane element = createElement(beanRicetta);
                elementContainer.getChildren().add(element);
            }
        }
    }

    // Metodo che mostra le ricette caricate a livello grafico
    @FXML
    protected void mostraRicette() throws IOException {

        if (provenienzaComboBox.getValue() == null || filtraggioComboBox.getValue() == null) {
            // Mostra un avviso se uno dei campi non è stato selezionato
            Popup.mostraPopup("Attenzione", "Si prega di selezionare entrambe le opzioni prima di procedere!", "error");

        } else if("Dal web".equals(provenienzaComboBox.getValue()) && "Filtrate in base alla dispensa".equals(filtraggioComboBox.getValue())) {

            // Mostra un popup con un messaggio specifico
            Popup.mostraPopup("Attenzione", "L'opzione 'Filtrate in base alla dispensa' non è disponibile con la provenienza 'Dal web'.", "error");

        } else {
            String categoriaScelta = this.categoriaScelta;

            FXMLLoader fxmlLoader = new FXMLLoader();
            Stage stage = ApplicazioneStage.getStage();
            Scene scene;

            String fxmlFile = "/com/iKitchen/elencoRicetteView.fxml";
            Parent rootNode = fxmlLoader.load(getClass().getResourceAsStream(fxmlFile));

            OttieniRicettaControllerGrafico controller = fxmlLoader.getController();
            controller.setCategoriaLabelTitle(categoriaScelta); // Setta la categoria selezionata al titolo
            controller.setCategoria(categoriaScelta); // Passa la categoria selezionata
            controller.setProvenienza(provenienzaComboBox.getValue().toString()); // Passa la provenienza selezionata
            controller.setFiltraggio(filtraggioComboBox.getValue().toString()); // Passa la filtraggio selezionata
            controller.initialize(categoriaScelta, provenienzaComboBox.getValue().toString(), filtraggioComboBox.getValue().toString());

            scene = new Scene(rootNode, ScreenSize.WIDTH_GUI1, ScreenSize.HEIGHT_GUI1);

            stage.setTitle("iKitchen");
            stage.setScene(scene);
            stage.show();
        }
    }

    // Gestione grafica di un elemento ricetta
    private BorderPane createElement(BeanRicetta ricettaBean) {
        BorderPane element = new BorderPane();

        // Creazione dell'immagine del piatto
        HBox imgBox;
        ImageView imageView;
        try {
            Image image;
            if (ricettaBean.getImmagine() != null && ricettaBean.getImmagine().getBinaryStream() != null) {
                InputStream inputStream = ricettaBean.getImmagine().getBinaryStream();
                image = new Image(inputStream, 100, 100, true, true); // Imposta dimensioni fisse e preserva il rapporto
            } else {
                image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/default_image.png")));
            }
            imageView = createStyledImageView(image);
        } catch (SQLException e) {
            Image defaultImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/default_image.png")));
            imageView = createStyledImageView(defaultImage);
        }
        imgBox = new HBox(imageView);
        element.setLeft(imgBox);

        // Creazione del titolo della ricetta
        Label titleLabel = new Label(ricettaBean.getTitolo());
        titleLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

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
        durataLabel.setStyle("-fx-font-size: 12px;");

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
            System.out.println("Like cliccato per: " + ricettaBean.getTitolo());
        });

        // Impostazione della posizione dell'icona del like nell'angolo in alto a destra
        StackPane likePane = new StackPane(likeIcon);
        StackPane.setAlignment(likeIcon, Pos.TOP_RIGHT);

        // Creazione della struttura principale
        HBox mainContent = new HBox(imgBox, titleAndDetailsBox);
        mainContent.setSpacing(10);
        mainContent.setAlignment(Pos.CENTER_LEFT); // Allinea tutto a sinistra

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
                        System.out.println("Navigazione web non supportata su questo sistema.");
                    }
                } catch (IOException | URISyntaxException e) {
                    e.printStackTrace();
                }
            });
        }
        
        return element;
    }

    // Metodo per creare e stilizzare un ImageView
    private ImageView createStyledImageView(Image image) {
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(70);
        imageView.setFitWidth(65);

        // Creazione di un Rectangle per angoli arrotondati delle immagini
        Rectangle clip = new Rectangle(65, 70);
        clip.setArcWidth(15);
        clip.setArcHeight(15);
        imageView.setClip(clip);

        return imageView;
    }

    // Metodo per mostrare la pagina dei dettagli della ricetta scelta
    private void mostraDettagliRicetta(BeanRicetta ricettaBean) {
        // Crea un nuovo Stage per il popup
        Stage popupStage = new Stage();
        popupStage.setTitle("Dettagli Ricetta");

        // Crea il contenuto del popup
        VBox popupContent = new VBox();
        popupContent.setSpacing(10);
        popupContent.setPadding(new Insets(10));

        // Inizializza il controller applicativo per ottenere i dettagli della ricetta
        ricetta = new OttieniRicettaControllerApplicativo();

        try {
            // Ottieni i dettagli completi della ricetta
            BeanRicetta dettagliRicetta = ricetta.ottieniDettagliRicetta(ricettaBean);

            // Primi dettagli della ricetta al popup
            Label titolo = new Label(dettagliRicetta.getTitolo());
            titolo.setStyle("-fx-font-weight: bold; -fx-font-size: 18px;");
            Label descrizione = new Label("Descrizione: " + dettagliRicetta.getDescrizione());
            Label cuoco = new Label("Cuoco: " + dettagliRicetta.getCuoco());
            Label calorie = new Label("Calorie: " + dettagliRicetta.getCalorie() + " Kcal");
            Label durata = new Label("Durata: " + dettagliRicetta.getDurataPreparazione() + " min");

            // Ciclo for per ottenere la lista di ingredienti
            StringBuilder ingredientiStringa = new StringBuilder("Ingredienti:\n");
            for (Ingrediente ingrediente : dettagliRicetta.getIngredienti().getListaIngredienti()) {
                ingredientiStringa.append("- ")
                        .append(ingrediente.getNome())
                        .append(", scadenza: ").append(ingrediente.getScadenza())
                        .append(", quantità: ").append(ingrediente.getQuantita())
                        .append(", limite: ").append(ingrediente.getLimite()).append("\n");
            }
            Label ingredienti = new Label(ingredientiStringa.toString());

            // Ultimi dettagli della ricetta al popup
            Label passaggi = new Label("Passaggi: " + dettagliRicetta.getPassaggi());
            Label videoUrl = new Label("Video: " + dettagliRicetta.getVideoUrl());
            Label likes = new Label("Likes: " + dettagliRicetta.getLikes());

            // Pulsante per confermare l'uso della ricetta
            CredentialsBean usernameBean = new CredentialsBean(Credentials.getUsername());
            Button confirmButton = new Button("Usa ricetta");
            EventHandler confirmHandler = (confirmEvent) -> {
                try {
                    ricetta.usaRicetta(usernameBean, dettagliRicetta);
                    Popup.mostraPopup("Successo", "La ricetta è stata usata con successo!", "success");
                } catch (DAOException | SQLException e) {
                    Popup.mostraPopup("Errore", "Si è verificato un errore durante l'uso della ricetta.", "error");
                }
            };
            confirmButton.setOnAction(confirmHandler);

            // Aggiungi gli elementi al layout
            popupContent.getChildren().addAll(titolo, descrizione, cuoco, calorie, durata, ingredienti, passaggi, videoUrl, likes, confirmButton);

            // Imposta il layout come scena del popup
            Scene popupScene = new Scene(popupContent, ScreenSize.POPUP_WIDTH_GUI1, ScreenSize.POPUP_HEIGHT_GUI1);
            popupStage.setScene(popupScene);

            // Mostra il popup
            popupStage.show();

        } catch (DAOException | SQLException e) {
            e.printStackTrace();
            popupContent.getChildren().add(new Label("Errore nel caricamento dei dettagli della ricetta"));
            Scene popupScene = new Scene(popupContent, ScreenSize.POPUP_WIDTH_GUI1, ScreenSize.POPUP_HEIGHT_GUI1);
            popupStage.setScene(popupScene);
            popupStage.show();
        }
    }

    // Metodo per mostrare la homepage utente
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

    // Metodo per mostrare la pagina dei filtri
    @FXML
    public void filtriView() throws IOException {

        // Ottieni la categoria selezionata dall'utente
        //String categoriaScelta = categoriesListView.getSelectionModel().getSelectedItem();

        // Controlla se l'utente ha selezionato una categoria
        if (categoriaScelta == null) {
            // Mostra un popup di errore se la categoria non è stata selezionata
            Popup.mostraPopup("Attenzione", "Prima di andare avanti, seleziona per favore la categoria della ricetta!", "error");
        } else {
            // Se una categoria è stata selezionata, procedi con il caricamento della nuova scena
            FXMLLoader fxmlLoader = new FXMLLoader();
            Stage stage = ApplicazioneStage.getStage();
            Scene scene;

            String fxmlFile = "/com/iKitchen/filtriView.fxml";
            Parent rootNode = fxmlLoader.load(getClass().getResourceAsStream(fxmlFile));

            OttieniRicettaControllerGrafico controller = fxmlLoader.getController();
            controller.setCategoria(categoriaScelta); // Passa la categoria selezionata

            scene = new Scene(rootNode, ScreenSize.WIDTH_GUI1, ScreenSize.HEIGHT_GUI1);

            stage.setTitle("iKitchen");
            stage.setScene(scene);
            stage.show();
        }
    }

    // Metodo per mostrare la pagina della scelta delle categorie
    public void categorieView() throws IOException {

        FXMLLoader fxmlLoader;
        Stage stage = ApplicazioneStage.getStage();
        Scene scene;

        String fxmlFile = "/com/iKitchen/categorieView.fxml";
        fxmlLoader = new FXMLLoader();
        Parent rootNode = fxmlLoader.load(getClass().getResourceAsStream(fxmlFile));

        OttieniRicettaControllerGrafico controller = fxmlLoader.getController();
        controller.initialize("", "", "");

        scene = new Scene(rootNode, ScreenSize.WIDTH_GUI1, ScreenSize.HEIGHT_GUI1);

        stage.setTitle("iKitchen");
        stage.setScene(scene);
        stage.show();
    }

    // Metodo per aggiornare dinamicamente i bottoni delle categorie
    @FXML
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

    // Metodo per mostrare la pagina dei preferiti
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

    public Label getLabelTitolo() {
        return this.titoloLabel;
    }
}