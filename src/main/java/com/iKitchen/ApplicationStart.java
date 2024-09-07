package com.iKitchen;

import com.iKitchen.exception.DAOException;
import com.iKitchen.model.dao.MostraRicetteDAO;
import com.iKitchen.model.domain.ApplicazioneStage;
import com.iKitchen.model.domain.FactoryRicetta;
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

        // Gestione servizio link web della ricetta
        hostServices = getHostServices();

        // TODO: Rimuovere il riempimento del file system
        String nomeFile = "iKitchen/RicetteUtenti/Primi piatti_Da chef.dat";
        MostraRicetteDAO mostraRicetteDAO = new MostraRicetteDAO();

        // Crea l'oggetto Ricetta con la categoria e provenienza corrette
        Ricetta ricetta = FactoryRicetta.createRicetta("Primi piatti");  // Usa la factory per creare l'oggetto Ricetta
        ricetta.setProvenienza("Da chef");

        // Esegui il metodo execute con l'oggetto Ricetta e il filtro
        ListRicette listRicette = mostraRicetteDAO.execute(ricetta, "Tutte le ricette");

        List<Ricetta> ricetteToAdd = new ArrayList<>();
        for (Ricetta recipe : listRicette.getListaRicette()) {
            ricetteToAdd.add(recipe);
        }
        salvaEventiSuFile(ricetteToAdd, nomeFile);



        ApplicazioneStage.setStage(stage);
        FXMLLoader fxmlLoader = new FXMLLoader(ApplicationStart.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), ScreenSize.WIDTH_GUI1, ScreenSize.HEIGHT_GUI1);
        stage.setTitle("iKitchen");
        stage.setScene(scene);
        stage.show();
    }

    // TODO: Rimuovere il metodo per il file system
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

    // Ritorno l'istanza utile al servizio link web della ricetta
    public static HostServices getHostServicesInstance() {
        return hostServices;
    }

    // MAIN
    public static void main(String[] args) {
        launch();
    }
}