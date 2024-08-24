package com.iKitchen.model.domain;

import java.util.ArrayList;
import java.util.List;

public class ListRicetta {
    List<Ricetta> listaRicetta = new ArrayList<>();

    public void addRicetta(Ricetta ricetta) {
        this.listaRicetta.add(ricetta);
    }

    public List<Ricetta> getListaRicetta(){
        return listaRicetta;
    }
}