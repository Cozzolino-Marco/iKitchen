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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
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

        /*if (categoria != null) {
            // Debug per verificare se 'categoria' è inizializzato
            System.out.println("Categoria è correttamente inizializzata");
        } else {
            System.err.println("Errore: categoria è null");
        }

        if (elementContainer != null) {
            caricaRicette(Collections.emptyList());
        }

        if (categoriaListView != null) {
            categoriaListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    categoria.setText(newValue); // Imposta la categoria selezionata nella label
                }
            });
        } else {
            System.err.println("Errore: categoriaListView è null");
        }*/
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

    /*private void caricaRicette(List<BeanRicetta> listaRicette) {
        elementContainer.getChildren().clear();
        if (listaRicette != null && !listaRicette.isEmpty()) {
            for (BeanRicetta ricetta : listaRicette) {
                BorderPane element = createElement(ricetta);
                elementContainer.getChildren().add(element);
            }
        } else {
            Text noRecipesText = new Text("Nessuna ricetta trovata.");
            elementContainer.getChildren().add(noRecipesText);
        }
    }*/

    // Dai parametri, interagisce con controller e DAO per ottenere la lista di ricetta dal DB
    private void caricaRicette(String categoria, String provenienza, String filtraggio) throws DAOException, SQLException {

        // Crea un bean con le informazioni selezionate
        BeanRicette infoPerListaRicette = new BeanRicette(categoria, provenienza, filtraggio);

        // Inizializza il controller applicativo
        ricette = new OttieniRicettaControllerApplicativo();

        // Ottieni la lista delle ricette dal controller applicativo
        BeanRicette listaRicette = ricette.mostraRicette(infoPerListaRicette);

        for (BeanRicetta beanRicetta : listaRicette.getListRicette()) {
            BorderPane element = createElement(beanRicetta);
            elementContainer.getChildren().add(element);
        }

        // Passa la lista delle ricette al metodo per aggiornare l'interfaccia grafica
        //mostraRicette(listaRicette.getListRicette());
    }

    private BorderPane createElement(BeanRicetta ricettaBean) {
        BorderPane element = new BorderPane();

        /*Label titleLabel = new Label(ricettaBean.getTitolo());
        titleLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

        Label descriptionLabel = new Label(ricettaBean.getDescrizione());
        descriptionLabel.setWrapText(true);
        descriptionLabel.setStyle("-fx-font-size: 12px;");

        VBox infoBox = new VBox(titleLabel, descriptionLabel);
        infoBox.setSpacing(6);
         */

        Label descrizioneText = new Label(ricettaBean.getTitolo());
        descrizioneText.setWrapText(true);

        HBox descrizione = new HBox(descrizioneText);

        element.setCenter(descrizione);
        //element.setPadding(new Insets(10));
        element.setStyle("-fx-border-color: gray; -fx-border-width: 1; -fx-background-color: white;");

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
