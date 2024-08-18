package com.iKitchen.model.domain;

import java.sql.Date;

public class Profile {
    static String username;
    static String nome;
    static String cognome;
    static Date dataNascita;

    public Profile(String username, String nome, String cognome, Date dataNAscita) {
        Profile.username = username;
        Profile.nome = nome;
        Profile.cognome = cognome;
        Profile.dataNascita = dataNAscita;
    }

    /*
    public static void summaEvento(int num){
        numEventi+=num;
    }

    public static void sottraiEvento(int num){
        if (numEventi-num < 0) {
            numEventi=0;
        } else {
            numEventi-=num;
        }

    }
     */
}