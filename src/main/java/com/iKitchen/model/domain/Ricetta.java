package com.iKitchen.model.domain;

import java.io.Serializable;
import java.sql.Blob;

public abstract class Ricetta implements Serializable {

    // Variabili
    String codRicetta;
    String titolo;
    String descrizione;
    transient Blob immagine;
    String provenienza;
    String cuoco;
    int durataPreparazione;
    int calorie;
    ListIngredienti ingredienti;
    String passaggi;
    String videoUrl;
    int likes;
    String linkRicetta;

    // Costruttore di default vuoto
    public Ricetta() {}

    // Costruttore utile al metodo scrapeRecipe
    public Ricetta(String titolo, String cuoco, int durataPreparazione, String calorie, String linkRicetta) {
        this.titolo = titolo;
        this.cuoco = cuoco;
        this.durataPreparazione = durataPreparazione;
        this.linkRicetta = linkRicetta;

        /* Gestione dei valori vuoti o non validi per la durata della preparazione
        try {
            this.durataPreparazione = durataPreparazione.isEmpty() ? 0 : Integer.parseInt(durataPreparazione);
        } catch (NumberFormatException e) {
            this.durataPreparazione = 0; // Imposta un valore di default o gestisci l'errore
        }*/

        // Gestione dei valori vuoti o non validi per le calorie
        try {
            this.calorie = calorie.isEmpty() ? 0 : Integer.parseInt(calorie);
        } catch (NumberFormatException e) {
            this.calorie = 0; // Imposta un valore di default o gestisci l'errore
        }
    }

    // Getter
    public String getCodice() {
        return codRicetta;
    }
    public String getTitolo() {
        return titolo;
    }
    public String getDescrizione() {
        return descrizione;
    }
    public Blob getImmagine() {
        return immagine;
    }
    public String getProvenienza() {
        return provenienza;
    }
    public String getCuoco() {
        return cuoco;
    }
    public int getDurataPreparazione() {
        return durataPreparazione;
    }
    public int getCalorie() {
        return calorie;
    }
    public ListIngredienti getIngredienti() {
        return ingredienti;
    }
    public String getPassaggi() {
        return passaggi;
    }
    public String getVideoUrl() {
        return videoUrl;
    }
    public int getLikes() {
        return likes;
    }
    public String getLinkRicetta() {
        return linkRicetta;
    }

    // Setter
    public void setCodice(String codRicetta) {
        this.codRicetta = codRicetta;
    }
    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }
    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }
    public void setImmagine(Blob immagine) {
        this.immagine = immagine;
    }
    public void setProvenienza(String provenienza) {
        this.provenienza = provenienza;
    }
    public void setCuoco(String cuoco) {
        this.cuoco = cuoco;
    }
    public void setDurataPreparazione(int durataPreparazione) {
        this.durataPreparazione = durataPreparazione;
    }
    public void setCalorie(int calorie) {
        this.calorie = calorie;
    }
    public void setIngredienti(ListIngredienti ingredienti) {
        this.ingredienti = ingredienti;
    }
    public void setPassaggi(String passaggi) {
        this.passaggi = passaggi;
    }
    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }
    public void setLikes(int likes) {
        this.likes = likes;
    }
    public void setLinkRicetta(String linkRicetta) {
        this.linkRicetta = linkRicetta;
    }

    // Realizzazione
    public abstract String getCategoria();
}
