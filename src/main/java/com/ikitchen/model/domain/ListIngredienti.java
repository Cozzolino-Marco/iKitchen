package com.ikitchen.model.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ListIngredienti implements Serializable {

    // Attributo
    private List<Ingrediente> listaIngredienti = new ArrayList<>();

    // Metodo per aggiungere un ingrediente alla lista
    public void addIngrediente(Ingrediente ingrediente) {
        this.listaIngredienti.add(ingrediente);
    }

    // Metodo per ottenere la lista degli ingredienti
    public List<Ingrediente> getListaIngredienti() {
        return listaIngredienti;
    }
}