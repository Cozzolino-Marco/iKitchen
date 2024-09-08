package com.ikitchen.model.domain;

public class RicettaDolci extends Ricetta {

    // Attributi
    private String tipoDolce;               // Esempio: "torta", "biscotti", "mousse"
    private String ingredientiSpeciali;     // Esempio: "cioccolato", "frutta", "noci"
    private String stileServizio;           // Esempio: "a fette", "in porzioni individuali", "al cucchiaio"

    // Getter
    public String getTipoDolce() {
        return tipoDolce;
    }
    public String getIngredientiSpeciali() {
        return ingredientiSpeciali;
    }
    public String getStileServizio() {
        return stileServizio;
    }

    // Setter
    public void setTipoDolce(String tipoDolce) {
        this.tipoDolce = tipoDolce;
    }
    public void setIngredientiSpeciali(String ingredientiSpeciali) {
        this.ingredientiSpeciali = ingredientiSpeciali;
    }
    public void setStileServizio(String stileServizio) {
        this.stileServizio = stileServizio;
    }

    @Override
    public String getCategoria() {
        return "Dolci";
    }
}