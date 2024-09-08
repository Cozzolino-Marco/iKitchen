package com.ikitchen.model.bean;

import java.util.ArrayList;
import java.util.List;

public class BeanRicette {

    // Variabili
    private String categoria;
    private String provenienza;
    private String filtraggio;
    private String storage;
    private List<BeanRicetta> listRicette;

    // Costruttore bean per restituzione lista
    public BeanRicette() {
        this.listRicette = new ArrayList<>();
    }

    // Informazioni per il metodo "listaRicette"
    public BeanRicette(String categoria, String provenienza, String filtraggio, String storage) {
        this.categoria = categoria;
        this.provenienza = provenienza;
        this.filtraggio = filtraggio;
        this.storage = storage;
        this.listRicette = new ArrayList<>();
    }

    // Metodi getter per le variabili categoria, provenienza, filtraggio e storage
    public String getCategoria() {
        return categoria;
    }
    public String getProvenienza() {
        return provenienza;
    }
    public String getFiltraggio() {
        return filtraggio;
    }
    public String getStorage() {
        return storage;
    }

    // Metodi setter per le variabili categoria, provenienza, filtraggio e storage
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
    public void setProvenienza(String provenienza) {
        this.provenienza = provenienza;
    }
    public void setFiltraggio(String filtraggio) {
        this.filtraggio = filtraggio;
    }
    public void setStorage(String storage) {
        this.storage = storage;
    }

    // Metodo per la restituzione della lista di ricette
    public List<BeanRicetta> getListRicette() {
        return listRicette;
    }

    // Metodo per l'aggiunta di una ricetta alla lista di ricette
    public void addRicetta(BeanRicetta beanRicetta){
        listRicette.add(beanRicetta);
    }
}