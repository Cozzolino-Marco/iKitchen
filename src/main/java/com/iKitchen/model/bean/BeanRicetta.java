package com.iKitchen.model.bean;

import com.iKitchen.model.domain.ListIngredienti;
import java.sql.Blob;
import java.util.Arrays;
import java.util.List;

public class BeanRicetta {

    // Variabili
    String titolo;
    String descrizione;
    Blob imageUrl;
    String categoria;
    String provenienza;
    String cuoco;
    Float durataPreparazione;
    ListIngredienti ingredienti;
    String passaggi;
    String videoUrl;
    int likes;

    // Per mandare i dati dal controller grafico a quello applicativo
    public BeanRicetta (String titolo, String descrizione, Blob imageUrl, String categoria, String provenienza, String cuoco, float durataPreparazione, ListIngredienti ingredienti, String passaggi, String videoUrl, int likes) {

        // Controllo della consistenza del titolo
        if (titolo.length() < 3) {
            throw new IllegalArgumentException("Non esiste una ricetta del genere! Inserire almeno 3 caratteri!");
        }

        // Controllo sulla lunghezza del campo descrizione
        if (descrizione.length() <= 20) {
            throw new IllegalArgumentException("La lunghezza della descrizione deve essere maggiore di 20 caratteri!");
        }

        // Controllo sulla correttezza del campo categoria
        List<String> categorieValide = Arrays.asList("Colazione", "Pasto veloce", "Bevande", "Primi piatti", "Secondi piatti", "Contorni", "Dolci");
        if (!categorieValide.contains(categoria)) {
            throw new IllegalArgumentException("La categoria non è valida! Le categorie valide sono: " + categorieValide);
        }

        // Controllo sulla correttezza del campo provenienza
        if (!provenienza.equals("Da chef") && !provenienza.equals("Dal web")) {
            throw new IllegalArgumentException("La provenienza deve essere 'da chef' oppure 'dal web'!");
        }

        // Controllo sulla correttezza del campo durata preparazione
        if (durataPreparazione < 1.0) {
            throw new IllegalArgumentException("La durata della preparazione deve essere di almeno 1 minuto!");
        }

        // Controllo sulla correttezza del campo likes
        if (likes < 0) {
            throw new IllegalArgumentException("La ricetta non può avere likes negativi!");
        }

        // Assegnazioni
        this.titolo = titolo;
        this.descrizione = descrizione;
        this.imageUrl = imageUrl;
        this.categoria = categoria;
        this.provenienza = provenienza;
        this.cuoco = cuoco;
        this.durataPreparazione = durataPreparazione;
        this.ingredienti = ingredienti;
        this.passaggi = passaggi;
        this.videoUrl = videoUrl;
        this.likes = likes;
    }

    // Per mandare i dati dal controller applicativo a quello grafico
    public BeanRicetta (String titolo, String categoria, String cuoco, float durataPreparazione) {

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
        if (durataPreparazione < 1.0) {
            throw new IllegalArgumentException("La durata della preparazione deve essere di almeno 1 minuto!");
        }

        // Assegnazioni
        this.titolo = titolo;
        this.categoria = categoria;
        this.cuoco = cuoco;
        this.durataPreparazione = durataPreparazione;
    }

    /* Per mandare i dati dal controller applicativo a quello grafico
    public BeanRicetta (String titolo, String descrizione, Blob imageUrl, String categoria, String provenienza, String cuoco, float durataPreparazione, ListIngredienti ingredienti, String passaggi, String videoUrl, int likes) {
        this.titolo = titolo;
        this.descrizione = descrizione;
        this.imageUrl = imageUrl;
        this.categoria = categoria;
        this.provenienza = provenienza;
        this.cuoco = cuoco;
        this.durataPreparazione = durataPreparazione;
        this.ingredienti = ingredienti;
        this.passaggi = passaggi;
        this.videoUrl = videoUrl;
        this.likes = likes;
    }*/

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
    public String getCategoria() {
        return categoria;
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
}
