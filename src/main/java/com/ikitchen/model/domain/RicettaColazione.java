package com.ikitchen.model.domain;

public class RicettaColazione extends Ricetta {
    private String tipoColazione;
    private int numeroPortate;
    private int consistenzaEnergetica;

    // Costruttore generico
    public RicettaColazione(String tipoColazione, int numeroPortate, int consistenzaEnergetica) {
        this.tipoColazione = tipoColazione;
        this.numeroPortate = numeroPortate;
        this.consistenzaEnergetica = consistenzaEnergetica;
    }

    @Override
    public String getCategoria() {
        return "Colazione";
    }
}