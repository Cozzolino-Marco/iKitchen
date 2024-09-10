package com.ikitchen.view_standard;

import com.ikitchen.model.domain.ApplicazioneStage;
import com.ikitchen.model.utility.Credentials;
import com.ikitchen.model.utility.Popup;
import com.ikitchen.model.utility.ScreenSize;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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

public class LoginGrafico {

    @FXML
    private TextField textFieldUsername;

    @FXML
    private PasswordField textFieldPassword;

    @FXML // Acquisizione credienziali e passaggio al controller del login
    protected void onLoginButtonClick() throws IOException {
        
        // Gestione eccezione per validità email
        CredentialsBean credB = null;
        try {
            credB = new CredentialsBean(textFieldUsername.getText(), textFieldPassword.getText());
        } catch (IllegalArgumentException e) {
            Popup.mostraPopup("Errore", "L'email fornita non è valida!", "error");
            loginView();
        }

        // Verifica se credB è stato istanziato correttamente
        if (credB != null) {
            LoginController loginController = new LoginController();

            // Chiamata al login controller per effettuare il login
            try {
                loginController.start(credB);

                // Recupera il nome associato allo username
                loginController.recuperaNome(credB);

                // Controlla il ruolo dell'utente e carica la view appropriata
                if (Credentials.getRole() != null) {
                    cambiaViewDopoLogin();
                } else {
                    Popup.mostraPopup("Errore", "Hai sbagliato username o password, per favore ricontrolla!", "error");
                    loginView();
                }
            } catch (DAOException | SQLException | IOException e) {
                Popup.mostraPopup("Errore", "Hai sbagliato username o password, per favore ricontrolla!", "error");
                loginView();
            }
        }
    }

    @FXML // Metodo per il login
    public void loginView() throws IOException {
        
        // Carica il file FXML per la vista del login
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/StandardGUI/login.fxml"));
        Parent root = fxmlLoader.load();

        // Ottieni lo stage attuale dalla classe ApplicazioneStage
        Stage stage = ApplicazioneStage.getStage();

        // Imposta la nuova scena con il layout caricato
        Scene scene = new Scene(root, ScreenSize.getSceneWidth(), ScreenSize.getSceneHeight());

        // Cambia la scena dello stage
        stage.setScene(scene);
        stage.show();
    }

    // Metodo per il cambio della grafica
    public void cambiaGrafica()throws IOException {

        // Invoca il metodo per il cambio grafica
        ScreenSize.changeGUI();

        // Settaggi scena
        FXMLLoader fxmlLoader;
        Stage stage = ApplicazioneStage.getStage();
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
        stage.setTitle("iKitchen");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    // Metodo che gestisce il caricamento della view in base al ruolo
    private void cambiaViewDopoLogin() throws IOException, DAOException, SQLException {

        String fxmlFile;
        Stage stage = ApplicazioneStage.getStage();
        FXMLLoader fxmlLoader = new FXMLLoader();

        if (Credentials.getRole().getId() == 1) {
            fxmlFile = "/com/StandardGUI/utentiView.fxml"; // View per utenti domestici
        } else {
            fxmlFile = "/com/StandardGUI/chefView.fxml"; // View per chef
        }

        // Carica l'FXML
        fxmlLoader.setLocation(getClass().getResource(fxmlFile));
        Parent rootNode = fxmlLoader.load();

        // Imposta la scena
        Scene scene = new Scene(rootNode, ScreenSize.getSceneWidth(), ScreenSize.getSceneHeight());
        stage.setTitle("iKitchen");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    // Chiamata al controller della registrazione
    public void registratiView() throws IOException {

        // Carica il file FXML per la vista del login
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/StandardGUI/registratiView.fxml"));
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