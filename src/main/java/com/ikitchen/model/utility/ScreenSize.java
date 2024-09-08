package com.ikitchen.model.utility;

import java.awt.Toolkit;

public class ScreenSize {

    // Costruttore privato per impedire l'istanza della classe
    private ScreenSize() {}

    // Per tenere traccia dello stato attuale dell'interfaccia grafica
    private static int currentGui = 0;

    // Standard screen size
    private static int widthGui = 330;
    private static int heightGui = 600;

    // Dimensione stimata della barra delle applicazioni del desktop
    private static final int DOCK_HEIGHT = 70;

    // Usato dal login grafico per ottenere la GUI attuale
    public static int getGUI() {
        return currentGui;
    }

    // Usato dal login grafico per cambiare la GUI
    public static void changeGUI() {
        if (currentGui == 0) {
            // Ottieni le dimensioni dello schermo del computer
            Toolkit toolkit = Toolkit.getDefaultToolkit();
            java.awt.Dimension screenSize = toolkit.getScreenSize();
            widthGui = screenSize.width;
            heightGui = screenSize.height - DOCK_HEIGHT;
            currentGui = 1;
        } else {
            // Ripristina le dimensioni predefinite
            currentGui = 0;
            widthGui = 330;
            heightGui = 600;
        }
    }

    // Getter di altezza e larghezza
    public static double getSceneWidth(){
        return widthGui;
    }
    public static double getSceneHeight(){
        return heightGui;
    }
}