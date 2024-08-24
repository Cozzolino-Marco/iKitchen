package com.iKitchen.model.bean;

public class CredentialsBean {
    String username;
    String password;

    public CredentialsBean(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Getter
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
}

