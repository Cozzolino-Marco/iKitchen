package com.iKitchen.model.domain;

import java.sql.Blob;

public abstract class Ricetta {

    // Variabili
    String titolo;
    String descrizione;
    Blob imageUrl;
    String provenienza;
    String cuoco;
    Float durataPreparazione;
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
    public Blob getImageUrl() {
        return imageUrl;
    }
    public String getProvenienza() {
        return provenienza;
    }
    public String getCuoco() {
        return cuoco;
    }
    public Float getDurataPreparazione() {
        return durataPreparazione;
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
    public void setImageUrl(Blob imageUrl) {
        this.imageUrl = imageUrl;
    }
    public void setProvenienza(String provenienza) {
        this.provenienza = provenienza;
    }
    public void setCuoco(String cuoco) {
        this.cuoco = cuoco;
    }
    public void setDurataPreparazione(Float durataPreparazione) {
        this.durataPreparazione = durataPreparazione;
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
