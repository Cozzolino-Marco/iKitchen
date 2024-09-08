package com.ikitchen.model.domain;

public class RicettaPastoVeloce extends Ricetta {

    // Attributi
    private String tipoPiatto;              // Esempio: "panino", "insalata", "wrap"
    private String tipoAccompagnamento;     // Esempio: "patatine", "salsa", "verdure grigliate"
    private String livelloSpezie;           // Esempio: "basso", "medo", "piccante"

    // Getter
    public String getTipoPiatto() {
        return tipoPiatto;
    }
    public String getTipoAccompagnamento() {
        return tipoAccompagnamento;
    }
    public String getLivelloSpezie() {
        return livelloSpezie;
    }

    // Setter
    public void setTipoPiatto(String tipoPiatto) {
        this.tipoPiatto = tipoPiatto;
    }
    public void setTipoAccompagnamento(String tipoAccompagnamento) {
        this.tipoAccompagnamento = tipoAccompagnamento;
    }
    public void setLivelloSpezie(String livelloSpezie) {
        this.livelloSpezie = livelloSpezie;
    }

    @Override
    public String getCategoria() {
        return "Pasto veloce";
    }
}