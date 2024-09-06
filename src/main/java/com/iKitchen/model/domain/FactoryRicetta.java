package com.iKitchen.model.domain;

public class FactoryRicetta {

    public Ricetta createRicetta(String categoria) {
        switch (categoria.toLowerCase()) {
            case "colazione":
                return new RicettaColazione();
            case "pasto veloce":
                return new RicettaPastoVeloce();
            case "bevande":
                return new RicettaBevande();
            case "primi piatti":
                return new RicettaPrimiPiatti();
            case "secondi piatti":
                return new RicettaSecondiPiatti();
            case "contorni":
                return new RicettaContorni();
            case "dolci":
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
