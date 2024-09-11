package com.ikitchen.controller;

import java.io.IOException;

// Interfaccia per definire il comportamento grafico di un controller legato alla visualizzazione delle categorie di ricette
public interface GraphicController {

    // Metodo per mostrare la pagina della scelta delle categorie.
    void categorieView() throws IOException;

    /**
     * Ogni implementazione caricherà la rispettiva grafica in base
     * alla versione dell'interfaccia utente.
     */
}