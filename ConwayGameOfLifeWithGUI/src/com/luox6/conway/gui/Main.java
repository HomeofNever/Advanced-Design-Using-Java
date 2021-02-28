package com.luox6.conway.gui;

public class Main {
    /**
     * This main method is main for test purpose.
     * @param args
     */
    public static void main(String[] args) {
        init();
    }

    /**
     * Main application entry, for commandline hook
     */
    public static void init() {
        // Controller
        GUIController guiController = new GUIController();
        // View
        GUIViewer GUIViewer = new GUIViewer(guiController);
        guiController.setGuiViewer(GUIViewer);

        // Launch GUI
        guiController.start();
    }
}
