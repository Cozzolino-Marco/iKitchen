package com.ikitchen.model.domain;

import java.io.Serializable;
import java.sql.Blob;

public abstract class Ricetta implements Serializable {

    // Variabili
    protected String codRicetta;
    protected String titolo;
    protected String descrizione;
    protected transient Blob immagine;
    protected String provenienza;
    protected String cuoco;
    protected int durataPreparazione;
    protected int calorie;
    protected ListIngredienti ingredienti;
    protected String passaggi;
    protected String videoUrl;
    protected int likes;

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

    // Realizzazione
    public abstract String getCategoria();
}
