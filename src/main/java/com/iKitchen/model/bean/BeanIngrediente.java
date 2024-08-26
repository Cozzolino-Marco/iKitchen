package com.iKitchen.model.bean;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class BeanIngrediente {

    // Variabili
    private String nome;
    private Date scadenza;
    private int quantita;
    private int limite;

    // Per mandare i dati dal controller grafico a quello applicativo
    public BeanIngrediente (String nome, Date scadenza, int quantita, int limite) {

        // Controllo della consistenza del nome
        if (nome.length() < 3) {
            throw new IllegalArgumentException("Non esiste un prodotto del genere! Inserire almeno 3 caratteri!");
        }

        // Controllo sulla validita' della datan di scadenza del prodotto
        LocalDate currentDate = LocalDate.now();
        java.util.Date currentDateAsDate =  java.util.Date.from(currentDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        if (scadenza.before(currentDateAsDate)) {
            throw new IllegalArgumentException("La data inserita deve essere successiva alla data attuale!");
        }

        // Controllo della consistenza del parametro quantita'
        if (quantita <= 0) {
            throw new IllegalArgumentException("La quantita' deve essere almeno positiva!");
        }

        // Controllo della consistenza del parametro limite
        if (limite <= 0) {
            throw new IllegalArgumentException("La soglia limite deve essere almeno positiva!");
        }
    }

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
}
