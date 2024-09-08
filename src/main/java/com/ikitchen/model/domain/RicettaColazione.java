package com.ikitchen.model.domain;

public class RicettaColazione extends Ricetta {

    // Variabili
    private String tipoColazione;
    private int numeroPortate;
    private int consistenzaEnergetica;

    // Getter
    public String getTipoColazione() {
        return tipoColazione;
    }
    public int getNumeroPortate() {
        return numeroPortate;
    }
    public int getConsistenzaEnergetica() {
        return consistenzaEnergetica;
    }

    // Setter
    public void setTipoColazione(String tipoColazione) {
        this.tipoColazione = tipoColazione;
    }
    public void setNumeroPortate(int numeroPortate) {
        this.numeroPortate = numeroPortate;
    }
    public void setConsistenzaEnergetica(int consistenzaEnergetica) {
        this.consistenzaEnergetica = consistenzaEnergetica;
    }

    @Override
    public String getCategoria() {
        return "Colazione";
    }
}