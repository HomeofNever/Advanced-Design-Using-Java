package com.luox6.conway.gui;

public class Main {
    public static void main(String[] args) {
        // Controller
        GUIController guiController = new GUIController();
        // Init View
        GUIViewer GUIViewer = new GUIViewer(guiController);
        guiController.setGuiViewer(GUIViewer);

        guiController.start();
    }
}
