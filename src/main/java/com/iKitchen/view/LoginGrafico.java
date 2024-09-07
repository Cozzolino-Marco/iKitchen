package com.iKitchen.view;

import com.iKitchen.ApplicationStart;
import com.iKitchen.model.domain.ApplicazioneStage;
import com.iKitchen.model.domain.Credentials;
import com.iKitchen.model.utility.Popup;
import com.iKitchen.model.utility.ScreenSize;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import com.iKitchen.controller.LoginController;
import com.iKitchen.exception.DAOException;
import com.iKitchen.model.bean.CredentialsBean;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.SQLException;

public class LoginGrafico {

    @FXML
    private TextField textFieldUsername;

    @FXML
    private PasswordField textFieldPassword;

    @FXML
    public void loginView() throws IOException {
        // Carica il file FXML per la vista del login
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/iKitchen/login.fxml"));
        Parent root = fxmlLoader.load();

        // Ottieni lo stage attuale dalla classe ApplicazioneStage
        Stage stage = ApplicazioneStage.getStage();

        // Imposta la nuova scena con il layout caricato
        Scene scene = new Scene(root, ScreenSize.WIDTH_GUI1, ScreenSize.HEIGHT_GUI1);

        // Cambia la scena dello stage
        stage.setScene(scene);
        stage.show();
    }

    // Acquisizione credienziali e passaggio al controller del login
    @FXML
    protected void onLoginButtonClick() {
        CredentialsBean credB;
        credB = new CredentialsBean(textFieldUsername.getText(), textFieldPassword.getText());

        try {
            LoginController loginController = new LoginController();
            Credentials cred = null;

            // Chiamata al login controller per effettuare il login
            try {
                cred = loginController.start(credB);
            } catch (DAOException | SQLException e) {
                Popup.mostraPopup("Errore", "Hai sbagliato username o password, per favore ricontrolla!", "error");
            }

            // Recupera il nome associato allo username
            loginController.recuperaNome(credB);

            // Settaggi di caricamento scena ed FXML
            FXMLLoader fxmlLoader;
            Stage stage = ApplicazioneStage.getStage();
            Scene scene;

            // Controlla il ruolo dell'utente e carica la view appropriata
            if (cred.getRole() != null) {
                cambiaViewDopoLogin(cred, stage);
            } else {
                fxmlLoader = new FXMLLoader(ApplicationStart.class.getResource("login.fxml"));
                scene = new Scene(fxmlLoader.load(), ScreenSize.WIDTH_GUI1, ScreenSize.HEIGHT_GUI1);
            }

        } catch (DAOException | IOException e) {
            throw new IllegalArgumentException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Metodo che gestisce il caricamento della view in base al ruolo
    private void cambiaViewDopoLogin(Credentials credentials, Stage stage) throws IOException, DAOException, SQLException {

        String fxmlFile;
        FXMLLoader fxmlLoader = new FXMLLoader();

        if (credentials.getRole().getId() == 1) {
            fxmlFile = "/com/iKitchen/utentiView.fxml"; // View per utenti domestici
        } else {
            fxmlFile = "/com/iKitchen/chefView.fxml"; // View per chef
        }

        // Carica l'FXML
        fxmlLoader.setLocation(getClass().getResource(fxmlFile));
        Parent rootNode = fxmlLoader.load();

        // Imposta la scena
        Scene scene = new Scene(rootNode, ScreenSize.WIDTH_GUI1, ScreenSize.HEIGHT_GUI1);
        stage.setTitle("iKitchen");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    // Chiamata al controller della registrazione
    public void registratiView() throws IOException {

        // Carica il file FXML per la vista del login
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/iKitchen/registratiView.fxml"));
        Parent root = fxmlLoader.load();

        // Ottieni lo stage attuale dalla classe ApplicazioneStage
        Stage stage = ApplicazioneStage.getStage();

        // Imposta la nuova scena con il layout caricato
        Scene scene = new Scene(root, ScreenSize.WIDTH_GUI1, ScreenSize.HEIGHT_GUI1);

        // Cambia la scena dello stage
        stage.setScene(scene);
        stage.show();
    }

    // Metodo per il cambio della grafica
    public void cambiaGrafica()throws IOException {

        // Invoca il metodo per il cambio grafica
        ScreenSize.changeGUI();

        FXMLLoader fxmlLoader;
        Stage stage = ApplicazioneStage.getStage();
        Scene scene;

        String fxmlFile;
        if (ScreenSize.getGUI() == 0){
            /*if(Credentials.getRole().getId() == 1) {
                fxmlFile = "/com/iKitchen/utentiView.fxml";
            }else{
                fxmlFile = "/com/iKitchen/chefView.fxml";
            }*/
            fxmlFile = "/com/iKitchen/login.fxml";

        } else {
            /*if(Credentials.getRole().getId() == 1) {
                fxmlFile = "/com/iKitchen/utentiView2.fxml";
            }else{
                fxmlFile = "/com/iKitchen/chefView2.fxml";
            }*/
            fxmlFile = "/com/IpovisionGUI/login2.fxml";
        }

        fxmlLoader = new FXMLLoader();
        Parent rootNode = fxmlLoader.load(getClass().getResourceAsStream(fxmlFile));
        scene = new Scene(rootNode, ScreenSize.getSceneWidth(), ScreenSize.getSceneHeight());

        stage.setTitle("iKitchen");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }
}