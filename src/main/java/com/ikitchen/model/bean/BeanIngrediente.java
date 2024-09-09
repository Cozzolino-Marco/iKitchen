package com.ikitchen.model.bean;

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
    private String tipo;
    private boolean validita;

    public BeanIngrediente() {}

    // Per mandare i dati dal controller grafico a quello applicativo
    public BeanIngrediente (String codIngrediente, String nome, Date scadenza, int quantita, int limite, Blob immagine, String tipo) {

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

        // Controllo della validità del parametro tipo
        if (!tipo.equals("cibo") && !tipo.equals("drink")) {
            throw new IllegalArgumentException("Il tipo non è valido!");
        }

        // Assegnazioni
        this.codIngrediente = codIngrediente;
        this.nome = nome;
        this.scadenza = scadenza;
        this.quantita = quantita;
        this.limite = limite;
        this.immagine = immagine;
        this.tipo = tipo;
    }

    // Setter
    public void setNome(String nome) {
        this.nome = nome;
    }
    public void setScadenza(Date scadenza) {
        this.scadenza = scadenza;
    }
    public void setQuantita(int quantita) {
        this.quantita = quantita;
    }
    public void setLimite(int limite) {
        this.limite = limite;
    }
    public void setValidita(boolean validita) {
        this.validita = validita;
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
    public String getTipo() {
        return tipo;
    }
    public boolean getValidita() {
        return validita;
    }
}
