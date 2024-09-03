package com.iKitchen.model.domain;

import java.sql.Blob;
import java.util.Date;

public class Ingrediente {

    // Variabili
    private String codIngrediente;
    private String nome;
    private Date scadenza;
    private int quantita;
    private int limite;
    transient Blob immagine;
    private String tipo;

    // Costruttore di default vuoto
    public Ingrediente() {}

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

    // Setter
    public void setCodIngrediente(String codIngrediente) {
        this.codIngrediente = codIngrediente;
    }
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
    public void setImmagine(Blob immagine) {
        this.immagine = immagine;
    }
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}