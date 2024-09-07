package com.ikitchen.model.domain;

public class Credentials {

    // Variabili
    private static String username;
    private static String password;
    private static String ripetiPassword;
    private static Role role;
    private static String nome;
    private static String cognome;

    // Costruttore privato
    private Credentials () {}

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
    public static void setNome(String nome) {
        Credentials.nome = nome;
    }
    public static void setCognome(String cognome) {
        Credentials.cognome = cognome;
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
    public static void setRipetiPassword(String ripetiPassword) {
        Credentials.ripetiPassword = ripetiPassword;
    }
}