package com.iKitchen.controller;

import com.iKitchen.model.utility.ScreenSize;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.iKitchen.ApplicationStart;
import com.iKitchen.exception.DAOException;
import com.iKitchen.model.bean.CredentialsBean;
import com.iKitchen.model.dao.ConnectionFactory;
import com.iKitchen.model.dao.LoginProcedureDAO;
import com.iKitchen.model.domain.ApplicazioneStage;
import com.iKitchen.model.domain.Credentials;
import java.io.IOException;
import java.sql.SQLException;

public class LoginController {
    public void start(CredentialsBean credB) throws DAOException, IOException, SQLException {

        Credentials cred= new Credentials(credB.getUsername(),credB.getPassword());

        try {
            new LoginProcedureDAO().execute(cred);
        } catch(DAOException | SQLException e) {
            throw new IllegalArgumentException(e);
        }

        FXMLLoader fxmlLoader;
        Stage stage = ApplicazioneStage.getStage();
        Scene scene;
        if (cred.getRole() == null) {
            fxmlLoader = new FXMLLoader(ApplicationStart.class.getResource("login.fxml"));
            scene = new Scene(fxmlLoader.load(), ScreenSize.WIDTH_GUI1, ScreenSize.HEIGHT_GUI1);
        } else {
            String fxmlFile;

            try {
                ConnectionFactory.changeRole(cred.getRole());
            } catch(SQLException e) {
                throw new IllegalArgumentException(e);
            }

            if (cred.getRole().getId() == 1) {
                fxmlFile = "/com/iKitchen/utentiView.fxml";
            } else {
                fxmlFile = "/com/iKitchen/chefview.fxml";
            }
            fxmlLoader = new FXMLLoader();
            Parent rootNode = fxmlLoader.load(getClass().getResourceAsStream(fxmlFile));
            scene = new Scene(rootNode, ScreenSize.WIDTH_GUI1, ScreenSize.HEIGHT_GUI1);
        }
        stage.setTitle("iKitchen");
        stage.setScene(scene);
        stage.show();
    }
}