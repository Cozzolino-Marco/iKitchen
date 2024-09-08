package com.ikitchen.model.domain;

public class RicettaContorni extends Ricetta {

    // Attributi
    private String tipoVegetale;            // Esempio: "patate", "verdure", "funghi"
    private String metodoCottura;           // Esempio: "bollito", "grigliato", "al forno"
    private String tipoCondimento;          // Esempio: "olio", "aceto", "salsa di soia"

    // Getter
    public String getTipoVegetale() {
        return tipoVegetale;
    }
    public String getMetodoCottura() {
        return metodoCottura;
    }
    public String getTipoCondimento() {
        return tipoCondimento;
    }

    // Setter
    public void setTipoVegetale(String tipoVegetale) {
        this.tipoVegetale = tipoVegetale;
    }
    public void setMetodoCottura(String metodoCottura) {
        this.metodoCottura = metodoCottura;
    }
    public void setTipoCondimento(String tipoCondimento) {
        this.tipoCondimento = tipoCondimento;
    }

    @Override
    public String getCategoria() {
        return "Contorni";
    }
}