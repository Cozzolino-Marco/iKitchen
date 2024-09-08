package com.ikitchen.model.dao;

import com.ikitchen.model.domain.ListRicette;
import com.ikitchen.model.domain.Ricetta;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MostraRicetteFS {

    private static final Logger logger = Logger.getLogger(MostraRicetteFS.class.getName());

    // Metodo per recuperare ricette dal file system
    public ListRicette recuperaRicetteDaFile(Ricetta ricetta, ListRicette... existingList) {

        // Ottengo lista ricette dal metodo opportuno
        ListRicette listaRicette = ottieniListaRicette(existingList);

        // Invoco il metodo per costruire il nome del file basato sui filtri di categoria e provenienza.
        String nomeFile = costruisciNomeFile(ricetta.getCategoria(), ricetta.getProvenienza());

        // Metodo per la gestione file e directory se necessario
        gestisciCreazioneFile(nomeFile);

        // Metodo per acquisire le ricette filtrate in base alla categoria
        leggiRicetteDalFile(nomeFile, ricetta.getCategoria(), listaRicette);

        // Restituisce la lista di ricette
        return listaRicette;
    }

    // Metodo per ottenere una lista esistente o crearne una nuova se non esiste
    private ListRicette ottieniListaRicette(ListRicette... existingList) {
        ListRicette listaRicette;

        // Controlla se è stata passata una lista esistente
        if (existingList != null) {
            if (existingList.length > 0) {
                // Usa la prima lista disponibile se presente
                listaRicette = existingList[0];
            } else {
                // Se l'array è vuoto, crea una nuova lista
                listaRicette = new ListRicette();
            }
        } else {
            // Se non è stata passata nessuna lista, crea una nuova lista
            listaRicette = new ListRicette();
        }

        return listaRicette;
    }

    // Metodo per costruire il nome del file basato sui filtri di categoria e provenienza
    private String costruisciNomeFile(String categoria, String provenienza) {
        return "iKitchen/RicetteUtenti/" + categoria + "_" + provenienza + ".dat";
    }

    // Se il file non esiste, tenta di creare le directory necessarie e il file stesso
    private void gestisciCreazioneFile(String nomeFile) {
        File file = new File(nomeFile);
        if (!file.exists()) {
            try {
                creaDirectory(file);
                creaFile(file);
            } catch (IOException e) {
                throw new IllegalArgumentException("Impossibile creare il file: " + nomeFile, e);
            }
        }
    }

    // Verifica se le directory genitori del file esistono e, se necessario, le crea
    private void creaDirectory(File file) throws IOException {
        File dirParents = file.getParentFile();
        if (dirParents != null && !dirParents.exists()) {
            boolean directoryCreata = dirParents.mkdirs();
            if (!directoryCreata) {
                throw new IOException("Impossibile creare le directory necessarie per il file: " + file.getPath());
            }
        }
    }

    // Se il file viene creato con successo, logga il risultato.
    private void creaFile(File file) throws IOException {
        boolean fileCreato = file.createNewFile();
        if (fileCreato) {
            // Il logger si occuperà di inserire nomeFile al posto del segnaposto senza bisogno di concatenazione esplicita
            logger.log(Level.INFO, "File creato con successo: {0}" + file.getPath());
        }
    }

    // Legge gli oggetti serializzati dal file e li aggiunge alla lista se corrispondono alla categoria specificata
    private void leggiRicetteDalFile(String nomeFile, String categoria, ListRicette listaRicette) {
        try (FileInputStream fis = new FileInputStream(nomeFile);
             ObjectInputStream ois = new ObjectInputStream(fis)) {

            Ricetta ricetta;
            // Legge le ricette una alla volta fino a quando non finisce il file
            while ((ricetta = (Ricetta) ois.readObject()) != null) {
                if (categoria == null || ricetta.getCategoria().equalsIgnoreCase(categoria)) {
                    listaRicette.addRicetta(ricetta);
                }
            }

        } catch (EOFException | FileNotFoundException e) {
            // EOF (End Of File), nessuna azione necessaria
        } catch (IOException | ClassNotFoundException e) {
            throw new IllegalArgumentException("Errore nella lettura del file: " + nomeFile, e);
        }
    }
}