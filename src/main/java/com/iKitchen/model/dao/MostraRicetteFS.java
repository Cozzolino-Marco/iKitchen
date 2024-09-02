package com.iKitchen.model.dao;

import com.iKitchen.model.domain.ListRicette;
import com.iKitchen.model.domain.Ricetta;
import java.io.*;

public class MostraRicetteFS {

    // Metodo per recuperare ricette dal file system, gestendo il caso di lista esistente o creazione di una nuova lista
    public ListRicette recuperaRicetteDaFile(String categoria, String provenienza, String filtro, ListRicette... existingList) {

        // Dichiarazioni iniziali
        ListRicette listRicette;

        // Se viene passata una lista esistente, usala; altrimenti, creane una nuova
        if (existingList != null && existingList.length > 0) {
            listRicette = existingList[0];
        } else {
            listRicette = new ListRicette();
        }

        // Costruisci il nome del file system
        String nomeFile = costruisciNomeFile(categoria, provenienza);

        // Verifica se il file esiste, altrimenti crealo
        File file = new File(nomeFile);
        if (!file.exists()) {
            try {
                // Crea il file se non esiste e le directory necessarie
                file.getParentFile().mkdirs();
                file.createNewFile();
            } catch (IOException e) {
                throw new IllegalArgumentException("Impossibile creare il file: " + nomeFile, e);
            }
        }

        // Apertura file e creazione "ois" per leggere oggetti serializzati dal file
        try (FileInputStream fis = new FileInputStream(nomeFile);
             ObjectInputStream ois = new ObjectInputStream(fis)) {

            // Lettura degli oggetti dal file uno alla volta
            Ricetta ricetta;
            while ((ricetta = (Ricetta) ois.readObject()) != null) {
                if (categoria == null || ricetta.getCategoria().equalsIgnoreCase(categoria)) {
                    listRicette.addRicetta(ricetta);
                }
            }

        } catch (EOFException | FileNotFoundException e) {
            // EOF (End Of File)
        } catch (IOException | ClassNotFoundException e) {
            // Eccezione
            throw new IllegalArgumentException(e);
        }
        return listRicette;
    }

    // Metodo per costruire il nome del file basato sui filtri
    private String costruisciNomeFile(String categoria, String provenienza) {
        return "iKitchen/RicetteUtenti/" + categoria + "_" + provenienza + ".dat";
    }
}
