package com.iKitchen.view;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import com.iKitchen.controller.LoginController;
import com.iKitchen.exception.DAOException;
import com.iKitchen.model.bean.CredentialsBean;
import java.io.IOException;

public class LoginGrafico {
    @FXML
    private TextField textFieldUsername;
    @FXML
    private PasswordField textFieldPassword;

    @FXML
    protected void onHelloButtonClick(){
        CredentialsBean credB;
        credB = new CredentialsBean(textFieldUsername.getText(), textFieldPassword.getText());
        try {
            LoginController loginController = new LoginController();
            loginController.start(credB);
        } catch (DAOException | IOException e) {
            throw new IllegalArgumentException(e);
        }
    }
}