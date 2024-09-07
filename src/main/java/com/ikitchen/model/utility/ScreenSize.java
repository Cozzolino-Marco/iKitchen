package com.ikitchen.model.utility;

import java.awt.Toolkit;

public class ScreenSize {

    private static int GUI = 0;

    // Standard screen size
    public static int WIDTH_GUI1 = 330;
    public static int HEIGHT_GUI1 = 600;

    // Dimensione stimata della barra delle applicazioni del desktop
    private static final int DOCK_HEIGHT = 70;

    // Usato dal login grafico per ottenere la GUI attuale
    public static int getGUI() {
        return GUI;
    }

    // Usato dal login grafico per cambiare la GUI
    public static void changeGUI() {
        if (GUI == 0) {
            // Ottieni le dimensioni dello schermo del computer
            Toolkit toolkit = Toolkit.getDefaultToolkit();
            java.awt.Dimension screenSize = toolkit.getScreenSize();
            WIDTH_GUI1 = screenSize.width;
            HEIGHT_GUI1 = screenSize.height - DOCK_HEIGHT;
            GUI = 1;
        } else {
            // Ripristina le dimensioni predefinite
            GUI = 0;
            WIDTH_GUI1 = 330;
            HEIGHT_GUI1 = 600;
        }
    }

    public static double getSceneWidth(){
        return WIDTH_GUI1;
    }

    public static double getSceneHeight(){
        return HEIGHT_GUI1;
    }
}