package com.ikitchen.view_ipovision;

import com.ikitchen.controller.RegistratiController;
import com.ikitchen.exception.DAOException;
import com.ikitchen.model.bean.CredentialsBean;
import com.ikitchen.model.domain.ApplicazioneStage;
import com.ikitchen.model.domain.Role;
import com.ikitchen.model.utility.Popup;
import com.ikitchen.model.utility.ScreenSize;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.SQLException;

public class RegistratiGrafico2 {

    @FXML
    private PasswordField textFieldPassword;

    @FXML
    private PasswordField textFieldRipetiPassword;

    @FXML
    private ComboBox<Role> comboBoxRuolo;

    @FXML
    private TextField textFieldUsername;

    @FXML
    private TextField textFieldNome;

    @FXML
    private TextField textFieldCognome;

    public void initialize() {
        // Configura la ComboBox con i valori dell'enum Role
        comboBoxRuolo.setItems(FXCollections.observableArrayList(Role.values()));
    }

    @FXML // Metodo per effettuare la registrazione chiamato dalla view
    public void confermaRegistrazione() {

        // Prendo i dati dai campi di testo della view registrazione
        String nome = textFieldNome.getText();
        String cognome = textFieldCognome.getText();
        Role ruolo = comboBoxRuolo.getValue();
        String username = textFieldUsername.getText();
        String password = textFieldPassword.getText();
        String ripetiPassword = textFieldRipetiPassword.getText();

        // Mostra un avviso se anche uno dei campi non è stato selezionato
        if (username.isEmpty() || password.isEmpty() || ripetiPassword.isEmpty() || nome.isEmpty() || cognome.isEmpty() || ruolo == null) {
            Popup.mostraPopup("Attenzione", "Si prega di selezionare tutte le opzioni prima di procedere!", "warning");
            return;
        }

        // Creazione di un oggetto bean e riempimento con i dati acquisiti
        CredentialsBean beanRegister = new CredentialsBean();
        beanRegister.setNome(nome);
        beanRegister.setCognome(cognome);
        beanRegister.setRole(ruolo);
        beanRegister.setPassword(password);
        beanRegister.setRipetiPassword(ripetiPassword);

        // Variabile che indica se l'email è valida
        boolean validEmail = true;

        // Gestione eccezione per validità email
        try {
            beanRegister.setUsername(username);
        } catch (IllegalArgumentException e) {
            Popup.mostraPopup("Errore", "L'email fornita non è valida!", "error");
            validEmail = false;  // L'email non è valida, quindi si ferma il flusso
        }

        // Continua solo se l'email è valida
        if (validEmail) {

            // Chiama il controller applicativo per effettuare la registrazione
            RegistratiController controllerRegistrati = new RegistratiController();

            // Mostra il popup in base all'esito della query
            try {
                controllerRegistrati.effettuaRegistrazione(beanRegister);

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

                // Messaggio di successo
                Popup.mostraPopup("Successo", "Ti sei registrato con successo!", "success");

            } catch (IllegalArgumentException e) {

                // Messaggio di warning
                Popup.mostraPopup("Attenzione", "Password non coincidenti!", "warning");

            } catch (DAOException | SQLException | IOException e) {

                // Messaggio di errore
                Popup.mostraPopup("Errore", "Si è verificato un errore durante la registrazione.", "error");
            }
        }
    }

    // Chiamata al controller del login
    public void loginView() throws IOException {

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