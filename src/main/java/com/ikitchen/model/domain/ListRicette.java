package com.ikitchen.model.domain;

import java.util.ArrayList;
import java.util.List;

public class ListRicette {

    // Attributo
    private List<Ricetta> listaRicette = new ArrayList<>();

    // Metodo per aggiungere una ricetta alla lista
    public void addRicetta(Ricetta ricetta) {
        this.listaRicette.add(ricetta);
    }

    // Metodo per ottenere la lista delle ricette
    public List<Ricetta> getListaRicette(){
        return listaRicette;
    }
}