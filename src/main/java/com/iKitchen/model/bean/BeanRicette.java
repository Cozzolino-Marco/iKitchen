package com.iKitchen.model.bean;

import java.util.ArrayList;
import java.util.List;

public class BeanRicette {

    // Variabili
    List<BeanRicetta> listRicetta;

    public BeanRicette() {
        this.listRicetta = new ArrayList<>();
    }

    // Metodo per la restituzione della lista di ricette
    public List<BeanRicetta> getListRicetta() {
        return listRicetta;
    }

    // Metodo per l'aggiunta di una ricetta alla lista di ricette
    public void addRicetta(BeanRicetta beanRicetta){
        listRicetta.add(beanRicetta);
    }
}
