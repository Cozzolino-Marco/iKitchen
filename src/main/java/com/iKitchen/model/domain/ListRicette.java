package com.iKitchen.model.domain;

import java.util.ArrayList;
import java.util.List;

public class ListRicette {
    List<Ricetta> listaRicette = new ArrayList<>();

    public void addRicetta(Ricetta ricetta) {
        this.listaRicette.add(ricetta);
    }

    public List<Ricetta> getListaRicette(){
        return listaRicette;
    }
}