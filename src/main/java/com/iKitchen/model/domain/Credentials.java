package com.iKitchen.model.domain;

public class Credentials {

    // Variabili
    static String username;
    static String password;
    static String ripetiPassword;
    static Role role;
    static String nome;
    static String cognome;

    // Costruttore usato dal DAO per il recupero del nome
    public Credentials () {}

    // Usato dal controller applicativo del login per passare i dati al DAO
    public Credentials(String username, String password) {
        Credentials.username = username;
        Credentials.password = password;
    }

    // Usato dal controller applicativo della registrazione per passare i dati al DAO
    public Credentials(String username, String password, String ripetiPassword, Role role, String nome, String cognome) {
        Credentials.username = username;
        Credentials.password = password;
        Credentials.ripetiPassword = ripetiPassword;
        Credentials.role = role;
        Credentials.nome = nome;
        Credentials.cognome = cognome;
    }

    // Getter
    public static String getNome() {
        return nome;
    }
    public static String getCognome() {
        return cognome;
    }
    public static Role getRole() {
        return role;
    }
    public static String getUsername() {
        return username;
    }
    public static String getPassword() {
        return password;
    }
    public static String getRipetiPassword() {
        return ripetiPassword;
    }

    // Setter
    public void setNome(String nome) {
        this.nome = nome;
    }
    public void setCognome(String cognome) {
        this.cognome = cognome;
    }
    public static void setRole(Role role){
        Credentials.role = role;
    }
    public static void setUsername(String username) {
        Credentials.username = username;
    }
    public static void setPassword(String password) {
        Credentials.password = password;
    }
    public void setRipetiPassword(String ripetiPassword) {
        this.ripetiPassword = ripetiPassword;
    }
}