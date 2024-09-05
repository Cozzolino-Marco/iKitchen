package com.iKitchen.model.utility;

public class ScreenSize {

    private static int GUI = 0;

    // Standard screen size
    public static int WIDTH_GUI1 = 330;
    public static int HEIGHT_GUI1 = 600;

    public static int getGUI() {
        return GUI;
    }

    public static void setGUI(int GUI) {
        ScreenSize.GUI = GUI;
    }

    public static void changeGUI() {
        if (GUI == 0) {
            GUI = 1;
            WIDTH_GUI1 = 1200;
            HEIGHT_GUI1 = 700;
        } else {
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
