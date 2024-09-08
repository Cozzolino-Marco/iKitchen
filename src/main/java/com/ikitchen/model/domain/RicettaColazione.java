package com.ikitchen.model.domain;

public class RicettaColazione extends Ricetta {

    // Attributi
    private String tipoColazione;           // Esempio: "Italiana", "Continentale", "Inglese", "Americana"
    private int numeroPortate;              // Esempio: "1 per colazione semplice", "3 per colazione completa"
    private int consistenzaEnergetica;      // Esempio: "250 per una colazione leggera", "600 per una colazione sostanziosa"

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