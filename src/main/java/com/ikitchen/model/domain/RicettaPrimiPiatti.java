package com.ikitchen.model.domain;

public class RicettaPrimiPiatti extends Ricetta {

    // Attributi
    private String tipoPasta;               // Esempio: "spaghetti", "penne", "risotto"
    private String sugo;                    // Esempio: "pomodoro", "carbonara", "ragù"
    private String tipoAroma;               // Esempio: "parmigiano", "peperoncino", "erbe aromatiche"

    // Getter
    public String getTipoPasta() {
        return tipoPasta;
    }
    public String getSugo() {
        return sugo;
    }
    public String getTipoAroma() {
        return tipoAroma;
    }

    // Setter
    public void setTipoPasta(String tipoPasta) {
        this.tipoPasta = tipoPasta;
    }
    public void setSugo(String sugo) {
        this.sugo = sugo;
    }
    public void setTipoAroma(String tipoCondimento) {
        this.tipoAroma = tipoAroma;
    }

    @Override
    public String getCategoria() {
        return "Primi piatti";
    }
}