package com.ikitchen.model.bean;

import java.util.ArrayList;
import java.util.List;

public class BeanIngredienti {

    // Variabili
    private String username;
    private final List<BeanIngrediente> listIngredienti;

    // Costruttore bean per restituzione lista
    public BeanIngredienti() {
        this.listIngredienti = new ArrayList<>();
    }

    // Costruttore con informazioni per il metodo "listaIngredienti"
    public BeanIngredienti(String username) {
        this.username = username;
        this.listIngredienti = new ArrayList<>();
    }

    // Metodo per la restituzione della lista di ingredienti
    public List<BeanIngrediente> getListIngredienti() {
        return listIngredienti;
    }

    // Metodo per l'aggiunta di un ingrediente alla lista di ingredienti
    public void addIngrediente(BeanIngrediente beanIngrediente) {
        listIngredienti.add(beanIngrediente);
    }
}