package com.ikitchen.view_ipovision;

import com.ikitchen.model.domain.ApplicazioneStage;
import com.ikitchen.model.utility.Credentials;
import com.ikitchen.model.utility.Popup;
import com.ikitchen.model.utility.ScreenSize;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.LoadException;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import com.ikitchen.controller.LoginController;
import com.ikitchen.exception.DAOException;
import com.ikitchen.model.bean.CredentialsBean;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.SQLException;

public class LoginGrafico2 {

    // Dichiarazioni costanti
    private static final String ERROR_POPUP_TYPE = "error";

    @FXML
    private TextField textFieldUsername;

    @FXML
    private PasswordField textFieldPassword;

    // Chiamata al controller della registrazione
    public void registratiView() throws IOException {
        // Carica il file FXML per la vista del login
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/IpovisionGUI/registratiView2.fxml"));
        Parent root = fxmlLoader.load();

        // Ottieni lo stage attuale dalla classe ApplicazioneStage
        Stage stage = ApplicazioneStage.getStage();

        // Imposta la nuova scena con il layout caricato
        Scene scene = new Scene(root, ScreenSize.getSceneWidth(), ScreenSize.getSceneHeight());

        // Cambia la scena dello stage
        stage.setScene(scene);
        stage.show();
    }

    @FXML // Acquisizione credienziali e passaggio al controller del login
    protected void onLoginButtonClick() throws IOException {

        // Gestione eccezione per validità email
        CredentialsBean credentialsBean = null;
        try {
            credentialsBean = new CredentialsBean(textFieldUsername.getText(), textFieldPassword.getText());
        } catch (IllegalArgumentException e) {
            Popup.mostraPopup("Errore email", "L'email fornita non è valida!", ERROR_POPUP_TYPE);
            loginView();
        }

        // Verifica se credB è stato istanziato correttamente
        if (credentialsBean != null) {
            LoginController loginController = new LoginController();

            // Chiamata al login controller per effettuare il login
            try {
                loginController.start(credentialsBean);

                // Recupera il nome associato allo username
                loginController.recuperaNome(credentialsBean);

                // Controlla il ruolo dell'utente e carica la view appropriata
                if (Credentials.getRole() != null) {
                    cambiaViewDopoLogin();
                }

            } catch (LoadException e) {

                // Warning
                Popup.mostraPopup("Caricamento non riuscito", "Impossibile caricamento della pagina!", ERROR_POPUP_TYPE);
                loginView();

            } catch (DAOException | SQLException | IOException e) {

                // Errore
                Popup.mostraPopup("Errore", "Hai sbagliato username o password, per favore ricontrolla!", ERROR_POPUP_TYPE);
                loginView();
            }
        }
    }

    @FXML // Metodo per il login
    public void loginView() throws IOException {

        // Carica file FXML per la vista del login
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/IpovisionGUI/login2.fxml"));
        Parent root = fxmlLoader.load();

        // Ottieni stage attuale dalla classe ApplicazioneStage
        Stage stage = ApplicazioneStage.getStage();

        // Imposta la nuova scena con il layout appena caricato
        Scene scene = new Scene(root, ScreenSize.getSceneWidth(), ScreenSize.getSceneHeight());

        // Cambia la scena dello stage
        stage.setScene(scene);
        stage.show();
    }

    // Metodo che gestisce il caricamento della view in base al ruolo
    private void cambiaViewDopoLogin() throws IOException, DAOException, SQLException {

        String fxmlFile;
        Stage stage = ApplicazioneStage.getStage();
        FXMLLoader fxmlLoader = new FXMLLoader();

        if (Credentials.getRole().getId() == 1) {
            fxmlFile = "/com/IpovisionGUI/utentiView2.fxml"; // View per utenti domestici
        } else {
            fxmlFile = "/com/IpovisionGUI/chefView2.fxml"; // View per chef
        }

        // Carica l'FXML
        fxmlLoader.setLocation(getClass().getResource(fxmlFile));
        Parent rootNode = fxmlLoader.load();

        // Ottieni il controller dall'FXMLLoader
        UtenteControllerGrafico2 controller = fxmlLoader.getController();

        // Chiama l'inizializzatore del controller grafico di utente
        controller.initialize();

        // Imposta la scena
        Scene scene = new Scene(rootNode, ScreenSize.getSceneWidth(), ScreenSize.getSceneHeight());
        stage.setTitle("iKitchen");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    // Metodo per il cambio della grafica
    public void cambiaGrafica()throws IOException {

        // Invoca il metodo per il cambio grafica
        ScreenSize.changeGUI();

        // Settaggi scena
        FXMLLoader fxmlLoader;
        Stage newStage = ApplicazioneStage.getStage();
        Scene scene;
        String fxmlFile;

        // Caricamento del login in base alla GUI
        if (ScreenSize.getGUI() == 0){
            fxmlFile = "/com/StandardGUI/login.fxml";
        } else {
            fxmlFile = "/com/IpovisionGUI/login2.fxml";
        }

        // Caricamento della scena
        fxmlLoader = new FXMLLoader();
        Parent rootNode = fxmlLoader.load(getClass().getResourceAsStream(fxmlFile));
        scene = new Scene(rootNode, ScreenSize.getSceneWidth(), ScreenSize.getSceneHeight());
        newStage.setTitle("iKitchen");
        newStage.setScene(scene);
        newStage.centerOnScreen();
        newStage.show();
    }
}