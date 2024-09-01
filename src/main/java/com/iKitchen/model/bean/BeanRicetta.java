package com.iKitchen.model.bean;

import com.iKitchen.model.domain.ListIngredienti;
import java.sql.Blob;
import java.util.Arrays;
import java.util.List;

public class BeanRicetta {

    // Variabili
    String codRicetta;
    String titolo;
    String descrizione;
    Blob immagine;
    String categoria;
    String provenienza;
    String cuoco;
    int durataPreparazione;
    int calorie;
    ListIngredienti ingredienti;
    String passaggi;
    String videoUrl;
    int likes;
    String linkRicetta;

    // Costruttore di default vuoto usato da scrapeRicette
    public BeanRicetta() {}

    // Passaggio di dati completo per la visualizzazione della ricetta in dettaglio
    public BeanRicetta (String codRicetta, String titolo, String descrizione, Blob immagine, String categoria, String cuoco, int durataPreparazione, int calorie, ListIngredienti ingredienti, String passaggi, String videoUrl, int likes) {

        // Controllo della consistenza del titolo
        if (titolo.length() < 3) {
            throw new IllegalArgumentException("Non esiste una ricetta del genere! Inserire almeno 3 caratteri!");
        }

        // Controllo sulla lunghezza del campo descrizione
        if (descrizione.length() <= 10) {
            throw new IllegalArgumentException("La lunghezza della descrizione deve essere maggiore di 10 caratteri!");
        }

        // Controllo sulla correttezza del campo categoria
        List<String> categorieValide = Arrays.asList("Colazione", "Pasto veloce", "Bevande", "Primi piatti", "Secondi piatti", "Contorni", "Dolci");
        if (!categorieValide.contains(categoria)) {
            throw new IllegalArgumentException("La categoria non è valida! Le categorie valide sono: " + categorieValide);
        }

        /* Controllo sulla correttezza del campo provenienza
        if (!provenienza.equals("Da chef") && !provenienza.equals("Dal web")) {
            throw new IllegalArgumentException("La provenienza deve essere 'da chef' oppure 'dal web'!");
        }*/

        // Controllo sulla correttezza del campo durata preparazione
        if (durataPreparazione < 0) {
            throw new IllegalArgumentException("La durata della preparazione non può essere negativa!");
        }

        // Controllo sulla correttezza del campo calorie
        if (calorie < 0) {
            throw new IllegalArgumentException("Non puo' esistere una ricetta con calorie negative!");
        }

        // Controllo sulla correttezza del campo likes
        if (likes < 0) {
            throw new IllegalArgumentException("La ricetta non può avere likes negativi!");
        }

        // Assegnazioni
        this.codRicetta = codRicetta;
        this.titolo = titolo;
        this.descrizione = descrizione;
        this.immagine = immagine;
        this.categoria = categoria;
        this.cuoco = cuoco;
        this.durataPreparazione = durataPreparazione;
        this.calorie = calorie;
        this.ingredienti = ingredienti;
        this.passaggi = passaggi;
        this.videoUrl = videoUrl;
        this.likes = likes;
    }

    // Passaggio di dati parziale per la visualizzazione delle info base delle ricette
    public BeanRicetta (String codRicetta, String titolo, Blob immagine, String categoria, String cuoco, int durataPreparazione, int calorie) {

        // Controllo della consistenza del titolo
        if (titolo.length() < 3) {
            throw new IllegalArgumentException("Non esiste una ricetta del genere! Inserire almeno 3 caratteri!");
        }

        // Controllo sulla correttezza del campo categoria
        List<String> categorieValide = Arrays.asList("Colazione", "Pasto veloce", "Bevande", "Primi piatti", "Secondi piatti", "Contorni", "Dolci");
        if (!categorieValide.contains(categoria)) {
            throw new IllegalArgumentException("La categoria non è valida! Le categorie valide sono: " + categorieValide);
        }

        // Controllo sulla correttezza del campo durata preparazione
        if (durataPreparazione < 1) {
            throw new IllegalArgumentException("La durata della preparazione deve essere di almeno 1 minuto!");
        }

        // Controllo sulla correttezza del campo calorie
        if (calorie < 0) {
            throw new IllegalArgumentException("Non puo' esistere una ricetta con calorie negative!");
        }

        // Assegnazioni
        this.codRicetta = codRicetta;
        this.titolo = titolo;
        this.immagine = immagine;
        this.categoria = categoria;
        this.cuoco = cuoco;
        this.durataPreparazione = durataPreparazione;
        this.calorie = calorie;
    }

    // Setter
    public void setTitolo(String titolo) {
        if (titolo == null) {
            this.titolo = "TBA";
        } else {
            this.titolo = titolo;
        }
    }
    public void setImmagine(Blob immagine) {
        /*if (immagine == null) {
            this.immagine = null;
            return false;
        } else {
            this.immagine = immagine;
            return true;
        }*/
        this.immagine = immagine;
    }
    public void setCuoco(String cuoco) {
        this.cuoco = cuoco;
    }
    public void setDurataPreparazione(int durataPreparazione) {
        this.durataPreparazione = durataPreparazione;
    }
    public void setCalorie(int calorie) {
        if (calorie < 0) {
            this.calorie = 0;
        } else {
            this.calorie = calorie;
        }
    }
    public void setLinkRicetta(String linkRicetta) {
        if (linkRicetta == null) {
            this.linkRicetta = "TBA";
        } else {
            this.linkRicetta = linkRicetta;
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
    public String getCategoria() {
        return categoria;
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
}
