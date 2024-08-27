package com.iKitchen.view;

import com.iKitchen.controller.OttieniRicettaControllerApplicativo;
import com.iKitchen.exception.DAOException;
import com.iKitchen.model.bean.BeanRicetta;
import com.iKitchen.model.bean.BeanRicette;
import com.iKitchen.model.domain.ApplicazioneStage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import static com.iKitchen.model.domain.ScreenSize.HEIGHT_GUI1;
import static com.iKitchen.model.domain.ScreenSize.WIDTH_GUI1;

public class OttieniRicettaControllerGrafico {

    // Elementi grafici
    @FXML
    private Label categoria;

    @FXML
    private ListView<String> categoriaListView;

    @FXML
    private ComboBox provenienzaComboBox;

    @FXML
    private ComboBox filtraggioComboBox;

    @FXML
    private VBox elementContainer; // Contenitore per gli elementi delle ricette

    // Variabili
    private OttieniRicettaControllerApplicativo ricette = null;
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

        scene = new Scene(rootNode, WIDTH_GUI1, HEIGHT_GUI1);

        stage.setTitle("iKitchen");
        stage.setScene(scene);
        stage.show();
    }

    // Metodo che mostra le ricette caricate
    @FXML
    protected void mostraRicette() throws DAOException, SQLException, IOException {

        String categoriaScelta = this.categoriaScelta;

        FXMLLoader fxmlLoader = new FXMLLoader();
        Stage stage = ApplicazioneStage.getStage();
        Scene scene;

        String fxmlFile = "/com/iKitchen/elencoRicetteView.fxml";
        Parent rootNode = fxmlLoader.load(getClass().getResourceAsStream(fxmlFile));

        OttieniRicettaControllerGrafico controller = fxmlLoader.getController();
        controller.setCategoria(categoriaScelta); // Passa la categoria selezionata
        controller.setProvenienza(provenienzaComboBox.getValue().toString()); // Passa la provenienza selezionata
        controller.setFiltraggio(filtraggioComboBox.getValue().toString()); // Passa la filtraggio selezionata
        controller.initialize(categoriaScelta, provenienzaComboBox.getValue().toString(), filtraggioComboBox.getValue().toString());

        scene = new Scene(rootNode, WIDTH_GUI1, HEIGHT_GUI1);

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

    // Gestione grafica di un elemento ricetta
    private BorderPane createElement(BeanRicetta ricettaBean) {
        BorderPane element = new BorderPane();

        // Creazione dell'immagine del piatto
        HBox img = new HBox();
        if (ricettaBean.getImmagine() != null) {
            try {
                InputStream inputStream = ricettaBean.getImmagine().getBinaryStream();
                if (inputStream != null) {  // Controllo per evitare il NullPointerException
                    Image image = new Image(inputStream);
                    ImageView imageView = new ImageView(image);
                    imageView.setFitHeight(60);
                    imageView.setFitWidth(60);
                    imageView.setPreserveRatio(true);
                    img = new HBox(imageView);
                } else {
                    img = new HBox(new Text("Immagine non presente"));
                }
            } catch (SQLException e) {
                img = new HBox(new Text("Immagine non presente"));
            }
        } else {
            img = new HBox(new Text("Immagine non presente"));
        }

        // Creazione del titolo della ricetta
        Label titleLabel = new Label(ricettaBean.getTitolo());
        titleLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

        // Creazione dell'icona del cuoco e del nome del cuoco
        ImageView cuocoIcon = new ImageView(new Image(getClass().getResourceAsStream("/cuoco_icon.jpg")));
        cuocoIcon.setFitHeight(16);
        cuocoIcon.setFitWidth(16);
        Label cuocoLabel = new Label(ricettaBean.getCuoco());
        HBox cuocoBox = new HBox(cuocoIcon, cuocoLabel);
        cuocoBox.setSpacing(5);

        // Creazione dell'icona per le calorie
        ImageView calorieIcon = new ImageView(new Image(getClass().getResourceAsStream("/calorie_icon.png")));
        calorieIcon.setFitHeight(16);
        calorieIcon.setFitWidth(16);
        Label calorieLabel = new Label(ricettaBean.getCalorie() + " Kcal");

        // Creazione dell'icona per durata della preparazione
        ImageView durataIcon = new ImageView(new Image(getClass().getResourceAsStream("/duration_icon.png")));
        durataIcon.setFitHeight(16);
        durataIcon.setFitWidth(16);
        Label durataLabel = new Label(ricettaBean.getDurataPreparazione() + " min");

        HBox infoBox = new HBox(calorieIcon, calorieLabel, durataIcon, durataLabel);
        infoBox.setSpacing(10);

        // VBox che contiene le informazioni del cuoco e durata
        VBox detailsBox = new VBox(cuocoBox, infoBox);
        detailsBox.setSpacing(5);

        // Creazione dell'icona del like interattiva
        ImageView likeIcon = new ImageView(new Image(getClass().getResourceAsStream("/like_icon.jpg")));
        likeIcon.setFitHeight(24);
        likeIcon.setFitWidth(24);
        // Impostare il comportamento interattivo del like
        likeIcon.setOnMouseClicked(event -> {
            // Aggiungi il comportamento quando si clicca l'icona del like
            System.out.println("Like cliccato per: " + ricettaBean.getTitolo());
        });

        // Impostazione della posizione dell'icona del like nell'angolo in alto a destra
        StackPane likePane = new StackPane(likeIcon);
        StackPane.setAlignment(likeIcon, Pos.TOP_RIGHT);

        // Creazione della struttura principale
        HBox mainContent = new HBox(img, detailsBox);
        mainContent.setSpacing(10);

        // Impostazione dell'elemento grafico
        element.setTop(titleLabel);
        element.setCenter(mainContent);
        element.setRight(likePane);
        element.setPadding(new Insets(10));
        element.setStyle("-fx-border-color: gray; -fx-border-width: 1; -fx-background-color: white; -fx-border-radius: 10; -fx-background-radius: 10;");

        return element;
    }

    public void homePageUtente() throws IOException {
        FXMLLoader fxmlLoader;
        Stage stage = ApplicazioneStage.getStage();
        Scene scene;

        String fxmlFile = "/com/iKitchen/utentiView.fxml";
        fxmlLoader = new FXMLLoader();
        Parent rootNode = fxmlLoader.load(getClass().getResourceAsStream(fxmlFile));
        scene = new Scene(rootNode, WIDTH_GUI1, HEIGHT_GUI1);

        stage.setTitle("iKitchen");
        stage.setScene(scene);
        stage.show();
    }

    public void categorieView() throws IOException {

        /* Aggiorna la variabile categoriaSelezionata con la categoria attualmente selezionata
        this.categoriaSelezionata = this.categoria.getText();*/

        FXMLLoader fxmlLoader;
        Stage stage = ApplicazioneStage.getStage();
        Scene scene;

        String fxmlFile = "/com/iKitchen/categorieView.fxml";
        fxmlLoader = new FXMLLoader();
        Parent rootNode = fxmlLoader.load(getClass().getResourceAsStream(fxmlFile));
        scene = new Scene(rootNode, WIDTH_GUI1, HEIGHT_GUI1);

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
        scene = new Scene(rootNode, WIDTH_GUI1, HEIGHT_GUI1);

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
        scene = new Scene(rootNode, WIDTH_GUI1, HEIGHT_GUI1);

        stage.setTitle("iKitchen");
        stage.setScene(scene);
        stage.show();
    }
}
