package com.ikitchen.model.dao;

import com.ikitchen.model.domain.ListRicette;
import com.ikitchen.model.domain.Ricetta;
import java.io.*;

public class MostraRicetteFS {

    // Metodo per recuperare ricette dal file system, gestendo il caso di lista esistente o creazione di una nuova lista
    public ListRicette recuperaRicetteDaFile(Ricetta ricetta, ListRicette... existingList) {

        // Dichiarazioni iniziali
        ListRicette listRicette;

        // Se viene passata una lista esistente, usala; altrimenti, creane una nuova
        if (existingList != null && existingList.length > 0) {
            listRicette = existingList[0];
        } else {
            listRicette = new ListRicette();
        }

        // Costruisci il nome del file system
        String nomeFile = costruisciNomeFile(ricetta.getCategoria(), ricetta.getProvenienza());

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
            Ricetta recipe;
            while ((recipe = (Ricetta) ois.readObject()) != null) {
                if (ricetta.getCategoria() == null || recipe.getCategoria().equalsIgnoreCase(ricetta.getCategoria())) {
                    listRicette.addRicetta(recipe);
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
