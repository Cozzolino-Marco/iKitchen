package com.ikitchen.model.utility;

import com.ikitchen.model.domain.Role;

public class Credentials {

    // Variabili
    private static String username;
    private static String password;
    private static Role role;
    private static String nome;
    private static String cognome;

    // Costruttore privato per impedire l'istanza della classe
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

    // Setter
    public static void setNome(String nome) {
        Credentials.nome = nome;
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
}