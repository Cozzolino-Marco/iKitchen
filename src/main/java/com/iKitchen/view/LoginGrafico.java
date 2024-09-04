package com.iKitchen.view;

import com.iKitchen.model.domain.ApplicazioneStage;
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
    protected void onLoginButtonClick(){
        CredentialsBean credB;
        credB = new CredentialsBean(textFieldUsername.getText(), textFieldPassword.getText());
        try {
            LoginController loginController = new LoginController();
            loginController.start(credB);
        } catch (DAOException | IOException e) {
            throw new IllegalArgumentException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Chiamata al controller della registrazione
    public void registratiView() throws IOException {
        RegistratiGrafico registratiGrafico = new RegistratiGrafico();
        registratiGrafico.registratiView();
    }
}