package com.iKitchen.model.bean;

import com.iKitchen.model.domain.Role;

public class CredentialsBean {

    // Variabili
    static String username;
    static String password;
    static String ripetiPassword;
    static Role role;
    static String nome;
    static String cognome;

    // Usato dal controller grafico della registrazione per popolare i dati utente
    public CredentialsBean() {}

    // Usato dal controller grafico di login per passare i dati al suo applicativo
    public CredentialsBean(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Usato dai controller grafici di ottieni ricetta e utente controller
    public CredentialsBean(String username) {
        this.username = username;
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
    public void setRole(Role role){
        this.role = role;
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
}