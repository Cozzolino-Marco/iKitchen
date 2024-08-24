package com.iKitchen.controller;

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
import static com.iKitchen.model.domain.ScreenSize.HEIGHT_GUI1;
import static com.iKitchen.model.domain.ScreenSize.WIDTH_GUI1;

public class LoginController {
    public void start(CredentialsBean credB) throws DAOException, IOException {

        Credentials cred= new Credentials(credB.getUsername(),credB.getPassword());

        try {
            new LoginProcedureDAO().execute(cred);
        } catch(DAOException | SQLException e) {
            throw new IllegalArgumentException(e);
        }

        FXMLLoader fxmlLoader;
        Stage stage = ApplicazioneStage.getStage();
        Scene scene;
        //System.out.print("ciaooooooooooooooooooooooo" + cred.getRole());
        if(cred.getRole() == null) {
            fxmlLoader = new FXMLLoader(ApplicationStart.class.getResource("login.fxml"));
            scene = new Scene(fxmlLoader.load(), WIDTH_GUI1, HEIGHT_GUI1);
        }else{
            String fxmlFile;

            try {
                ConnectionFactory.changeRole(cred.getRole());
            } catch(SQLException e) {
                throw new IllegalArgumentException(e);
            }

            if (cred.getRole().getId() == 1) {
                //System.out.println("Utente domestico");

                /*try {
                    new ProfileProcedureDAO().execute(cred);
                } catch(DAOException | SQLException e) {
                    throw new IllegalArgumentException(e);
                }*/

                fxmlFile = "/com/iKitchen/utentiView.fxml";
            } else {
                System.out.println("Chef");
                fxmlFile = "/com/iKitchen/chefview.fxml";
            }
            fxmlLoader = new FXMLLoader();
            Parent rootNode = fxmlLoader.load(getClass().getResourceAsStream(fxmlFile));
            scene = new Scene(rootNode, WIDTH_GUI1, HEIGHT_GUI1);
        }

        stage.setTitle("iKitchen");
        stage.setScene(scene);
        stage.show();
    }
}