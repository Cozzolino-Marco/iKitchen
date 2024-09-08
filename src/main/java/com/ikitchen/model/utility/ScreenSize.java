package com.ikitchen.model.utility;

import java.awt.Toolkit;

public class ScreenSize {

    // Costruttore privato per impedire l'istanza della classe
    private ScreenSize() {}

    // Per tenere traccia dello stato attuale dell'interfaccia grafica
    private static int current_gui = 0;

    // Standard screen size
    private static int width_gui = 330;
    private static int height_gui = 600;

    // Dimensione stimata della barra delle applicazioni del desktop
    private static final int DOCK_HEIGHT = 70;

    // Usato dal login grafico per ottenere la GUI attuale
    public static int getGUI() {
        return current_gui;
    }

    // Usato dal login grafico per cambiare la GUI
    public static void changeGUI() {
        if (current_gui == 0) {
            // Ottieni le dimensioni dello schermo del computer
            Toolkit toolkit = Toolkit.getDefaultToolkit();
            java.awt.Dimension screenSize = toolkit.getScreenSize();
            width_gui = screenSize.width;
            height_gui = screenSize.height - DOCK_HEIGHT;
            current_gui = 1;
        } else {
            // Ripristina le dimensioni predefinite
            current_gui = 0;
            width_gui = 330;
            height_gui = 600;
        }
    }

    // Getter di altezza e larghezza
    public static double getSceneWidth(){
        return width_gui;
    }
    public static double getSceneHeight(){
        return height_gui;
    }
}