package com.luox6.epidemic;

import com.luox6.epidemic.gui.GUIController;
import com.luox6.epidemic.gui.GUIViewer;

/**
 * Main Application Entry
 */
public class Main {

    /**
     * Application entry
     * @param args not taking args for now
     */
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
