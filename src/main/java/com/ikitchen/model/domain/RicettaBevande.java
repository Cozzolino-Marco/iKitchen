package com.ikitchen.model.domain;

public class RicettaBevande extends Ricetta {

    // Attributi
    private String tipoBevanda;             // Esempio: "cocktail", "smoothie", "succhi"
    private String ingredientiBase;         // Esempio: "frutta", "latte", "acqua"
    private String temperaturaServizio;     // Esempio: "caldo", "freddo"

    // Getter per i nuovi attributi
    public String getTipoBevanda() {
        return tipoBevanda;
    }
    public String getIngredientiBase() {
        return ingredientiBase;
    }
    public String getTemperaturaServizio() {
        return temperaturaServizio;
    }

    // Setter per i nuovi attributi
    public void setTipoBevanda(String tipoBevanda) {
        this.tipoBevanda = tipoBevanda;
    }
    public void setIngredientiBase(String ingredientiBase) {
        this.ingredientiBase = ingredientiBase;
    }
    public void setTemperaturaServizio(String temperaturaServizio) {
        this.temperaturaServizio = temperaturaServizio;
    }

    @Override
    public String getCategoria() {
        return "Bevande";
    }
}