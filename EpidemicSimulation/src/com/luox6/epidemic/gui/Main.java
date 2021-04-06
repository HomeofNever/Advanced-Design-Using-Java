package com.luox6.epidemic.gui;

public class Main {

    public static void main(String[] args) {
        // Controller
        GUIController guiController = new GUIController();
        // View
        GUIViewer GUIViewer = new GUIViewer(guiController);
        guiController.setGuiViewer(GUIViewer);

        // Launch GUI
        guiController.start();
    }
}
