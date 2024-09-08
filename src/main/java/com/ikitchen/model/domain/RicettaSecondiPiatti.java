package com.ikitchen.model.domain;

public class RicettaSecondiPiatti extends Ricetta {

    // Attributi
    private String tipoCarne;               // Esempio: "pollo", "manzo", "maiale"
    private String salsa;                   // Esempio: "bbq", "soia", "crema"
    private String metodoCottura;           // Esempio: "grigliato", "arrosto", "saltato"

    // Getter
    public String getTipoCarne() {
        return tipoCarne;
    }
    public String getSalsa() {
        return salsa;
    }
    public String getMetodoCottura() {
        return metodoCottura;
    }

    // Setter
    public void setTipoCarne(String tipoCarne) {
        this.tipoCarne = tipoCarne;
    }
    public void setSalsa(String salsa) {
        this.salsa = salsa;
    }
    public void setMetodoCottura(String metodoCottura) {
        this.metodoCottura = metodoCottura;
    }

    @Override
    public String getCategoria() {
        return "Secondi piatti";
    }
}