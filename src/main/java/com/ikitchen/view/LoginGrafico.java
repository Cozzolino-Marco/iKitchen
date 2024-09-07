package com.ikitchen.view;

import com.ikitchen.model.domain.ApplicazioneStage;
import com.ikitchen.model.domain.Credentials;
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

    @FXML
    public void loginView() throws IOException {
        // Carica il file FXML per la vista del login
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/ikitchen/login.fxml"));
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
    protected void onLoginButtonClick() throws IOException {
        CredentialsBean credB = new CredentialsBean(textFieldUsername.getText(), textFieldPassword.getText());
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
        } catch (DAOException | SQLException e) {
            Popup.mostraPopup("Errore", "Hai sbagliato username o password, per favore ricontrolla!", "error");
            loginView();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Metodo che gestisce il caricamento della view in base al ruolo
    private void cambiaViewDopoLogin() throws IOException, DAOException, SQLException {

        String fxmlFile;
        Stage stage = ApplicazioneStage.getStage();
        FXMLLoader fxmlLoader = new FXMLLoader();

        if (Credentials.getRole().getId() == 1) {
            fxmlFile = "/com/ikitchen/utentiView.fxml"; // View per utenti domestici
        } else {
            fxmlFile = "/com/ikitchen/chefView.fxml"; // View per chef
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
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/ikitchen/registratiView.fxml"));
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
            fxmlFile = "/com/ikitchen/login.fxml";

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