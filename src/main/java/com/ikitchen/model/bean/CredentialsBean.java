package com.ikitchen.model.bean;

import com.ikitchen.model.domain.Role;

public class CredentialsBean {

    // Variabili
    private String username;
    private String password;
    private String ripetiPassword;
    private Role role;
    private String nome;
    private String cognome;

    // Usato dal controller grafico della registrazione per popolare i dati utente
    public CredentialsBean() {}

    // Usato dal controller grafico di login per passare i dati al suo applicativo
    public CredentialsBean(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Getter
    public String getNome() {
        return nome;
    }
    public String getCognome() {
        return cognome;
    }
    public Role getRole() {
        return role;
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
        this.username = username;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setRipetiPassword(String ripetiPassword) {
        this.ripetiPassword = ripetiPassword;
    }
}