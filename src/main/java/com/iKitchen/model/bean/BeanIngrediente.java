package com.iKitchen.model.bean;

import java.sql.Blob;
import java.util.Date;

public class BeanIngrediente {

    // Variabili
    private String codIngrediente;
    private String nome;
    private Date scadenza;
    private int quantita;
    private int limite;
    private Blob immagine;

    // Per mandare i dati dal controller grafico a quello applicativo
    public BeanIngrediente (String codIngrediente, String nome, Date scadenza, int quantita, int limite, Blob immagine) {

        // Controllo della consistenza del nome
        if (nome.length() < 3) {
            throw new IllegalArgumentException("Non esiste un prodotto del genere! Inserire almeno 3 caratteri!");
        }

        // Controllo della consistenza del parametro quantita'
        if (quantita < 0) {
            throw new IllegalArgumentException("La quantita' deve essere almeno positiva!");
        }

        // Controllo della consistenza del parametro limite
        if (limite <= 0) {
            throw new IllegalArgumentException("La soglia limite deve essere almeno positiva!");
        }

        // Assegnazioni
        this.codIngrediente = codIngrediente;
        this.nome = nome;
        this.scadenza = scadenza;
        this.quantita = quantita;
        this.limite = limite;
        this.immagine = immagine;
    }

    // Getter
    public String getCodIngrediente() {
        return codIngrediente;
    }
    public String getNome() {
        return nome;
    }
    public Date getScadenza() {
        return scadenza;
    }
    public int getQuantita() {
        return quantita;
    }
    public int getLimite() {
        return limite;
    }
    public Blob getImmagine() {
        return immagine;
    }
}
