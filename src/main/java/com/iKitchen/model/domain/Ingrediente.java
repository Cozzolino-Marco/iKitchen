package com.iKitchen.model.domain;

import java.util.Date;

public class Ingrediente {

    // Variabili
    private String nome;
    private Date scadenza;
    private int quantita;
    private int limite;

    // Getter
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
}