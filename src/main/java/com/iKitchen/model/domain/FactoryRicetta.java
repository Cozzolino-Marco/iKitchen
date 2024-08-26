package com.iKitchen.model.domain;

public class FactoryRicetta {

    public Ricetta createRicetta(String categoria) {
        switch (categoria) {
            case "Colazione":
                return new RicettaColazione();
            case "Pasto veloce":
                return new RicettaPastoVeloce();
            case "Bevande":
                return new RicettaBevande();
            case "Primi piatti":
                return new RicettaPrimiPiatti();
            case "Secondi piatti":
                return new RicettaSecondiPiatti();
            case "Contorni":
                return new RicettaContorni();
            case "Dolci":
                return new RicettaDolci();
            default:
                throw new IllegalArgumentException("Categoria non valida: " + categoria);
        }
    }

    public Ricetta createRicettaColazione() {
        return new RicettaColazione();
    }

    public Ricetta createRicettaPastoVeloce() {
        return new RicettaPastoVeloce();
    }

    public Ricetta createRicettaBevande() {
        return new RicettaBevande();
    }

    public Ricetta createRicettaPrimiPiatti() {
        return new RicettaPrimiPiatti();
    }

    public Ricetta createRicettaSecondiPiatti() {
        return new RicettaSecondiPiatti();
    }

    public Ricetta createRicettaContorni() {
        return new RicettaContorni();
    }

    public Ricetta createRicettaDolci() {
        return new RicettaDolci();
    }
}
