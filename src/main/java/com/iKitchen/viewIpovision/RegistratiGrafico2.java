package com.iKitchen.viewIpovision;

import com.iKitchen.controller.RegistratiController;
import com.iKitchen.exception.DAOException;
import com.iKitchen.model.bean.CredentialsBean;
import com.iKitchen.model.domain.ApplicazioneStage;
import com.iKitchen.model.domain.Role;
import com.iKitchen.model.utility.Popup;
import com.iKitchen.model.utility.ScreenSize;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.SQLException;

public class RegistratiGrafico2 {

    @FXML
    private VBox mainContainer;

    @FXML
    private TextField textFieldNome;

    @FXML
    private TextField textFieldCognome;

    @FXML
    private ComboBox<Role> comboBoxRuolo;

    @FXML
    private TextField textFieldUsername;

    @FXML
    private PasswordField textFieldPassword;

    @FXML
    private PasswordField textFieldRipetiPassword;

    public void initialize() {
        // Configura la ComboBox con i valori dell'enum Role
        comboBoxRuolo.setItems(FXCollections.observableArrayList(Role.values()));
    }

    // Caricamento del file FXML della registrazione
    public void registratiView() throws IOException {
        FXMLLoader fxmlLoader;
        Stage stage = ApplicazioneStage.getStage();
        Scene scene;

        String fxmlFile = "/com/iKitchen/registratiView.fxml";
        fxmlLoader = new FXMLLoader();
        Parent rootNode = fxmlLoader.load(getClass().getResourceAsStream(fxmlFile));

        scene = new Scene(rootNode, ScreenSize.WIDTH_GUI1, ScreenSize.HEIGHT_GUI1);

        stage.setTitle("iKitchen");
        stage.setScene(scene);
        stage.show();
    }

    // Chiamata al controller del login
    public void loginView() throws IOException {
        LoginGrafico2 loginGrafico = new LoginGrafico2();
        loginGrafico.loginView();
    }

    // Metodo per effettuare la registrazione chiamato dalla view
    @FXML
    public void confermaRegistrazione() {

        // Prendo i dati dai campi di testo della view registrazione
        String nome = textFieldNome.getText();
        String cognome = textFieldCognome.getText();
        Role ruolo = comboBoxRuolo.getValue();
        String username = textFieldUsername.getText();
        String password = textFieldPassword.getText();
        String ripetiPassword = textFieldRipetiPassword.getText();

        // Mostra un avviso se anche uno dei campi non è stato selezionato
        if (nome.isEmpty() || cognome.isEmpty() || ruolo == null || username.isEmpty() || password.isEmpty() || ripetiPassword.isEmpty()) {
            Popup.mostraPopup("Attenzione", "Si prega di selezionare tutte le opzioni prima di procedere!", "warning");
            return;
        }

        // Creazione di un oggetto bean e riempimento con i dati acquisiti
        CredentialsBean beanRegistrazione = new CredentialsBean();
        beanRegistrazione.setNome(nome);
        beanRegistrazione.setCognome(cognome);
        beanRegistrazione.setRole(ruolo);
        beanRegistrazione.setUsername(username);
        beanRegistrazione.setPassword(password);
        beanRegistrazione.setRipetiPassword(ripetiPassword);

        // Chiama il controller applicativo per effettuare la registrazione
        RegistratiController controllerRegistrati = new RegistratiController();

        // Mostra il popup in base all'esito della query
        try {
            controllerRegistrati.effettuaRegistrazione();
            LoginGrafico2 loginGrafico = new LoginGrafico2();
            loginGrafico.loginView();
            Popup.mostraPopup("Successo", "Ti sei registrato con successo!", "success");
        } catch (IllegalArgumentException e) {
            Popup.mostraPopup("Attenzione", "Password non coincidenti!", "warning");
        } catch (DAOException | SQLException ex) {
            Popup.mostraPopup("Errore", "Si è verificato un errore durante la registrazione.", "error");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}