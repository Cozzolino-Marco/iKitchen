package com.iKitchen.model.domain;

public enum Role {
    UTENTE(1),
    CHEF(2);

    private final int id;

    Role(int id) {
        this.id = id;
    }

    public static Role fromInt(int id) {
        for (Role type : values()) {
            if (type.getId() == id) {
                return type;
            }
        }
        return null;
    }

    public int getId() {
        return id;
    }
}