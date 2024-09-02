package com.iKitchen;

import com.iKitchen.exception.DAOException;
import com.iKitchen.model.dao.MostraRicetteDAO;
import com.iKitchen.model.domain.ApplicazioneStage;
import com.iKitchen.model.domain.ListRicette;
import com.iKitchen.model.domain.Ricetta;
import com.iKitchen.model.utility.ScreenSize;
import javafx.application.Application;
import javafx.application.HostServices;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ApplicationStart extends Application {

    private static HostServices hostServices;

    @Override
    public void start(Stage stage) throws IOException, DAOException, SQLException {

        hostServices = getHostServices();

        String nomeFile = "iKitchen/RicetteUtenti/Primi piatti_Da chef.dat";
        MostraRicetteDAO mostraRicetteDAO = new MostraRicetteDAO();
        ListRicette listRicette = new ListRicette();
        listRicette = mostraRicetteDAO.execute("Primi piatti", "Da chef", "Tutte le ricette");

        List<Ricetta> ricetteToAdd = new ArrayList<>();
        for (Ricetta ricetta : listRicette.getListaRicette()) {
            ricetteToAdd.add(ricetta);
        }
        salvaEventiSuFile(ricetteToAdd, nomeFile);



        ApplicazioneStage.setStage(stage);
        FXMLLoader fxmlLoader = new FXMLLoader(ApplicationStart.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), ScreenSize.WIDTH_GUI1, ScreenSize.HEIGHT_GUI1);
        stage.setTitle("iKitchen");
        stage.setScene(scene);
        stage.show();
    }

    public void salvaEventiSuFile(List<Ricetta> ricette, String nomeFile) {
        try (FileOutputStream fos = new FileOutputStream(nomeFile);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            for (Ricetta ricetta : ricette) {
                oos.writeObject(ricetta);
            }
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static HostServices getHostServicesInstance() {
        return hostServices;
    }

    public static void main(String[] args) {
        launch();
    }
}