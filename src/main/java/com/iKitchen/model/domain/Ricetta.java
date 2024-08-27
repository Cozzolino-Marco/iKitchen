package com.iKitchen.model.domain;

import java.io.Serializable;
import java.sql.Blob;

public abstract class Ricetta implements Serializable {

    // Variabili
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

    // Getter
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

    // Setter
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

    // Realizzazione
    public abstract String getCategoria();
}
