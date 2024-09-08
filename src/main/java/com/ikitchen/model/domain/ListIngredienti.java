package com.ikitchen.model.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ListIngredienti implements Serializable {
    private List<Ingrediente> listaIngredienti = new ArrayList<>();

    public void addIngrediente(Ingrediente ingrediente) {
        this.listaIngredienti.add(ingrediente);
    }

    public List<Ingrediente> getListaIngredienti() {
        return listaIngredienti;
    }
}