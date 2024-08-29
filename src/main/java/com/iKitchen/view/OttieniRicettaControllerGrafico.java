package com.iKitchen.view;

import com.iKitchen.controller.OttieniRicettaControllerApplicativo;
import com.iKitchen.exception.DAOException;
import com.iKitchen.model.bean.BeanRicetta;
import com.iKitchen.model.bean.BeanRicette;
import com.iKitchen.model.bean.CredentialsBean;
import com.iKitchen.model.domain.ApplicazioneStage;
import com.iKitchen.model.domain.Credentials;
import com.iKitchen.model.domain.Ingrediente;
import com.iKitchen.model.domain.ScreenSize;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

public class OttieniRicettaControllerGrafico {

    // Elementi grafici
    @FXML
    private Label categoriaLabelTitle;

    @FXML
    private ListView<String> categoriaListView;

    @FXML
    private ComboBox provenienzaComboBox;

    @FXML
    private ComboBox filtraggioComboBox;

    @FXML
    private Label titoloLabel;

    @FXML
    private VBox elementContainer; // Contenitore per gli elementi delle ricette

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
            } catch (DAOException e) {
                throw new RuntimeException(e);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
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

    @FXML
    public void filtriView() throws IOException {

        String categoriaScelta = categoriaListView.getSelectionModel().getSelectedItem();

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

    // Dai parametri, interagisce con controller e DAO per ottenere la lista di ricette dal DB
    private void caricaRicette(String categoria, String provenienza, String filtraggio) throws DAOException, SQLException {

        // Crea un bean con le informazioni selezionate
        BeanRicette infoPerListaRicette = new BeanRicette(categoria, provenienza, filtraggio);

        // Inizializza il controller applicativo
        ricette = new OttieniRicettaControllerApplicativo();

        // Ottieni la lista delle ricette dal controller applicativo
        BeanRicette listaRicette = ricette.mostraRicette(infoPerListaRicette);

        // Ciclo che itera la creazione di un elemento ricetta per la lista ricette
        for (BeanRicetta beanRicetta : listaRicette.getListRicette()) {
            BorderPane element = createElement(beanRicetta);
            elementContainer.getChildren().add(element);
        }
    }

    // Metodo che mostra le ricette caricate a livello grafico
    @FXML
    protected void mostraRicette() throws DAOException, SQLException, IOException {

        String categoriaScelta = this.categoriaScelta;

        FXMLLoader fxmlLoader = new FXMLLoader();
        Stage stage = ApplicazioneStage.getStage();
        Scene scene;

        String fxmlFile = "/com/iKitchen/elencoRicetteView.fxml";
        Parent rootNode = fxmlLoader.load(getClass().getResourceAsStream(fxmlFile));

        OttieniRicettaControllerGrafico controller = fxmlLoader.getController();
        controller.setCategoriaLabelTitle(categoriaScelta); // Setta la categoria selezionata
        controller.setCategoria(categoriaScelta); // Passa la categoria selezionata
        controller.setProvenienza(provenienzaComboBox.getValue().toString()); // Passa la provenienza selezionata
        controller.setFiltraggio(filtraggioComboBox.getValue().toString()); // Passa la filtraggio selezionata
        controller.initialize(categoriaScelta, provenienzaComboBox.getValue().toString(), filtraggioComboBox.getValue().toString());

        scene = new Scene(rootNode, ScreenSize.WIDTH_GUI1, ScreenSize.HEIGHT_GUI1);

        stage.setTitle("iKitchen");
        stage.setScene(scene);
        stage.show();
    }

    // Gestione grafica di un elemento ricetta
    private BorderPane createElement(BeanRicetta ricettaBean) {
        BorderPane element = new BorderPane();

        // Creazione dell'immagine del piatto
        HBox imgBox;
        if (ricettaBean.getImmagine() != null) {
            try {
                InputStream inputStream = ricettaBean.getImmagine().getBinaryStream();
                if (inputStream != null) {
                    Image image = new Image(inputStream, 100, 100, true, true); // Imposta dimensioni fisse e preserva il rapporto
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
        Label calorieLabel = new Label(ricettaBean.getCalorie() + " Kcal");
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

        // Gestore di eventi per il click sull'elemento
        element.setOnMouseClicked(event -> mostraDettagliRicetta(ricettaBean));
        
        return element;
    }

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
            //final HBox[] msg1 = new HBox[1];
            //AtomicReference<HBox> msg2 = null;
            CredentialsBean usernameBean = new CredentialsBean(Credentials.getUsername());
            Button confirmButton = new Button("Usa ricetta");
            EventHandler confirmHandler = (confirmEvent) -> {
                try {
                    boolean result = ricetta.usaRicetta(usernameBean, dettagliRicetta);
                    System.out.println(result);
                    //msg1[0] = new HBox(new Text("La ricetta è stata usata con successo!"));
                    //Utils.showNotify("La ricetta è stata usata con successo!");
                } catch (DAOException | SQLException e) {
                    //msg2.set(new HBox(new Text("Errore durante l'uso della ricetta.")));
                    //Utils.showErrorPopup("Errore durante l'uso della ricetta.");
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

    public Label getLabelTitolo() {
        return this.titoloLabel;
    }
}