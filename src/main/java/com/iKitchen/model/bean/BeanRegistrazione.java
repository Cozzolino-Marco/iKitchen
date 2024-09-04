package com.iKitchen.model.bean;

public class BeanRegistrazione {

    // Variabili
    private String nome;
    private String cognome;
    private String ruolo;
    private String username;
    private String password;
    private String ripetiPassword;

    // Costruttore vuoto per il controller grafico
    public BeanRegistrazione() {}

    // Per mandare i dati dal controller grafico a quello applicativo
    public BeanRegistrazione (String nome, String cognome, String ruolo, String username, String password, String ripetiPassword) {

        // Controllo della consistenza del nome
        if (nome.length() < 3) {
            throw new IllegalArgumentException("Non esiste un nome del genere! Inserire almeno 3 caratteri!");
        }

        // Controllo della consistenza del cognome
        if (cognome.length() < 3) {
            throw new IllegalArgumentException("Non esiste un cognome del genere! Inserire almeno 3 caratteri!");
        }

        // Controllo della validità del parametro ruolo
        if (!ruolo.equals("utenteDomestico") && !ruolo.equals("chef")) {
            throw new IllegalArgumentException("Il ruolo non è valido!");
        }

        // Assegnazioni
        this.nome = nome;
        this.cognome = cognome;
        this.ruolo = ruolo;
        this.username = username;
        this.password = password;
        this.ripetiPassword = ripetiPassword;
    }

    // Setter
    public void setNome(String nome) {
        this.nome = nome;
    }
    public void setCognome(String cognome) {
        this.cognome = cognome;
    }
    public void setRuolo(String ruolo) {
        this.ruolo = ruolo;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setRipetiPassword(String ripetiPassword) {
        this.ripetiPassword = ripetiPassword;
    }

    // Getter
    public String getNome() {
        return nome;
    }
    public String getCognome() {
        return cognome;
    }
    public String getRuolo() {
        return ruolo;
    }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public String getRipetiPassword() {
        return ripetiPassword;
    }
}
