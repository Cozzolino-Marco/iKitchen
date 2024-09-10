package com.ikitchen.model.bean;

import com.ikitchen.model.domain.Role;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CredentialsBean {

    // Variabili
    private String username;
    private String password;
    private String ripetiPassword;
    private Role role;
    private String nome;
    private String cognome;

    // Regex per validare un'email
    private String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    Pattern pattern = Pattern.compile(EMAIL_REGEX);

    // Usato dal controller grafico della registrazione per popolare i dati utente
    public CredentialsBean() {}

    // Usato dal controller grafico di login per passare i dati al suo applicativo
    public CredentialsBean(String username, String password) {

        // Matcher per confrontare l'email con il pattern
        Matcher matcher = pattern.matcher(username);

        // Eccezione per validità email
        if (!matcher.matches()) {
            // Lancia un'eccezione se l'email non è valida
            throw new IllegalArgumentException("L'email fornita non è valida: " + username);
        }

        // Se l'email è valida, viene assegnato allo username
        this.username = username;

        // Assegnazione della password
        this.password = password;
    }

    // Getter
    public String getNome() {
        return nome;
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

        // Matcher per confrontare l'email con il pattern
        Matcher matcher = pattern.matcher(username);

        // Eccezione per validità email
        if (!matcher.matches()) {
            // Lancia un'eccezione se l'email non è valida
            throw new IllegalArgumentException("L'email fornita non è valida: " + username);
        }

        // Se l'email è valida, viene assegnato allo username
        this.username = username;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setRipetiPassword(String ripetiPassword) {
        this.ripetiPassword = ripetiPassword;
    }
}