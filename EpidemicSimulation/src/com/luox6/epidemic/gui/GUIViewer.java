package com.luox6.epidemic.gui;

import com.luox6.epidemic.gui.components.*;

import javax.swing.*;
import java.awt.*;

public class GUIViewer extends JFrame {
    /**
     * Reference of the controller, for notification purpose
     */
    protected GUIController guiController;

    /**
     * Top Menu Bar section
     */
    protected TopMenuBar topMenuBar;

    /**
     * Lower bar of the status
     */
    protected StatusPanel statusPanel;

    /**
     * Panel buttons on the left side of the application
     */
    protected VisualActionPanel visualActionPanel;

    /**
     * Main game board
     */
    protected GraphPanel graphPanel;

    /**
     * User Configuration panel
     */
    protected ConfigurationPanel configurationPanel;

    /**
     * Main layout panel
     */
    private JPanel mainPanel;

    public GUIViewer(GUIController guiController) {
        // Creating instance of JFrame
        super("Epidemic Simulation");
        this.guiController = guiController;
        setSize(1280, 720);
        // Maximize Window
        setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH );
        setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
        // Init Layout
        init();
    }

    /**
     * Initialization subcomponents and set it to the corresponded location of the layout
     */
    private void init() {
        mainPanel = new JPanel(new BorderLayout());
        add(mainPanel);

        // Set their locations
        setFrame();
        setPageStart();
        setCenter();
        setPageLeft();
        setPageEnd();

        configurationPanel = new ConfigurationPanel(guiController);
    }


    /* The method below are layout settings */
    private void setPageStart() { }

    private void setPageLeft() {
        visualActionPanel = new VisualActionPanel(guiController);
        mainPanel.add(visualActionPanel, BorderLayout.LINE_START);
    }

    private void setPageEnd() {
        statusPanel = new StatusPanel();
        mainPanel.add(statusPanel, BorderLayout.PAGE_END);
    }

    private void setFrame() {
        topMenuBar = new TopMenuBar(guiController);
        setJMenuBar(topMenuBar);
    }

    private void setCenter() {
        graphPanel = new GraphPanel();
        mainPanel.add(graphPanel, BorderLayout.CENTER);
    }
}
